package com.zthulj.blog.service;

import com.google.common.base.Charsets;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StorageService {
    private final static Logger log = LoggerFactory.getLogger(StorageService.class.getName());

    @Value("${image.static.path}")
    private String uploadPath;

    /*
        TODO :
            - Check if file exists
            - Check if file an image
     */
    public String storeImage(MultipartFile file) throws IOException{
        byte[] bytes = file.getBytes();
        Path path = Paths.get(uploadPath + File.separator + URLEncoder.encode(file.getOriginalFilename(), Charsets.UTF_8));
        Files.write(path, bytes);
        return "resources" + File.separator + path.getFileName();
    }

    public List<String> listUploadedFiles(){
        File f = new File(uploadPath);
        return Arrays.asList(f.listFiles()).stream().map(e -> e.getName()).collect(Collectors.toList());
    }
}
