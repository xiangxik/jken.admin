package jken.site.modules.core.repo;

import com.querydsl.core.types.dsl.StringExpression;
import jken.site.modules.core.entity.QRole;
import jken.site.modules.core.entity.Role;
import jken.site.support.data.jpa.QuerydslEntityRepository;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface RoleRepository extends QuerydslEntityRepository<Role, Long>, QuerydslBinderCustomizer<QRole> {

    @Override
    default void customize(QuerydslBindings querydslBindings, QRole qRole) {
        querydslBindings.bind(qRole.name).first((StringExpression::contains));
    }
}
