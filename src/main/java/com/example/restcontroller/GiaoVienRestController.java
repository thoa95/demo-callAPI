package com.example.restcontroller;

import com.example.dto.GiaoVienDTO;
import com.example.form.FormResponseGV;
import com.example.model.BoMon;
import com.example.model.GiaoVien;
import com.example.repository.BoMonRepository;
import com.example.repository.GiaoVienRepository;
import com.example.service_excel.GiaoVien_excelService;
import com.example.service.GiaoVienService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("giaovien/")
public class GiaoVienRestController {
    @Autowired
    GiaoVienService giaoVienService;
    @Autowired
    GiaoVienRepository giaoVienRepository;
    @Autowired
    BoMonRepository boMonRepository;


//----CREATE DL----------------
    @PostMapping("/add-gv")
    public ResponseEntity addGV(@RequestBody GiaoVienDTO giaoVienDTO){
      boolean check = giaoVienService.checkInsertGv(giaoVienDTO);
      if (check == true){
          //convert DT0 to Entity
          GiaoVien giaoVien = new ModelMapper().map(giaoVienDTO,GiaoVien.class);
          //save:
          GiaoVien addGV=  giaoVienRepository.save(giaoVien);
          return new ResponseEntity(addGV, HttpStatus.OK);
      }else{
          return new ResponseEntity("SAVE failed", HttpStatus.OK);

      }
    }


