package com.example.restcontroller;

import com.example.dto.KhoaDTO;
import com.example.model.Khoa;
import com.example.repository.KhoaRepository;
import com.example.service.KhoaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("/khoa")
public class KhoaRestController {
    @Autowired
    KhoaService khoaService;
    @Autowired
    KhoaRepository khoaRepository;

    @PostMapping("/add-khoa")
    public ResponseEntity addKhoa(@RequestBody KhoaDTO khoaDTO){
        boolean check= khoaService.checkTonTaiMaKhoa(khoaDTO);
        if(check == false) {
            // convert DTO sang Entity
            Khoa khoa = new ModelMapper().map(khoaDTO, Khoa.class);
            //lưu xuống DB:
            Khoa khoaDB = khoaRepository.save(khoa);
            System.out.println("LUU THANH CONG MA KHOA: " + khoaDB.getMaKhoa());
            return new ResponseEntity (khoaDB, HttpStatus.OK);

        }else {
            return new ResponseEntity ("MÃ KHOA= "+khoaDTO.getMaKhoa()+" đã tồn tại",HttpStatus.OK);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity getAllKhoa() {
        ArrayList<Khoa> list = khoaService.getKhoaAll();
        if (list != null) {
            return new ResponseEntity (list, HttpStatus.OK);

        } else {
            return new ResponseEntity ("không tìm thấy bản ghi nào của bảng KHOA",HttpStatus.OK);

        }
    }

//    @GetMapping("/sdfgd")
//    public String sdgsa(){
//        return "index";
//    }
}
