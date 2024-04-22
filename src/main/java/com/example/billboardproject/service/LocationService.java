package com.example.billboardproject.service;

import com.example.billboardproject.model.City;
import com.example.billboardproject.model.Location;

import java.util.List;

public interface LocationService {
    List<Location> getLocationsOfCity(City city);
    List<Location> getAllLocations();
}
