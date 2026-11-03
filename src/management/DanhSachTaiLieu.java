package management;

import management.interfaces.IQuanLy;
import model.enums.LoaiTaiLieu;
import model.factory.TaiLieuFactory;
import model.tailieu.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class DanhSachTaiLieu implements IQuanLy {
    private ArrayList<TaiLieu> dsTaiLieu;
    private Scanner scanner;

    public DanhSachTaiLieu() {
        this.dsTaiLieu = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public ArrayList<TaiLieu> getDsTaiLieu() { return dsTaiLieu; }

    @Override
    public void them() {
        System.out.println("\n--- THÊM TÀI LIỆU MỚI ---");
        System.out.println("1. Thêm Sách | 2. Thêm Tạp chí | 3. Thêm Báo");
        int chon = Integer.parseInt(scanner.nextLine());
        TaiLieu tl = null;
        if (chon == 1) tl = TaiLieuFactory.createTaiLieu(LoaiTaiLieu.SACH);
        else if (chon == 2) tl = TaiLieuFactory.createTaiLieu(LoaiTaiLieu.TAPCHI);
        else if (chon == 3) tl = TaiLieuFactory.createTaiLieu(LoaiTaiLieu.BAO);

        if (tl != null) {
            tl.nhap(scanner);
            dsTaiLieu.add(tl);
            System.out.println("✅ Thêm tài liệu thành công!");
        }
    }

    @Override
    public void xuatDanhSach() {
        if (dsTaiLieu.isEmpty()) {
            System.out.println("📂 Danh sách tài liệu trống!");
            return;
        }
        System.out.println("\n========== DANH SÁCH TÀI LIỆU (ĐANG HOẠT ĐỘNG) ==========");
        for (TaiLieu tl : dsTaiLieu) {
            if (tl.isTrangThai()) tl.xuat();
        }
    }

    @Override
    public void xoa() {
        System.out.print("Nhập mã tài liệu cần xóa: ");
        String ma = scanner.nextLine().trim();
        for (TaiLieu tl : dsTaiLieu) {
            if (tl.getMaTaiLieu().equalsIgnoreCase(ma) && tl.isTrangThai()) {
                tl.setTrangThai(false);
                System.out.println("✅ Xóa mềm thành công!");
                return;
            }
        }
        System.out.println("❌ Không tìm thấy mã tài liệu!");
    }

    @Override
    public void sua() {
        System.out.print("Nhập mã tài liệu cần sửa: ");
        String ma = scanner.nextLine().trim();
        for (TaiLieu tl : dsTaiLieu) {
            if (tl.getMaTaiLieu().equalsIgnoreCase(ma) && tl.isTrangThai()) {
                tl.nhap(scanner);
                System.out.println("✅ Cập nhật thành công!");
                return;
            }
        }
        System.out.println("❌ Không tìm thấy mã tài liệu!");
    }

    @Override
    public void timKiem() {
        System.out.print("Nhập mã tài liệu cần tìm: ");
        String ma = scanner.nextLine().trim();
        for (TaiLieu tl : dsTaiLieu) {
            if (tl.getMaTaiLieu().equalsIgnoreCase(ma)) {
                tl.xuat();
                return;
            }
        }
        System.out.println("❌ Không tìm thấy!");
    }

    public void docTuFile(String duongDan) {
        File file = new File(duongDan);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] p = line.split("\\|", -1);
                if (p.length < 5) continue;
                String loai = p[0].toUpperCase();
                if ("SACH".equals(loai) && p.length >= 7) {
                    Sach s = new Sach(p[1], p[2], Double.parseDouble(p[3]), p[4], Integer.parseInt(p[5]));
                    s.setTrangThai(Boolean.parseBoolean(p[6]));
                    dsTaiLieu.add(s);
                } else if ("TAPCHI".equals(loai) && p.length >= 7) {
                    TapChi tc = new TapChi(p[1], p[2], Double.parseDouble(p[3]), Integer.parseInt(p[4]), Integer.parseInt(p[5]));
                    tc.setTrangThai(Boolean.parseBoolean(p[6]));
                    dsTaiLieu.add(tc);
                } else if ("BAO".equals(loai) && p.length >= 6) {
                    Bao b = new Bao(p[1], p[2], Double.parseDouble(p[3]), p[4]);
                    b.setTrangThai(Boolean.parseBoolean(p[5]));
                    dsTaiLieu.add(b);
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Lỗi đọc file tài liệu: " + e.getMessage());
        }
    }

    public void ghiRaFile(String duongDan) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(duongDan), StandardCharsets.UTF_8))) {
            bw.write("# LOAI|ma|nxb|gia|ext1|ext2|trangThai\n");
            for (TaiLieu tl : dsTaiLieu) {
                bw.write(tl.toFileString() + "\n");
            }
        } catch (Exception e) {
            System.err.println("❌ Lỗi ghi file tài liệu: " + e.getMessage());
        }
    }
}