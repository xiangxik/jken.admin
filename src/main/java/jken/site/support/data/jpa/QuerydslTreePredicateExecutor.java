package jken.site.support.data.jpa;

import com.querydsl.core.types.Predicate;
import jken.site.support.data.Hierarchical;
import jken.site.support.data.TreeHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.querydsl.EntityPathResolver;

import javax.persistence.EntityManager;
import java.util.List;

public class QuerydslTreePredicateExecutor<T extends Hierarchical<T>> extends QuerydslEntityPredicateExecutor<T> implements HierarchicalQuerydslPredicateExecutor<T> {

    public QuerydslTreePredicateExecutor(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager, EntityPathResolver resolver, CrudMethodMetadata metadata) {
        super(entityInformation, entityManager, resolver, metadata);
    }

    @Override
    public List<T> findTree(Predicate predicate) {
        return TreeHelper.toTree(super.findAll(predicate));
    }

    @Override
    public List<T> findTree(Predicate predicate, Pageable pageable) {
        return TreeHelper.toTree(super.findAll());
    }
}
