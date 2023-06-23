/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author HP
 */
public class LoadSanPham {
    private int maSach;
    private String tenSach;
    private float donGiaBan;
    private int soLuongTon;
    private String moTa;
    private String tenNXB;
    private String tenTL;
    private String tenTG;

    @Override
    public String toString() {
        return "LOADSANPHAMSACH{" + "maSach=" + maSach + ", tenSach=" + tenSach + ", donGiaBan=" + donGiaBan + ", soLuongTon=" + soLuongTon + ", moTa=" + moTa + ", tenNXB=" + tenNXB + ", tenTL=" + tenTL + ", tenTG=" + tenTG + '}';
    }

    public LoadSanPham(int maSach, String tenSach, float donGiaBan, int soLuongTon, String moTa, String tenNXB, String tenTL, String tenTG) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.donGiaBan = donGiaBan;
        this.soLuongTon = soLuongTon;
        this.moTa = moTa;
        this.tenNXB = tenNXB;
        this.tenTL = tenTL;
        this.tenTG = tenTG;
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

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getTenNXB() {
        return tenNXB;
    }

    public void setTenNXB(String tenNXB) {
        this.tenNXB = tenNXB;
    }

    public String getTenTL() {
        return tenTL;
    }

    public void setTenTL(String tenTL) {
        this.tenTL = tenTL;
    }

    public String getTenTG() {
        return tenTG;
    }

    public void setTenTG(String tenTG) {
        this.tenTG = tenTG;
    } 
}
