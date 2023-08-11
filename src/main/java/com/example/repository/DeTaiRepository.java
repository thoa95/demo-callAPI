package com.example.repository;

import com.example.model.DeTai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeTaiRepository extends JpaRepository<DeTai,String> {
    DeTai findByMaDT(String maDT);
}
