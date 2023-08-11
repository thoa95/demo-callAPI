package com.example.restcontroller;

import com.example.dto.DeTaiDTO;
import com.example.model.DeTai;
import com.example.repository.DeTaiRepository;
import com.example.service.DeTaiService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detai")

public class DeTaiRestController {
    @Autowired
    DeTaiService deTaiService;
    @Autowired
    DeTaiRepository deTaiRepository;

    @PostMapping("/add-dt")
    public ResponseEntity insertDT(@RequestBody DeTaiDTO deTaiDTO){
     boolean check = deTaiService.checkInsertDT(deTaiDTO);
         if(check ==true){
             DeTai deTai = new ModelMapper().map(deTaiDTO,DeTai.class);
             DeTai add = deTaiRepository.save(deTai);
             return  new ResponseEntity<>(add, HttpStatus.OK);
         }else {
             return  new ResponseEntity<>("SAVE failed", HttpStatus.OK);
         }
    }

    @RequestMapping(path = "/get-all", method = RequestMethod.GET)
    public ResponseEntity getAllDT(){
        List<DeTai> list = deTaiRepository.findAll();
        return (list == null)? new ResponseEntity<>("ko co dl de lay", HttpStatus.OK)
                : new ResponseEntity<>(list, HttpStatus.OK);
    }
}
