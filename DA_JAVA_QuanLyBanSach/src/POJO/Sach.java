/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author Maitr
 */
public class Sach {
     private int maSach;
    private String tenSach;
    private float donGiaBan;
    private int soLuongTon;
    private String hinhAnh;
    private String moTa;
    private int maNXB;
    private int maTL;
    private int maTG;
    private String trangThai;

    public Sach(int maSach, String tenSach, float donGiaBan, int soLuongTon, String hinhAnh, String moTa, int maNXB, int maTL, int maTG, String trangThai) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.donGiaBan = donGiaBan;
        this.soLuongTon = soLuongTon;
        this.hinhAnh = hinhAnh;
        this.moTa = moTa;
        this.maNXB = maNXB;
        this.maTL = maTL;
        this.maTG = maTG;
        this.trangThai = trangThai;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public float getDonGiaBan() {
        return donGiaBan;
    }

    public void setDonGiaBan(float donGiaBan) {
        this.donGiaBan = donGiaBan;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getMaNXB() {
        return maNXB;
    }

    public void setMaNXB(int maNXB) {
        this.maNXB = maNXB;
    }

    public int getMaTL() {
        return maTL;
    }

    public void setMaTL(int maTL) {
        this.maTL = maTL;
    }

    public int getMaTG() {
        return maTG;
    }

    public void setMaTG(int maTG) {
        this.maTG = maTG;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
    
}
