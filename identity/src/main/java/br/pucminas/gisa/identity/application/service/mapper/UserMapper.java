package br.pucminas.gisa.identity.application.service.mapper;

import br.pucminas.gisa.identity.application.domain.User;
import br.pucminas.gisa.identity.common.command.CreateUser;
import br.pucminas.gisa.identity.common.view.UserView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "jsr330", uses = ProfileMapper.class)
public interface UserMapper {

    @Mapping(target = "profile", ignore = true)
    User toUser(final CreateUser command);

    UserView toView(final User user);
}
