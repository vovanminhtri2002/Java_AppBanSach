/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author Maitr
 */
public class ChiTietPhieuGiao {
    int maPG, maSach, soLuong;

    public ChiTietPhieuGiao(int maPG, int maSach, int soLuong) {
        this.maPG = maPG;
        this.maSach = maSach;
        this.soLuong = soLuong;
    }

    public ChiTietPhieuGiao() {
    }

    public int getMaPG() {
        return maPG;
    }

    public void setMaPG(int maPG) {
        this.maPG = maPG;
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
}
