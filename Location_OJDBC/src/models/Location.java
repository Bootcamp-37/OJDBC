/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author ZFH
 */
public class Location {
      int location_id;
      String street_address;
      String postal_code;
      String city;
      String state_provice;
      String country_id;
public Location(){
    }
public Location(int location_id, String street_address, String postal_code, String city, String state_province, String country_id)
{
        this.location_id = location_id;
        this.street_address = street_address;
        this.postal_code = postal_code;
        this.city = city;
        this.state_provice = state_province;
        this.country_id = country_id;   
    }
    public int getLocalId() {
        return location_id;
    }

    public void setLocalId(int location_id) {
        this.location_id = location_id;
    }
    public String getAddress() {
        return street_address;
    }

    public void setAddress(String street_address) {
        this.street_address = street_address;
    }
    public String getPost() {
        return postal_code;
    }

    public void setPost(String postal_code) {
        this.postal_code = postal_code;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getProvince() {
        return state_provice;
    }

    public void setProvince(String state_province) {
        this.state_provice = state_province;
    }
    public String getCountry() {
        return country_id;
    }

    public void setCountry(String country_id) {
        this.country_id = country_id;
    }

    public Location(int location_id, String street_address) {
        this.location_id = location_id;
        this.street_address = street_address;
    }
    
}
