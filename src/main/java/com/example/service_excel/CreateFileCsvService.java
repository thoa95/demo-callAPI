package com.example.service_excel;

import com.example.model.GiaoVien;
import com.example.repository.GiaoVienRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static org.apache.tomcat.util.http.fileupload.FileUtils.deleteDirectory;


@Service
public class CreateFileCsvService {
    @Autowired
    GiaoVienRepository giaoVienRepository;
    private static final String HEADER_CSV = "MAGV,Address,GT,Name,Salary,Date,MABM,MAGV" + "\n";
    private static final String COMMA_CSV = ",";
    private static final String PATH_FILE = "D:\\hello\\gem\\";
    private static final String DEFAULT_NAME = "_giaovien.csv";

    public String createFileCsv() throws IOException {
        String fileName;
        StringBuilder sb;
        List<GiaoVien> giaoVienList = giaoVienRepository.findAll();
        if (ObjectUtils.isEmpty(giaoVienList)) {
            return "";
        }
        // Đường dẫn thư mục lưu trữ file CSV
        File folder = new File(PATH_FILE);

        //check thư mục:
        createFolder(folder);

        //tạo file trong folder:
        for (int i = 0; i < giaoVienList.size(); i++) {
            sb = new StringBuilder();
            sb.append(HEADER_CSV);
            generateLine(giaoVienList.get(i), sb);
            fileName = PATH_FILE + (i <= 8 ? "0" + (i + 1) : String.valueOf(i + 1)) + (DEFAULT_NAME);
            generateCsvFile(fileName, sb);
        }

        // Convert file CSV thành dạng Base64 và trả về
        return convertCsvToBase64(folder);
    }

    private String convertCsvToBase64(File folder) throws IOException {
        File[] csvFiles = folder.listFiles();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (File csvFile : Objects.requireNonNull(csvFiles)) {
            FileInputStream inputStream = new FileInputStream(csvFile);
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
        }
        byte[] csvData = outputStream.toByteArray();
        return Base64.encodeBase64String(csvData);
    }

    private void generateLine(GiaoVien giaoVien, StringBuilder sb) {
        sb.append(formatData(giaoVien.getMaGV())).append(COMMA_CSV)
                .append(formatData(giaoVien.getDiaChi())).append(COMMA_CSV)
                .append(formatData(giaoVien.getGioiTinh())).append(COMMA_CSV)
                .append(formatData(giaoVien.getHoTen())).append(COMMA_CSV)
                .append(formatData(ObjectUtils.isEmpty(giaoVien.getLuong()) ? "0" : String.valueOf(giaoVien.getLuong()))).append(COMMA_CSV)
                .append(formatData(giaoVien.getNgaySinh())).append(COMMA_CSV)
                .append(formatData(giaoVien.getBoMon() != null ? giaoVien.getBoMon().getMaBM() : null)).append(COMMA_CSV)
                .append(formatData(giaoVien.getGvqlcm() != null ? giaoVien.getGvqlcm().getMaGV() : null)).append("\n");
    }

    private void generateCsvFile(String fileName, StringBuilder sb) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(sb.toString());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private String formatData(String input) {
        if (ObjectUtils.isEmpty(input)) {
            return "";
        } else if (input.contains(",")) {
            return "\"" + input + "\"";
        } else {
            return input;
        }
    }

    private void createFolder(File folder) throws IOException {
        if (!folder.exists()) {
            folder.mkdirs(); // Tạo thư mục nếu nó không tồn tại
        } else {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file); // Đệ quy xóa tất cả các thư mục con
                    } else {
                        file.delete(); // Xóa tập tin
                    }
                }
            }
        }
    }

}
