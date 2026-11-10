package management;

import model.phieu.PhieuMuon;
import utils.QuanLyFileText;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class DanhSachPhieuMuon {
    private final List<PhieuMuon> danhSach = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public List<PhieuMuon> getDanhSach() {
        return Collections.unmodifiableList(danhSach);
    }

    public PhieuMuon timTheoMa(String maPhieu) {
        if (maPhieu == null) return null;
        for (PhieuMuon p : danhSach) {
            if (p.getMaPhieu().equalsIgnoreCase(maPhieu.trim())) return p;
        }
        return null;
    }

    public void timTheoMaPhieu(String maPhieu) {
        PhieuMuon p = timTheoMa(maPhieu);
        if (p != null) {
            System.out.println(p);
        } else {
            System.out.println("❌ Không tìm thấy phiếu mượn có mã: " + maPhieu);
        }
    }

    public void timTheoMaDocGia(String maDocGia) {
        String ma = maDocGia.trim();
        boolean timThay = false;
        System.out.println("🔎 Kết quả tìm kiếm phiếu mượn của độc giả [" + ma + "]:");
        for (PhieuMuon p : danhSach) {
            if (p.getMaDocGia().equalsIgnoreCase(ma)) {
                System.out.println(p);
                timThay = true;
            }
        }
        if (!timThay) {
            System.out.println("❌ Không tìm thấy phiếu mượn nào của độc giả có mã: " + maDocGia);
        }
    }

    public void timTheoMaTaiLieu(String maTaiLieu) {
        String ma = maTaiLieu.trim();
        boolean timThay = false;
        System.out.println("🔎 Kết quả tìm kiếm phiếu mượn cho tài liệu [" + ma + "]:");
        for (PhieuMuon p : danhSach) {
            if (p.getMaTaiLieu().equalsIgnoreCase(ma)) {
                System.out.println(p);
                timThay = true;
            }
        }
        if (!timThay) {
            System.out.println("❌ Không tìm thấy phiếu mượn nào cho tài liệu có mã: " + maTaiLieu);
        }
    }

    public void timKiem() {
        System.out.println("\n--- TÌM KIẾM PHIẾU MƯỢN ---");
        System.out.println("1. Tìm theo Mã phiếu");
        System.out.println("2. Tìm theo Mã độc giả");
        System.out.println("3. Tìm theo Mã tài liệu");
        System.out.print("👉 Chọn lựa chọn tìm kiếm (1-3): ");

        int chon;
        try {
            chon = Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception e) {
            System.out.println("❌ Lựa chọn không hợp lệ!");
            return;
        }

        switch (chon) {
            case 1:
                System.out.print("Nhập mã phiếu cần tìm: ");
                String mp = scanner.nextLine().trim();
                timTheoMaPhieu(mp);
                break;
            case 2:
                System.out.print("Nhập mã độc giả cần tìm: ");
                String mdg = scanner.nextLine().trim();
                timTheoMaDocGia(mdg);
                break;
            case 3:
                System.out.print("Nhập mã tài liệu cần tìm: ");
                String mtl = scanner.nextLine().trim();
                timTheoMaTaiLieu(mtl);
                break;
            default:
                System.out.println("❌ Lựa chọn không hợp lệ!");
                break;
        }
    }

    public void themPhieuMuon(PhieuMuon phieu) {
        if (phieu == null) throw new IllegalArgumentException("Phiếu mượn không được null!");
        if (timTheoMa(phieu.getMaPhieu()) != null) {
            throw new IllegalArgumentException("Mã phiếu đã tồn tại: " + phieu.getMaPhieu());
        }
        danhSach.add(phieu);
    }

    public PhieuMuon traSach(String maPhieu) {
        PhieuMuon phieu = timTheoMa(maPhieu);
        if (phieu == null) throw new IllegalArgumentException("Không tìm thấy mã phiếu: " + maPhieu);
        phieu.danhDauDaTra(LocalDate.now());
        return phieu;
    }

    public List<PhieuMuon> danhSachDangMuon() {
        List<PhieuMuon> ketQua = new ArrayList<>();
        for (PhieuMuon p : danhSach) {
            if (!PhieuMuon.DA_TRA.equals(p.getTrangThai())) ketQua.add(p);
        }
        return ketQua;
    }

    public void thongKePhieuQuaHan() {
        System.out.println("\n🚨 --- DANH SÁCH PHIẾU MƯỢN QUÁ HẠN TÍNH TIỀN PHẠT ---");
        boolean coOverdue = false;
        for (PhieuMuon p : danhSach) {
            if (p.tinhTienPhat() > 0) {
                System.out.println(p);
                coOverdue = true;
            }
        }
        if (!coOverdue) System.out.println("✅ Không có phiếu mượn nào bị trễ hạn phạt tiền!");
    }

    public void hienThiTatCa() {
        if (danhSach.isEmpty()) {
            System.out.println("📂 Danh sách phiếu mượn đang trống.");
            return;
        }
        System.out.println("------------------------------------------------------------------------------------------------");
        for (PhieuMuon p : danhSach) System.out.println(p);
        System.out.println("------------------------------------------------------------------------------------------------");
    }

    public void docTuFile(String duongDan) {
        danhSach.clear();
        danhSach.addAll(QuanLyFileText.docDanhSachPhieu(duongDan));
    }

    public void ghiRaFile(String duongDan) {
        QuanLyFileText.ghiDanhSachPhieu(duongDan, danhSach);
    }
}