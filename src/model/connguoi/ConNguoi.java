package model.connguoi;

import management.interfaces.IFileSerializable;
import java.util.Scanner;

public abstract class ConNguoi implements IFileSerializable {
    protected String hoTen;
    protected String ngaySinh;
    protected String gioiTinh;
    protected String sdt;
    protected boolean trangThai;

    public ConNguoi() {
        this.trangThai = true;
    }

    public ConNguoi(String hoTen, String ngaySinh, String gioiTinh, String sdt) {
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.sdt = sdt;
        this.trangThai = true;
    }

    public String getHoTen() { return hoTen; }
    public String getNgaySinh() { return ngaySinh; }
    public String getGioiTinh() { return gioiTinh; }
    public String getSdt() { return sdt; }
    public boolean isTrangThai() { return trangThai; }
    public void setTrangThai(boolean trangThai) { this.trangThai = trangThai; }

    public void nhap(Scanner scanner) {
        System.out.print("Nhập họ tên: ");
        this.hoTen = scanner.nextLine().trim();
        System.out.print("Nhập ngày sinh (dd/MM/yyyy): ");
        this.ngaySinh = scanner.nextLine().trim();
        System.out.print("Nhập giới tính: ");
        this.gioiTinh = scanner.nextLine().trim();
        System.out.print("Nhập số điện thoại: ");
        this.sdt = scanner.nextLine().trim();
    }

    public abstract void xuat();
}