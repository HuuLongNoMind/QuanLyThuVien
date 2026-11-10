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

        try {
            qlTaiLieu.docTuFile(FILE_TAI_LIEU);
            qlConNguoi.docTuFile(FILE_CON_NGUOI);
            qlPhieuMuon.docTuFile(FILE_PHIEU_MUON);
            System.out.println("✅ Khởi tạo và nạp dữ liệu từ File Text thành công!");
        } catch (Exception ex) {
            System.err.println("⚠️ Cảnh báo nạp file: " + ex.getMessage());
        }

        MenuConsole menu = new MenuConsole(qlTaiLieu, qlConNguoi, qlPhieuMuon);
        menu.hienThiMenuChinh();
    }
}