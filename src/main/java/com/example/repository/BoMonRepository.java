package com.example.repository;

import com.example.model.BoMon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoMonRepository extends JpaRepository<BoMon,String> {
    @Query("select bm from BoMon bm  where upper(bm.maBM) = upper(?1)")
    BoMon getBoMonByMaBM(String mabm);
}

