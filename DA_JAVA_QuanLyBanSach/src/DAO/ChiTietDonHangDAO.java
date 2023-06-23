/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.ChiTietDonHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author HP
 */
public class ChiTietDonHangDAO {
    public static ArrayList<ChiTietDonHang> getChiTietDonHang()
        {
        ArrayList<ChiTietDonHang> dsSach = new ArrayList<>();
        ConnectionDB conet = new ConnectionDB();
        String query = "SELECT * FROM CHITIETDH";

        try (Connection connection = conet.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            //preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int maDH = resultSet.getInt("MADH");
                int maSach = resultSet.getInt("MASACH");
                int soLuong = resultSet.getInt("SOLUONG");
                float tongTien = resultSet.getFloat("TONGTIEN");
                ChiTietDonHang employee = new ChiTietDonHang(maDH,maSach,soLuong,tongTien);
                dsSach.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSach;
    }
    public static ArrayList<ChiTietDonHang> getChiTietDonHangBangMaDH(int MaDH)
        {
        ArrayList<ChiTietDonHang> dsSach = new ArrayList<>();
        ConnectionDB conet = new ConnectionDB();
        String query = "SELECT * FROM CHITIETDH WHERE MADH = ?";

        try {
            Connection connection = conet.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, MaDH);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int maDH = resultSet.getInt("MADH");
                int maSach = resultSet.getInt("MASACH");
                int soLuong = resultSet.getInt("SOLUONG");
                float tongTien = resultSet.getFloat("TONGTIEN");
                ChiTietDonHang employee = new ChiTietDonHang(maDH,maSach,soLuong,tongTien);
                dsSach.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSach;
    }
}
