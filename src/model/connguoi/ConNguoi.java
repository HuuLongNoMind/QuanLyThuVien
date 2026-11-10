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

        // Ràng buộc dữ liệu giới tính
        int luaChonGT = 0;
        do {
            System.out.print("Chọn giới tính (1. Nam | 2. Nữ | 3. Khác): ");
            try {
                luaChonGT = Integer.parseInt(scanner.nextLine().trim());
                switch (luaChonGT) {
                    case 1:
                        this.gioiTinh = "Nam";
                        break;
                    case 2:
                        this.gioiTinh = "Nữ";
                        break;
                    case 3:
                        this.gioiTinh = "Khác";
                        break;
                    default:
                        System.out.println("Lỗi: Vui lòng chỉ chọn 1, 2 hoặc 3!");
                        luaChonGT = 0; // Reset về 0 để lặp lại
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập định dạng số (1, 2 hoặc 3)!");
                luaChonGT = 0;
            }
        } while (luaChonGT == 0);

        System.out.print("Nhập số điện thoại: ");
        this.sdt = scanner.nextLine().trim();
    }

    public abstract void xuat();
}