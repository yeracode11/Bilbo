package com.example.billboardproject;


import com.example.billboardproject.job.Helper;
import com.example.billboardproject.service.impl.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;


@Component
@Order(value = 1)
public class HelperRunner implements CommandLineRunner {
    private final UserServiceImpl userService;

    public HelperRunner(UserServiceImpl userService) {
        this.userService = userService;
    }


    @Override
    public void run(String... args) throws Exception {
        uploadUserFile();
    }
    public void uploadUserFile() throws IOException {
        FileSystemResource csvResource = new FileSystemResource("C:/Users/bqrec/Downloads/Billboard_Sprints/BillboardSprint-main/src/main/resources/data/users.csv");
        MultipartFile file = new MockMultipartFile(
                Objects.requireNonNull(csvResource.getFilename()),
                csvResource.getFile().getAbsolutePath(),
                "text/csv",
                csvResource.getInputStream()
        );

        if (Helper.hasCSVFormat(file)) {
            try {
                userService.importUserCsv(file);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
