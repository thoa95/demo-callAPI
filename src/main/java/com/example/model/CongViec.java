package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@IdClass(IDCongViec.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CongViec {
    @Id
    private String maDT;
    @Id
    private int soTT;
    private String tenCV;
    private String ngayBD;
    private String ngayKT;

//    @Transient
    @JoinColumn
    @ManyToOne
    private DeTai deTai;
}
