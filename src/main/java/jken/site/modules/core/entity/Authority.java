package jken.site.modules.core.entity;

import jken.site.support.data.Lockedable;
import jken.site.support.data.jpa.CorpableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "tbl_authority")
public class Authority extends CorpableEntity<User, Long> implements Lockedable {

    @NotNull
    @Size(max = 20)
    @Column(length = 63)
    private String name;

    @NotNull
    @Size(max = 20)
    @Column(length = 31)
    private String code;

    private boolean locked = true;

    @ManyToMany(mappedBy = "authorities")
    private Collection<Role> roles;

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
