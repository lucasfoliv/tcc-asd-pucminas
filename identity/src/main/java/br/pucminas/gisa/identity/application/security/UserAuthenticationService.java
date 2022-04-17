package br.pucminas.gisa.identity.application.security;

import java.util.List;
import java.util.Optional;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import io.micronaut.core.beans.BeanIntrospection;
import io.micronaut.security.authentication.AuthenticationResponse;

import br.pucminas.gisa.identity.common.security.EncryptionService;
import br.pucminas.gisa.identity.application.domain.Authority;
import br.pucminas.gisa.identity.application.domain.Profile;
import br.pucminas.gisa.identity.application.domain.User;
import br.pucminas.gisa.identity.application.service.AuthorityService;
import br.pucminas.gisa.identity.application.service.UserService;
import br.pucminas.gisa.identity.common.typing.FailureMessage;
import br.pucminas.gisa.identity.common.typing.IdentityStatus;

@Singleton
public class UserAuthenticationService {

    private final UserService userService;
    private final AuthorityService authorityService;
    private final EncryptionService encryptionService;

    @Inject
    public UserAuthenticationService(final UserService userService,
                                     final AuthorityService authorityService,
                                     final EncryptionService encryptionService) {
        this.userService = userService;
        this.authorityService = authorityService;
        this.encryptionService = encryptionService;
    }

    public Optional<User> findByIdentity(final String identity) {
        return userService.findByEmail(identity);
    }

    public List<Authority> findAuthoritiesByProfileId(final Profile profile) {
        return authorityService.findByProfileId(profile.getId());
    }

    public Optional<AuthenticationResponse> validateSecurityDetails(final User user, final String rawSecret) {
        final BeanIntrospection<User> introspection = BeanIntrospection.getIntrospection(User.class);
        final Profile profile = introspection.getRequiredProperty("profile", Profile.class).get(user);
        final IdentityStatus status = introspection.getRequiredProperty("status", IdentityStatus.class).get(user);
        final String secret = introspection.getRequiredProperty("secret", String.class).get(user);

        AuthenticationResponse response = null;

        if (secret == null || !encryptionService.matches(rawSecret, secret)) {
            response = AuthenticationResponse.failure(FailureMessage.CredentialsMismatch.getCause());
        } else if (status == null || status == IdentityStatus.Inactive) {
            response = AuthenticationResponse.failure(FailureMessage.AccountInactive.getCause());
        } else if (profile == null || profile.getStatus() == IdentityStatus.Inactive) {
            response = AuthenticationResponse.failure(FailureMessage.ProfileInactive.getCause());
        }
        return Optional.ofNullable(response);
    }
}
