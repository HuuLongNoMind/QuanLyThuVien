package model.tailieu;

import java.util.Scanner;

public class TapChi extends TaiLieu {
    private int soPhatHanh;
    private int thangPhatHanh;

    public TapChi() { super(); }

    public TapChi(String maTaiLieu, String nhaXuatBan, double gia, int soPhatHanh, int thangPhatHanh) {
        super(maTaiLieu, nhaXuatBan, gia);
        this.soPhatHanh = soPhatHanh;
        this.thangPhatHanh = thangPhatHanh;
    }

    public int getSoPhatHanh() { return soPhatHanh; }
    public int getThangPhatHanh() { return thangPhatHanh; }

    @Override
    public void nhap(Scanner scanner) {
        super.nhap(scanner);
        System.out.print("Nhập số phát hành: ");
        this.soPhatHanh = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Nhập tháng phát hành: ");
        this.thangPhatHanh = Integer.parseInt(scanner.nextLine().trim());
    }

    @Override
    public void xuat() {
        System.out.printf("📰 [TẠP CHÍ] Mã: %-6s | NXB: %-15s | Số PH: %-4d | Tháng PH: %-2d | Giá: %,.0f VNĐ | Trạng thái: %s\n",
                maTaiLieu, nhaXuatBan, soPhatHanh, thangPhatHanh, gia, trangThai ? "Hoạt động" : "Đã xóa");
    }

    @Override
    public String toFileString() {
        return String.format("TAPCHI|%s|%s|%.0f|%d|%d|%b", maTaiLieu, nhaXuatBan, gia, soPhatHanh, thangPhatHanh, trangThai);
    }
}