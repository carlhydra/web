package com.springmvc.springmongodbweb.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MyController {

    @Value("${upload.path}")
    private String path;

    @RequestMapping(value = "/doUpload", method = RequestMethod.POST)
    public String upload(@RequestParam MultipartFile file) throws IOException {

        if (!file.isEmpty()) {

         //  String fileName = file.getOriginalFilename();
      //      InputStream is = file.getInputStream();

          //  Files.copy(is, Paths.get(path + fileName),
                  //  StandardCopyOption.REPLACE_EXISTING);

             
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
          //  Files.copy(is, Paths.get(path + fileName),
            //        StandardCopyOption.REPLACE_EXISTING);
//
           File convFile = new File(file.getOriginalFilename());
            MultiValueMap<String, Object> body
            = new LinkedMultiValueMap<>();
          body.add("file",convFile);
          HttpEntity<MultiValueMap<String, Object>> requestEntity
          = new HttpEntity<>(body, headers);
          
         String serverUrl = "http://localhost:8080/storage/uploadFile";
          
         RestTemplate restTemplate = new RestTemplate();
         ResponseEntity<String> response = restTemplate
           .postForEntity(serverUrl, requestEntity, String.class);

            return "redirect:/success.html";

        } else {

            return "redirect:/failure.html";
        }
    }
}