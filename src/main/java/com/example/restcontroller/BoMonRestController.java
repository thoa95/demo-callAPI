package com.example.restcontroller;

import com.example.dto.BoMonDTO;
import com.example.model.BoMon;
import com.example.repository.BoMonRepository;
import com.example.service.BoMonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/bomon")
public class BoMonRestController {
    @Autowired
    BoMonRepository boMonRepository;
    @Autowired
    BoMonService boMonService;

    @PostMapping("/add-bomon")
    public ResponseEntity addBoMon(@RequestBody BoMonDTO boMonDTO){
      boolean check = boMonService.checkInsertBoMon(boMonDTO);
      if(check == true){
          BoMon boMon = new ModelMapper().map(boMonDTO,BoMon.class);
          BoMon bmDB = boMonRepository.save(boMon);
          return new ResponseEntity<>(bmDB, HttpStatus.OK);
      }else {
          return new ResponseEntity<>("dl không được lưu", HttpStatus.OK);

      }
    }
    @GetMapping("/get-all")
    public ResponseEntity getAllBoMon(){
        ArrayList<BoMon> listBM = boMonService.findAllBoMon();
        if( listBM!= null){
            return new ResponseEntity<>(listBM, HttpStatus.OK);
        }else
            return new ResponseEntity<>("HAVEN'T RECORD in BOMON TABLE", HttpStatus.OK);
    }
}