    //-------GET ALL:------------
            @GetMapping("/get-all")
            public ResponseEntity getAllGV(@RequestParam(name ="indexPage",required = false, defaultValue = "0") Integer index){
                List<GiaoVien> list= giaoVienRepository.getAll(null);
                Integer total =list.size();
                if(total>0){
                    Integer numberPage = (total%3 == 0 ? total/3: total/3+1);
                    System.out.println("co tong "+numberPage+ " trang");
                    if( index == null || index == 1 ){
                        list= giaoVienRepository.getAll(PageRequest.of(0,3,Sort.by("maGV").ascending()));
                        return new ResponseEntity(list, HttpStatus.OK);
                    }else if (index >1 && index <=numberPage){
                         list=giaoVienRepository.getAll(PageRequest.of(index-1,3,Sort.by("maGV").ascending()));
                        return new ResponseEntity(list, HttpStatus.OK);
                    }else {
                        return new ResponseEntity("Trang "+ index+ " ko ton tai", HttpStatus.OK);
                    }
                }else
                    return new ResponseEntity("KO CÓ DL ĐỂ LẤY", HttpStatus.OK);
            }

//----------READ: Theo Id,TYPE @PathVariable--------------
        @GetMapping("/get-gv/{maGV}")
        public ResponseEntity getById_typePath(@PathVariable String maGV ){
            System.out.println("TIM KIEU PATH-VARIABLE");
            GiaoVien timGV = giaoVienRepository.getGiaoVienByMaGV(maGV);
            if(timGV != null){
                return new ResponseEntity(timGV, HttpStatus.OK);
            }else {
                return new ResponseEntity("MAGV "+maGV+" khong tim thay", HttpStatus.OK);
            }
        }


//    ----------READ: Theo id , TYPE @RequestParam-----------
        @GetMapping("/get-gv")
        public ResponseEntity getById_typeParam(@RequestParam String maGV ){
            System.out.println("TIM KIEU PARAM");
            GiaoVien timGV = giaoVienRepository.getGiaoVienByMaGV(maGV);
            if(timGV != null){
                return new ResponseEntity(timGV, HttpStatus.OK);
            }else {
                return new ResponseEntity("MAGV "+maGV+" khong tim thay", HttpStatus.OK);
            }
        }

//    ----------UPDATE GIAO VIEN-----------------
            @PutMapping("/get-gv/edit")
            public ResponseEntity editGV(@RequestBody GiaoVienDTO giaoVienDTO){
                GiaoVien timGV= giaoVienRepository.getGiaoVienByMaGV(giaoVienDTO.getMaGV());
                if( timGV == null){
                    return new ResponseEntity("khong tim thay giao vien", HttpStatus.OK);
                }else {
                    timGV.setHoTen(giaoVienDTO.getHoTen());
                    timGV.setLuong(giaoVienDTO.getLuong());
                    timGV.setGioiTinh(giaoVienDTO.getGioiTinh());
                    timGV.setNgaySinh(giaoVienDTO.getNgaySinh());
                    timGV.setDiaChi(giaoVienDTO.getDiaChi());
                    timGV.setGvqlcm(giaoVienDTO.getGvqlcm());
                    timGV.setBoMon(giaoVienDTO.getBoMon());

                    giaoVienRepository.save(timGV);
                    System.out.println(timGV.getMaGV()+ " MAGV nay vua dc update lai");
                }
                return new ResponseEntity(timGV, HttpStatus.OK);
            }


//    ---------DELETE:
            @DeleteMapping("/get-gv/{maGV}/delete")
            public ResponseEntity deleteGV(@PathVariable String maGV){
                GiaoVien timGV= giaoVienRepository.getGiaoVienByMaGV(maGV);
                if(timGV != null){
                    giaoVienRepository.delete(timGV);
                    return new ResponseEntity(timGV, HttpStatus.OK);
                }else {
                    return new ResponseEntity("ko xoa dc ", HttpStatus.OK);
                }
            }

//    ---SEARCH: Theo MÃ BỘ MÔN
            @GetMapping("/getby-bomon/{maBM}")
            public ResponseEntity getByMABM(@PathVariable String maBM,@RequestParam(name ="indexPage",required = false) Integer index){
                    // Kiểm tra MABM có tồn tại ko?
                    BoMon timBM=  boMonRepository.getBoMonByMaBM(maBM);
                    // nếu MABM tìm kiếm == null:
                        if(timBM == null){
                            return new ResponseEntity("MABM = " + maBM+" khong ton tai", HttpStatus.OK);
                     // nếu MABM != null"
                        }else {
                            System.out.println("tim kiem ma bo mon: "+timBM.getMaBM());
                            List<GiaoVien> list= giaoVienRepository.findByBoMon(maBM,null);
                            // nếu DB emty:
                                if(list.isEmpty()){
                                    System.out.println("danh sách null");
                                    return new ResponseEntity("BO MÔN NÀY CHƯA CÓ GV ", HttpStatus.OK);
                             // nếu DB có bản ghi:
                                }else {
                                    Integer total= list.size();
                                        System.out.println("CO " + total+ " KET QUA TIM THAY");
                                        Integer numberPage = (total%3 == 0 ? total/3: total/3+1);
                                        System.out.println("co tong "+numberPage+ " trang");

                                    // nếu ko có index  trả ra Trang 1:
                                        if (index == null || index == 1) {
                                            list = giaoVienRepository.findByBoMon(maBM,PageRequest.of(0, 3, Sort.by("maGV").ascending()));
                                            return new ResponseEntity(list, HttpStatus.OK);
                                    // nếu index >1:
                                        } else if (index >1 && index <= numberPage ) {
                                            list = giaoVienRepository.findByBoMon(maBM,PageRequest.of(index - 1, 3, Sort.by("maGV").ascending()));
                                            return new ResponseEntity(list, HttpStatus.OK);
                                        }else {
                                            return new ResponseEntity("Trang "+index+" ko ton tai", HttpStatus.OK);
                                        }
                                }
                        }
            }

//------------------SEARCH : Theo GIỚI TÍNH--------------------
        @GetMapping("/getby-gender/{gioiTinh}")
        public ResponseEntity getByGender(@PathVariable String gioiTinh,@RequestParam(name = "indexPage",required = false) Integer index){
            List<GiaoVien> list= giaoVienRepository.findGiaoVienByGioiTinh(gioiTinh, null);

            Integer total= list.size();
            System.out.println("CO " + total+ " KET QUA TIM THAY");

            if(total >0) {
                // nếu ko có index  trả ra Trang 1:
                if (index == null || index == 1) {
                    list = giaoVienRepository.findGiaoVienByGioiTinh(gioiTinh, PageRequest.of(0, 3, Sort.by("maGV").ascending()));
                    return new ResponseEntity(list, HttpStatus.OK);
                    // nếu index >1:
                } else if (index > 1 && index <= (total % 3 != 0 ? total / 3 + 1 : total / 3)) {
                    list = giaoVienRepository.findGiaoVienByGioiTinh(gioiTinh, PageRequest.of(index - 1, 3, Sort.by("maGV").ascending()));
                    return new ResponseEntity(list, HttpStatus.OK);
                } else {
                    return new ResponseEntity("Trang "+index+" ko ton tai", HttpStatus.OK);
                }
            }else {
                return new ResponseEntity(gioiTinh + "KO tim thay gioi tinh nay", HttpStatus.OK);
            }
        }

//    --------------SEARCH: theo HỌ TÊN-----------

