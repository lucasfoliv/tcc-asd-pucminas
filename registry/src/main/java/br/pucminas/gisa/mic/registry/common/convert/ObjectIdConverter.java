package br.pucminas.gisa.mic.registry.common.convert;

import java.util.Optional;

import jakarta.inject.Singleton;

import io.micronaut.core.convert.ConversionContext;
import io.micronaut.core.convert.TypeConverter;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

@Singleton
public class ObjectIdConverter implements TypeConverter<String, ObjectId> {

    @Override
    public Optional<ObjectId> convert(final String source,
                                      final Class<ObjectId> targetType,
                                      final ConversionContext context) {
        if (StringUtils.isBlank(source)) {
            return Optional.empty();
        }

        try {
            return Optional.of(new ObjectId(source));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
