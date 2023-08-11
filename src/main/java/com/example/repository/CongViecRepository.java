package com.example.repository;

import com.example.model.CongViec;
import com.example.model.DeTai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CongViecRepository extends JpaRepository<CongViec, String> {
    @Query("select cv.deTai from CongViec cv where cv.deTai.maDT =?1")
    CongViec findCongViecByMaDT(String maDT);

}
