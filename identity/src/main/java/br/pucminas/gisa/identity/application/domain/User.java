package br.pucminas.gisa.identity.application.domain;

import javax.persistence.*;

import br.pucminas.gisa.identity.common.typing.IdentityStatus;

@Entity
public class User extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String secret;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IdentityStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(final String secret) {
        this.secret = secret;
    }

    public IdentityStatus getStatus() {
        return status;
    }

    public void setStatus(final IdentityStatus status) {
        this.status = status;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(final Profile profile) {
        this.profile = profile;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final User user)) {
            return false;
        }
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Override
    public String toString() {
        return "User{id=" + id +
               ", email=" + email +
               ", profile=" + profile +
               ", status=" + status +
               "}";
    }
}
