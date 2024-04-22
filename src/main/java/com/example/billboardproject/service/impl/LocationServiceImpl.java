package com.example.billboardproject.service.impl;

import com.example.billboardproject.model.City;
import com.example.billboardproject.model.Location;
import com.example.billboardproject.repository.LocationRepository;
import com.example.billboardproject.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    protected LocationRepository locationRepository;
    @Override
    public List<Location> getLocationsOfCity(City city) {
        List<Location> all = locationRepository.findAll();
        List<Location> incity = new ArrayList<>();
        for (Location l: all) {if (l.getCity_id().equals(city.getId())) incity.add(l);}
        return incity;
    }

    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }
}
