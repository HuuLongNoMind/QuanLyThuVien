package model.connguoi;

import java.util.Scanner;

public class NhanVien extends ConNguoi {
    protected String maNhanVien;
    protected String chucVu;
    protected double luong;

    public NhanVien() { super(); }

    public NhanVien(String hoTen, String ngaySinh, String gioiTinh, String sdt, String maNhanVien, String chucVu, double luong) {
        super(hoTen, ngaySinh, gioiTinh, sdt);
        this.maNhanVien = maNhanVien;
        this.chucVu = chucVu;
        this.luong = luong;
    }

    public String getMaNhanVien() { return maNhanVien; }
    public String getChucVu() { return chucVu; }
    public double getLuong() { return luong; }

    @Override
    public void nhap(Scanner scanner) {
        System.out.print("Nhập mã nhân viên: ");
        this.maNhanVien = scanner.nextLine().trim();
        super.nhap(scanner);
        System.out.print("Nhập chức vụ: ");
        this.chucVu = scanner.nextLine().trim();
        System.out.print("Nhập lương: ");
        this.luong = Double.parseDouble(scanner.nextLine().trim());
    }

    @Override
    public void xuat() {
        System.out.printf("👔 [NHÂN VIÊN] Mã: %-6s | Họ tên: %-18s | Chức vụ: %-15s | Lương: %,.0f VNĐ | TT: %s\n",
                maNhanVien, hoTen, chucVu, luong, trangThai ? "Đang làm" : "Đã nghỉ");
    }

    @Override
    public String toFileString() {
        return String.format("NHANVIEN|%s|%s|%s|%s|%s|%s|%.0f|%b", maNhanVien, hoTen, ngaySinh, gioiTinh, sdt, chucVu, luong, trangThai);
    }
}