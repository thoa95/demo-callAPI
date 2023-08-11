package com.example.service;

import com.example.dto.ChuDeDTO;
import com.example.form.FormResponseChuDe;
import com.example.model.ChuDe;
import com.example.repository.ChuDeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChuDeService {
    @Autowired
    ChuDeRepository chuDeRepository;

    public boolean checkInsertCD(ChuDeDTO chuDeDTO){
         if (chuDeDTO.getMaCD() == null) {
             return false;
         }else {
             ChuDe chuDe= chuDeRepository.findByMaCD(chuDeDTO.getMaCD());
             return (chuDe == null)?true:false;
         }
    }
//
//    public List<FormResponseChuDe> convertResponseChuDe(){
//        List<Object[]> listObj = chuDeRepository.getChuDeByTotalDTAndKinhPhi();
//        System.out.println(listObj.size());
//        List<FormResponseChuDe> danhSach = new ArrayList<>();
//            if (listObj.isEmpty()){
//                return null;
//            }else {
//
//                    for (Object[] obj: listObj) {
//                        FormResponseChuDe form =new FormResponseChuDe();
////                            String macd= obj[0].toString();
////                            String tencd= obj[1].toString();
////                            int soDT= Integer.parseInt(obj[2].toString());
////                            float soKinhPhi= Float.parseFloat(obj[3].toString());
//
//                            form.setMaCD (obj[0]== null? null:obj[0].toString());
//                            form.setTenCD (obj[1]== null? null:obj[1].toString());
//                            form.setTongDT (obj[2]== null? 0:Integer.parseInt(obj[2].toString()));
//                            form.setTongKinhPhi (obj[3]== null ? 0:Float.parseFloat(obj[3].toString()));
//                            danhSach.add(form);
//                        }
//                    return danhSach;
//            }
//    }
}
