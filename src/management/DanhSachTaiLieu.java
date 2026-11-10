package management;

import management.interfaces.IQuanLy;
import model.enums.LoaiTaiLieu;
import model.factory.TaiLieuFactory;
import model.tailieu.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DanhSachTaiLieu implements IQuanLy {
    private ArrayList<TaiLieu> dsTaiLieu;
    private Scanner scanner;

    public DanhSachTaiLieu() {
        this.dsTaiLieu = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public ArrayList<TaiLieu> getDsTaiLieu() { return dsTaiLieu; }

    @Override
    public void them() {
        System.out.println("\n--- THÊM TÀI LIỆU MỚI ---");
        System.out.println("1. Thêm Sách | 2. Thêm Tạp chí | 3. Thêm Báo");
        System.out.print("Chọn loại: ");
        int chon = Integer.parseInt(scanner.nextLine());
        TaiLieu tl = null;
        if (chon == 1) tl = TaiLieuFactory.createTaiLieu(LoaiTaiLieu.SACH);
        else if (chon == 2) tl = TaiLieuFactory.createTaiLieu(LoaiTaiLieu.TAPCHI);
        else if (chon == 3) tl = TaiLieuFactory.createTaiLieu(LoaiTaiLieu.BAO);

        if (tl != null) {
            tl.nhap(scanner);
            dsTaiLieu.add(tl);
            System.out.println("✅ Thêm tài liệu thành công!");
        }
    }

    @Override
    public void xuatDanhSach() {
        if (dsTaiLieu.isEmpty()) {
            System.out.println("📂 Danh sách tài liệu trống!");
            return;
        }
        System.out.println("\n========== DANH SÁCH TÀI LIỆU (ĐANG HOẠT ĐỘNG) ==========");
        for (TaiLieu tl : dsTaiLieu) {
            if (tl.isTrangThai()) tl.xuat();
        }
    }

    @Override
    public void xoa() {
        System.out.print("Nhập mã tài liệu cần xóa: ");
        String ma = scanner.nextLine().trim();
        for (TaiLieu tl : dsTaiLieu) {
            if (tl.getMaTaiLieu().equalsIgnoreCase(ma) && tl.isTrangThai()) {
                tl.setTrangThai(false);
                System.out.println("✅ Xóa mềm thành công!");
                return;
            }
        }
        System.out.println("❌ Không tìm thấy mã tài liệu!");
    }

    @Override
    public void sua() {
        System.out.print("Nhập mã tài liệu cần sửa: ");
        String ma = scanner.nextLine().trim();
        for (TaiLieu tl : dsTaiLieu) {
            if (tl.getMaTaiLieu().equalsIgnoreCase(ma) && tl.isTrangThai()) {
                tl.nhap(scanner);
                System.out.println("✅ Cập nhật thành công!");
                return;
            }
        }
        System.out.println("❌ Không tìm thấy mã tài liệu!");
    }

    @Override
    public void timKiem() {
        System.out.print("Nhập từ khóa cần tìm (Mã / NXB / Tác giả / Thể loại): ");
        String kw = scanner.nextLine().trim().toLowerCase();

        List<TaiLieu> res = dsTaiLieu.stream()
                .filter(TaiLieu::isTrangThai)
                .filter(tl -> tl.getMaTaiLieu().toLowerCase().contains(kw)
                        || tl.getNhaXuatBan().toLowerCase().contains(kw)
                        || (tl instanceof Sach && ((Sach) tl).getTenTacGia().toLowerCase().contains(kw))
                        || (tl instanceof Sach && ("sách".contains(kw) || "sach".contains(kw)))
                        || (tl instanceof TapChi && ("tạp chí".contains(kw) || "tapchi".contains(kw) || "tạp chi".contains(kw)))
                        || (tl instanceof Bao && ("báo".contains(kw) || "bao".contains(kw))))
                .collect(Collectors.toList());

        if (res.isEmpty()) {
            System.out.println("❌ Không tìm thấy kết quả phù hợp!");
        } else {
            System.out.println("🔎 Kết quả tìm kiếm:");
            res.forEach(TaiLieu::xuat);
        }
    }

    public void sapXepTheoGia(boolean tangDan) {
        dsTaiLieu.sort(tangDan ? Comparator.comparingDouble(TaiLieu::getGia) : Comparator.comparingDouble(TaiLieu::getGia).reversed());
        System.out.println("✅ Đã sắp xếp danh sách theo Giá tiền!");
        xuatDanhSach();
    }

    public void sapXepTheoMa() {
        dsTaiLieu.sort(Comparator.comparing(TaiLieu::getMaTaiLieu));
        System.out.println("✅ Đã sắp xếp danh sách theo Mã tài liệu!");
        xuatDanhSach();
    }

    public void thongKeLoaiTaiLieu() {
        int soSach = 0, soTapChi = 0, soBao = 0;
        for (TaiLieu tl : dsTaiLieu) {
            if (!tl.isTrangThai()) continue;
            if (tl instanceof Sach) soSach++;
            else if (tl instanceof TapChi) soTapChi++;
            else if (tl instanceof Bao) soBao++;
        }
        System.out.println("\n📊 --- THỐNG KÊ KHO TÀI LIỆU ---");
        System.out.println("📚 Tổng số Sách: " + soSach);
        System.out.println("📰 Tổng số Tạp chí: " + soTapChi);
        System.out.println("🗞️ Tổng số Báo: " + soBao);
        System.out.println("📦 Tổng cộng: " + (soSach + soTapChi + soBao) + " loại tài liệu.");
    }

    public void xuatBaoCaoCSV(String duongDanCSV) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(duongDanCSV), StandardCharsets.UTF_8))) {
            bw.write("\uFEFF"); // Ghi dấu BOM để Excel hiển thị đúng tiếng Việt
            bw.write("LOAI,MA_TAI_LIEU,NHA_XUAT_BAN,GIA_TIEN,TONG_SO_LUONG,DANG_MUON\n");
            for (TaiLieu tl : dsTaiLieu) {
                if (tl.isTrangThai()) {
                    String loai = (tl instanceof Sach) ? "SACH" : (tl instanceof TapChi) ? "TAPCHI" : "BAO";
                    bw.write(String.format("%s,%s,\"%s\",%.0f,%d,%d\n",
                            loai, tl.getMaTaiLieu(), tl.getNhaXuatBan(), tl.getGia(), tl.getSoLuongTong(), tl.getSoLuongDangMuon()));
                }
            }
            System.out.println("✅ Đã xuất báo cáo CSV thành công ra file: " + duongDanCSV);
        } catch (Exception e) {
            System.err.println("❌ Lỗi xuất báo cáo CSV: " + e.getMessage());
        }
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

                if ("SACH".equals(loai) && p.length >= 9) {
                    Sach s = new Sach(p[1], p[2], Double.parseDouble(p[3]), p[4], Integer.parseInt(p[5]));
                    s.setTrangThai(Boolean.parseBoolean(p[6]));
                    s.setSoLuongTong(Integer.parseInt(p[7]));
                    s.setSoLuongDangMuon(Integer.parseInt(p[8]));
                    dsTaiLieu.add(s);
                } else if ("TAPCHI".equals(loai) && p.length >= 9) {
                    TapChi tc = new TapChi(p[1], p[2], Double.parseDouble(p[3]), Integer.parseInt(p[4]), Integer.parseInt(p[5]));
                    tc.setTrangThai(Boolean.parseBoolean(p[6]));
                    tc.setSoLuongTong(Integer.parseInt(p[7]));
                    tc.setSoLuongDangMuon(Integer.parseInt(p[8]));
                    dsTaiLieu.add(tc);
                } else if ("BAO".equals(loai) && p.length >= 8) {
                    Bao b = new Bao(p[1], p[2], Double.parseDouble(p[3]), p[4]);
                    b.setTrangThai(Boolean.parseBoolean(p[5]));
                    b.setSoLuongTong(Integer.parseInt(p[6]));
                    b.setSoLuongDangMuon(Integer.parseInt(p[7]));
                    dsTaiLieu.add(b);
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Lỗi đọc file tài liệu: " + e.getMessage());
        }
    }

    public void ghiRaFile(String duongDan) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(duongDan), StandardCharsets.UTF_8))) {
            bw.write("# LOAI|ma|nxb|gia|ext1|ext2|trangThai|soLuongTong|soLuongDangMuon\n");
            for (TaiLieu tl : dsTaiLieu) {
                if (tl instanceof Sach) {
                    Sach s = (Sach) tl;
                    bw.write(String.format("SACH|%s|%s|%.0f|%s|%d|%b|%d|%d\n",
                            s.getMaTaiLieu(), s.getNhaXuatBan(), s.getGia(), s.getTenTacGia(), s.getSoTrang(), s.isTrangThai(), s.getSoLuongTong(), s.getSoLuongDangMuon()));
                } else if (tl instanceof TapChi) {
                    TapChi tc = (TapChi) tl;
                    bw.write(String.format("TAPCHI|%s|%s|%.0f|%d|%d|%b|%d|%d\n",
                            tc.getMaTaiLieu(), tc.getNhaXuatBan(), tc.getGia(), tc.getSoPhatHanh(), tc.getThangPhatHanh(), tc.isTrangThai(), tc.getSoLuongTong(), tc.getSoLuongDangMuon()));
                } else if (tl instanceof Bao) {
                    Bao b = (Bao) tl;
                    bw.write(String.format("BAO|%s|%s|%.0f|%s|%b|%d|%d\n",
                            b.getMaTaiLieu(), b.getNhaXuatBan(), b.getGia(), b.getNgayPhatHanh(), b.isTrangThai(), b.getSoLuongTong(), b.getSoLuongDangMuon()));
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Lỗi ghi file tài liệu: " + e.getMessage());
        }
    }
}