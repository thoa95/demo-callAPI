package com.example.dto;

public class ChuDeDTO {
    private String maCD;
    private String tenCD;

    public ChuDeDTO(String maCD, String tenCD) {
        this.maCD = maCD;
        this.tenCD = tenCD;
    }

    public String getMaCD() {
        return maCD;
    }

    public void setMaCD(String maCD) {
        this.maCD = maCD;
    }

    public String getTenCD() {
        return tenCD;
    }

    public void setTenCD(String tenCD) {
        this.tenCD = tenCD;
    }
}

