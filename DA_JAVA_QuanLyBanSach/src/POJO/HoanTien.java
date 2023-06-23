/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

import java.time.LocalDate;

/**
 *
 * @author Maitr
 */
public class HoanTien {
     private int maDH;
    private int maSach;
    private LocalDate ngayHoanTien;
    private String lyDoHoanTien;

    public HoanTien(int maDH, int maSach, LocalDate ngayHoanTien, String lyDoHoanTien) {
        this.maDH = maDH;
        this.maSach = maSach;
        this.ngayHoanTien = ngayHoanTien;
        this.lyDoHoanTien = lyDoHoanTien;
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

    public LocalDate getNgayHoanTien() {
        return ngayHoanTien;
    }

    public void setNgayHoanTien(LocalDate ngayHoanTien) {
        this.ngayHoanTien = ngayHoanTien;
    }

    public String getLyDoHoanTien() {
        return lyDoHoanTien;
    }

    public void setLyDoHoanTien(String lyDoHoanTien) {
        this.lyDoHoanTien = lyDoHoanTien;
    }

}
