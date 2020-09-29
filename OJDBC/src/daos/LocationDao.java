/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Location;
/**
 *
 * @author ZFH
 */
public class LocationDao {
  private Connection connection;

    
    public LocationDao(){}
    /**
     * Konstruktor yang akan digunakan jika nantinya ingin membuat objek dengan parameter connection
     * @param connection berisi connection yang terhubung dengan koneksi
     */
    public LocationDao(Connection connection){
        this.connection= connection;
    }
    
    /**
     * Method ini berfungsi untuk melakukan pencarian baik. To Lower Key berguna untuk merubah huruf kapital menjadi huruf kecil semua. 
     * Sedangkan huruf kecil sudah tidak dirubah karena sudah menjadi huruf kecil
     * 1,2,3,4 urutan kolom berdasarkan tanda tanya
     * @param name Parameter yang akan digunakan untuk pencarian keyword dengan parameter name
     * @return Mengembalikan list department
     */
    public List<Location> getSearchLocation(String name) { 
        List<Location> locations = new ArrayList<>();
        String query = "SELECT * FROM locations";
        PreparedStatement ps = null;
        try {
            if (!name.equals("")) {
                query = "SELECT * FROM locations WHERE (location_id) LIKE ? OR lower(street_address) LIKE ? OR lower(postal_code) LIKE ? OR lower(city) LIKE ? OR lower(state_province) LIKE ? OR lower(country_id) LIKE ?";
                ps = this.connection.prepareStatement(query);
                ps.setString(1, "%"+name.toLowerCase()+"%" );
                ps.setString(2, "%"+name.toLowerCase()+"%" );
                ps.setString(3, "%"+name.toLowerCase()+"%" );
                ps.setString(4, "%"+name.toLowerCase()+"%");
                ps.setString(5, "%"+name.toLowerCase()+"%");
                ps.setString(6, "%"+name.toLowerCase()+"%");
            }else{
                ps = this.connection.prepareStatement(query);
            }
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Location d = new Location();
                d.setLocalId(resultSet.getInt(1));
                d.setAddress(resultSet.getString(2));
                d.setPost(resultSet.getString(3));
                d.setCity(resultSet.getString(4));
                d.setProvince(resultSet.getString(5));
                d.setCountry(resultSet.getString(6));
                locations.add(d);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locations;
    }
    
    /**
     * Method ini berfungsi untuk Query Insert Update. Boolean disini sebagai Pilihan, apakah dia adalah Inser ? Jika Insert maka menjalankan Query Insert
     * dan sebaliknya.
     * @param department tabel department yang ingin ditambahkan atau dirubah
     * @param isInsert berisi true jika insert 
     * @return Mengembalikan sebuah informasi berhasil atau tidak
     */
    public boolean insertUpdateLocation(Location location, boolean isInsert){
        boolean result = false;
        String query = "UPDATE locations SET street_address = ?, postal_code = ?, city = ?, state_province = ?, country_id = ? WHERE location_id = ?";
        try {
            if(isInsert){
            query = "INSERT INTO locations(street_address,postal_code,city,state_province,country_id,location_id) VALUES (?,?,?,?,?,?)";
            }
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1, location.getAddress());
            ps.setString(2, location.getPost());
            ps.setString(3, location.getCity());
            ps.setString(4, location.getProvince());
            ps.setString(5, location.getCountry());
            ps.setInt(6, location.getLocalId());
            ps.executeUpdate();
            result=true;
        } catch (Exception e) {
            e.printStackTrace();
    }return result;
}

    /**
     * Method ini berfungsi untuk mendapatkan sebuah data departement berdasarkan dept_id
     * @param location_id parameter yang akan dilakukan sebagai pencarian
     * @return 
     */
     public Location getLocation(int location_id){
        Location location = new Location();
        String query = "SELECT * FROM locations WHERE location_id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1,location_id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {                
                Location d = new Location();
                d.setLocalId(resultSet.getInt(1));
                d.setAddress(resultSet.getString(2));
                d.setPost(resultSet.getString(3));
                d.setCity(resultSet.getString(4));
                d.setProvince(resultSet.getString(5));
                d.setCountry(resultSet.getString(6));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }
    
     /**
      * Metode ini berfungsi untuk menghapus data Tabel Departement berdasarkan ID Nya
      * @param location_id
      * @return 
      */
        public boolean deleteLocation(int location_id){
            boolean result = false;
            String query = "DELETE FROM locations WHERE location_id = ?";
            try {
                PreparedStatement ps = this.connection.prepareStatement(query);
                ps.setInt(1, location_id);
                ps.executeUpdate();
                result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

  
}
