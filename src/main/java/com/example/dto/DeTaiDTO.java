package com.example.dto;

import com.example.model.ChuDe;
import com.example.model.GiaoVien;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeTaiDTO {
    private String maDT;
    private String tenDT;
    private String capQL;
    private Float kinhPhi;
    private String ngayBD;
    private String ngayKT;
    private ChuDe chuDe;
    private GiaoVien gvCNDT;
}
