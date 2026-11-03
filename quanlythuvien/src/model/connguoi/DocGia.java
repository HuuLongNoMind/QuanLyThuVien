package model.connguoi;

import java.util.Scanner;

public class DocGia extends ConNguoi {
    private String maDocGia;
    private String ngayHetHanThe;

    public DocGia() { super(); }

    public DocGia(String hoTen, String ngaySinh, String gioiTinh, String sdt, String maDocGia, String ngayHetHanThe) {
        super(hoTen, ngaySinh, gioiTinh, sdt);
        this.maDocGia = maDocGia;
        this.ngayHetHanThe = ngayHetHanThe;
    }

    public String getMaDocGia() { return maDocGia; }
    public String getNgayHetHanThe() { return ngayHetHanThe; }

    @Override
    public void nhap(Scanner scanner) {
        System.out.print("Nhập mã độc giả: ");
        this.maDocGia = scanner.nextLine().trim();
        super.nhap(scanner);
        System.out.print("Nhập ngày hết hạn thẻ (dd/MM/yyyy): ");
        this.ngayHetHanThe = scanner.nextLine().trim();
    }

    @Override
    public void xuat() {
        System.out.printf("👤 [ĐỘC GIẢ] Mã: %-6s | Họ tên: %-18s | Giới tính: %-4s | SĐT: %-10s | Hạn thẻ: %-10s | TT: %s\n",
                maDocGia, hoTen, gioiTinh, sdt, ngayHetHanThe, trangThai ? "Hoạt động" : "Đã khóa");
    }

    @Override
    public String toFileString() {
        return String.format("DOCGIA|%s|%s|%s|%s|%s|%s|%b", maDocGia, hoTen, ngaySinh, gioiTinh, sdt, ngayHetHanThe, trangThai);
    }
}