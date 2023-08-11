package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class ChuDe {
    @Id
    private String maCD;
    private String tenCD;
    @Transient
    @OneToMany(mappedBy = "chuDe")
    private  List<DeTai> deTaiList;

    public ChuDe(String maCD, String tenCD) {
        this.maCD = maCD;
        this.tenCD = tenCD;
    }
}
