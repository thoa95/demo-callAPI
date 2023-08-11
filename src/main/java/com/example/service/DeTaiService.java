package com.example.service;

import com.example.dto.DeTaiDTO;
import com.example.model.ChuDe;
import com.example.model.DeTai;
import com.example.model.GiaoVien;
import com.example.repository.ChuDeRepository;
import com.example.repository.GiaoVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeTaiService {
//    public List<DeTai> insertDB(){
//        GiaoVien gv1= new GiaoVien();
//        gv1.setMaGV("002");
//        GiaoVien gv4= new GiaoVien();
//        gv1.setMaGV("004");
//        GiaoVien gv5= new GiaoVien();
//        gv1.setMaGV("005");
//        GiaoVien gv7= new GiaoVien();
//        gv1.setMaGV("007");
//
//        ChuDe cd1 = new ChuDe();
//        cd1.setMaCD("QLGD");
//        ChuDe cd2 = new ChuDe();
//        cd1.setMaCD("NCPT");
//        ChuDe cd3 = new ChuDe();
//        cd1.setMaCD("ƯDCN");
//
//        DeTai deTai1= new DeTai ("001","HTTT quản lý các trường ĐH","ĐHQG",20f,"10/20/2007","10/20/2008",cd1,gv1);
//        DeTai deTai2= new DeTai("002","HTTT quản lý giáo vụ cho một Khoa","Trường",20f,"10/12/2000","10/12/2001",cd1,gv1);
//        DeTai deTai3= new DeTai("003","Nghiên cứu chế tạo sợi Nanô Platin","ĐHQG",300f,"05/15/2008","05/15/2010",cd2,gv5);
//        DeTai deTai4= new DeTai("004","Tạo vật liệu sinh học bằng màng ối người","Nhà nước",100f,"01/01/2007","12/31/2009",cd2,gv4);
//        DeTai deTai5= new DeTai("005","Ứng dụng hóa học xanh","Trường",200f,"10/10/2003","12/10/2004",cd3,gv7);
//        DeTai deTai6= new DeTai("006","Nghiên cứu tế bào gốc","Nhà nước",4000f,"10/20/2006","10/20/2009",cd2,gv4);
//        DeTai deTai7= new DeTai("007","HTTT quản lý thư viện ở các trường ĐH","Trường",20f,"5/10/2009","05/10/2010",cd1,gv1);
//        List<DeTai> list= new ArrayList<>();
//        list.add(deTai1);
//        list.add(deTai2);
//        list.add(deTai3);
//        list.add(deTai4);
//        list.add(deTai5);
//        list.add(deTai6);
//        list.add(deTai7);
//        return list;
//    }

    @Autowired
    ChuDeRepository chuDeRepository;
    @Autowired
    GiaoVienRepository giaoVienRepository;

    public boolean checkInsertDT(DeTaiDTO deTaiDTO){
        //1.MADT Not NULL
        //2.đúng CHỦ ĐỀ
        //3. MAGV hoặc là null (chưa có gv tiếp nhận)/ hoặc phải đúng MAGV trong DB

        if(deTaiDTO.getMaDT() == null){
            return false;
        }else {
            if(deTaiDTO.getChuDe() == null){
                return false;
            }else {
                String maCD= deTaiDTO.getChuDe().getMaCD();
                ChuDe timCD = chuDeRepository.findByMaCD(maCD);
                    if(timCD == null) {
                        return  false;
                    }else {
                        GiaoVien gvqlcm = deTaiDTO.getGvCNDT();
                        if(gvqlcm == null){
                            return true;
                        }else {
                            GiaoVien timGV = giaoVienRepository.getGiaoVienByMaGV(gvqlcm.getMaGV());
                            return (timGV != null) ? true: false;
                        }

                    }
            }

        }
    }
}
