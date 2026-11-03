package main;

import management.DanhSachConNguoi;
import management.DanhSachPhieuMuon;
import management.DanhSachTaiLieu;
import model.phieu.PhieuMuon;
import utils.TienIchInput;

import java.time.LocalDate;

public class MenuConsole {
    private final DanhSachTaiLieu qlTaiLieu;
    private final DanhSachConNguoi qlConNguoi;
    private final DanhSachPhieuMuon qlPhieuMuon;

    private static final String FILE_TL = "data/tai_lieu.txt";
    private static final String FILE_CN = "data/con_nguoi.txt";
    private static final String FILE_PM = "data/phieu_muon.txt";

    public MenuConsole(DanhSachTaiLieu qlTaiLieu, DanhSachConNguoi qlConNguoi, DanhSachPhieuMuon qlPhieuMuon) {
        this.qlTaiLieu = qlTaiLieu;
        this.qlConNguoi = qlConNguoi;
        this.qlPhieuMuon = qlPhieuMuon;
    }

    public void hienThiMenuChinh() {
        boolean tiepTuc = true;
        while (tiepTuc) {
            System.out.println("\n==================================================");
            System.out.println("      HỆ THỐNG QUẢN LÝ THƯ VIỆN TOÀN CẢNH         ");
            System.out.println("==================================================");
            System.out.println("1. Quản lý Tài liệu & Sách (TV 1)");
            System.out.println("2. Quản lý Độc giả & Nhân viên (TV 2)");
            System.out.println("3. Quản lý Mượn / Trả Sách (TV 3)");
            System.out.println("0. Lưu dữ liệu & Thoát chương trình");
            System.out.println("==================================================");
            int chon = TienIchInput.nhapSoNguyen("👉 Chọn phân hệ chức năng: ", 0, 3);

            try {
                switch (chon) {
                    case 1: menuTaiLieu(); break;
                    case 2: menuConNguoi(); break;
                    case 3: menuMuonTra(); break;
                    case 0:
                        luuTatCaFile();
                        tiepTuc = false;
                        System.out.println("👋 Đã lưu dữ liệu thành công. Cảm ơn bạn!");
                        break;
                }
            } catch (RuntimeException ex) {
                System.out.println("⚠️ [LỖI NGHỆP VỤ] " + ex.getMessage());
            }
        }
    }

    private void luuTatCaFile() {
        qlTaiLieu.ghiRaFile(FILE_TL);
        qlConNguoi.ghiRaFile(FILE_CN);
        qlPhieuMuon.ghiRaFile(FILE_PM);
    }

    private void menuTaiLieu() {
        int chon = -1;
        do {
            System.out.println("\n--- SUB-MENU: QUẢN LÝ TÀI LIỆU ---");
            System.out.println("1. Xem danh sách tài liệu");
            System.out.println("2. Thêm tài liệu mới");
            System.out.println("3. Sửa thông tin tài liệu");
            System.out.println("4. Xóa mềm tài liệu");
            System.out.println("5. Tìm kiếm theo mã");
            System.out.println("0. Quay lại");
            chon = TienIchInput.nhapSoNguyen("👉 Chọn chức năng: ", 0, 5);
            switch (chon) {
                case 1: qlTaiLieu.xuatDanhSach(); break;
                case 2: qlTaiLieu.them(); break;
                case 3: qlTaiLieu.sua(); break;
                case 4: qlTaiLieu.xoa(); break;
                case 5: qlTaiLieu.timKiem(); break;
            }
        } while (chon != 0);
    }

    private void menuConNguoi() {
        int chon = -1;
        do {
            System.out.println("\n--- SUB-MENU: QUẢN LÝ CON NGƯỜI ---");
            System.out.println("1. Xem danh sách con người");
            System.out.println("2. Thêm mới con người");
            System.out.println("3. Sửa thông tin");
            System.out.println("4. Khóa/Xóa mềm");
            System.out.println("5. Tìm kiếm theo tên");
            System.out.println("0. Quay lại");
            chon = TienIchInput.nhapSoNguyen("👉 Chọn chức năng: ", 0, 5);
            switch (chon) {
                case 1: qlConNguoi.xuatDanhSach(); break;
                case 2: qlConNguoi.them(); break;
                case 3: qlConNguoi.sua(); break;
                case 4: qlConNguoi.xoa(); break;
                case 5: qlConNguoi.timKiem(); break;
            }
        } while (chon != 0);
    }

    private void menuMuonTra() {
        int chon = -1;
        do {
            System.out.println("\n--- SUB-MENU: QUẢN LÝ MƯỢN TRẢ SÁCH ---");
            System.out.println("1. Lập phiếu mượn mới");
            System.out.println("2. Trả sách");
            System.out.println("3. Hiển thị tất cả phiếu mượn");
            System.out.println("4. Tìm phiếu theo Mã phiếu");
            System.out.println("5. Tìm phiếu theo Mã độc giả");
            System.out.println("6. Tìm phiếu theo Mã tài liệu");
            System.out.println("7. Danh sách phiếu chưa trả");
            System.out.println("0. Quay lại");
            chon = TienIchInput.nhapSoNguyen("👉 Chọn chức năng: ", 0, 7);

            switch (chon) {
                case 1: lapPhieuMuon(); break;
                case 2:
                    String mp = TienIchInput.nhapChuoi("Nhập mã phiếu trả: ");
                    PhieuMuon pTra = qlPhieuMuon.traSach(mp);
                    System.out.println("✅ Trả sách thành công: " + pTra);
                    break;
                case 3: qlPhieuMuon.hienThiTatCa(); break;
                case 4:
                    String maphieu = TienIchInput.nhapChuoi("Nhập mã phiếu: ");
                    PhieuMuon p = qlPhieuMuon.timTheoMa(maphieu);
                    if (p != null) System.out.println(p);
                    else System.out.println("❌ Không tìm thấy!");
                    break;
                case 5:
                    String mdg = TienIchInput.nhapChuoi("Nhập mã độc giả: ");
                    qlPhieuMuon.timTheoDocGia(mdg).forEach(System.out::println);
                    break;
                case 6:
                    String mtl = TienIchInput.nhapChuoi("Nhập mã tài liệu: ");
                    qlPhieuMuon.timTheoTaiLieu(mtl).forEach(System.out::println);
                    break;
                case 7:
                    qlPhieuMuon.danhSachDangMuon().forEach(System.out::println);
                    break;
            }
        } while (chon != 0);
    }

    private void lapPhieuMuon() {
        System.out.println("\n--- LẬP PHIẾU MƯỢN MỚI ---");
        String maPhieu = TienIchInput.nhapChuoi("Mã phiếu mượn: ");
        String maDocGia = TienIchInput.nhapChuoi("Mã độc giả: ");
        String maTaiLieu = TienIchInput.nhapChuoi("Mã tài liệu: ");
        LocalDate ngayMuon = TienIchInput.nhapNgay("Ngày mượn");
        LocalDate hanTra = TienIchInput.nhapNgayKhongTruoc("Hạn trả", ngayMuon);

        PhieuMuon phieu = new PhieuMuon(maPhieu, maDocGia, maTaiLieu, ngayMuon, hanTra);
        qlPhieuMuon.themPhieuMuon(phieu);
        System.out.println("✅ Tạo phiếu mượn thành công!");
    }
}