            @GetMapping("/getby-hoten")
            public ResponseEntity getByHoTen(@RequestParam String hoTen,@RequestParam(name = "indexPage",required = false) Integer index){
                List<GiaoVien> list= giaoVienRepository.findGiaoVienByHoTen(hoTen, null);

                Integer total= list.size();
                System.out.println("CO " + total+ " KET QUA TIM THAY");

                if(total >0) {
                    // nếu ko có index  trả ra Trang 1:
                    if (index == null || index == 1) {
                        list = giaoVienRepository.findGiaoVienByHoTen(hoTen, PageRequest.of(0, 3, Sort.by("maGV").ascending()));
                        return new ResponseEntity(list, HttpStatus.OK);
                        // nếu index >1:
                    } else if (index > 1 && index <= (total % 3 != 0 ? total / 3 + 1 : total / 3)) {
                        list = giaoVienRepository.findGiaoVienByHoTen(hoTen, PageRequest.of(index - 1, 3, Sort.by("maGV").ascending()));
                        return new ResponseEntity(list, HttpStatus.OK);
                    } else {
                        return new ResponseEntity("Trang "+index+" ko ton tai", HttpStatus.OK);
                    }
                }else {
                    return new ResponseEntity(hoTen + "KO tim thay gioi tinh nay", HttpStatus.OK);
                }
            }
//==========ĐẾM SLUONG  GVQLCM===============
    @GetMapping("/count-gvqlcm")
    public ResponseEntity countGvByGvqlcm(){
        Integer dem= giaoVienRepository.countGvByGvqlcm();
        if(dem >0){
            return new ResponseEntity<>("có "+dem+" GVQLCM",HttpStatus.OK);
        }else {
            return new ResponseEntity<>(" ko có  GVQLCM",HttpStatus.OK);

        }


    }

//    -------LẤY thông tin gv LÀ GVQLCM
            @GetMapping("/get-gvqlcm")
           public ResponseEntity getGVQLCM(){

                List<GiaoVien> gvBiQL= giaoVienRepository.getGiaoVienCoGvqlcm();
                Set <GiaoVien> listGVQLCM= new HashSet<>();
                for (GiaoVien gv: gvBiQL) {
                    listGVQLCM.add(gv.getGvqlcm());
                }
                    System.out.println("CÓ "+ listGVQLCM.size()+" gvqlcm");
                return new ResponseEntity(listGVQLCM, HttpStatus.OK);
           }

//---------Lấy thông tin GV là GVQLCM:
//    @GetMapping("ahihi")
//    public List<FormResponseGiaoVienQL> ahihi(){
//        List<GiaoVien> giaoVienList = giaoVienRepository.getGiaoVienCoGvqlcm();
//        System.out.println("ahihihihi");
//        System.out.println(giaoVienList);
//        List<GiaoVien> giaoVienQLs = new ArrayList<>();
//        List<FormResponseGiaoVienQL> formResponseGiaoVienQLS = new ArrayList<>();
//        for(GiaoVien giaoVien : giaoVienList) {
//            FormResponseGiaoVienQL formResponseGiaoVienQL = new FormResponseGiaoVienQL();
//            GiaoVien giaoVienQL = giaoVien.getGvqlcm();
//            formResponseGiaoVienQL.setMaGVQL(giaoVienQL.getMaGV());
//            formResponseGiaoVienQL.setMaGV(giaoVien.getMaGV());
//            formResponseGiaoVienQL.setNameGVQL(giaoVienQL.getHoTen());
//            formResponseGiaoVienQL.setNameGV(giaoVien.getHoTen());
//
//            formResponseGiaoVienQLS.add(formResponseGiaoVienQL);
//        }
//
//        return formResponseGiaoVienQLS;
//    }

    @Autowired
    GiaoVien_excelService giaoVienexcelService;

    @RequestMapping(value = "/read-excel",method = RequestMethod.POST)
    public ResponseEntity nhapExcelGV(@RequestPart MultipartFile fileExcel) throws IOException {
        List<GiaoVien> danhSach = giaoVienexcelService.checkDataExcelGV(fileExcel);
        System.out.println("SIZE DS ="+ danhSach.size());
        if(!danhSach.isEmpty()){
            List<GiaoVien> adDB= giaoVienRepository.saveAll(danhSach);
            return new ResponseEntity<>(adDB,HttpStatus.OK);
        }else {
            return new ResponseEntity<>("ko đọc được DATA phù hợp ",HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/export-excel", method = RequestMethod.GET)
    public void xuatExcelGV(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=xuatGV.xlsx";
        response.setHeader(headerKey,headerValue);

        giaoVienexcelService.exportDataToExcel(response);
    }


}
