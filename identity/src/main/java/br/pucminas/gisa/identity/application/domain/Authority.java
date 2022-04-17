package br.pucminas.gisa.identity.application.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class Authority extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "authorities")
    private Set<Profile> profiles = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Set<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(final Set<Profile> profiles) {
        this.profiles = profiles;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final Authority authority)) {
            return false;
        }
        return name.equals(authority.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Authority{id=" + id +
               ", name=" + name +
               "}";
    }
}
