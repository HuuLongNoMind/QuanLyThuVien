package main;

import management.DanhSachConNguoi;
import management.DanhSachPhieuMuon;
import management.DanhSachTaiLieu;

public class Main {
    private static final String FILE_CON_NGUOI = "data/con_nguoi.txt";
    private static final String FILE_TAI_LIEU = "data/tai_lieu.txt";
    private static final String FILE_PHIEU_MUON = "data/phieu_muon.txt";

    public static void main(String[] args) {
        System.out.println("🚀 Khởi động Hệ thống Quản lý Thư viện...");

        DanhSachTaiLieu qlTaiLieu = new DanhSachTaiLieu();
        DanhSachConNguoi qlConNguoi = new DanhSachConNguoi();
        DanhSachPhieuMuon qlPhieuMuon = new DanhSachPhieuMuon();

        // 1. Nạp dữ liệu tự động từ 3 file text khi ứng dụng khởi chạy
        try {
            qlTaiLieu.docTuFile(FILE_TAI_LIEU);
            qlConNguoi.docTuFile(FILE_CON_NGUOI);
            qlPhieuMuon.docTuFile(FILE_PHIEU_MUON);
            System.out.println("✅ Khởi tạo và nạp thành công dữ liệu ban đầu từ File Text!");
        } catch (Exception ex) {
            System.err.println("⚠️ Lỗi nạp file: " + ex.getMessage());
        }

        // 2. Chạy Menu điều hướng Console
        MenuConsole menu = new MenuConsole(qlTaiLieu, qlConNguoi, qlPhieuMuon);
        menu.hienThiMenuChinh();
    }
}