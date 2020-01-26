package jken.site.support.data.jpa;

import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface QuerydslTreeRepository<T extends TreeEntity<T, ?, I>, I extends Serializable> extends TreeRepository<T, I>, QuerydslEntityRepository<T, I> {
}
