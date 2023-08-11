package com.example.repository;

import com.example.model.Khoa;
import com.example.service.KhoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KhoaRepository extends JpaRepository<Khoa,String> {
    @Query("select k from Khoa k where k.maKhoa = ?1")
    Khoa getByMaKhoa(String maKhoa);

}

