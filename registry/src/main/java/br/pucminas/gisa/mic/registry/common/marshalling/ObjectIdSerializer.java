package br.pucminas.gisa.mic.registry.common.marshalling;

import java.io.IOException;

import jakarta.inject.Singleton;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.bson.types.ObjectId;

@Singleton
public class ObjectIdSerializer extends JsonSerializer<ObjectId> {

    @Override
    public void serialize(final ObjectId value,
                          final JsonGenerator generator,
                          final SerializerProvider serializers) throws IOException {

        if (value == null) {
            generator.writeNull();
        } else {
            generator.writeString(value.toString());
        }
    }
}
