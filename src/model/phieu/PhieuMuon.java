package model.phieu;

import management.interfaces.IFileSerializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class PhieuMuon implements IFileSerializable {
    public static final String DANG_MUON = "DANG_MUON";
    public static final String DA_TRA = "DA_TRA";
    public static final String QUA_HAN = "QUA_HAN";
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String maPhieu;
    private String maDocGia;
    private String maTaiLieu;
    private LocalDate ngayMuon;
    private LocalDate hanTra;
    private LocalDate ngayTra;
    private String trangThai;

    public PhieuMuon(String maPhieu, String maDocGia, String maTaiLieu, LocalDate ngayMuon, LocalDate hanTra) {
        this.maPhieu = maPhieu;
        this.maDocGia = maDocGia;
        this.maTaiLieu = maTaiLieu;
        this.ngayMuon = ngayMuon;
        this.hanTra = hanTra;
        this.ngayTra = null;
        this.trangThai = DANG_MUON;
    }

    public PhieuMuon(String maPhieu, String maDocGia, String maTaiLieu, LocalDate ngayMuon, LocalDate hanTra, LocalDate ngayTra, String trangThai) {
        this.maPhieu = maPhieu;
        this.maDocGia = maDocGia;
        this.maTaiLieu = maTaiLieu;
        this.ngayMuon = ngayMuon;
        this.hanTra = hanTra;
        this.ngayTra = ngayTra;
        this.trangThai = trangThai;
    }

    public String getMaPhieu() { return maPhieu; }
    public String getMaDocGia() { return maDocGia; }
    public String getMaTaiLieu() { return maTaiLieu; }
    public LocalDate getNgayMuon() { return ngayMuon; }
    public LocalDate getHanTra() { return hanTra; }
    public LocalDate getNgayTra() { return ngayTra; }
    public String getTrangThai() { return trangThai; }

    public void danhDauDaTra(LocalDate ngayTra) {
        this.ngayTra = ngayTra;
        this.trangThai = DA_TRA;
    }

    // Yêu cầu 2: Tự động tính tiền phạt trễ hạn (5.000 VNĐ / ngày)
    public double tinhTienPhat() {
        if (DA_TRA.equals(trangThai) && ngayTra != null && ngayTra.isAfter(hanTra)) {
            long soNgayTre = ChronoUnit.DAYS.between(hanTra, ngayTra);
            return soNgayTre * 5000.0;
        } else if (DANG_MUON.equals(trangThai) && LocalDate.now().isAfter(hanTra)) {
            long soNgayTre = ChronoUnit.DAYS.between(hanTra, LocalDate.now());
            return soNgayTre * 5000.0;
        }
        return 0;
    }

    @Override
    public String toString() {
        String strNgayTra = (ngayTra != null) ? ngayTra.format(FMT) : "Chưa trả";
        double phat = tinhTienPhat();
        String strPhat = (phat > 0) ? String.format(" | Phạt: %,.0f VNĐ", phat) : "";
        return String.format("📋 [PHIẾU MUỢN] Mã: %-6s | Độc giả: %-6s | Tài liệu: %-6s | Mượn: %s | Hạn: %s | Trả: %-10s | TT: %s%s",
                maPhieu, maDocGia, maTaiLieu, ngayMuon.format(FMT), hanTra.format(FMT), strNgayTra, trangThai, strPhat);
    }

    @Override
    public String toFileString() {
        String strNgayTra = (ngayTra != null) ? ngayTra.format(FMT) : "";
        return String.format("PHIEU|%s|%s|%s|%s|%s|%s|%s",
                maPhieu, maDocGia, maTaiLieu, ngayMuon.format(FMT), hanTra.format(FMT), strNgayTra, trangThai);
    }
}