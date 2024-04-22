package com.example.billboardproject.service.impl;

import com.example.billboardproject.model.Billboard;
import com.example.billboardproject.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadServiceImpl implements FileUploadService {


    @Autowired
    private BillboardServiceImpl billboardService;

    @Value("${targetURL}")
    private String targetURL;

    @Override
    public boolean uploadImg(MultipartFile file, Billboard billboard) {
        try {
//            String fileToken = DigestUtils.sha1Hex(billboard.getId() + "_!");
            String fileToken = file.getOriginalFilename();
            String fileName = fileToken;
            byte[] bytes = file.getBytes();
            Path path = Paths.get(targetURL + "/" + fileName);
            Files.write(path, bytes);
            billboard.setBillboard_url(fileToken);
            billboardService.updateBillboard(billboard);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
