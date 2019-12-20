/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-20T22:23:11.869+08:00
 *
 */

package jken.site.support.data.jpa;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BeanPath;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import jken.site.support.data.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

public class EntityRepositoryImpl<T, I extends Serializable> extends QuerydslJpaRepository<T, I> implements EntityRepository<T, I> {
    private static final EntityPathResolver DEFAULT_ENTITY_PATH_RESOLVER = SimpleEntityPathResolver.INSTANCE;
    private final EntityPath<T> path;
    private final PathBuilder<T> builder;
    private final Querydsl querydsl;
    private final JpaEntityInformation<T, I> entityInformation;
    private final EntityManager entityManager;
    private final CorpDetection corpDetection;

    public EntityRepositoryImpl(CorpDetection corpDetection, JpaEntityInformation<T, I> entityInformation, EntityManager entityManager) {
        this(corpDetection, entityInformation, entityManager, DEFAULT_ENTITY_PATH_RESOLVER);
    }

    public EntityRepositoryImpl(CorpDetection corpDetection, JpaEntityInformation<T, I> entityInformation, EntityManager entityManager, EntityPathResolver resolver) {
        super(entityInformation, entityManager, resolver);

        this.entityInformation = entityInformation;
        this.entityManager = entityManager;
        this.corpDetection = corpDetection;

        this.path = resolver.createPath(entityInformation.getJavaType());
        this.builder = new PathBuilder<>(path.getType(), path.getMetadata());
        this.querydsl = new Querydsl(entityManager, builder);
    }

    @Override
    public <S extends T> S save(S entity) {
        if (entity instanceof Corpable && Strings.isNullOrEmpty(((Corpable) entity).getCorpCode())) {
            ((Corpable) entity).setCorpCode(corpDetection.getCurrentCorpCode());
        }
        return super.save(entity);
    }

    @Override
    public void delete(T entity) {
        if (entity instanceof Lockedable) {
            if (((Lockedable) entity).isLocked()) {
                throw new RuntimeException("cannot delete the locked entity.");
            }
        } else if (entity instanceof LogicDeleteable) {
            ((LogicDeleteable) entity).setDeleted(true);
            super.save(entity);
        } else {
            super.delete(entity);
        }
    }

    @Override
    protected JPQLQuery<?> createQuery(Predicate... predicate) {
        JPQLQuery<?> query = super.createQuery(predicate);
        makePredicate(query);
        query = genSortable(query);
        return query;
    }

    @Override
    protected JPQLQuery<?> createCountQuery(Predicate... predicate) {
        JPQLQuery<?> query = super.createCountQuery(predicate);
        makePredicate(query);
        return query;
    }

    @Override
    protected <S extends T> TypedQuery<S> getQuery(Specification<S> spec, Class<S> domainClass, Sort sort) {
        // 排序实体
        if (ClassUtils.isAssignable(Sortable.class, domainClass)) {

            Sort sortNoAsc = Sort.by(Sort.Direction.ASC, "sortNo");
            if (sort == null) {
                sort = sortNoAsc;
            } else {
                if (!Iterables.tryFind(sort, s -> s != null && Objects.equal(s.getProperty(), "sortNo")
                ).isPresent()) {
                    sort.and(sortNoAsc);
                }

            }
        }

        // 数据默认按创建时间倒序排序
        if (ClassUtils.isAssignable(AbstractAuditable.class, domainClass)) {
            Sort createdDateDesc = Sort.by(Sort.Direction.DESC, "createdDate");

            if (sort == null) {
                sort = createdDateDesc;
            } else {
                if (!Iterables.tryFind(sort, s -> s != null && Objects.equal(s.getProperty(), "createdDate")).isPresent()) {
                    sort.and(createdDateDesc);
                }
            }
        }
        return super.getQuery(makeSpecification(spec, domainClass), domainClass, sort);
    }

    @Override
    protected <S extends T> TypedQuery<Long> getCountQuery(Specification<S> spec, Class<S> domainClass) {
        return super.getCountQuery(makeSpecification(spec, domainClass), domainClass);
    }

    protected <S extends T> Specification<S> makeSpecification(Specification<S> spec, Class<S> domainClass) {
        if (ClassUtils.isAssignable(LogicDeleteable.class, domainClass)) {
            spec = Specification.where(spec).and((root, query, cb) -> cb.isFalse(root.get("deleted")));
        }
        if (ClassUtils.isAssignable(Corpable.class, domainClass)) {
            spec = Specification.where(spec).and((root, query, cb) -> cb.equal(root.get("corpCode"), corpDetection.getCurrentCorpCode()));
        }
        return spec;
    }

    protected void makePredicate(JPQLQuery<?> query) {
        Class<T> domainClass = getDomainClass();
        if (ClassUtils.isAssignable(LogicDeleteable.class, domainClass)) {
            BeanPath<T> beanPath = new BeanPath<>(domainClass, StringUtils.uncapitalize(domainClass.getSimpleName()));
            BooleanExpression deletedPropertyIsFalse = Expressions.booleanPath(PathMetadataFactory.forProperty(beanPath, "deleted"))
                    .isFalse();
            query.where(deletedPropertyIsFalse);
        }
        if (ClassUtils.isAssignable(Corpable.class, domainClass)) {
            BeanPath<T> beanPath = new BeanPath<>(domainClass, StringUtils.uncapitalize(domainClass.getSimpleName()));
            BooleanExpression equalsCurrentCorpCode = Expressions.comparablePath(String.class, PathMetadataFactory.forProperty(beanPath, "corpCode")).eq(corpDetection.getCurrentCorpCode());
            query.where(equalsCurrentCorpCode);
        }
    }

    protected JPQLQuery<?> genSortable(JPQLQuery<?> query) {
        Class<T> domainClass = getDomainClass();
        if (ClassUtils.isAssignable(Sortable.class, domainClass)) {
            Sort sortNoAsc = Sort.by(Sort.Direction.ASC, "sortNo");
            query = querydsl.applySorting(sortNoAsc, query);
        }
        if (ClassUtils.isAssignable(AbstractAuditable.class, domainClass)) {
            Sort createdDateDesc = Sort.by(Sort.Direction.DESC, "createdDate");
            query = querydsl.applySorting(createdDateDesc, query);
        }
        return query;
    }


    @Override
    public T createNew() {
        try {
            return getDomainClass().getDeclaredConstructor((Class<?>[]) null).newInstance((Object[]) null);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
