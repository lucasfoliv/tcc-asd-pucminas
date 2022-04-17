package br.pucminas.gisa.identity.application.service.mapper;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import br.pucminas.gisa.identity.application.domain.Authority;
import br.pucminas.gisa.identity.application.domain.Profile;
import br.pucminas.gisa.identity.common.view.ProfileView;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jsr330")
public interface ProfileMapper {

    ProfileView toView(final Profile profile);

    default Set<String> getAuthorities(final Set<Authority> authorities) {
        return CollectionUtils.isNotEmpty(authorities)
            ? authorities.stream().map(Authority::getName).collect(Collectors.toCollection(TreeSet::new))
            : Collections.emptySet();
    }
}
