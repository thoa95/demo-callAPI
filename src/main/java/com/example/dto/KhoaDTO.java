package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhoaDTO {
    private String maKhoa;
    private String tenKhoa;
    private int namTL;
    private String phong;
    private String dienThoai;
    private String truongKhoa;
    private String ngayNhanChuc;
}
