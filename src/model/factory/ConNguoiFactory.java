package model.factory;

import model.enums.LoaiConNguoi;
import model.connguoi.*;

public class ConNguoiFactory {
    public static ConNguoi getConNguoi(LoaiConNguoi loai) {
        if (loai == null) return null;
        switch (loai) {
            case DOCGIA: return new DocGia();
            case NHANVIEN: return new NhanVien();
            case THUTHU: return new ThuThu();
            default: return null;
        }
    }
}