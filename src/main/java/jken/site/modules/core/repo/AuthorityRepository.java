package jken.site.modules.core.repo;

import com.querydsl.core.types.dsl.StringExpression;
import jken.site.modules.core.entity.Authority;
import jken.site.modules.core.entity.QAuthority;
import jken.site.support.data.jpa.QuerydslEntityRepository;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface AuthorityRepository extends QuerydslEntityRepository<Authority, Long>, QuerydslBinderCustomizer<QAuthority> {
    @Override
    default void customize(QuerydslBindings querydslBindings, QAuthority qAuthority) {
        querydslBindings.bind(qAuthority.name).first((StringExpression::contains));
    }
}
