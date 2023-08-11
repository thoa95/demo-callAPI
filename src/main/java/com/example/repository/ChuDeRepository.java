package com.example.repository;

import com.example.model.ChuDe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChuDeRepository extends JpaRepository<ChuDe,String> {
    ChuDe findByMaCD(String maCD);

////   tính tổng đề tài;
////   số lượng đề tài, tổng kinh phí theo mỗi chủ đề, danh sách chủ đề có sắp xếp theo tên
//
//    @Query(value = "select cd.macd, cd.tencd,count(dt.madt), sum(dt.kinh_phi) from de_tai dt " +
//            "right join chu_de cd on cd.macd= dt.chu_de_macd " +
//            "group by cd.macd, cd.tencd " +
//            "order by cd.tencd asc ", nativeQuery = true)
//        List<Object[]> getChuDeByTotalDTAndKinhPhi();



}
