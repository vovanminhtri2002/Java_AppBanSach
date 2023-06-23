/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author Maitr
 */
public class LoaiDonHang {
    private int maLDH;
    private String tenLDH;

    public LoaiDonHang(int maLDH, String tenLDH) {
        this.maLDH = maLDH;
        this.tenLDH = tenLDH;
    }

    public int getMaLDH() {
        return maLDH;
    }

    public void setMaLDH(int maLDH) {
        this.maLDH = maLDH;
    }

    public String getTenLDH() {
        return tenLDH;
    }

    public void setTenLDH(String tenLDH) {
        this.tenLDH = tenLDH;
    }
    
}
