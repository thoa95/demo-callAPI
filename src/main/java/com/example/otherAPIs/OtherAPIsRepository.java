package com.example.otherAPIs;

import com.example.model.GiaoVien;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface OtherAPIsRepository extends JpaRepository<GiaoVien,String> {

 //Q30:tính tổng đề tài;số lượng đề tài, tổng kinh phí theo mỗi chủ đề,
 //danh sách chủ đề có sắp xếp theo tên

     @Query(value = "(select cd.*,count(dt.madt), sum(dt.kinh_phi)\n" +
             "from chu_de cd\n" +
             "left join de_tai dt on cd.macd = dt.chu_de_macd\n" +
             "group by cd.macd, cd.tencd\n" +
             "order by cd.tencd asc)\n" +
             "union all\n" +
             "    select 'sum','=',count(madt), sum(kinh_phi) from de_tai ", nativeQuery = true)
     List<Object[]> getChuDeByTotalDTAndKinhPhi();



//Q31: API lấy ra danh sách giáo viên và số lượng đề tài mà giao viên đó làm chủ nhiệm,
// sắp xếp giảm dần theo số lượng đề tài

     @Query(value = "select gv.magv,gv.ho_ten, count(madt) from de_tai dt\n" +
             "inner join giao_vien gv on gv.magv=dt.gvcndt_magv\n" +
             "group by  gv.magv\n" +
             "order by count(madt) desc", nativeQuery = true)
     List<Object[]> getGiaoVienbyTotalDT();



//Q79. Xuất ra thông tin của giáo viên (MAGV, HOTEN) và mức lương của giáo viên.
//Mức lương được xếp theo,quy tắc:
//     < $1800 : “THẤP” ;
//     Từ $1800 đến $2200: TRUNG BÌNH;
//     Lương > $2200:“CAO”

     @Query(value = " (select magv, ho_ten, luong,'THẤP' muc_luong\n" +
             "    from giao_vien\n"+
             "    where luong <1800\n" +
             "    order by luong asc)\n" +
             "union all\n" +
             "    (select magv, ho_ten, luong,'TRUNG BÌNH' muc_luong\n" +
             "    from giao_vien\n" +
             "    where luong between 1800 and 2200\n" +
             "    order by luong asc)\n" +
             "union all\n" +
             "    (select magv, ho_ten, luong,'CAO' muc_luong\n" +
             "    from giao_vien\n" +
             "    where luong >2200\n" +
             "    order by luong asc)"
             ,nativeQuery = true)
     List<Object[]> getSalaryGVByRankHighToLow(PageRequest pageRequest);

     @Query(value = "select magv, ho_ten, luong,'THẤP' muc_luong\n" +
             "    from giao_vien\n" +
             "    where luong <1800\n" +
             "    order by luong asc", nativeQuery = true)
     List<Object[]> getGVBySalaryLow(PageRequest pageRequest);

     @Query(value ="select magv, ho_ten, luong,'TRUNG BÌNH' muc_luong\n" +
             "    from giao_vien\n" +
             "    where luong between 1800 and 2200\n" +
             "    order by luong asc", nativeQuery = true)
     List<Object[]> getGVBySalaryAVG(PageRequest pageRequest);

     @Query(value = "select magv, ho_ten, luong,'CAO' muc_luong\n" +
             "    from giao_vien\n" +
             "    where luong >2200\n" +
             "    order by luong asc", nativeQuery = true)
     List<Object[]> getGVBySalaryHigh(PageRequest pageRequest);


//Q80. Xuất ra thông tin giáo viên (MAGV, HOTEN) và xếp hạng dựa vào mức lương.
// Nếu giáo viên có lương cao nhất thì hạng là 1.

     @Query(value = "\n" +
             "(select magv, ho_ten, luong, 1 RANK from giao_vien\n" +
             "where luong =  (select max(luong) from giao_vien)\n" +
             "order by luong desc)\n" +
             "    union all\n" +
             "(select magv, ho_ten, luong,3 RANK  from giao_vien\n" +
             "where luong =  (select min(luong) from giao_vien)\n" +
             "order by luong desc)\n" +
             "    union all\n" +
             "(select magv, ho_ten, luong, 2 RANK from giao_vien\n" +
             "where luong not in (select max(luong) from giao_vien) and luong not in (select min(luong) from giao_vien)\n" +
             "order by luong desc)",nativeQuery = true)
     List<Object[]> getGVByRank1To3(PageRequest pageRequest);

     @Query(value=
             "select magv, ho_ten, luong,1 RANK from giao_vien\n" +
                     "where luong =  (select max(luong) from giao_vien)\n" +
                     "order by luong desc", nativeQuery = true)
     List<Object[]> getGVBySalaryRank1(PageRequest pageRequest);


     @Query(value=
             "select magv, ho_ten, luong, 2 RANK from giao_vien\n" +
                     "where luong not in (select max(luong) from giao_vien) and luong not in (select min(luong) from giao_vien)\n" +
                     "order by luong desc", nativeQuery = true)
     List<Object[]> getGVBySalaryRank2(PageRequest pageRequest);

     @Query(value=
             "select magv, ho_ten, luong,3 RANK  from giao_vien\n" +
                     "where luong =  (select min(luong) from giao_vien)\n" +
                     "order by luong desc", nativeQuery = true)
     List<Object[]> getGVBySalaryRank3(PageRequest pageRequest);

// Q81. Xuất ra thông tin thu nhập của giáo viên. Thu nhập của giáo viên được tính bằng LƯƠNG + PHỤ CẤP.
// Nếu giáo viên là trưởng bộ môn thì PHỤ CẤP là 300,
// và giáo viên là trưởng khoa thì PHỤ CẤP là 600.
     @Query(value =
             "select  kq.*,sum(kq.luong+kq.phucap) from\n" +
                     "(\n" +
                     "    (select gv.magv, gv.ho_ten,gv.luong,300 phucap  from bo_mon bm\n" +
                     "    inner join giao_vien gv on gv.magv= bm.truongbm)\n" +
                     "    union\n" +
                     "    (select k.truong_khoa,gv.ho_ten,gv.luong,600 phucap from khoa k\n" +
                     "    inner join giao_vien gv on gv.magv = k.truong_khoa)\n" +
                     "    union\n" +
                     "    (select gv.magv, gv.ho_ten, gv.luong,0 phucap from giao_vien gv\n" +
                     "    where gv.magv not in (select bm.truongbm from bo_mon bm inner join giao_vien gv on gv.magv= bm.truongbm)\n" +
                     "    and gv.magv not in (select k.truong_khoa from khoa k inner join giao_vien gv on gv.magv = k.truong_khoa))\n" +
                     ") as kq\n" +
                     "group by kq.magv,kq.ho_ten,kq.luong,kq.phucap\n" +
                     "having kq.luong is not null\n" +
                     "order by sum(kq.luong+kq.phucap) desc ", nativeQuery = true)
     List<Object[]> getGVByThuNhap(PageRequest pageRequest);


//Q82. Xuất ra năm mà giáo viên dự kiến sẽ nghĩ hưu với quy định: Tuổi nghỉ hưu của Nam là 60, của Nữ là 55.

     @Query(value =
             "(select magv, ho_ten, gioi_tinh,ngay_sinh, sum(EXTRACT(YEAR FROM CAST(ngay_sinh AS DATE))+60) as nam_nghi_huu\n" +
             "from giao_vien\n" +
             "where gioi_tinh = 'Nữ'\n" +
             "group by magv, ho_ten, ngay_sinh, gioi_tinh)\n" +

             "union all\n" +

             "(select magv, ho_ten, gioi_tinh,ngay_sinh, sum(EXTRACT(YEAR FROM CAST(ngay_sinh AS DATE))+55)\n" +
             "from giao_vien\n" +
             "where gioi_tinh = 'Nam'\n" +
             "group by magv, ho_ten, ngay_sinh, gioi_tinh)", nativeQuery = true)
     List<Object[]> getGVByNamNghiHuu(PageRequest pageRequest);

     @Query(value =
             "select magv, ho_ten, gioi_tinh,ngay_sinh, sum(EXTRACT(YEAR FROM CAST(ngay_sinh AS DATE))+60)\n" +
                     "from giao_vien\n" +
                     "where gioi_tinh = 'Nữ'\n" +
                     "group by magv, ho_ten, ngay_sinh, gioi_tinh", nativeQuery = true)
     List<Object[]> getNamNghiHuuByNu(PageRequest pageRequest);

     @Query(value =
             "select magv, ho_ten, gioi_tinh,ngay_sinh, sum(EXTRACT(YEAR FROM CAST(ngay_sinh AS DATE))+55)\n" +
                     "from giao_vien\n" +
                     "where gioi_tinh = 'Nam'\n" +
                     "group by magv, ho_ten, ngay_sinh, gioi_tinh", nativeQuery = true)
     List<Object[]> getNamNghiHuuByNam(PageRequest pageRequest);

}
