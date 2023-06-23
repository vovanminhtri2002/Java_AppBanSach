/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

import java.math.BigDecimal;

/**
 *
 * @author Maitr
 */
public class GioHang {
    private int maKH;
    private int maSach;
    private int soLuong;
    private BigDecimal tongThanhTien;
    private int tongSanPham;

    public GioHang(int maKH, int maSach, int soLuong, BigDecimal tongThanhTien, int tongSanPham) {
        this.maKH = maKH;
        this.maSach = maSach;
        this.soLuong = soLuong;
        this.tongThanhTien = tongThanhTien;
        this.tongSanPham = tongSanPham;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getTongThanhTien() {
        return tongThanhTien;
    }

    public void setTongThanhTien(BigDecimal tongThanhTien) {
        this.tongThanhTien = tongThanhTien;
    }

    public int getTongSanPham() {
        return tongSanPham;
    }

    public void setTongSanPham(int tongSanPham) {
        this.tongSanPham = tongSanPham;
    }
    
}
