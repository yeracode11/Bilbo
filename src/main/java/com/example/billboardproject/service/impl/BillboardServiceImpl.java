package com.example.billboardproject.service.impl;

import com.example.billboardproject.model.Billboard;
import com.example.billboardproject.repository.BillboardRepository;
import com.example.billboardproject.service.BillboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BillboardServiceImpl implements BillboardService {

    @Autowired
    private BillboardRepository billboardRepository;

    @Override
    public List<Billboard> getAllBillboards() {
        return billboardRepository.findAll();
    }

    @Override
    public Billboard addBillboard(Billboard billboard) {
        return billboardRepository.save(billboard);
    }

    @Override
    public List<Billboard> getAllNotActiveBillboards() {
        List<Billboard> billboards = getAllBillboards();
        List<Billboard> activeBillboards = new ArrayList<>();

        for (Billboard bill:billboards) {
            if (!bill.isActive()) {
                activeBillboards.add(bill);
            }
        }

        return activeBillboards;
    }

    @Override
    public List<Billboard> getAllActiveBillboards() {
        List<Billboard> billboards = getAllBillboards();
        List<Billboard> activeBillboards = new ArrayList<>();

        for (Billboard bill:billboards) {
            if (bill.isActive()) {
                activeBillboards.add(bill);
            }
        }
        return activeBillboards;
    }

    @Override
    public Billboard getBillboardById(Long id) {
        return billboardRepository.getReferenceById(id);
    }

    @Override
    public Billboard updateBillboard(Billboard billboard) {
        return billboardRepository.save(billboard);
    }

    @Override
    public void deleteBillboard(Long id) {
        billboardRepository.deleteById(id);
    }

}
