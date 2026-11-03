package model.phieu;

import management.interfaces.IFileSerializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    public String getTrangThai() { return trangThai; }

    public void danhDauDaTra(LocalDate ngayTra) {
        this.ngayTra = ngayTra;
        this.trangThai = DA_TRA;
    }

    @Override
    public String toString() {
        String strNgayTra = (ngayTra != null) ? ngayTra.format(FMT) : "Chưa trả";
        return String.format("📋 [PHIẾU MUỢN] Mã: %-6s | Độc giả: %-6s | Tài liệu: %-6s | Mượn: %s | Hạn: %s | Trả: %-10s | Trạng thái: %s",
                maPhieu, maDocGia, maTaiLieu, ngayMuon.format(FMT), hanTra.format(FMT), strNgayTra, trangThai);
    }

    @Override
    public String toFileString() {
        String strNgayTra = (ngayTra != null) ? ngayTra.format(FMT) : "";
        return String.format("PHIEU|%s|%s|%s|%s|%s|%s|%s",
                maPhieu, maDocGia, maTaiLieu, ngayMuon.format(FMT), hanTra.format(FMT), strNgayTra, trangThai);
    }
}