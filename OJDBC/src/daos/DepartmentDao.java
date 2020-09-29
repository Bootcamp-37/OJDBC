/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Department;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author Deo Lahara
 */
public class DepartmentDao {
    private Connection connection;

    
    public DepartmentDao(){}
    /**
     * Konstruktor yang akan digunakan jika nantinya ingin membuat objek dengan parameter connection
     * @param connection berisi connection yang terhubung dengan koneksi
     */
    public DepartmentDao(Connection connection){
        this.connection= connection;
    }
    
    /**
     * Method ini berfungsi untuk melakukan pencarian baik. To Lower Key berguna untuk merubah huruf kapital menjadi huruf kecil semua. 
     * Sedangkan huruf kecil sudah tidak dirubah karena sudah menjadi huruf kecil
     * 1,2,3,4 urutan kolom berdasarkan tanda tanya
     * @param name Parameter yang akan digunakan untuk pencarian keyword dengan parameter name
     * @return Mengembalikan list department
     */
    public List<Department> getSearchDepartment(String name) { 
        List<Department> departments = new ArrayList<>();
        String query = "SELECT * FROM departments";
        PreparedStatement ps = null;
        try {
            if (!name.equals("")) {
                query = "SELECT * FROM departments WHERE (department_id) LIKE ? OR lower(department_name) LIKE ? OR (manager_id) LIKE ? OR (location_id) LIKE ?";
                ps = this.connection.prepareStatement(query);
                ps.setString(1, "%"+name.toLowerCase()+"%" );
                ps.setString(2, "%"+name.toLowerCase()+"%" );
                ps.setString(3, "%"+name.toLowerCase()+"%" );
                ps.setString(4, "%"+name.toLowerCase()+"%");
            }else{
                ps = this.connection.prepareStatement(query);
            }
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Department d = new Department();
                d.setDept_id(resultSet.getInt(1));
                d.setDept_name(resultSet.getString(2));
                d.setManager_id(resultSet.getInt(3));
                d.setLoc_id(resultSet.getInt(4));
                departments.add(d);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return departments;
}
    
    public List<Department> getSearchDepartmentSP(String name) {
        List<Department> departments = new ArrayList<>();
        String query = "{ call SP_GetAllDepartment(?)}";
        CallableStatement cs = null;
        try {
            if (!name.equals("")) {
                query = "{call SP_FindDepartment(?,?)}";
                cs = this.connection.prepareCall(query);
                cs.registerOutParameter(1, OracleTypes.CURSOR);
                cs.setString(2, "%"+name.toLowerCase()+"%" );
            }else{
                cs = this.connection.prepareCall(query);
            }
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();
            ResultSet resultSet = (ResultSet)cs.getObject(1);
            while (resultSet.next()) {
                Department d = new Department();
                d.setDept_id(resultSet.getInt(1));
                d.setDept_name(resultSet.getString(2));
                d.setManager_id(resultSet.getInt(3));
                d.setLoc_id(resultSet.getInt(4));
                departments.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return departments;
}
    
        /**
     * Method ini berfungsi untuk mendapatkan sebuah data departement berdasarkan dept_id
     * @param dept_id parameter yang akan dilakukan sebagai pencarian
     * @return 
     */
     public Department getDepartment(int dept_id){
        Department department = new Department();
        String query = "SELECT * FROM departments WHERE department_id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1,dept_id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {                
                Department d = new Department();
                d.setDept_id(resultSet.getInt(1));
                d.setDept_name(resultSet.getString(2));
                d.setManager_id(resultSet.getInt(3));
                d.setLoc_id(resultSet.getInt(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return department;
    }
     
     public Department getDepartmentSP(int dept_id){
        Department department = new Department();
        String query = "{ call SP_GetDepartment(?,?)}"; 
        try {
            CallableStatement cs = this.connection.prepareCall(query);
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.setInt(2, dept_id );
            cs.execute();
            ResultSet resultSet = (ResultSet)cs.getObject(1);
            while (resultSet.next()) {                
                department.setDept_id(resultSet.getInt(1));
                department.setDept_name(resultSet.getString(2));
                department.setManager_id(resultSet.getInt(3));
                department.setLoc_id(resultSet.getInt(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return department;
    }
     
     /**
      * Metode ini berfungsi untuk menghapus data Tabel Departement berdasarkan ID Nya
      * @param dept_id
      * @return 
      */

        
        public boolean deleteDepartmentSP(int dept_id){
             boolean result = false;
            String query = "{call SP_DeleteDepartments(?,?)}";
            try {
                CallableStatement cs = this.connection.prepareCall(query);
                cs.setInt(1, dept_id);
                cs.registerOutParameter(2, java.sql.Types.INTEGER);
                cs.executeUpdate();
                result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

  //Departments Insert
        public boolean insertUpdateDepartmentsNEW(Department department, boolean isInsert){
        String query = "{call SP_InsertDepartmentsNEW(?,?,?,?)}";
        CallableStatement cs = null;
            try {
                if (!isInsert){
                    query = "{call SP_UpdateDepartmentsNEW(?,?,?,?,?)}";
                    cs = this.connection.prepareCall(query);
                    cs.setInt(5, department.getDept_id());
                } else{
                    cs = this.connection.prepareCall(query);
                }
                cs.registerOutParameter(1, java.sql.Types.INTEGER);
                cs.setString(2, department.getDept_name());
                cs.setInt(3, department.getManager_id());
                cs.setInt(4, department.getLoc_id());
                cs.executeUpdate();
                return  (cs.getInt(1)==0)?false:true;
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
    }
}
        
        

            
