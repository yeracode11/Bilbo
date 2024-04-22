package com.example.billboardproject;

import com.example.billboardproject.controller.ManagerController;
import com.example.billboardproject.model.Billboard;
import com.example.billboardproject.service.BillboardService;
import com.example.billboardproject.service.FileUploadService;
import com.example.billboardproject.service.impl.CityServiceImpl;
import com.example.billboardproject.service.impl.LocationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ManagerControllerTest {

    @InjectMocks
    ManagerController managerController;

    @Mock
    BillboardService billboardService;

    @Mock
    FileUploadService fileUploadService;

    @Mock
    CityServiceImpl cityService;

    @Mock
    LocationServiceImpl locationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEditBillboard() {
        Billboard billboard = new Billboard();
        billboard.setId(1L);

        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test data".getBytes());

        when(billboardService.getBillboardById(anyLong())).thenReturn(billboard);

        String result = managerController.editBillboard(1L, 1L, 1L, "size", "type", true, 100, file);

        verify(billboardService).getBillboardById(1L);
    }
}
