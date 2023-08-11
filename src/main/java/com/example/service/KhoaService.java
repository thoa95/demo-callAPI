package com.example.service;

import com.example.dto.KhoaDTO;
import com.example.model.Khoa;
import com.example.repository.KhoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class KhoaService {
    @Autowired
    KhoaRepository khoaRepository;

    public boolean checkTonTaiMaKhoa(KhoaDTO khoaDTO){
        //NOT NULL + Ko trùng khớp
        if(khoaDTO.getMaKhoa() == null){
            return false;
        }else {
            return (khoaRepository.getByMaKhoa(khoaDTO.getMaKhoa()) == null) ? false : true;
        }

    }

    public ArrayList<Khoa> getKhoaAll(){
        ArrayList<Khoa> listKhoa = (ArrayList<Khoa>) khoaRepository.findAll();
        return listKhoa;
    }

}
