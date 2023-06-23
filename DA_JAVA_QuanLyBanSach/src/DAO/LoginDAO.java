/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import POJO.NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author Maitr
 */
public class LoginDAO {

    public NhanVien getEmployeeByUsernameAndPassword(String username, String password, int quyen) {
        NhanVien employee = null;
        ConnectionDB conet = new ConnectionDB();
        String query = "SELECT *FROM dbo.GetNhanVienByUsernameAndPassword(?, ?, ?);";

        try (Connection connection = conet.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, quyen);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Lấy nhân viên thành công. Xin chào " + username);
                int maNV = resultSet.getInt("MaNV");
                String tenNV = resultSet.getString("TenNV").trim();
                String diaChi = resultSet.getString("DiaChi").trim();
                String soDienThoai = resultSet.getString("SODIENTHOAI").trim();
                String email = resultSet.getString("Email").trim();
                String gioiTinh = resultSet.getString("GioiTinh").trim();
                LocalDate ngaySinh = resultSet.getDate("NgaySinh").toLocalDate();
                int maCV = resultSet.getInt("MaCV");
                String soCCCD = resultSet.getString("SoCCCD").trim();
                String tentk = resultSet.getString("username").trim();
                employee = new NhanVien(maNV, tenNV, diaChi, soDienThoai, email, gioiTinh, ngaySinh, maCV, soCCCD, tentk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

}
