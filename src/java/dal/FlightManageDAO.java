/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import model.Flights;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author user
 */
public class FlightManageDAO extends DBConnect {

    public List<Flights> getAllFlights() {
        List<Flights> list = new ArrayList<>();
        String sql = "select * from Flight";
        try {
            PreparedStatement prepare = conn.prepareStatement(sql);
            ResultSet resultSet = prepare.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");        
                int minutes = resultSet.getInt("minutes");
                int departureAirportId = resultSet.getInt("departureAirportId");
                int destinationAirportId = resultSet.getInt("destinationAirportId");
               
                list.add(new Flights(id, minutes, departureAirportId, destinationAirportId));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return list;
    }
    
   
    
    
    
    
    
    
    
     
    
    
    
    }

