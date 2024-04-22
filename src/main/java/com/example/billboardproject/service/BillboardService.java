package com.example.billboardproject.service;

import com.example.billboardproject.model.Billboard;

import java.util.List;

public interface BillboardService {
    List<Billboard> getAllBillboards();
    List<Billboard> getAllActiveBillboards();

    List<Billboard> getAllNotActiveBillboards();
    Billboard addBillboard(Billboard billboard);
    Billboard updateBillboard(Billboard billboard);

    Billboard getBillboardById(Long id);
    void deleteBillboard(Long id);
}
