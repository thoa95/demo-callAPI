package com.example.restcontroller;

import com.example.dto.CongViecDTO;
import com.example.model.CongViec;
import com.example.model.DeTai;
import com.example.repository.CongViecRepository;
import com.example.repository.DeTaiRepository;
import com.example.service.CongViecService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/congviec")
public class CongViecRestController {

    @Autowired
    CongViecService congViecService;
    @Autowired
    CongViecRepository congViecRepository;

    @Autowired
    DeTaiRepository deTaiRepository;

    @RequestMapping(value = "/add-cv", method = RequestMethod.POST)
    public ResponseEntity addCV(@RequestBody CongViecDTO cvDTO) throws ParseException {
        boolean check = congViecService.checkInsetCV(cvDTO);
        if(check ==true){
            CongViec congViec = new ModelMapper().map(cvDTO,CongViec.class);
                System.out.println(congViec.toString());
                congViec.setDeTai(deTaiRepository.findByMaDT(cvDTO.getMaDT()));
                CongViec add = congViecRepository.save(congViec);
            System.out.println(congViec.toString());

              return new ResponseEntity(add, HttpStatus.OK);
        }else {
                return  new ResponseEntity ("SAVE failed", HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/get-all", method = RequestMethod.GET)
    public ResponseEntity getAllCV(){
        List<CongViec> list = congViecRepository.findAll();
        return (list.isEmpty())?new ResponseEntity("ko co dl de lay", HttpStatus.OK)
                             :new ResponseEntity(list, HttpStatus.OK);
    }

}
