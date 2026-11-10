package model.connguoi;

import java.util.Scanner;

public class ThuThu extends NhanVien {
    private String caLamViec;

    public ThuThu() { super(); }

    public ThuThu(String hoTen, String ngaySinh, String gioiTinh, String sdt, String maNhanVien, String chucVu, double luong, String caLamViec) {
        super(hoTen, ngaySinh, gioiTinh, sdt, maNhanVien, chucVu, luong);
        this.caLamViec = caLamViec;
    }

    public String getCaLamViec() { return caLamViec; }

    @Override
    public void nhap(Scanner scanner) {
        super.nhap(scanner);
        System.out.print("Nhập ca làm việc (Sáng/Chiều/Tối): ");
        this.caLamViec = scanner.nextLine().trim();
    }

    @Override
    public void xuat() {
        System.out.printf("🔑 [THỦ THƯ] Mã: %-6s | Họ tên: %-18s | NS: %-10s | Phái: %-4s | SĐT: %-10s | Chức vụ: %-15s | Lương: %,.0f VNĐ | Ca làm: %-6s | TT: %s\n",
                maNhanVien, hoTen, ngaySinh, gioiTinh, sdt, chucVu, luong, caLamViec, trangThai ? "Đang làm" : "Đã nghỉ");
    }

    @Override
    public String toFileString() {
        return String.format("THUTHU|%s|%s|%s|%s|%s|%s|%.0f|%s|%b", maNhanVien, hoTen, ngaySinh, gioiTinh, sdt, chucVu, luong, caLamViec, trangThai);
    }
}