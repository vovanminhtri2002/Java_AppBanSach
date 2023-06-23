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
public class ChiTietDonHang {
     private int maDH;
    private int maSach;
    private int soLuong;
    private float tongTien;

    @Override
    public String toString() {
        return "ChiTietDonHang{" + "maDH=" + maDH + ", maSach=" + maSach + ", soLuong=" + soLuong + ", tongTien=" + tongTien + '}';
    }

    public int getMaDH() {
        return maDH;
    }

    public void setMaDH(int maDH) {
        this.maDH = maDH;
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

    public float getTongTien() {
        return tongTien;
    }

    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }

    public ChiTietDonHang(int maDH, int maSach, int soLuong, float tongTien) {
        this.maDH = maDH;
        this.maSach = maSach;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
    }
}