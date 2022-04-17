package br.pucminas.gisa.api.gateway.common.helper;

import java.io.IOException;

import io.micronaut.http.MediaType;
import io.micronaut.http.client.multipart.MultipartBody;
import io.micronaut.http.client.multipart.MultipartBody.Builder;
import io.micronaut.http.multipart.CompletedFileUpload;
import org.apache.commons.lang3.ClassUtils;

public final class MultipartBodyHelper {

    private MultipartBodyHelper() {
        throw new AssertionError("%s class cannot be instantiated.".formatted(ClassUtils.getSimpleName(getClass())));
    }

    public static MultipartBody of(final CompletedFileUpload fileUpload) {
        try {
            final Builder builder = MultipartBody.builder()
                    .addPart(fileUpload.getName(),
                            fileUpload.getFilename(),
                            fileUpload.getContentType().orElse(MediaType.MULTIPART_FORM_DATA_TYPE),
                            fileUpload.getBytes());
            return builder.build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
