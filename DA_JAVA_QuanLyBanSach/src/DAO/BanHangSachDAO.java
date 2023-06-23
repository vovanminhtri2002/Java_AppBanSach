/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.BanHangSach;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author HP
 */
public class BanHangSachDAO {
    public static ArrayList<BanHangSach> getBanHangSach()
        {
        ArrayList<BanHangSach> dsSach = new ArrayList<>();
        ConnectionDB conet = new ConnectionDB();
        String query = "SELECT MASACH, TENSACH, SLTON, DONGIABAN  FROM SACH";

        try (Connection connection = conet.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            //preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int maSach = resultSet.getInt("MASACH");
                String tenSach = resultSet.getString("TENSACH").trim();
                int soLuongTon = resultSet.getInt("SLTON");
                float donGiaBan = resultSet.getFloat("DONGIABAN");
                BanHangSach employee = new BanHangSach(maSach,tenSach,soLuongTon,donGiaBan);
                dsSach.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSach;
    }
    public ArrayList<BanHangSach> getBanHangSachSearch(String txtSearch) {
        ArrayList<BanHangSach> dsSach= new ArrayList<>();
        ConnectionDB conet = new ConnectionDB();
        String query = "SELECT * FROM TIMKIEMLOADSANPHAM WHERE TENSACH LIKE N'%"+txtSearch+"%';";
        try (Connection connection = conet.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            //preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int maSach = resultSet.getInt("MASACH");
                String tenSach = resultSet.getString("TENSACH").trim();
                int soLuongTon = resultSet.getInt("SLTON");
                float donGiaBan = resultSet.getFloat("DONGIABAN");
                BanHangSach employee = new BanHangSach(maSach,tenSach,soLuongTon,donGiaBan);
                dsSach.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSach;
    }
}
