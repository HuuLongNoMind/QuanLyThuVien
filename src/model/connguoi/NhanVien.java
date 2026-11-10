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
        // Đảm bảo lương truyền vào qua constructor cũng không bị âm
        this.luong = Math.max(luong, 0);
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

        // Kiểm tra điều kiện lương không được âm và phải là số hợp lệ
        boolean validLuong = false;
        do {
            System.out.print("Nhập lương: ");
            try {
                this.luong = Double.parseDouble(scanner.nextLine().trim());
                if (this.luong < 0) {
                    System.out.println("Lỗi: Mức lương không được là số âm. Vui lòng nhập lại!");
                } else {
                    validLuong = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập định dạng số hợp lệ!");
            }
        } while (!validLuong);
    }

    @Override
    public void xuat() {
        System.out.printf("👔 [NHÂN VIÊN] Mã: %-6s | Họ tên: %-18s | NS: %-10s | Phái: %-4s | SĐT: %-10s | Chức vụ: %-15s | Lương: %,.0f VNĐ | TT: %s\n",
                maNhanVien, hoTen, ngaySinh, gioiTinh, sdt, chucVu, luong, trangThai ? "Đang làm" : "Đã nghỉ");
    }

    @Override
    public String toFileString() {
        return String.format("NHANVIEN|%s|%s|%s|%s|%s|%s|%.0f|%b", maNhanVien, hoTen, ngaySinh, gioiTinh, sdt, chucVu, luong, trangThai);
    }
}