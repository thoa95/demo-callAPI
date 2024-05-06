package com.example.restcontroller.fileCsv;

import com.example.service_excel.CreateFileCsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/file")
public class CreateCsvFileController {
    @Autowired
    CreateFileCsvService service;

    @GetMapping("/export")
    public ResponseEntity<?> createFileCsv(HttpServletResponse response) {
//        try {
//            String csvData = service.createFileCsv();
//            if (csvData.isEmpty()) {
//                return ResponseEntity.noContent().build();
//            }
//            byte[] csvBytes = csvData.getBytes(StandardCharsets.UTF_8);
//            String base64Data = Base64Utils.encodeToString(csvBytes);
//            return ResponseEntity.ok()
//                    .header("Content-Disposition", "attachment; filename=ofms.zip")
//                    .header("Content-Type", "text/csv")
//                    .body(base64Data);
//        } catch (Exception e) {
//            return ResponseEntity
//                    .badRequest()
//                    .body("Failed to export CSV: " + e.getMessage());
//        }
//    }
        try {
            String base64Data = service.createFileCsv();
            if (!base64Data.isEmpty()) {
                // Trả về dữ liệu CSV dưới dạng Base64
                return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=gem.zip")
                    .header("Content-Type", "text/csv")
                    .body(base64Data);
            } else {
                // Trả về 204 No Content nếu không có dữ liệu để xuất
                return ResponseEntity.noContent().build();
            }
        } catch (IOException e) {
            // Trả về lỗi 500 Internal Server Error nếu có lỗi xảy ra
            return ResponseEntity.status(500).body("Failed to export CSV: " + e.getMessage());
        }
    }
}
