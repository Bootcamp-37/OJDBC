/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.RegionDao;
import java.util.List;
import models.Region;
import tools.Koneksi;

/**
 *
 * @author User
 */
public class RegionController {
    
    private RegionDao regionDao;
    
    /**
     * Konstruktor untuk membuat object jobcontroller yang mengisi atribut jobdao dari koneksi
     */
    public RegionController() {
        this.regionDao = new RegionDao(new Koneksi().getConnection());
    }
    
    /**
     * Method ini digunakan ketika ingin mencari seluruh data region
     * @return  = method ini mengembalikan list berisi seluruh data region
     */
    public List<Region> getDataRegions(){
        return regionDao.getSearchRegion("");
    }
    public List<Region> getDataRegionsSP(){
        return regionDao.getSearchRegionSP("");
    }
    
    /**
     * Method ini digunakan ketika ingin menambahkan region baru
     * @param id = berisi id pada data yang ingin dimasukkan
     * @param name = berisi nama region yang ingin dimasukkan
     * @return = method ini akan mengembalikan nilai string berupa sukses jika berhasil dan gagal jika gagal menambahkan
     */
    public String saveRegion(String id, String name){
        int rId = Integer.parseInt(id);
        Region region = new Region(rId, name);
        return regionDao.insertUpdateRegion(region,true)? "Sukses":"Gagal";
    }
    public String saveRegionSP(String id, String name){
        int rId = Integer.parseInt(id);
        Region region = new Region(rId, name);
        return regionDao.insertUpdateRegionSP(region,true)? "Sukses menambahkan":"Gagal menambahkan";
    }
    
    /**
     * Method ini digunakan ketika ingin merubah region baru
     * @param id = berisi id pada data yang ingin diubah
     * @param name = berisi nama region yang ingin diubah
     * @return = method ini akan mengembalikan nilai string berupa sukses jika berhasil dan gagal jika gagal diubah
     */
    public String editRegion(String id, String name){
        int rId = Integer.parseInt(id);
        Region region = new Region(rId, name);
        return regionDao.insertUpdateRegion(region,false)? "Sukses":"Gagal";
    }
    
    public String editRegionSP(String id, String name){
        int rId = Integer.parseInt(id);
        Region region = new Region(rId, name);
        return regionDao.insertUpdateRegionSP(region,false)? "Sukses":"Gagal";
    }
    
    /**
     * Method ini digunakan ketika ingin mencari data region dengan menggunakan keyword
     * @param name = berisi keyword yang akan digunakan dalam pencarian
     * @return = method ini mengembalikan list berisi seluruh data region sesuai dengan keyword
     */
    public List<Region> findRegion(String name){
        return regionDao.getSearchRegion(name);
    }
    public List<Region> findRegionSP(String name){
        return regionDao.getSearchRegionSP(name);
    }
    
    /**
     Method ini digunakan ketika ingin mencari sebuah data region berdasarkan id
     * @param id = berisi id data yang akan dicari
     * @return  = method ini mengembalikan object region yang sudah dicari
     */
    public Region getDataRegion(String id){
        int rId = Integer.parseInt(id);
        return regionDao.getRegion(rId);
    }
    
    public Region getDataRegionSP(String id){
        int rId = Integer.parseInt(id);
        return regionDao.getRegion(rId);
    }
    
    /**
     * Method ini digunakan ketika ingin menghapus data region berdasarkan id
     * @param id = berisi id data yang akan dihapus
     * @return = method ini mengambilkan string yang berisi jika berhasil maka sukses dan jika tidak berhasil maka gagal 
     */
    public String removeRegion(String id){
        int rId = Integer.parseInt(id);
        return regionDao.deleteRegion(rId)? "Sukses":"Gagal";
    }
    
    public String removeRegionSP(String id){
        int rId = Integer.parseInt(id);
        return regionDao.deleteRegionSP(rId)? "Sukses delete data":"Gagal delete data";
    }
}
