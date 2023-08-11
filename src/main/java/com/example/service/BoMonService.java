package com.example.service;

import com.example.dto.BoMonDTO;
import com.example.model.BoMon;
import com.example.model.Khoa;
import com.example.repository.BoMonRepository;
import com.example.repository.KhoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BoMonService {
    @Autowired
    BoMonRepository boMonRepository;
    @Autowired
    KhoaRepository khoaRepository;

    public boolean checkInsertBoMon(BoMonDTO boMonDTO){
        //1. MABM không NULL +  KO ĐƯỢC trùng khớp
        //2. MÃ KHOA phải tồn tại trong DB

        if(boMonDTO.getMaBM() == null){
            return false;
        }else {
            BoMon timBM= boMonRepository.getBoMonByMaBM(boMonDTO.getMaBM());
               if (timBM != null){
                   return false;
               }else {
                   if(boMonDTO.getKhoa() == null){
                       return false;
                   }else {
                       String maKhoa= boMonDTO.getKhoa().getMaKhoa();
                       Khoa timKhoa= khoaRepository.getByMaKhoa(maKhoa);
                       return (timKhoa == null)? false:true;
                   }
               }
        }
    }


    public ArrayList<BoMon> findAllBoMon(){
        ArrayList<BoMon> listAll = (ArrayList<BoMon>)boMonRepository.findAll();
        return listAll.size()>0 ? listAll: null;
    }
}
