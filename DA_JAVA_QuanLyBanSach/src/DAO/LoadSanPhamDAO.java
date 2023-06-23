/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.LoadSanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author HP
 */
public class LoadSanPhamDAO {
        public static ArrayList<LoadSanPham> getLoadSanPham()
        {
        ArrayList<LoadSanPham> dsSach = new ArrayList<>();
        ConnectionDB conet = new ConnectionDB();
        String query = "SELECT * FROM TIMKIEMLOADSANPHAM";

        try (Connection connection = conet.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            //preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int maSach = resultSet.getInt("MASACH");
                String tenSach = resultSet.getString("TENSACH").trim();
                float donGiaBan = resultSet.getFloat("DONGIABAN");
                int soLuongTon = resultSet.getInt("SLTON");
                String moTa = resultSet.getString("MOTA").trim();
                String tenNXB = resultSet.getString("TENNXB").trim();
                String tenTL = resultSet.getString("TENTL").trim();
                String tenTG = resultSet.getString("TENTG").trim();
                LoadSanPham employee = new LoadSanPham(maSach,tenSach,donGiaBan,soLuongTon,moTa,tenNXB,tenTL,tenTG);
                dsSach.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSach;
    }
        
        public ArrayList<LoadSanPham> getDanhSachSPSearch(String txtSearch) {
        ArrayList<LoadSanPham> dsSP = new ArrayList<>();
        ConnectionDB conet = new ConnectionDB();
        String query = "SELECT * FROM TIMKIEMLOADSANPHAM WHERE TENSACH LIKE N'%"+txtSearch+"%' "+ " OR DONGIABAN LIKE '%"+txtSearch+"%' "+ "OR TENNXB LIKE N'%"+txtSearch+"%' "+ " OR TENTL LIKE N'%"+txtSearch+"%' "+ " OR TENTG LIKE N'%"+txtSearch+"%';";
        try (Connection connection = conet.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            //preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int maSach = resultSet.getInt("MASACH");
                String tenSach = resultSet.getString("TENSACH").trim();
                float donGiaBan = resultSet.getFloat("DONGIABAN");
                int soLuongTon = resultSet.getInt("SLTON");
                String moTa = resultSet.getString("MOTA").trim();
                String tenNXB = resultSet.getString("TENNXB").trim();
                String tenTL = resultSet.getString("TENTL").trim();
                String tenTG = resultSet.getString("TENTG").trim();
                LoadSanPham employee = new LoadSanPham(maSach,tenSach,donGiaBan,soLuongTon,moTa,tenNXB,tenTL,tenTG);
                dsSP.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsSP;
    }
}
