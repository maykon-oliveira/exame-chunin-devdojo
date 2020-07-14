package com.maykonoliveira.examechunindevdojo.service;

import com.maykonoliveira.examechunindevdojo.exception.FileUploadException;
import lombok.AllArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.file.Paths;
import java.util.Optional;

/** @author maykon-oliveira */
@Service
@AllArgsConstructor
public class VehicleImageService {
  private final String FILES_UPLOAD_DIR = "/tmp/";

  public Mono<String> store(Mono<FilePart> filePartMono) {
    return filePartMono.flatMap(
        file -> {
          var timeMillis = System.currentTimeMillis();
          var extension =
              getExtension(file.filename())
                  .orElseThrow(
                      () -> {
                        throw new FileUploadException("File dosen't have a extension");
                      });
          var filename = FILES_UPLOAD_DIR + timeMillis + extension;
          return file.transferTo(Paths.get(filename)).then(Mono.just(filename));
        });
  }

  private Optional<String> getExtension(String filename) {
    return Optional.ofNullable(filename)
        .filter(f -> f.contains("."))
        .map(f -> f.substring(filename.lastIndexOf(".")));
  }
}
