/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.LocationDao;
import java.util.List;
import models.Location;
import tools.Koneksi;
/**
 *
 * @author ZFH
 */

public class LocationController {
     private LocationDao locationDao;
    
    public LocationController(){
        this.locationDao = new LocationDao(new Koneksi().getConnection());
    }
   /**
    * 
    * @return 
    */
    public List<Location> getDataLocations(){
        return locationDao.getSearchLocation("");
    }
    
    /**
     * 
     * @param location_id
     * @param street_address
     * @param postal_code
     * @param city
     * @param state_province
     * @param country_id
     * @return 
     */
    public String addLocation(String location_id, String street_address, String postal_code, String city, String state_province, String country_id){
        int dLocation_id = Integer.parseInt(location_id);
        Location location = new Location(dLocation_id, street_address, postal_code, city, state_province, country_id);
        return locationDao.insertUpdateLocation(location,true)?"Sukses Menambahkan":"Gagal Menambahkan";
    }
    
    /**
     * 
     * @param location_id
     * @param street_address
     * @param postal_code
     * @param city
     * @param state_province
     * @param country_id
     * @return 
     */
    public String editLocation(String location_id, String street_address, String postal_code, String city, String state_province, String country_id){
        int dLocation_id = Integer.parseInt(location_id);
        Location location = new Location(dLocation_id, street_address, postal_code, city, state_province, country_id);
        return locationDao.insertUpdateLocation(location,false)?"Sukses Mengedit":"Gagal Mengedit";
    }
    
    /**
     * Method ini digunakan untuk mencari data pada Location dengan menggunakan keyword
     * @param name parameter yang digunakan sebagai keyword
     * @return 
     */
    public List<Location> findLocation(String name){
        return locationDao.getSearchLocation(name);
    }
    
    public Location getDataLocation(String location_id){
        int dLocation_id = Integer.parseInt(location_id);
        return locationDao.getLocation(dLocation_id);
    }
    
    /**
     * 
     * @param location_id
     * @return 
     */
    public String deleteLocation(String location_id){
        int dLocation_id = Integer.parseInt(location_id);
        return locationDao.deleteLocation(dLocation_id)?"Sukses Menghapus":"Gagal Menghapus";

    }
    

     }
