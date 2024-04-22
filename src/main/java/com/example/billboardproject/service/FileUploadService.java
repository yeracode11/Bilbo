package com.example.billboardproject.service;

import com.example.billboardproject.model.Billboard;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    boolean uploadImg(MultipartFile file, Billboard billboard);

}
