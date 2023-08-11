package com.example.service_excel;

import com.example.dto.GiaoVienDTO;
import com.example.model.BoMon;
import com.example.model.GiaoVien;
import com.example.repository.GiaoVienRepository;
import com.example.service.GiaoVienService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class GiaoVien_excelService {
    @Autowired
    GiaoVienService giaoVienService;
    @Autowired
    GiaoVienRepository giaoVienRepository;

    public Object getValue(Cell cell){
        switch (cell.getCellTypeEnum()){
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return cell.getNumericCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }

    public List<GiaoVien> checkDataExcelGV(MultipartFile multipartFile) throws IOException {
        List<GiaoVien> danhSach = new ArrayList<>();
            if (!multipartFile.isEmpty()) {
                //lấy đối tượng workBook (đại diện file excel)
                XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
                    //lấy sheet tên "GiaoVien"
                    try {
                        XSSFSheet sheet = workbook.getSheet("GiaoVien");
                        if (sheet.getPhysicalNumberOfRows() <= 1) {
                            return danhSach;
                        } else {
                            //lấy chỉ số của từng trường trong bảng GV
                            int oMAGV = -1;
                            int oHoTen = -1;
                            int oLuong = -1;
                            int oGioiTinh = -1;
                            int oNgaySinh = -1;
                            int oDiaChi = -1;
                            int oGvqlcm = -1;
                            int oBoMon = -1;

                            int indexRowFirst = sheet.getFirstRowNum();
                            XSSFRow rowFirst = sheet.getRow(indexRowFirst);
                            int indexColumFirst = rowFirst.getFirstCellNum();

                            System.out.println("TOTAL ROW VẬT LÝ=" + sheet.getPhysicalNumberOfRows());
                            System.out.println("dong dau tien = " + indexRowFirst);
                            System.out.println("dong cuoi = " + sheet.getLastRowNum() + "\n");
                            System.out.println("TOTAL COLUMN VẬT LÝ:" + rowFirst.getPhysicalNumberOfCells());
                            System.out.println("cot dau tien= " + indexColumFirst);
                            System.out.println("cot cuoi= " + rowFirst.getLastCellNum() + "\n");

                            for (int j = indexColumFirst; j < rowFirst.getLastCellNum(); j++) {
                                switch (rowFirst.getCell(j).getStringCellValue().trim().toUpperCase()) {
                                    case "HỌ TÊN":
                                        oHoTen = j;
                                        continue;
                                    case "MAGV":
                                        oMAGV = j;
                                        continue;
                                    case "LƯƠNG":
                                        oLuong = j;
                                        continue;
                                    case "GIỚI TÍNH":
                                        oGioiTinh = j;
                                        continue;
                                    case "ĐỊA CHỈ":
                                        oDiaChi = j;
                                        continue;
                                    case "NGÀY SINH":
                                        oNgaySinh = j;
                                        continue;
                                    case "MABM":
                                        oBoMon = j;
                                        continue;
                                    case "GVQLCM":
                                        oGvqlcm = j;
                                }
                            }
                            System.out.println("oMAGV= " + oMAGV);
                            System.out.println("oHoTen= " + oHoTen);
                            System.out.println("oNgaySinh= " + oNgaySinh);
                            System.out.println("oGioiTinh=" + oGioiTinh);
                            System.out.println("oGvqlcm=" + oGvqlcm);
                            System.out.println("oDiaChi" + oDiaChi);
                            System.out.println("oBoMon=" + oBoMon);
                            System.out.println("oLuong=" + oLuong + "\n");
                            //lấy số dòng hiện tại
                            int totalRow = sheet.getLastRowNum();
                            System.out.println("dong cuoi=" + totalRow);
                            // Lặp qua từng dòng:
                            for (int i = (indexRowFirst + 1); i <= sheet.getLastRowNum(); i++) {
                                System.out.println("i=" + i);
                                Row row = sheet.getRow(i);
                                //bỏ qua dòng trống
                                if (row == null) {
                                    continue;
                                }
                                Cell cell_maGV = row.getCell(oMAGV);
                                System.out.println("cell_maGV= " + cell_maGV);
                                String maGV = (cell_maGV == null) ? null : String.valueOf(getValue(cell_maGV));

                                Cell cell_hoTen = row.getCell(oHoTen);
                                String hoTen = (cell_hoTen == null) ? null : String.valueOf(getValue(cell_hoTen));

                                Cell cell_luong = row.getCell(oLuong);
                                Float luong = (cell_luong == null) ? null : Float.parseFloat(getValue(cell_luong).toString());

                                Cell cell_gt = row.getCell(oGioiTinh);
                                String gioiTinh = (cell_gt == null) ? null : String.valueOf(getValue(cell_gt));

                                Cell cell_ngs = row.getCell(oNgaySinh);
                                String ngs = (cell_ngs == null) ? null : String.valueOf(getValue(cell_ngs));
                                String ngaySinh = null;
                                if (ngs != null) {
                                    ngaySinh = giaoVienService.xuLyDate(ngs);
                                }

                                Cell cell_dc = row.getCell(oDiaChi);
                                String diaChi = (cell_dc == null) ? null : String.valueOf(getValue(cell_dc));

                                Cell cell_gvqlcm = row.getCell(oGvqlcm);
                                GiaoVien gvqlcm = new GiaoVien();
                                if (cell_gvqlcm != null) {
                                    String maGVQLCM = String.valueOf(getValue(cell_gvqlcm));
                                    gvqlcm.setMaGV(maGVQLCM);
                                } else {
                                    gvqlcm = null;
                                }
                                Cell cell_maBM = row.getCell(oBoMon);
                                BoMon boMon = new BoMon();
                                if (cell_maBM != null) {
                                    String maBM = String.valueOf(getValue(cell_maBM));
                                    boMon.setMaBM(maBM);
                                } else {
                                    boMon = null;
                                }

                                GiaoVienDTO giaoVienDTO = new GiaoVienDTO(maGV, hoTen, luong, gioiTinh, ngaySinh, diaChi, gvqlcm, boMon);
                                System.out.println(giaoVienDTO.toString());

                                if (giaoVienService.checkInsertGv(giaoVienDTO) == true) {
                                    //convert GiaoVienDto sang GiaoVien
                                    GiaoVien newGV = new ModelMapper().map(giaoVienDTO, GiaoVien.class);
                                    danhSach.add(newGV);
                                }
                            }
                        }
                    }catch (Exception e){
                        System.out.println("ko co sheet GiaoVien");
                        e.printStackTrace();
                    }
            }else{
                    System.out.println("File Excel Empty: " + multipartFile.isEmpty());
            }
            return danhSach;
        }


    public void exportDataToExcel(HttpServletResponse response) throws IOException {
//            File file = new File("C:\\Users\\admin\\Desktop\\SpringBoot\\APACHE POI\\xuatgv.xlsx");
            //tạo WoorkBook
                Workbook workbook = new XSSFWorkbook();
            //tạo sheet moi:
                Sheet sheet = workbook.createSheet("giaovien2");
            //tạo header:
                Row row0 = sheet.createRow(0);
                row0.createCell(0).setCellValue("MAGV");
                row0.createCell(1).setCellValue("HỌ TÊN");
                row0.createCell(2).setCellValue("LƯƠNG");
                row0.createCell(3).setCellValue("GIỚI TÍNH");
                row0.createCell(4).setCellValue("NGÀY SINH");
                row0.createCell(5).setCellValue("DỊA CHỈ");
                row0.createCell(6).setCellValue("GVQLCM");
                row0.createCell(7).setCellValue("BỘ MÔN");
            //xuất Data:
                List<GiaoVien> danhSach = giaoVienRepository.findAll();
                    if (!danhSach.isEmpty()) {
                        int indexRow= 1;
                        for (GiaoVien gv: danhSach) {
                            Row row = sheet.createRow(indexRow);
                                row.createCell(0).setCellValue((gv.getMaGV()==null)?"":gv.getMaGV());
                                row.createCell(1).setCellValue((gv.getHoTen()==null)?"":gv.getHoTen());
                                row.createCell(2).setCellValue(String.valueOf((gv.getLuong()==null)?"":gv.getLuong()));
                                row.createCell(3).setCellValue((gv.getGioiTinh()==null)?"":gv.getGioiTinh());
                                row.createCell(4).setCellValue((gv.getNgaySinh()==null)?"":gv.getNgaySinh());
                                row.createCell(5).setCellValue((gv.getDiaChi()==null)?"":gv.getDiaChi());
                                row.createCell(6).setCellValue((gv.getGvqlcm()==null)?"":gv.getGvqlcm().getMaGV());
                                row.createCell(7).setCellValue((gv.getBoMon()==null)?"":gv.getBoMon().getMaBM());
                            indexRow ++;
                        }
                    }
               ServletOutputStream out=  response.getOutputStream();
                workbook.write(out);
                workbook.close();
                out.close();
    }

}
