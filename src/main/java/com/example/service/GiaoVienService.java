package com.example.service;

import com.example.dto.GiaoVienDTO;
import com.example.form.FormResponseGV;
import com.example.model.BoMon;
import com.example.model.GiaoVien;
import com.example.repository.BoMonRepository;
import com.example.repository.GiaoVienRepository;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GiaoVienService {

    @Autowired
    GiaoVienRepository giaoVienRepository;
    @Autowired
    BoMonRepository boMonRepository;

    public String xuLyDate(String ngaySinh) {
        String date = null;
        int day = 0;
        int thang = 0;
        int nam = 0;
        String[] strNS = ngaySinh.split("\\D");
            if (strNS.length > 3) {
                date = null;
            } else if (strNS.length < 3) {
                try {
                    Date javaDate = DateUtil.getJavaDate(Double.parseDouble(ngaySinh));
                    String d = new SimpleDateFormat("MM/dd/yyyy").format(javaDate);
                        String[] dStr = d.split("/");
                        day = Integer.parseInt(dStr[1]);
                        thang = Integer.parseInt(dStr[0]);
                        nam = Integer.parseInt(dStr[2]);
                        LocalDateTime dateTime = LocalDateTime.now();
                        return date = ((day <= 0 || day > 31) || (thang < 1 || thang > 12) || (nam < 0 || nam > dateTime.getYear())) ? null : d;
                } catch (Exception e) {
                    date = null;
                }
            } else {
                try {
                    Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(ngaySinh);
                        day = Integer.parseInt(strNS[1]);
                        thang = Integer.parseInt(strNS[0]);
                        nam = Integer.parseInt(strNS[2]);
                        LocalDateTime dateTime = LocalDateTime.now();
                        return date = ((day <= 0 || day > 31) || (thang < 1 || thang > 12) || (nam < 0 || nam > dateTime.getYear())) ? null : ngaySinh;
                } catch (Exception e) {
                    date = null;
                }
            }
        return date;
    }
    public boolean checkInsertGv(GiaoVienDTO giaoVienDTO) {
        //1.Có MÃ GV và MÃ BỘ MÔN:  NOT NULL
        //2. MÃ GV ko ĐƯỢC trùng khớp
        //3. MÃ BỘ MÔN phải tồn tại trong DB
        //4. GVQLCM nếu có phải trùng khớp với DB

        if (giaoVienDTO.getMaGV() == null || giaoVienDTO.getBoMon() == null) {
            return false;
        }else {
            GiaoVien isGVNull = giaoVienRepository.getGiaoVienByMaGV(giaoVienDTO.getMaGV());
            if (isGVNull != null ) {
                return false;
            } else {
                String maBM = giaoVienDTO.getBoMon().getMaBM();
                String timMABM = boMonRepository.getBoMonByMaBM(maBM).getMaBM();
                    if(!maBM.equals(timMABM)){
                        return false;
                    }else {
                        if(giaoVienDTO.getGvqlcm()== null){
                            return true;
                        }else {
                            String maGVQL = giaoVienDTO.getGvqlcm().getMaGV();
                            GiaoVien timGvqlcm =giaoVienRepository.getGiaoVienByMaGV(maGVQL);
                            return (timGvqlcm!= null)?true:false;
                        }
                    }
            }
        }
    }

    public ArrayList<GiaoVien> findAllGiaoVien(Pageable pageable){
        ArrayList<GiaoVien> listGV= (ArrayList<GiaoVien>) giaoVienRepository.findAll();
        return listGV;
    }


}
