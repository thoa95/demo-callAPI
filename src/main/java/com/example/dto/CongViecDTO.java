package com.example.dto;

import com.example.model.DeTai;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CongViecDTO {

    private String maDT;
    private int soTT;
    private String tenCV;
    private String ngayBD;
    private String ngayKT;
}
