package com.example.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@Data
public class IDCongViec implements Serializable {
    private String maDT;
    private int soTT;
}
