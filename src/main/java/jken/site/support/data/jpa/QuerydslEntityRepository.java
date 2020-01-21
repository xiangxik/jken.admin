package jken.site.support.data.jpa;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface QuerydslEntityRepository<T, I extends Serializable> extends EntityRepository<T, I>, QuerydslPredicateExecutor<T> {
}
