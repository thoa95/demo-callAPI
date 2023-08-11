package com.example.restcontroller;

import com.example.dto.ChuDeDTO;
import com.example.form.FormResponseGiaoVienQL;
import com.example.form.FormResponseChuDe;
import com.example.model.ChuDe;
import com.example.repository.ChuDeRepository;
import com.example.service.ChuDeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chude")
public class ChuDeRestController {
    @Autowired
    ChuDeService chuDeService;
    @Autowired
    ChuDeRepository chuDeRepository;

    @PostMapping("/add-cd")
    public ResponseEntity insertCD(@RequestBody ChuDeDTO chuDeDTO){
      boolean check = chuDeService.checkInsertCD(chuDeDTO);
      if (check ==true) {
          ChuDe chuDe = new ModelMapper().map(chuDeDTO, ChuDe.class);
          ChuDe add = chuDeRepository.save(chuDe);
          return new ResponseEntity<>(add,HttpStatus.OK);

      }else
          return new ResponseEntity<>("SAVE FAILED",HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity getAllCD(){
        List<ChuDe> list = chuDeRepository.findAll() ;
        if(list !=null){
            return new ResponseEntity<>(list,HttpStatus.OK);
        }else {
            return new ResponseEntity<>("ko co dl de lay",HttpStatus.OK);
        }
    }



    @GetMapping("/person")
    public @ResponseBody ResponseEntity<FormResponseGiaoVienQL> getPerson() {
        FormResponseGiaoVienQL formResponseGiaoVienQL =  new FormResponseGiaoVienQL();
        formResponseGiaoVienQL.setNameGV("aihihi");
        formResponseGiaoVienQL.setMaGV("ahuhu");
        return new ResponseEntity<>(formResponseGiaoVienQL, HttpStatus.OK);
    }
//    @GetMapping("/getCD")
//    public ResponseEntity getCDbyDTandKP(){
//        List<FormResponseChuDe> ds=chuDeService.convertResponseChuDe();
//            if(ds.isEmpty()){
//                return new ResponseEntity<>("ko co dl de lay", HttpStatus.OK);
//            }else {
//                return new ResponseEntity<>(ds, HttpStatus.OK);
//            }
//    }


}
