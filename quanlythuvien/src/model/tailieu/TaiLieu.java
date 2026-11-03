package model.tailieu;

import management.interfaces.IFileSerializable;
import java.util.Scanner;

public abstract class TaiLieu implements IFileSerializable {
    protected String maTaiLieu;
    protected String nhaXuatBan;
    protected double gia;
    protected boolean trangThai;

    public TaiLieu() {
        this.trangThai = true;
    }

    public TaiLieu(String maTaiLieu, String nhaXuatBan, double gia) {
        this.maTaiLieu = maTaiLieu;
        this.nhaXuatBan = nhaXuatBan;
        this.gia = gia;
        this.trangThai = true;
    }

    public String getMaTaiLieu() { return maTaiLieu; }
    public void setMaTaiLieu(String maTaiLieu) { this.maTaiLieu = maTaiLieu; }
    public String getNhaXuatBan() { return nhaXuatBan; }
    public void setNhaXuatBan(String nhaXuatBan) { this.nhaXuatBan = nhaXuatBan; }
    public double getGia() { return gia; }
    public void setGia(double gia) { this.gia = gia; }
    public boolean isTrangThai() { return trangThai; }
    public void setTrangThai(boolean trangThai) { this.trangThai = trangThai; }

    public void nhap(Scanner scanner) {
        System.out.print("Nhập mã tài liệu: ");
        this.maTaiLieu = scanner.nextLine().trim();
        System.out.print("Nhập nhà xuất bản: ");
        this.nhaXuatBan = scanner.nextLine().trim();
        System.out.print("Nhập giá tiền: ");
        this.gia = Double.parseDouble(scanner.nextLine().trim());
    }

    public abstract void xuat();
}