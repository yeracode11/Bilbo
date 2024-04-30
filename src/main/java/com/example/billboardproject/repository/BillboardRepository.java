package com.example.billboardproject.repository;

import com.example.billboardproject.model.Billboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface BillboardRepository extends JpaRepository<Billboard, Long> {
  @Query(value = "select * from billboard order by created_at desc",nativeQuery = true)
    List<Billboard> findAllOrderByCreatedAt();
}
