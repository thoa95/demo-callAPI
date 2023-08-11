package com.example.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeTai {
    @Id
    private String maDT;
    private String tenDT;
    private String capQL;
    private Float kinhPhi;
    private String ngayBD;
    private String ngayKT;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private ChuDe chuDe;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private GiaoVien gvCNDT;

    @Transient
    @OneToMany(mappedBy = "maDT", cascade = CascadeType.ALL)
    List<CongViec> viecList;

}
