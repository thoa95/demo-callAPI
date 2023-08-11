package com.example.otherAPIs;

import com.example.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController

@RequestMapping("/giaovien")
public class OtherAPIsRestController {

    @Autowired
    OtherAPIsService otherAPIsService;

//Q30: API tính tổng đề tài; số lượng đề tài, tổng kinh phí theo mỗi chủ đề,
//danh sách chủ đề có sắp xếp theo tên
    @GetMapping("/kinh-phi")
    public ResponseEntity getCDbyDTandKP(){
        List<FormResponseChuDe> ds=otherAPIsService.convertResponseChuDe();
            if(ds.isEmpty()){
                return new ResponseEntity<>("ko co dl de lay", HttpStatus.OK);
            }else {
                return new ResponseEntity<>(ds, HttpStatus.OK);
            }
    }

// Q31: API lấy ra danh sách giáo viên và số lượng đề tài mà giao viên đó làm chủ nhiệm,
// sắp xếp giảm dần theo số lượng đề tài
    @GetMapping("/sl-dt")
    public ResponseEntity get(){
        List<FormResponseGV> DS= otherAPIsService.convertFormGV();
            if(DS.isEmpty()){
                return new ResponseEntity<>("ko co dl", HttpStatus.OK);
            }else {
                return new ResponseEntity<>(DS,HttpStatus.OK);
            }
    }


    //Q79:Xuất ra thông tin của giáo viên (MAGV, HOTEN) và mức lương của giáo viên.
    // Mức lương được xếp theo,quy tắc:
    // Lương của giáo viên < $1800 : “THẤP” ;
    // Từ $1800 đến $2200: TRUNG BÌNH;
    // Lương > $2200:“CAO”
    @GetMapping("/rank")
    public ResponseEntity getGVByRank(@RequestParam(name = "itemRank",required = false)String itemRank,
                                      @RequestParam(name = "index",required = false,defaultValue = "1")int index){
        if(index == 0){
            return new ResponseEntity<>("trang ko ton tai", HttpStatus.OK);
        }
        List<FormGVbyRankSalary> ds= otherAPIsService.convertToFormSalaryByRank(itemRank,index);
        return ds == null ? new ResponseEntity<>("DATA EMPTY", HttpStatus.OK)
                : new ResponseEntity<>(ds,HttpStatus.OK);
    }

    //Q80: Xuất ra thông tin giáo viên (MAGV, HOTEN) và xếp hạng dựa vào mức lương.
    // Nếu giáo viên có lương cao nhất thì hạng là 1.

    @GetMapping("/rank123")
    public ResponseEntity getGVByRank123(@RequestParam(name = "itemRank",required = false)String itemRank,
                                         @RequestParam(name = "index",required = false,defaultValue = "1")int index){
        if(index == 0){
            return new ResponseEntity<>("trang ko ton tai", HttpStatus.OK);
        }
        List<FormGVbyRankSalary> ds= otherAPIsService.convertToFormSalaryByRank1To3(itemRank,index);
            return ds==null ? new ResponseEntity<>("DATA EMPTY", HttpStatus.OK)
                             : new ResponseEntity<>(ds,HttpStatus.OK);
    }

    //Q81:Xuất ra thông tin thu nhập của giáo viên.
    // Thu nhập của giáo viên được tính bằng LƯƠNG + PHỤ CẤP.
    //Nếu giáo viên là trưởng bộ môn thì PHỤ CẤP là 300,
    // và giáo viên là trưởng khoa thì PHỤ CẤP là 600.

    @GetMapping("/thu-nhap")
    public ResponseEntity getGVbyRankThuNhap(@RequestParam(name = "index", required = false, defaultValue = "1") int index){
        if(index == 0){
            return new ResponseEntity<>("trang ko ton tai", HttpStatus.OK);
        }
        List<FormGVByPhuCap> ds= otherAPIsService.convertToFormGVbyPhuCap(index);
            System.out.println("tong kq:" +ds.size());
            System.out.println("trang so:" +index);
            return ds.isEmpty()? new ResponseEntity<>("DATA EMPTY", HttpStatus.OK)
                                 : new ResponseEntity<>(ds,HttpStatus.OK);
    }

    //Q82: Xuất ra năm mà giáo viên dự kiến sẽ nghĩ hưu với quy định:
    // Tuổi nghỉ hưu của Nam là 60, của Nữ là 55.
    @GetMapping("/nghihuu")
    public ResponseEntity getGVByTuoiNghiHuu(@RequestParam(name = "gioiTinh",required = false)String gioiTinh,
                                             @RequestParam(name = "index", required = false, defaultValue = "1") int index){
        List<Form82> ds= otherAPIsService.convertToForm82(gioiTinh,index);
            return ds == null ? new ResponseEntity<>("DATA EMPTY", HttpStatus.OK)
                                 : new ResponseEntity<>(ds,HttpStatus.OK);
    }

}
