package com.example.otherAPIs;

import com.example.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OtherAPIsService {

    @Autowired
    OtherAPIsRepository otherAPIsRepository;

//Q30:
    public List<FormResponseChuDe> convertResponseChuDe() {
    List<Object[]> listObj = otherAPIsRepository.getChuDeByTotalDTAndKinhPhi();
    System.out.println(listObj.size());
    List<FormResponseChuDe> danhSach = new ArrayList<>();
    if (listObj.isEmpty()) {
        return null;
    } else {

        for (Object[] obj : listObj) {
            FormResponseChuDe form = new FormResponseChuDe();
            form.setMaCD(obj[0] == null ? null : obj[0].toString());
            form.setTenCD(obj[1] == null ? null : obj[1].toString());
            form.setTongDT(obj[2] == null ? 0 : Integer.parseInt(obj[2].toString()));
            form.setTongKinhPhi(obj[3] == null ? 0 : Float.parseFloat(obj[3].toString()));
            danhSach.add(form);
        }
        return danhSach;
    }
}

//Q31:
    public List<FormResponseGV> convertFormGV(){
    List<Object[]> list= otherAPIsRepository.getGiaoVienbyTotalDT();
    List<FormResponseGV> danhSach= new ArrayList<>();
    if(list.isEmpty()){
        return null;
    }else {
        for (Object[] obj: list) {
            FormResponseGV form = new FormResponseGV();
            form.setMaGV  ((obj[0]== null)?null:obj[0].toString());
            form.setHoTen ((obj[1]== null)?null:obj[1].toString());
            form.setTotalDT ((obj[2]== null)?0:Integer.parseInt(obj[2].toString()));
            danhSach.add(form);
        }
        return danhSach;
    }
}


//Q79:----------------------
    public List<FormGVbyRankSalary> convertToFormSalaryByRank(String itemRank, int index){
        List<FormGVbyRankSalary> danhSach = new ArrayList<>();

            List<Object[]> listObj = new ArrayList<>();
            //nếu ko có itemRank: trả tất cả các hạng mục
            if(itemRank == null) {
                listObj = otherAPIsRepository.getSalaryGVByRankHighToLow(PageRequest.of(index-1, 3));
                System.out.println("itemRANK BI NULL");
            //nếu có itemRank:
            }else {
                System.out.println("itemRANK CO GTRI");

                switch (itemRank.toUpperCase()) {
                    case "THẤP":
                        System.out.println("itemRANK thấp");
                        listObj = otherAPIsRepository.getGVBySalaryLow(PageRequest.of(index-1, 3));
                        break;
                    case "CAO":
                        System.out.println("itemRANK cao");
                        listObj = otherAPIsRepository.getGVBySalaryHigh(PageRequest.of(index-1, 3));
                        break;
                    case "TRUNG BÌNH":
                        System.out.println("itemRANK trung bình");
                        listObj = otherAPIsRepository.getGVBySalaryAVG(PageRequest.of(index-1, 3));
                        break;
                }
            }
                    if (listObj.isEmpty()) {
                        return null;
                    } else {
                        for (Object[] obj : listObj) {
                            FormGVbyRankSalary form = new FormGVbyRankSalary();
                            form.setMaGV(obj[0] == null ? null : obj[0].toString());
                            form.setHoTen(obj[1] == null ? null : obj[1].toString());
                            form.setLuong(obj[2] == null ? 0 : Float.parseFloat(obj[2].toString()));
                            form.setItemRank(obj[3] == null ? null : obj[3].toString());
                            danhSach.add(form);
                        }
                        return danhSach;
                    }
        }


    //Q80:---------------
    public List<FormGVbyRankSalary> convertToFormSalaryByRank1To3(String itemRank,int index){
        List<FormGVbyRankSalary> danhSach = new ArrayList<>();

        List<Object[]> listObj = new ArrayList<>();
        //nếu ko có itemRank: trả tất cả các hạng mục
        if(itemRank == null) {
            listObj = otherAPIsRepository.getGVByRank1To3(PageRequest.of(index-1,3));
            //nếu có itemRank:
        }else {
            switch (itemRank) {
                case "1":
                    listObj = otherAPIsRepository.getGVBySalaryRank1(PageRequest.of(index-1,3));
                    System.out.println("vao rank 1");
                    break;
                case "2":
                    listObj = otherAPIsRepository.getGVBySalaryRank2(PageRequest.of(index-1,3));
                    System.out.println("vao rank 2");
                    break;
                case "3":
                    listObj = otherAPIsRepository.getGVBySalaryRank3(PageRequest.of(index-1,3));
                    System.out.println("vao rank 3");
                    break;
            }
        }
            if (listObj.isEmpty()) {
                return null;
            } else {
                for (Object[] obj : listObj) {
                    FormGVbyRankSalary form = new FormGVbyRankSalary();
                    form.setMaGV(obj[0] == null ? null : obj[0].toString());
                    form.setHoTen(obj[1] == null ? null : obj[1].toString());
                    form.setLuong(obj[2] == null ? 0 : Float.parseFloat(obj[2].toString()));
                    form.setItemRank(obj[3] == null ? null : obj[3].toString());
                    danhSach.add(form);
                }
                return danhSach;
            }
    }

    //Q81:--------------
        public List<FormGVByPhuCap> convertToFormGVbyPhuCap(int index){
            List<FormGVByPhuCap> danhSach = new ArrayList<>();

            List<Object[]>listObj= otherAPIsRepository.getGVByThuNhap(PageRequest.of(index-1,3));
            if(listObj.isEmpty()){
                System.out.println(listObj.isEmpty());
                return danhSach;
            }
            for (Object[] obj : listObj) {
                FormGVByPhuCap form = new FormGVByPhuCap();
                form.setMaGV(obj[0]==null?null:obj[0].toString());
                form.setHoTen(obj[1]==null?null:obj[1].toString());
                form.setLuong(obj[2]==null?0:Float.parseFloat(obj[2].toString()));
                form.setPhuCap(obj[3]==null?0:Float.parseFloat(obj[3].toString()));
                form.setThuNhap(obj[4]==null?0:Float.parseFloat(obj[4].toString()));
                danhSach.add(form);
            }
            return danhSach;
        }

     //Q82:---------------
    public List<Form82> convertToForm82(String gioiTinh,int index){
        List<Form82> danhSach = new ArrayList<>();

        List<Object[]> listObj = new ArrayList<>();
        //nếu ko có itemRank: trả tất cả các hạng mục
        if(gioiTinh == null) {
            listObj = otherAPIsRepository.getGVByNamNghiHuu(PageRequest.of(index-1,3));
            //nếu có itemRank:
        }else {
            switch (gioiTinh.toUpperCase()) {
                case "NỮ":
                    listObj = otherAPIsRepository.getNamNghiHuuByNu(PageRequest.of(index-1,3));
                    break;
                case "NAM":
                    listObj = otherAPIsRepository.getNamNghiHuuByNam(PageRequest.of(index-1,3));
                    break;
            }
        }
        if (listObj.isEmpty()) {
            return null;
        } else {
            for (Object[] obj : listObj) {
                Form82 form = new Form82();
                form.setMaGV(obj[0] == null ? null : obj[0].toString());
                form.setHoTen(obj[1] == null ? null : obj[1].toString());
                form.setGioiTinh(obj[2] == null ? null :obj[2].toString());
                form.setNgaySinh(obj[3] == null ? null : obj[3].toString());
                form.setNamNghiHuu(obj[4] == null ? 0: (int)Double.parseDouble(obj[4].toString()));
                danhSach.add(form);
            }
            return danhSach;
        }
    }


}
