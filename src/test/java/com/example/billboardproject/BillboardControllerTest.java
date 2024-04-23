package com.example.billboardproject;

import com.example.billboardproject.model.Billboard;
import com.example.billboardproject.repository.BillboardRepository;
import com.example.billboardproject.service.impl.BillboardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BillboardServiceImplTest {

    @Mock
    private BillboardRepository billboardRepository;

    @InjectMocks
    private BillboardServiceImpl billboardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllBillboards() {
        List<Billboard> billboards = new ArrayList<>();

        billboards.add(new Billboard());
        billboards.add(new Billboard());

        when(billboardRepository.findAll()).thenReturn(billboards);

        List<Billboard> result = billboardService.getAllBillboards();

        assertEquals(billboards.size(), result.size());
    }

    @Test
    void addBillboard() {
        Billboard billboard = new Billboard();
        when(billboardRepository.save(any())).thenReturn(billboard);

        Billboard result = billboardService.addBillboard(new Billboard());

        assertEquals(billboard, result);
        verify(billboardRepository, times(1)).save(any());
    }

    @Test
    void getAllNotActiveBillboards() {
        List<Billboard> billboards = new ArrayList<>();

        Billboard activeBillboard = new Billboard();
        activeBillboard.setActive(true);
        billboards.add(activeBillboard);
        Billboard inactiveBillboard = new Billboard();
        inactiveBillboard.setActive(false);
        billboards.add(inactiveBillboard);

        when(billboardRepository.findAll()).thenReturn(billboards);

        List<Billboard> result = billboardService.getAllNotActiveBillboards();

        assertEquals(1, result.size());
        assertEquals(inactiveBillboard, result.get(0));
    }

    @Test
    void getAllActiveBillboards() {
        List<Billboard> billboards = new ArrayList<>();

        Billboard activeBillboard = new Billboard();
        activeBillboard.setActive(true);
        billboards.add(activeBillboard);
        Billboard inactiveBillboard = new Billboard();
        inactiveBillboard.setActive(false);
        billboards.add(inactiveBillboard);

        when(billboardRepository.findAll()).thenReturn(billboards);

        List<Billboard> result = billboardService.getAllActiveBillboards();

        assertEquals(1, result.size());
        assertEquals(activeBillboard, result.get(0));
    }

    @Test
    void getBillboardById() {
        Long id = 1L;
        Billboard billboard = new Billboard();
        when(billboardRepository.getReferenceById(id)).thenReturn(billboard);

        Billboard result = billboardService.getBillboardById(id);

        assertEquals(billboard, result);
    }

    @Test
    void updateBillboard() {
        Billboard billboard = new Billboard();
        when(billboardRepository.save(billboard)).thenReturn(billboard);

        Billboard result = billboardService.updateBillboard(billboard);

        assertEquals(billboard, result);
    }

    @Test
    void deleteBillboard() {
        Long id = 1L;

        billboardService.deleteBillboard(id);

        verify(billboardRepository, times(1)).deleteById(id);
    }
}
 