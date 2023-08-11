package com.example.dto;

import com.example.model.Khoa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoMonDTO {
    private String maBM;
    private String tenBoMon;
    private String phong;
    private String dienThoai;
    private String truongBM;
    private Khoa khoa;
    private String ngayNhanChuc;

    public String getMaBM() {
        return maBM;
    }

    public void setMaBM(String maBM) {
        this.maBM = maBM;
    }
}
