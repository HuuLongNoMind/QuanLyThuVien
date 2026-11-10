package model.tailieu;

import management.interfaces.IFileSerializable;
import java.util.Scanner;

public abstract class TaiLieu implements IFileSerializable {
    protected String maTaiLieu;
    protected String nhaXuatBan;
    protected double gia;
    protected boolean trangThai;
    protected int soLuongTong;
    protected int soLuongDangMuon;

    public TaiLieu() {
        this.trangThai = true;
        this.soLuongTong = 10;
        this.soLuongDangMuon = 0;
    }

    public TaiLieu(String maTaiLieu, String nhaXuatBan, double gia) {
        this.maTaiLieu = maTaiLieu;
        this.nhaXuatBan = nhaXuatBan;
        this.gia = gia;
        this.trangThai = true;
        this.soLuongTong = 10;
        this.soLuongDangMuon = 0;
    }

    public String getMaTaiLieu() { return maTaiLieu; }
    public void setMaTaiLieu(String maTaiLieu) { this.maTaiLieu = maTaiLieu; }
    public String getNhaXuatBan() { return nhaXuatBan; }
    public void setNhaXuatBan(String nhaXuatBan) { this.nhaXuatBan = nhaXuatBan; }
    public double getGia() { return gia; }
    public void setGia(double gia) { this.gia = gia; }
    public boolean isTrangThai() { return trangThai; }
    public void setTrangThai(boolean trangThai) { this.trangThai = trangThai; }

    public int getSoLuongTong() { return soLuongTong; }
    public void setSoLuongTong(int soLuongTong) { this.soLuongTong = soLuongTong; }
    public int getSoLuongDangMuon() { return soLuongDangMuon; }
    public void setSoLuongDangMuon(int soLuongDangMuon) { this.soLuongDangMuon = soLuongDangMuon; }

    public boolean conCoTheMuon() {
        return (soLuongTong - soLuongDangMuon) > 0;
    }

    public void nhap(Scanner scanner) {
        System.out.print("Nhập mã tài liệu: ");
        this.maTaiLieu = scanner.nextLine().trim();
        System.out.print("Nhập nhà xuất bản: ");
        this.nhaXuatBan = scanner.nextLine().trim();
        System.out.print("Nhập giá tiền: ");
        this.gia = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Nhập tổng số lượng nhập kho: ");
        this.soLuongTong = Integer.parseInt(scanner.nextLine().trim());
    }

    public abstract void xuat();
}