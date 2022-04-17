package br.pucminas.gisa.mic.registry.common.convert;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import jakarta.inject.Singleton;

import io.micronaut.core.convert.ConversionContext;
import io.micronaut.core.convert.TypeConverter;
import org.apache.commons.lang3.StringUtils;

@Singleton
public class ZonedDateTimeConverter implements TypeConverter<String, ZonedDateTime> {

    @Override
    public Optional<ZonedDateTime> convert(final String source,
                                           final Class<ZonedDateTime> targetType,
                                           final ConversionContext context) {
        if (StringUtils.isBlank(source)) {
            return Optional.empty();
        }

        try {
            return Optional.of(ZonedDateTime.parse(source));
        } catch (DateTimeParseException e) {
            context.reject(source, e);
            return Optional.empty();
        }
    }
}
