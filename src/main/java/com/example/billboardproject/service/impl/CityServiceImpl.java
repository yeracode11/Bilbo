package com.example.billboardproject.service.impl;

import com.example.billboardproject.model.City;
import com.example.billboardproject.repository.CityRepository;
import com.example.billboardproject.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private CityRepository  cityRepository;
    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }
}
