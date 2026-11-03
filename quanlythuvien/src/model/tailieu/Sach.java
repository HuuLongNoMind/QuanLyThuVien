package model.tailieu;

import java.util.Scanner;

public class Sach extends TaiLieu {
    private String tenTacGia;
    private int soTrang;

    public Sach() { super(); }

    public Sach(String maTaiLieu, String nhaXuatBan, double gia, String tenTacGia, int soTrang) {
        super(maTaiLieu, nhaXuatBan, gia);
        this.tenTacGia = tenTacGia;
        this.soTrang = soTrang;
    }

    public String getTenTacGia() { return tenTacGia; }
    public int getSoTrang() { return soTrang; }

    @Override
    public void nhap(Scanner scanner) {
        super.nhap(scanner);
        System.out.print("Nhập tên tác giả: ");
        this.tenTacGia = scanner.nextLine().trim();
        System.out.print("Nhập số trang: ");
        this.soTrang = Integer.parseInt(scanner.nextLine().trim());
    }

    @Override
    public void xuat() {
        System.out.printf("📚 [SÁCH] Mã: %-6s | NXB: %-15s | Tác giả: %-18s | Trang: %-4d | Giá: %,.0f VNĐ | Trạng thái: %s\n",
                maTaiLieu, nhaXuatBan, tenTacGia, soTrang, gia, trangThai ? "Hoạt động" : "Đã xóa");
    }

    @Override
    public String toFileString() {
        return String.format("SACH|%s|%s|%.0f|%s|%d|%b", maTaiLieu, nhaXuatBan, gia, tenTacGia, soTrang, trangThai);
    }
}