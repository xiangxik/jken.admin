/*
 * Copyright (c) 2019.
 * @Link: http://jken.site
 * @Author: ken kong
 * @LastModified: 2019-12-21T21:19:36.165+08:00
 *
 */

package jken.site.support.data.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.*;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryComposition;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.core.support.RepositoryFragment;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.lang.reflect.Field;

public class DataRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable> extends JpaRepositoryFactoryBean<R, T, I> {
    public DataRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new CustomRepositoryFactory<>(entityManager);
    }

    private static class CustomRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory {

        public CustomRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
        }

        @Override
        protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
            if (isEntityRepository(information.getRepositoryInterface())) {
                if (isTreeRepository(information.getRepositoryInterface())) {
                    return new TreeRepositoryImpl(getEntityInformation(information.getDomainType()), entityManager);
                }
                return new EntityRepositoryImpl(getEntityInformation(information.getDomainType()), entityManager);
            }
            return super.getTargetRepository(information, entityManager);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            if (isEntityRepository(metadata.getRepositoryInterface())) {
                if (isTreeRepository(metadata.getRepositoryInterface())) {
                    return TreeRepositoryImpl.class;
                }
                return EntityRepositoryImpl.class;
            }
            return super.getRepositoryBaseClass(metadata);
        }

        @Override
        protected RepositoryComposition.RepositoryFragments getRepositoryFragments(RepositoryMetadata metadata) {
            RepositoryComposition.RepositoryFragments fragments = super.getRepositoryFragments(metadata);
            RepositoryComposition.RepositoryFragments transferred = RepositoryComposition.RepositoryFragments.empty();

            for (RepositoryFragment<?> fragment : fragments) {
                if (fragment.getImplementation().isPresent()) {
                    Object fragmentImpl = fragment.getImplementation().get();
                    if (fragmentImpl instanceof QuerydslJpaPredicateExecutor) {
                        EntityManager entityManager = (EntityManager) getPrivateFieldValue(fragmentImpl, "entityManager");
                        EntityPathResolver entityPathResolver = SimpleEntityPathResolver.INSTANCE;
                        CrudMethodMetadata crudMethodMetadata = (CrudMethodMetadata) getPrivateFieldValue(fragmentImpl, "metadata");

                        JpaEntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata.getDomainType());
                        fragment = RepositoryFragment.implemented(getTargetRepositoryViaReflection(QuerydslEntityPredicateExecutor.class, entityInformation, entityManager, entityPathResolver, crudMethodMetadata));
                    }
                }
                transferred = transferred.append(fragment);
            }

            return transferred;
        }

        private Object getPrivateFieldValue(Object obj, String name) {
            Field field = ReflectionUtils.findField(obj.getClass(), name);
            ReflectionUtils.makeAccessible(field);
            return ReflectionUtils.getField(field, obj);
        }

        private boolean isEntityRepository(Class<?> repositoryInterface) {
            return ClassUtils.isAssignable(EntityRepository.class, repositoryInterface);
        }

        private boolean isTreeRepository(Class<?> repositoryInterface) {
            return ClassUtils.isAssignable(TreeRepository.class, repositoryInterface);
        }
    }
}
