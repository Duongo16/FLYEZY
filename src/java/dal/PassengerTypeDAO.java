/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.PassengerType;

public class PassengerTypeDAO extends DBConnect{
    public String getPassengerTypeNameById(int id) {
        String sql = "SELECT name FROM Passenger_Types WHERE id = "+id;
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                return name;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    public List<PassengerType> getAllPassengerType() {
        List<PassengerType> list = new ArrayList<>();
        String sql = "select * from Passenger_Types";
        try {
            PreparedStatement prepare = conn.prepareStatement(sql);
            ResultSet resultSet = prepare.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                float price = resultSet.getFloat("price");
                list.add(new PassengerType(id, name,price));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
    public float getPassengerTypePriceNameById(int id) {
        String sql = "SELECT price FROM Passenger_Types WHERE id = "+id;
        try {
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                float price = rs.getFloat("price");
                return price;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }
    
    public static void main(String[] args) {
        PassengerTypeDAO ptd = new PassengerTypeDAO();
        System.out.println(ptd.getPassengerTypePriceNameById(2));
    }
}
