package br.pucminas.gisa.identity.application.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import br.pucminas.gisa.identity.common.typing.IdentityStatus;

@Entity
public class Profile extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IdentityStatus status;

    @OneToMany(mappedBy = "profile")
    private Set<User> users = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "profile_authority",
        joinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private Set<Authority> authorities = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public IdentityStatus getStatus() {
        return status;
    }

    public void setStatus(final IdentityStatus status) {
        this.status = status;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(final Set<User> users) {
        this.users = users;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(final Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final Profile profile)) {
            return false;
        }
        return name.equals(profile.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Profile{id=" + id +
               ", name=" + name +
               ", status=" + status +
               "}";
    }
}
