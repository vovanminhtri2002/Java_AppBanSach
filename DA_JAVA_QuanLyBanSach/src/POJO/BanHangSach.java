/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author HP
 */
public class BanHangSach {
    private int maSach;
    private String tenSach;
    private int soLuongTon;
    private float donGiaBan;

    @Override
    public String toString() {
        return "BanHangSach{" + "maSach=" + maSach + ", tenSach=" + tenSach + ", soLuongTon=" + soLuongTon + ", donGiaBan=" + donGiaBan + '}';
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

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public float getDonGiaBan() {
        return donGiaBan;
    }

    public void setDonGiaBan(float donGiaBan) {
        this.donGiaBan = donGiaBan;
    }

    public BanHangSach(int maSach, String tenSach, int soLuongTon, float donGiaBan) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.soLuongTon = soLuongTon;
        this.donGiaBan = donGiaBan;
    }
}