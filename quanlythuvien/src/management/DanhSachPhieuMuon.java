package management;

import model.phieu.PhieuMuon;
import utils.QuanLyFileText;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DanhSachPhieuMuon {
    private final List<PhieuMuon> danhSach = new ArrayList<>();

    public List<PhieuMuon> getDanhSach() {
        return Collections.unmodifiableList(danhSach);
    }

    public PhieuMuon timTheoMa(String maPhieu) {
        if (maPhieu == null) return null;
        for (PhieuMuon p : danhSach) {
            if (p.getMaPhieu().equalsIgnoreCase(maPhieu.trim())) return p;
        }
        return null;
    }

    public void themPhieuMuon(PhieuMuon phieu) {
        if (phieu == null) throw new IllegalArgumentException("Phiếu mượn không được null!");
        if (timTheoMa(phieu.getMaPhieu()) != null) {
            throw new IllegalArgumentException("Mã phiếu đã tồn tại: " + phieu.getMaPhieu());
        }
        danhSach.add(phieu);
    }

    public PhieuMuon traSach(String maPhieu) {
        PhieuMuon phieu = timTheoMa(maPhieu);
        if (phieu == null) throw new IllegalArgumentException("Không tìm thấy mã phiếu: " + maPhieu);
        phieu.danhDauDaTra(LocalDate.now());
        return phieu;
    }

    public List<PhieuMuon> timTheoDocGia(String maDocGia) {
        List<PhieuMuon> res = new ArrayList<>();
        for (PhieuMuon p : danhSach) {
            if (p.getMaDocGia().equalsIgnoreCase(maDocGia.trim())) res.add(p);
        }
        return res;
    }

    public List<PhieuMuon> timTheoTaiLieu(String maTaiLieu) {
        List<PhieuMuon> res = new ArrayList<>();
        for (PhieuMuon p : danhSach) {
            if (p.getMaTaiLieu().equalsIgnoreCase(maTaiLieu.trim())) res.add(p);
        }
        return res;
    }

    public List<PhieuMuon> danhSachDangMuon() {
        List<PhieuMuon> res = new ArrayList<>();
        for (PhieuMuon p : danhSach) {
            if (!PhieuMuon.DA_TRA.equals(p.getTrangThai())) res.add(p);
        }
        return res;
    }

    public void docTuFile(String duongDan) {
        danhSach.clear();
        danhSach.addAll(QuanLyFileText.docDanhSachPhieu(duongDan));
    }

    public void ghiRaFile(String duongDan) {
        QuanLyFileText.ghiDanhSachPhieu(duongDan, danhSach);
    }

    public void hienThiTatCa() {
        if (danhSach.isEmpty()) {
            System.out.println("📂 Danh sách phiếu mượn đang trống.");
            return;
        }
        System.out.println("------------------------------------------------------------------------------------------------");
        for (PhieuMuon p : danhSach) System.out.println(p);
        System.out.println("------------------------------------------------------------------------------------------------");
    }
}