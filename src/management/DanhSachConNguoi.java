package management;

import management.interfaces.IQuanLy;
import model.connguoi.*;
import model.enums.LoaiConNguoi;
import model.factory.ConNguoiFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class DanhSachConNguoi implements IQuanLy {
    private ArrayList<ConNguoi> dsConNguoi;
    private Scanner scanner;

    public DanhSachConNguoi() {
        this.dsConNguoi = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public ArrayList<ConNguoi> getDsConNguoi() { return dsConNguoi; }

    @Override
    public void them() {
        System.out.println("\n--- THÊM CON NGƯỜI MỚI ---");
        System.out.println("1. Độc giả | 2. Nhân viên | 3. Thủ thư");
        int chon = Integer.parseInt(scanner.nextLine());
        ConNguoi cn = null;
        if (chon == 1) cn = ConNguoiFactory.getConNguoi(LoaiConNguoi.DOCGIA);
        else if (chon == 2) cn = ConNguoiFactory.getConNguoi(LoaiConNguoi.NHANVIEN);
        else if (chon == 3) cn = ConNguoiFactory.getConNguoi(LoaiConNguoi.THUTHU);

        if (cn != null) {
            cn.nhap(scanner);
            dsConNguoi.add(cn);
            System.out.println("✅ Thêm thành công!");
        }
    }

    @Override
    public void xuatDanhSach() {
        if (dsConNguoi.isEmpty()) {
            System.out.println("📂 Danh sách con người trống!");
            return;
        }
        System.out.println("\n========== DANH SÁCH CON NGƯỜI (ĐANG HOẠT ĐỘNG) ==========");
        for (ConNguoi cn : dsConNguoi) {
            if (cn.isTrangThai()) cn.xuat();
        }
    }

    @Override
    public void xoa() {
        System.out.print("Nhập tên người cần khóa/xóa: ");
        String ten = scanner.nextLine().trim();
        for (ConNguoi cn : dsConNguoi) {
            if (cn.getHoTen().equalsIgnoreCase(ten) && cn.isTrangThai()) {
                cn.setTrangThai(false);
                System.out.println("✅ Đã chuyển trạng thái nghỉ việc/khóa thẻ!");
                return;
            }
        }
        System.out.println("❌ Không tìm thấy!");
    }

    @Override
    public void sua() {
        System.out.print("Nhập tên người cần sửa: ");
        String ten = scanner.nextLine().trim();
        for (ConNguoi cn : dsConNguoi) {
            if (cn.getHoTen().equalsIgnoreCase(ten) && cn.isTrangThai()) {
                cn.nhap(scanner);
                System.out.println("✅ Cập nhật thành công!");
                return;
            }
        }
        System.out.println("❌ Không tìm thấy!");
    }

    @Override
    public void timKiem() {
        System.out.println("\n--- TÌM KIẾM CON NGƯỜI ---");
        System.out.println("1. Tìm theo Mã con người (Mã ĐG / Mã NV / Mã TT)");
        System.out.println("2. Tìm theo Họ tên (Gần đúng)");
        System.out.println("3. Tìm theo Giới tính (Nam / Nữ)");
        System.out.print("👉 Chọn lựa chọn tìm kiếm (1-3): ");

        int chon;
        try {
            chon = Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception e) {
            System.out.println("❌ Lựa chọn không hợp lệ!");
            return;
        }

        switch (chon) {
            case 1:
                System.out.print("Nhập mã con người cần tìm: ");
                String ma = scanner.nextLine().trim();
                boolean timThayMa = false;
                for (ConNguoi cn : dsConNguoi) {
                    if (cn.isTrangThai() && layMa(cn).equalsIgnoreCase(ma)) {
                        cn.xuat();
                        timThayMa = true;
                        break;
                    }
                }
                if (!timThayMa) System.out.println("❌ Không tìm thấy con người có mã này!");
                break;

            case 2:
                System.out.print("Nhập họ tên cần tìm: ");
                String ten = scanner.nextLine().trim().toLowerCase();
                boolean timThayTen = false;
                System.out.println("🔎 Kết quả tìm kiếm theo họ tên [" + ten + "]:");
                for (ConNguoi cn : dsConNguoi) {
                    if (cn.isTrangThai() && cn.getHoTen().toLowerCase().contains(ten)) {
                        cn.xuat();
                        timThayTen = true;
                    }
                }
                if (!timThayTen) System.out.println("❌ Không tìm thấy ai có tên phù hợp!");
                break;

            case 3:
                System.out.print("Nhập giới tính cần tìm (Nam/Nữ): ");
                String gt = scanner.nextLine().trim();
                boolean timThayGT = false;
                System.out.println("🔎 Kết quả tìm kiếm theo giới tính [" + gt + "]:");
                for (ConNguoi cn : dsConNguoi) {
                    if (cn.isTrangThai() && cn.getGioiTinh().equalsIgnoreCase(gt)) {
                        cn.xuat();
                        timThayGT = true;
                    }
                }
                if (!timThayGT) System.out.println("❌ Không tìm thấy ai có giới tính này!");
                break;

            default:
                System.out.println("❌ Lựa chọn không hợp lệ!");
                break;
        }
    }
    private String layMa(ConNguoi cn) {
        if (cn instanceof DocGia) return ((DocGia) cn).getMaDocGia();
        if (cn instanceof NhanVien) return ((NhanVien) cn).getMaNhanVien();
        return "";
    }

    public void docTuFile(String duongDan) {
        File file = new File(duongDan);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] p = line.split("\\|", -1);
                if (p.length < 6) continue;
                String loai = p[0].toUpperCase();
                if ("DOCGIA".equals(loai) && p.length >= 8) {
                    DocGia dg = new DocGia(p[2], p[3], p[4], p[5], p[1], p[6]);
                    dg.setTrangThai(Boolean.parseBoolean(p[7]));
                    dsConNguoi.add(dg);
                } else if ("NHANVIEN".equals(loai) && p.length >= 9) {
                    NhanVien nv = new NhanVien(p[2], p[3], p[4], p[5], p[1], p[6], Double.parseDouble(p[7]));
                    nv.setTrangThai(Boolean.parseBoolean(p[8]));
                    dsConNguoi.add(nv);
                } else if ("THUTHU".equals(loai) && p.length >= 10) {
                    ThuThu tt = new ThuThu(p[2], p[3], p[4], p[5], p[1], p[6], Double.parseDouble(p[7]), p[8]);
                    tt.setTrangThai(Boolean.parseBoolean(p[9]));
                    dsConNguoi.add(tt);
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Lỗi đọc file con người: " + e.getMessage());
        }
    }

    public void ghiRaFile(String duongDan) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(duongDan), StandardCharsets.UTF_8))) {
            bw.write("# LOAI|ma|hoTen|ngaySinh|gioiTinh|sdt|ext1|ext2|ext3|trangThai\n");
            for (ConNguoi cn : dsConNguoi) {
                bw.write(cn.toFileString() + "\n");
            }
        } catch (Exception e) {
            System.err.println("❌ Lỗi ghi file con người: " + e.getMessage());
        }
    }
}