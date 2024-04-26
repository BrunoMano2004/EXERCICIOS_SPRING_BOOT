package api_consumo_arquivos.api_consumo_arquivos.controller;

import api_consumo_arquivos.api_consumo_arquivos.property.FileStorageProperties;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;
@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    FileStorageProperties fileStorageProperties;

    @PostMapping("/uploadFile")
    public FileStorageResponse uploadFile(@RequestParam("file") MultipartFile file){

    }
}
