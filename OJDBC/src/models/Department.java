/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Deo Lahara
 */


public class Department {
    private int dept_id;
    private String dept_name;
    private int manager_id;
    private int loc_id;
    
public Department(){}
    
/**
 * Konstruktor pada Objek Department, Parameter tersebut berdasarkan Kolom Tabel Department Schema HR
 * @param dept_id parameter yang berisi department id dalam bentuk int
 * @param dept_name parameter yang berisi department name dalam bentuk String
 * @param manager_id parameter yang berisi manager id dalam bentuk int
 * @param loc_id parameter yang berisi location id dalam bentuk int
 */
    public Department(int dept_id, String dept_name, int manager_id, int loc_id) {
        this.dept_id = dept_id;
        this.dept_name = dept_name;
        this.manager_id = manager_id;
        this.loc_id = loc_id;
        }
   
    public int getDept_id(){
        return dept_id;
        }
    
    public void setDept_id(int dept_id){
        this.dept_id = dept_id;
        }
  
    public String getDept_name (){
        return dept_name;
        }
    
    public void setDept_name(String dept_name){
        this.dept_name = dept_name;
        }
    
    public int getManager_id (){
        return manager_id;
        }
    
    public void setManager_id(int manager_id){
        this.manager_id = manager_id;
        }
    
    public int getLoc_id(){
        return loc_id;
        }

    public void setLoc_id(int loc_id){
        this.loc_id = loc_id;
        }
}
