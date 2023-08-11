package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Khoa {
    @Id
    @Column(name = "maKhoa")
    private String maKhoa;

    @Column(name = "tenKhoa")
    private String tenKhoa;

    @Column(name = "namTL")
    private int namTL;

    @Column(name = "phong")
    private String phong;

    @Column(name = "dienThoai")
    private String dienThoai;

    @Column(name = "truongKhoa")
    private String truongKhoa;

    @Column(name = "ngayNhanChuc")
    private String ngayNhanChuc;

}
