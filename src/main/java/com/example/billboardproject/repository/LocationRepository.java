package com.example.billboardproject.repository;

import com.example.billboardproject.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface LocationRepository extends JpaRepository<Location, Long> {
}
