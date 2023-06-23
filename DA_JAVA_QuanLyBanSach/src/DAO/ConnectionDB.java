package DAO;

import java.sql.*;
public class ConnectionDB {
    private Connection  connection;
    public  Connection getConnection() {
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=QL_BANSACH";
            String user = "sa";
            String pass = "123";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Kết nối csdl thành công");
        } catch (Exception e) {
            System.out.println("Kết nối csdl không thành công");
        }
        return connection;
    }
    
    public void close(){
        try {
            this.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ResultSet executeQuery(String sql){
        ResultSet rs = null;
        try {
            Statement stm = connection.createStatement();
            rs = stm.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public int executeUpdate(String sql){
        int i = -1;
        try {
            Statement stm = connection.createStatement();
            i = stm.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
}
