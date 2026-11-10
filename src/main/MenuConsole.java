package main;

import management.DanhSachConNguoi;
import management.DanhSachPhieuMuon;
import management.DanhSachTaiLieu;
import model.connguoi.ConNguoi;
import model.connguoi.DocGia;
import model.phieu.PhieuMuon;
import model.tailieu.TaiLieu;
import utils.TienIchInput;

import java.time.LocalDate;

public class MenuConsole {
    private final DanhSachTaiLieu qlTaiLieu;
    private final DanhSachConNguoi qlConNguoi;
    private final DanhSachPhieuMuon qlPhieuMuon;

    private static final String FILE_TL = "data/tai_lieu.txt";
    private static final String FILE_CN = "data/con_nguoi.txt";
    private static final String FILE_PM = "data/phieu_muon.txt";

    private String currentRole = "USER";

    public MenuConsole(DanhSachTaiLieu qlTaiLieu, DanhSachConNguoi qlConNguoi, DanhSachPhieuMuon qlPhieuMuon) {
        this.qlTaiLieu = qlTaiLieu;
        this.qlConNguoi = qlConNguoi;
        this.qlPhieuMuon = qlPhieuMuon;
    }

    public void dangNhap() {
        System.out.println("\n🔐 ================= PHÂN QUYỀN HỆ THỐNG =================");
        System.out.println("1. Đăng nhập ADMIN");
        System.out.println("2. Đăng nhập USER");
        int chon = TienIchInput.nhapSoNguyen("👉 Chọn Vai trò đăng nhập: ", 1, 2);
        if (chon == 1) {
            String pass = TienIchInput.nhapChuoi("🔑 Nhập mật khẩu ADMIN (Mặc định 123): ");
            if ("123".equals(pass)) {
                currentRole = "ADMIN";
                System.out.println("✅ Đăng nhập ADMIN thành công!");
            } else {
                System.out.println("❌ Sai mật khẩu! Đặt mặc định truy cập dưới quyền USER.");
                currentRole = "USER";
            }
        } else {
            currentRole = "USER";
            System.out.println("✅ Bạn đang truy cập dưới quyền USER.");
        }
    }

    public void hienThiMenuChinh() {
        dangNhap();
        boolean tiepTuc = true;
        while (tiepTuc) {
            System.out.println("\n==================================================");
            System.out.println("   HỆ THỐNG QUẢN LÝ THƯ VIỆN (" + currentRole + ")   ");
            System.out.println("==================================================");
            System.out.println("1. Quản lý Kho Tài liệu & Tồn kho");
            System.out.println("2. Quản lý Độc giả & Nhân sự");
            System.out.println("3. Quản lý Mượn / Trả Sách");
            System.out.println("4. Xuất Báo Cáo Kho Tài Liệu ra Excel (.CSV)");
            System.out.println("0. Thoát chương trình");
            System.out.println("==================================================");
            int chon = TienIchInput.nhapSoNguyen("👉 Chọn phân hệ chức năng: ", 0, 4);

            try {
                switch (chon) {
                    case 1: menuTaiLieu(); break;
                    case 2: menuConNguoi(); break;
                    case 3: menuMuonTra(); break;
                    case 4: qlTaiLieu.xuatBaoCaoCSV("data/bao_cao_tai_lieu.csv"); break;
                    case 0:
                        luuTatCaFile();
                        autoBackup();
                        tiepTuc = false;
                        System.out.println("👋 lưu dữ liệu & Auto-Backup thành công!");
                        break;
                }
            } catch (Exception ex) {
                System.out.println("⚠️ [LỖI HỆ THỐNG] " + ex.getMessage());
            }
        }
    }

    private void luuTatCaFile() {
        qlTaiLieu.ghiRaFile(FILE_TL);
        qlConNguoi.ghiRaFile(FILE_CN);
        qlPhieuMuon.ghiRaFile(FILE_PM);
    }

    private void autoBackup() {
        String timeStamp = String.valueOf(System.currentTimeMillis());
        qlTaiLieu.ghiRaFile("data/tai_lieu_backup_" + timeStamp + ".txt");
        System.out.println("📦 [Auto-Backup] Đã tạo bản sao lưu!");
    }

