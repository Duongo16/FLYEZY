/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;


public class Flights {
    private int id;
    private int minutes;
    private int departureAirportId;
    private int destinationAirportId;
    private int statusId;
 

    public Flights() {
    }


    
    
    public Flights(int id, int minutes, int departureAirportId, int destinationAirportId){
        this.id = id;
        this.minutes = minutes;
        this.departureAirportId = departureAirportId;
        this.destinationAirportId = destinationAirportId;   
    }
    
    
    
    public Flights(int minutes, int departureAirportId, int destinationAirportId) {
        this.minutes = minutes;
        this.departureAirportId = departureAirportId;
        this.destinationAirportId = destinationAirportId;
    
    }

    public Flights(int id, int minutes, int departureAirportId, int destinationAirportId, int statusId) {
        this.id = id;
        this.minutes = minutes;
        this.departureAirportId = departureAirportId;
        this.destinationAirportId = destinationAirportId;
        this.statusId = statusId;
    }
         

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
  

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getDepartureAirportId() {
        return departureAirportId;
    }

    public void setDepartureAirportId(int departureAirportId) {
        this.departureAirportId = departureAirportId;
    }

    public int getDestinationAirportId() {
        return destinationAirportId;
    }

    public void setDestinationAirportId(int destinationAirportId) {
        this.destinationAirportId = destinationAirportId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
    


   
    
}
