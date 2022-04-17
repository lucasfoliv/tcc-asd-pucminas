package br.pucminas.gisa.mic.registry.common.convert;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import jakarta.inject.Singleton;

import io.micronaut.core.convert.ConversionContext;
import io.micronaut.core.convert.TypeConverter;
import org.apache.commons.lang3.StringUtils;

@Singleton
public class LocalDateTimeConverter implements TypeConverter<String, LocalDateTime> {

    @Override
    public Optional<LocalDateTime> convert(final String source,
                                           final Class<LocalDateTime> targetType,
                                           final ConversionContext context) {
        if (StringUtils.isEmpty(source)) {
            return Optional.empty();
        }

        try {
            return Optional.of(LocalDateTime.parse(source));
        } catch (DateTimeParseException e) {
            context.reject(source, e);
            return Optional.empty();
        }
    }
}
