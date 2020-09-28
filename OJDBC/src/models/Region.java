/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author User
 */
public class Region {
    private int id;
    private String name;

    public Region() {
    }

    /**
     * Merupakan konstruktor untuk membuat object Region
     * @param id = berisi data id region
     * @param name  = berisi data name region
     */
    public Region(int id, String name) {
        this.id = id;
        this.name = name;
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
       
}
