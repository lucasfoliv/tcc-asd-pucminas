package br.pucminas.gisa.identity.infrastructure;

import java.util.*;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import br.pucminas.gisa.identity.common.security.EncryptionService;
import br.pucminas.gisa.identity.application.domain.Authority;
import br.pucminas.gisa.identity.application.domain.Profile;
import br.pucminas.gisa.identity.application.domain.User;
import br.pucminas.gisa.identity.application.repository.AuthorityRepository;
import br.pucminas.gisa.identity.application.repository.ProfileRepository;
import br.pucminas.gisa.identity.application.repository.UserRepository;
import br.pucminas.gisa.identity.common.typing.AuthorityType;
import br.pucminas.gisa.identity.common.typing.IdentityStatus;
import br.pucminas.gisa.identity.common.typing.ProfileType;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class Bootstrap {

    private static final Logger LOG = LoggerFactory.getLogger(Bootstrap.class);
    private static final String ADMIN_USERNAME_ENV_VAR = "ADMIN_USERNAME";
    private static final String ADMIN_SECRET_ENV_VAR = "ADMIN_SECRET";

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final ProfileRepository profileRepository;
    private final EncryptionService encryptionService;

    @Inject
    public Bootstrap(final UserRepository userRepository,
                     final AuthorityRepository authorityRepository,
                     final ProfileRepository profileRepository,
                     final EncryptionService encryptionService) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.profileRepository = profileRepository;
        this.encryptionService = encryptionService;
    }

    @EventListener
    @Transactional(rollbackOn = Exception.class)
    public void onApplicationStartup(final StartupEvent event) {
        try {
            final List<Authority> authorities = findAuthoritiesByName(AuthorityType.values());

            if (CollectionUtils.isEmpty(authorities)) {
                Arrays.stream(AuthorityType.values()).forEach(it -> authorityRepository.save(toAuthority(it)));
                Arrays.stream(ProfileType.values()).forEach(this::createProfiles);

                final boolean adminlUserExists = userRepository.existsByEmail("admin@gisa.com.br");

                if (!adminlUserExists) {
                    final Optional<Profile> optionalAdminProfile =
                            profileRepository.findByName(ProfileType.Administrator.name());
                    optionalAdminProfile.ifPresent(it -> userRepository.save(toAdminUser(it)));
                }
            }
        } catch (Exception e) {
            LOG.error("Bootstrap failed to run", e);
            throw new BootstrapException(e);
        }
    }

    private List<Authority> findAuthoritiesByName(final AuthorityType[] authorityTypes) {
        final Set<String> authorityNames =
                Arrays.stream(authorityTypes).map(AuthorityType::getAlias).collect(Collectors.toSet());
        return authorityRepository.findByNameIn(authorityNames);
    }

    private void createProfiles(final ProfileType profileType) {
        switch (profileType) {
            case Administrator -> createProfile(profileType, AuthorityType.RoleAdmin);
            case Employee -> createProfile(profileType);
        }
    }

    private void createProfile(final ProfileType profileType, final AuthorityType... authorityTypes) {
        if (ArrayUtils.isNotEmpty(authorityTypes)) {
            final List<Authority> authorities = findAuthoritiesByName(authorityTypes);
            profileRepository.save(toProfile(profileType, new HashSet<>(authorities)));
        } else {
            profileRepository.save(toProfile(profileType, Collections.emptySet()));
        }
    }

    private Authority toAuthority(final AuthorityType authorityType) {
        final Authority authority = new Authority();
        authority.setName(authorityType.getAlias());
        return authority;
    }

    private Profile toProfile(final ProfileType profileType, final Set<Authority> authorities) {
        final Profile profile = new Profile();
        profile.setName(profileType.name());
        profile.setStatus(IdentityStatus.Active);

        if (!authorities.isEmpty()) {
            profile.setAuthorities(authorities);
        }
        return profile;
    }

    private User toAdminUser(final Profile profile) {
        final User user = new User();
        user.setEmail(System.getenv(ADMIN_USERNAME_ENV_VAR));
        user.setSecret(encryptionService.encode(System.getenv(ADMIN_SECRET_ENV_VAR)));
        user.setStatus(IdentityStatus.Active);
        user.setProfile(profile);
        return user;
    }

    private static class BootstrapException extends RuntimeException {

        public BootstrapException(final Throwable cause) {
            super("Bootstrap has failed to run.", cause);
        }
    }
}
