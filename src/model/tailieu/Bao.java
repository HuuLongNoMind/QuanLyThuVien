package model.tailieu;

import java.util.Scanner;

public class Bao extends TaiLieu {
    private String ngayPhatHanh;

    public Bao() { super(); }

    public Bao(String maTaiLieu, String nhaXuatBan, double gia, String ngayPhatHanh) {
        super(maTaiLieu, nhaXuatBan, gia);
        this.ngayPhatHanh = ngayPhatHanh;
    }

    public String getNgayPhatHanh() { return ngayPhatHanh; }

    @Override
    public void nhap(Scanner scanner) {
        super.nhap(scanner);
        System.out.print("Nhập ngày phát hành (dd/MM/yyyy): ");
        this.ngayPhatHanh = scanner.nextLine().trim();
    }

    @Override
    public void xuat() {
        System.out.printf("🗞️ [BÁO] Mã: %-6s | NXB: %-15s | Ngày PH: %-10s | Giá: %,.0f VNĐ | Trạng thái: %s\n",
                maTaiLieu, nhaXuatBan, ngayPhatHanh, gia, trangThai ? "Hoạt động" : "Đã xóa");
    }

    @Override
    public String toFileString() {
        return String.format("BAO|%s|%s|%.0f|%s|%b", maTaiLieu, nhaXuatBan, gia, ngayPhatHanh, trangThai);
    }
}