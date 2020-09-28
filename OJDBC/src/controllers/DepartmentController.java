/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.DepartmentDao;
import java.util.List;
import models.Department;
import tools.Koneksi;

/**
 *
 * @author Deo Lahara
 */
public class DepartmentController {
    
    private DepartmentDao departmentDao;
    
    public DepartmentController(){
        this.departmentDao = new DepartmentDao(new Koneksi().getConnection());
    }
    /**
     * Konstruktor yang akan digunakan jika nantinya ingin membuat objek dengan parameter connection
     * @return 
     */
    
    public List<Department> getDataDepartements(){
        return departmentDao.getSearchDepartment("");
    }
    
    public List<Department> getDataDepartementSp(){
        return departmentDao.getSearchDepartmentSP("");
    }
    
    public String insertDepartmentsNEW(String dept_name, String manager_id, String loc_id){
        int dManager_id = Integer.parseInt(manager_id);
        int dLoc_id = Integer.parseInt(loc_id);
        Department department = new Department(0, dept_name, dManager_id, dLoc_id);
        return departmentDao.insertUpdateDepartmentsNEW(department, true)?"Sukses Menambahkan" : "Gagal Menambahkan";
        } 
   
    public String updateDepartmentsNEW(String dept_id, String dept_name, String manager_id, String loc_id){
        int dDept_id = Integer.parseInt(dept_id);
        int dManager_id = Integer.parseInt(manager_id);
        int dLoc_id = Integer.parseInt(loc_id);
        Department department = new Department(dDept_id, dept_name, dManager_id, dLoc_id);
        return departmentDao.insertUpdateDepartmentsNEW(department, false)?"Sukses Mengedit":"Gagal Mengedit";
    }
    
    /**
     * Method ini digunakan untuk mencari data pada Department dengan menggunakan keyword
     * @param name parameter yang digunakan sebagai keyword
     * @return 
     */
    public List<Department> findDepartment(String name){
        return departmentDao.getSearchDepartment(name);
    }
    
    public List<Department> findDepartmentSP(String name){
        return departmentDao.getSearchDepartmentSP(name);
    }
    
    public Department getDataDepartment(String dept_id){
        int dDept_id = Integer.parseInt(dept_id);
        return departmentDao.getDepartment(dDept_id);
    }
    
    public Department getDataDepartmentSP(String dept_id){
        int dDept_id = Integer.parseInt(dept_id);
        return departmentDao.getDepartmentSP(dDept_id);
    }
    
    /**
     * Method ini digunakan untuk menghapus department yang diinginkan berdasarkan dept_id
     * Mengambil nilai yang akan ditampilkan menjadi String. Dengan cara mengubah nilai awalan (int) menjadi String dengan Integer.parseInt
     * @param dept_id
     * @return 
     */
 
    public String deleteDepartmentSP(String dept_id){
        int dDept_Id = Integer.parseInt(dept_id);
        return departmentDao.deleteDepartmentSP(dDept_Id)?"Sukses Menghapus":"Gagal Menghapus";
    }
 }
