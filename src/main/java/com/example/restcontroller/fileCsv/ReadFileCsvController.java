package com.example.restcontroller.fileCsv;

import com.example.model.GiaoVien;
import com.example.service_excel.ReadCsvByCSVReaderService;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/file")
public class ReadFileCsvController {
    @Autowired
    ReadCsvByCSVReaderService service;
    @PostMapping("/readCsv/by-csvReader")
    public ResponseEntity<?> readFileCsv(){
        try {
            List<GiaoVien> list = service.readCsvFile();
            return ResponseEntity.ok().body(list);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
