package model.factory;

import model.enums.LoaiTaiLieu;
import model.tailieu.*;

public class TaiLieuFactory {
    public static TaiLieu createTaiLieu(LoaiTaiLieu loai) {
        if (loai == null) return null;
        switch (loai) {
            case SACH: return new Sach();
            case TAPCHI: return new TapChi();
            case BAO: return new Bao();
            default: return null;
        }
    }
}