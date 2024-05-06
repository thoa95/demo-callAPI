package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
