package jken.site.support.data.jpa;

import com.querydsl.core.types.Predicate;
import jken.site.support.data.Hierarchical;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface HierarchicalQuerydslPredicateExecutor<T extends Hierarchical<T>> extends QuerydslPredicateExecutor<T> {

    List<T> findTree(Predicate predicate);

    List<T> findTree(Predicate predicate, Pageable pageable);
}