    private void menuTaiLieu() {
        int chon = -1;
        do {
            System.out.println("\n--- SUB-MENU: QUẢN LÝ TÀI LIỆU ---");
            System.out.println("1. Xem danh sách tài liệu đang phục vụ");
            System.out.println("2. Thêm mới tài liệu vào kho " + ("ADMIN".equals(currentRole) ? "" : "(Khóa)"));
            System.out.println("3. Sửa thông tin tài liệu " + ("ADMIN".equals(currentRole) ? "" : "(Khóa)"));
            System.out.println("4. Xóa mềm tài liệu " + ("ADMIN".equals(currentRole) ? "" : "(Khóa)"));
            System.out.println("5. Tìm kiếm");
            System.out.println("6. Thống kê số lượng từng loại tài liệu trong kho");
            System.out.println("7. Sắp xếp tài liệu theo Giá tăng dần");
            System.out.println("8. Sắp xếp tài liệu theo Mã tài liệu");
            System.out.println("0. Quay lại Menu chính");

            chon = TienIchInput.nhapSoNguyen("👉 Chọn chức năng: ", 0, 8);

            switch (chon) {
                case 1: qlTaiLieu.xuatDanhSach(); break;
                case 2: if (checkAdmin()) qlTaiLieu.them(); break;
                case 3: if (checkAdmin()) qlTaiLieu.sua(); break;
                case 4: if (checkAdmin()) qlTaiLieu.xoa(); break;
                case 5: qlTaiLieu.timKiem(); break;
                case 6: qlTaiLieu.thongKeLoaiTaiLieu(); break;
                case 7: qlTaiLieu.sapXepTheoGia(true); break;
                case 8: qlTaiLieu.sapXepTheoMa(); break;
            }
        } while (chon != 0);
    }

    private void menuConNguoi() {
        int chon = -1;
        do {
            System.out.println("\n--- SUB-MENU: QUẢN LÝ CON NGƯỜI ---");
            System.out.println("1. Xem danh sách con người đang hoạt động");
            System.out.println("2. Thêm mới Độc giả / Nhân viên " + ("ADMIN".equals(currentRole) ? "" : "(Khóa)"));
            System.out.println("3. Sửa thông tin con người " + ("ADMIN".equals(currentRole) ? "" : "(Khóa)"));
            System.out.println("4. Khóa / Xóa mềm " + ("ADMIN".equals(currentRole) ? "" : "(Khóa)"));
            System.out.println("5. Tìm kiếm");
            System.out.println("0. Quay lại Menu chính");
            chon = TienIchInput.nhapSoNguyen("👉 Chọn chức năng: ", 0, 5);

            switch (chon) {
                case 1: qlConNguoi.xuatDanhSach(); break;
                case 2: if (checkAdmin()) qlConNguoi.them(); break;
                case 3: if (checkAdmin()) qlConNguoi.sua(); break;
                case 4: if (checkAdmin()) qlConNguoi.xoa(); break;
                case 5: qlConNguoi.timKiem(); break;
            }
        } while (chon != 0);
    }

    private void menuMuonTra() {
        int chon = -1;
        do {
            System.out.println("\n--- SUB-MENU: QUẢN LÝ MƯỢN / TRẢ SÁCH ---");
            System.out.println("1. Lập phiếu mượn sách mới " + ("ADMIN".equals(currentRole) ? "" : "(Khóa)"));
            System.out.println("2. Trả sách " + ("ADMIN".equals(currentRole) ? "" : "(Khóa)"));
            System.out.println("3. Hiển thị tất cả phiếu mượn");
            System.out.println("4. Tìm kiếm");
            System.out.println("5. Xem danh sách các phiếu chưa trả");
            System.out.println("6. Danh sách phiếu mượn trễ hạn & Tiền phạt");
            System.out.println("0. Quay lại Menu chính");
            chon = TienIchInput.nhapSoNguyen("👉 Chọn chức năng: ", 0, 6);

            switch (chon) {
                case 1: if (checkAdmin()) lapPhieuMuon(); break;
                case 2: if (checkAdmin()) traSach(); break;
                case 3: qlPhieuMuon.hienThiTatCa(); break;
                case 4: qlPhieuMuon.timKiem(); break;
                case 5: qlPhieuMuon.danhSachDangMuon().forEach(System.out::println); break;
                case 6: qlPhieuMuon.thongKePhieuQuaHan(); break;
            }
        } while (chon != 0);
    }

