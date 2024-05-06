package com.example.service_excel;

import com.example.model.BoMon;
import com.example.model.GiaoVien;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReadCsvByCSVReaderService {
    private static final String PATH_FILE = "D:\\hello\\gem\\";

    public List<GiaoVien> readCsvFile() throws CsvValidationException, FileNotFoundException {
        List<GiaoVien> giaoVienList = new ArrayList<>();
        File folder = ResourceUtils.getFile(PATH_FILE);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".csv")) {
                    System.out.println("Reading file: " + file.getName());
                    giaoVienList.addAll(processCSVFile(file));
                }
            }
        } else {
            System.out.println("Directory is empty or does not exist.");
        }

        return giaoVienList;
    }

    private List<GiaoVien> processCSVFile(File file) throws CsvValidationException {
        List<GiaoVien> giaoVienList = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            // Bỏ qua dòng đầu tiên
            reader.skip(1);
            //Đọc từng dòng trong file:
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                    GiaoVien gv = new GiaoVien();
                    gv.setMaGV(nextLine[0]);
                    gv.setDiaChi(nextLine[1]);
                    gv.setGioiTinh(nextLine[2]);
                    gv.setHoTen(nextLine[3]);
                    gv.setLuong(Float.valueOf(nextLine[4]));
                    gv.setNgaySinh(nextLine[5]);
                    gv.setBoMon(BoMon.builder().maBM(nextLine[6]).build());
                    gv.setGvqlcm(GiaoVien.builder().maGV(nextLine[7]).build());
                    giaoVienList.add(gv);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return giaoVienList;
    }
}
