package jken.site.support.data.jpa;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface QuerydslTreeRepository<T extends TreeEntity<T, U, I>, U, I extends Serializable> extends TreeRepository<T, U, I>, QuerydslPredicateExecutor<T> {
}
