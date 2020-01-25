package jken.site.modules.core.entity;

import jken.site.support.data.Lockedable;
import jken.site.support.data.jpa.CorpableEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tbl_role")
public class Role extends CorpableEntity<User, Long> implements Lockedable {

    @NotNull
    @Size(max = 20)
    @Column(length = 63)
    private String name;

    @NotNull
    @Size(max = 20)
    @Column(length = 31)
    private String code;

    @Size(max = 1000)
    @Column(length = 1023)
    private String description;

    private boolean locked = false;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @ElementCollection
    @CollectionTable(name = "tbl_role_authority")
    private List<String> authorities = new ArrayList<String>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
