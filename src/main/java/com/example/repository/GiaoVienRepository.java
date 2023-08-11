package com.example.repository;

import com.example.model.GiaoVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiaoVienRepository extends JpaRepository<GiaoVien,String> {
    //tìm giáo vien theo id
    @Query("select gv from GiaoVien gv")
    List<GiaoVien> getAll(Pageable pageable);

    //tìm giáo vien theo id
    @Query("select gv from GiaoVien gv where gv.maGV = :maGV")
    GiaoVien getGiaoVienByMaGV(String maGV);

    //Tìm giáo viên theo Bộ môn:
    @Query("select gv from GiaoVien gv where  upper(gv.boMon.maBM) = upper(?1)")
    List<GiaoVien> findByBoMon(String maBM, Pageable pageable);

    //Tìm giáo viên theo Gioi Tinh:
    @Query("select gv from GiaoVien gv where  upper(gv.gioiTinh) = upper(?1)")
    List<GiaoVien> findGiaoVienByGioiTinh(String gioiTinh, Pageable pageable);

    //Tìm giáo viên theo Họ TÊN:
    @Query("select gv from GiaoVien gv where  upper(gv.hoTen) like upper(concat('%', ?1,'%'))")
    List<GiaoVien> findGiaoVienByHoTen(String hoTen, Pageable pageable);

    //Đếm bao nhiêu giáo viên có GVQLCM:
    @Query("select  count(gv.maGV) from GiaoVien gv where gv.gvqlcm is not null")
    Integer countGvByGvqlcm();

    //TÌM giáo viên LÀ GVQLCM:
    @Query("select gv from GiaoVien gv where gv.gvqlcm.maGV= gv.gvqlcm.maGV")
    List<GiaoVien> findGiaoVienByGvqlcm();

    @Query("select gv from GiaoVien gv where gv.gvqlcm is not null")
    List<GiaoVien> getGiaoVienCoGvqlcm();





}
