/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class PlaneCategory {
    private int id;
    private String name;
    private String image;
    private String info;
    private int Airlineid;

    public PlaneCategory() {
    }

    public PlaneCategory(int id) {
        this.id = id;
    }

    public PlaneCategory(String name, String image, String info, int Airlineid) {
        this.name = name;
        this.image = image;
        this.info = info;
        this.Airlineid = Airlineid;
    }

    public PlaneCategory(int id, String name, String image, String info, int Airlineid) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.info = info;
        this.Airlineid = Airlineid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAirlineid() {
        return Airlineid;
    }

    public void setAirlineid(int Airlineid) {
        this.Airlineid = Airlineid;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    
    
}
