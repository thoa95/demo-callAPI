package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class GiaoVien {
    @Id
    private String maGV;
    private String hoTen;
    private Float luong;
    private String gioiTinh;
    private String ngaySinh;
    private String diaChi;
    @ManyToOne
    @JoinColumn
    private GiaoVien gvqlcm;
    @ManyToOne
    @JoinColumn
    private BoMon boMon;
    @Transient
    @OneToMany(mappedBy = "gvCNDT")
    List<DeTai> deTais;

    public GiaoVien(String maGV, String hoTen, Float luong, String gioiTinh, String ngaySinh, String diaChi, GiaoVien gvqlcm, BoMon boMon) {
        this.maGV = maGV;
        this.hoTen = hoTen;
        this.luong = luong;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.gvqlcm = gvqlcm;
        this.boMon = boMon;
    }
}
