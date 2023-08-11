package com.example.service;

import com.example.dto.CongViecDTO;
import com.example.model.CongViec;
import com.example.model.DeTai;
import com.example.repository.CongViecRepository;
import com.example.repository.DeTaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CongViecService {
    @Autowired
    CongViecRepository congViecRepository;
    @Autowired
    DeTaiRepository deTaiRepository;

    public boolean checkInsetCV(CongViecDTO cvDTO) throws ParseException {
        //1. ĐỀ TÀI phải tồn tại trong db
        //2. SoTT ko trùng khớp
        //3. ngày KT> ngày BĐ

        String maDT= cvDTO.getMaDT();
        int stt= cvDTO.getSoTT();

            if(maDT == null){
                return false;
            }else {
                DeTai timDT = deTaiRepository.findByMaDT(maDT);
                    if(timDT == null){
                        return false;
                    }else {
                        CongViec timCV= congViecRepository.findCongViecByMaDT(maDT);
                        //CV đã tồn tại:
                            if( timCV !=null && stt == timCV.getSoTT()){
                                return false;
                            }else {
                                if((cvDTO.getNgayBD()==null && cvDTO.getNgayKT()==null )|| (cvDTO.getNgayBD()==null ||cvDTO.getNgayKT()==null)){
                                    return true;
                                }else {
                                    Date ngayBD = new SimpleDateFormat("dd/MM/yyyy").parse(cvDTO.getNgayBD());
                                    Date ngayKT = new SimpleDateFormat("dd/MM/yyyy").parse(cvDTO.getNgayKT());
                                    return (ngayKT.compareTo(ngayBD) > 0)?true:false;
                                }

                            }
                    }

            }
    }
}
