package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TienIchInput {
    private static final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String nhapChuoi(String thongBao) {
        System.out.print(thongBao);
        return sc.nextLine().trim();
    }

    public static int nhapSoNguyen(String thongBao, int min, int max) {
        while (true) {
            try {
                System.out.print(thongBao);
                int val = Integer.parseInt(sc.nextLine().trim());
                if (val >= min && val <= max) return val;
                System.out.println("❌ Lựa chọn phải từ " + min + " đến " + max + "!");
            } catch (Exception e) {
                System.out.println("❌ Vui lòng nhập số nguyên hợp lệ!");
            }
        }
    }

    public static LocalDate nhapNgay(String thongBao) {
        while (true) {
            try {
                System.out.print(thongBao + " (dd/MM/yyyy): ");
                return LocalDate.parse(sc.nextLine().trim(), FMT);
            } catch (Exception e) {
                System.out.println("❌ Định dạng ngày sai (Ví dụ đúng: 04/11/2026)!");
            }
        }
    }

    public static LocalDate nhapNgayKhongTruoc(String thongBao, LocalDate ngayMoc) {
        while (true) {
            LocalDate ngay = nhapNgay(thongBao);
            if (!ngay.isBefore(ngayMoc)) return ngay;
            System.out.println("❌ Ngày hạn trả không được trước ngày mượn!");
        }
    }
}