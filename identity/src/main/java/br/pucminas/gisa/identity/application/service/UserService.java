package br.pucminas.gisa.identity.application.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import br.pucminas.gisa.identity.common.security.EncryptionService;
import br.pucminas.gisa.identity.application.domain.Profile;
import br.pucminas.gisa.identity.application.domain.User;
import br.pucminas.gisa.identity.application.repository.UserRepository;
import br.pucminas.gisa.identity.application.service.mapper.UserMapper;
import br.pucminas.gisa.identity.common.command.CreateUser;
import br.pucminas.gisa.identity.common.typing.IdentityStatus;
import br.pucminas.gisa.identity.common.view.UserView;
import org.apache.commons.collections4.IterableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository repository;
    private final EncryptionService encryptionService;
    private final ProfileService profileService;
    private final UserMapper mapper;

    @Inject
    public UserService(final UserRepository repository,
                       final EncryptionService encryptionService,
                       final ProfileService profileService,
                       final UserMapper mapper) {
        this.repository = repository;
        this.encryptionService = encryptionService;
        this.profileService = profileService;
        this.mapper = mapper;
    }

    @Transactional
    public Optional<User> findByEmail(final String email) {
        return repository.findByEmail(email);
    }

    @Transactional
    public List<UserView> findAll() {
        return IterableUtils.toList(repository.findAll())
                .stream()
                .map(mapper::toView)
                .toList();
    }

    @Transactional(rollbackOn = Exception.class)
    public Optional<UserView> create(final CreateUser command) {
        try {
            final Optional<Profile> optionalProfile = profileService.findByName(command.profile());

            if (optionalProfile.isPresent()) {
                final String secret = encryptionService.encode(command.secret());
                final Profile profile = optionalProfile.get();
                final User user = mapper.toUser(command);

                user.setStatus(IdentityStatus.Active);
                user.setProfile(profile);
                user.setSecret(secret);

                profile.getUsers().add(user);

                return Optional.of(mapper.toView(repository.save(user)));
            }
            return Optional.empty();
        } catch (Exception e) {
            LOG.error("Failed to create user", e);
            throw new RuntimeException(e);
        }
    }
}
