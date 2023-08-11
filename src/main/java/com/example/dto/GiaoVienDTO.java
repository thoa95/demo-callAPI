package com.example.dto;

import com.example.model.BoMon;
import com.example.model.GiaoVien;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiaoVienDTO {
    private String maGV;
    private String hoTen;
    private Float luong;
    private String gioiTinh;
    private String ngaySinh;
    private String diaChi;
    private GiaoVien gvqlcm;
    private BoMon boMon;

}
