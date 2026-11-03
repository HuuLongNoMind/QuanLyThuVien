package utils;

import model.phieu.PhieuMuon;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class QuanLyFileText {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static List<PhieuMuon> docDanhSachPhieu(String duongDan) {
        List<PhieuMuon> ds = new ArrayList<>();
        File file = new File(duongDan);
        if (!file.exists()) return ds;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] p = line.split("\\|", -1);
                if (p.length >= 8 && "PHIEU".equals(p[0])) {
                    LocalDate ngayMuon = LocalDate.parse(p[4], FMT);
                    LocalDate hanTra = LocalDate.parse(p[5], FMT);
                    LocalDate ngayTra = p[6].isEmpty() ? null : LocalDate.parse(p[6], FMT);
                    ds.add(new PhieuMuon(p[1], p[2], p[3], ngayMuon, hanTra, ngayTra, p[7]));
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Lỗi đọc file phiếu mượn: " + e.getMessage());
        }
        return ds;
    }

    public static void ghiDanhSachPhieu(String duongDan, List<PhieuMuon> ds) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(duongDan), StandardCharsets.UTF_8))) {
            bw.write("# PHIEU|maPhieu|maDocGia|maTaiLieu|ngayMuon|hanTra|ngayTra|trangThai\n");
            for (PhieuMuon p : ds) {
                bw.write(p.toFileString() + "\n");
            }
        } catch (Exception e) {
            System.err.println("❌ Lỗi ghi file phiếu mượn: " + e.getMessage());
        }
    }
}