    private boolean checkAdmin() {
        if (!"ADMIN".equals(currentRole)) {
            System.out.println("⛔ Lỗi: Tài khoản USER không có quyền thực hiện thao tác Thêm/Sửa/Xóa!");
            return false;
        }
        return true;
    }

    private void lapPhieuMuon() {
        System.out.println("\n--- LẬP PHIẾU MƯỢN MỚI ---");
        String maPhieu = TienIchInput.nhapChuoi("Mã phiếu mượn: ");

        if (qlPhieuMuon.timTheoMa(maPhieu) != null) {
            System.out.println("❌ Lỗi: Mã phiếu mượn đã tồn tại!");
            return;
        }

        String maDocGia = TienIchInput.nhapChuoi("Mã độc giả: ");
        DocGia docGiaTarget = null;
        for (ConNguoi cn : qlConNguoi.getDsConNguoi()) {
            if (cn instanceof DocGia && ((DocGia) cn).getMaDocGia().equalsIgnoreCase(maDocGia) && cn.isTrangThai()) {
                docGiaTarget = (DocGia) cn;
                break;
            }
        }
        if (docGiaTarget == null) {
            System.out.println("❌ Lỗi: Mã độc giả không tồn tại hoặc đã bị khóa!");
            return;
        }

        String maTaiLieu = TienIchInput.nhapChuoi("Mã tài liệu: ");
        TaiLieu taiLieuTarget = null;
        for (TaiLieu tl : qlTaiLieu.getDsTaiLieu()) {
            if (tl.getMaTaiLieu().equalsIgnoreCase(maTaiLieu) && tl.isTrangThai()) {
                taiLieuTarget = tl;
                break;
            }
        }
        if (taiLieuTarget == null) {
            System.out.println("❌ Lỗi: Mã tài liệu không tồn tại!");
            return;
        }

        if (!taiLieuTarget.conCoTheMuon()) {
            System.out.println("❌ Lỗi: Tài liệu " + maTaiLieu + " đã HẾT SÁCH TRONG KHO ("
                    + taiLieuTarget.getSoLuongDangMuon() + "/" + taiLieuTarget.getSoLuongTong() + ")!");
            return;
        }

        LocalDate ngayMuon = TienIchInput.nhapNgay("Ngày mượn");
        LocalDate hanTra = TienIchInput.nhapNgayKhongTruoc("Hạn trả", ngayMuon);

        PhieuMuon phieu = new PhieuMuon(maPhieu, maDocGia, maTaiLieu, ngayMuon, hanTra);
        qlPhieuMuon.themPhieuMuon(phieu);
        taiLieuTarget.setSoLuongDangMuon(taiLieuTarget.getSoLuongDangMuon() + 1);

        System.out.println("✅ Tạo phiếu mượn thành công! Khả dụng còn lại: "
                + (taiLieuTarget.getSoLuongTong() - taiLieuTarget.getSoLuongDangMuon()) + " bản.");
    }

    private void traSach() {
        String maPhieu = TienIchInput.nhapChuoi("Nhập mã phiếu cần trả: ");
        PhieuMuon phieu = qlPhieuMuon.timTheoMa(maPhieu);

        if (phieu == null) {
            System.out.println("❌ Không tìm thấy mã phiếu mượn!");
            return;
        }

        if (PhieuMuon.DA_TRA.equals(phieu.getTrangThai())) {
            System.out.println("⚠️ Phiếu mượn này đã được trả trước đó!");
            return;
        }

        qlPhieuMuon.traSach(maPhieu);

        for (TaiLieu tl : qlTaiLieu.getDsTaiLieu()) {
            if (tl.getMaTaiLieu().equalsIgnoreCase(phieu.getMaTaiLieu())) {
                tl.setSoLuongDangMuon(Math.max(0, tl.getSoLuongDangMuon() - 1));
                break;
            }
        }
        System.out.println("✅ Trả sách thành công! Đã cập nhật hoàn tồn kho.");
    }
}