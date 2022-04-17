package br.pucminas.gisa.mcd.receiver.application.service;

import java.io.File;
import java.io.IOException;

import jakarta.inject.Singleton;

import io.micronaut.http.multipart.StreamingFileUpload;

@Singleton
public class WorksheetService {

    public File createFile(final StreamingFileUpload publisher) {
        final String filename = publisher.getFilename();
        final String[] filenameParts = filename.split("\\.");

        try {
            return File.createTempFile(filenameParts[0], "." + filenameParts[1]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
