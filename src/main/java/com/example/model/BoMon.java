package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class BoMon {
    @Id
    private String maBM;
    private String tenBoMon;
    private String phong;
    private String dienThoai;
    private String truongBM;
    private String ngayNhanChuc;
    @JoinColumn (name = "maKhoa")
    @ManyToOne
    private Khoa khoa;

    @Transient
    @OneToMany(mappedBy = "boMon")
    private List<GiaoVien> gvBM;

    public BoMon(String maBM, String tenBoMon, String phong, String dienThoai, String truongBM, Khoa khoa, String ngayNhanChuc) {
        this.maBM = maBM;
        this.tenBoMon = tenBoMon;
        this.phong = phong;
        this.dienThoai = dienThoai;
        this.truongBM = truongBM;
        this.khoa = khoa;
        this.ngayNhanChuc = ngayNhanChuc;
    }
}
