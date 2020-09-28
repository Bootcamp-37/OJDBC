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
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Job;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author User
 */
public class JobDao {

    private Connection connection;

    public JobDao() {
    }

    /**
     * Konstruktor ini digunakan jika ingin membuat object dengan parameter connection
     * @param connection = berisi connection berasal dari koneksi
     */
    public JobDao(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Method ini digunakan untuk menambahkan dan merubah isi job
     * @param job = berisi data job yang akan ditambahkan atau diubah
     * @param isInsert = beriisi true jika perintah yg diinginkan yaitu menambahkan dan false jika perintahnya merubah
     * @return  = Jika method ini berhasil dijalankan maka akan mengembalikan nilai true dan jika gagal mengembalikan nilai false
     */
    public boolean insertUpdateJob(Job job, boolean isInsert ) {
        boolean result = false;
        String query = "UPDATE jobs SET job_title = ? , min_salary = ? , max_salary = ? WHERE job_id = ?";
        try {
            if (isInsert) {
                query = "INSERT INTO jobs (job_title,min_salary,max_salary,job_id) VALUES (?,?,?,?)";
            }
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1, job.getTitle());
            ps.setInt(2, job.getMin_salary());
            ps.setInt(3, job.getMax_salary());
            ps.setString(4, job.getId());
            ps.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean insertUpdateJobSP(Job job, boolean isInsert){
        String query = "{call SP_UpdateJob(?,?,?,?,?)}";
        try {
            if (isInsert) {
                query = "{call SP_InsertJobs(?,?,?,?,?)}";
            }
            CallableStatement cs = this.connection.prepareCall(query);
            cs.setString(1, job.getId());
            cs.setString(2, job.getTitle());
            cs.setInt(3, job.getMin_salary());
            cs.setInt(4, job.getMax_salary());
            cs.registerOutParameter(5, java.sql.Types.INTEGER);
            cs.executeUpdate();
            return (cs.getInt(5)==0)? false:true;
        } catch (SQLException ex) {
            Logger.getLogger(JobDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false; 
    }

    /**
     * Method ini berfungsi untuk melakukan pencarian jobs baik dengan keyword atau tidak
     * @param title = berisi keyword yang akan digunakan dalam pencarian
     * @return = Method ini akan mengembalikan list yang berisi banyak job sesuai dengan isi dari parameter
     */
    public List<Job> getSearchJobs(String keyword) {
        List<Job> jobs = new ArrayList<>();
        String query = "SELECT * FROM jobs";
        PreparedStatement ps = null;
        try {
            if (!keyword.equals("")) {
                query = "SELECT * FROM jobs WHERE lower(job_title) LIKE ? OR min_salary LIKE ? OR max_salary LIKE ? OR lower(job_id) LIKE ? ";
                ps = this.connection.prepareStatement(query);
                ps.setString(1, "%"+keyword+"%" );
                ps.setString(2, "%"+keyword+"%" );
                ps.setString(3, "%"+keyword+"%" );
                ps.setString(4, "%"+keyword+"%"  );
            }else{
                ps = this.connection.prepareStatement(query);
            }
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Job j = new Job();
                j.setId(resultSet.getString(1));
                j.setTitle(resultSet.getString(2));
                j.setMin_salary(resultSet.getInt(3));
                j.setMax_salary(resultSet.getInt(4));
                jobs.add(j);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jobs;
    }

    public List<Job> getSearchJobsSP(String keyword){
        List<Job> jobs = new ArrayList<>();
        String query = "{ call SP_GetAllJobs(?)}";
        CallableStatement cs = null;
        try {
            if (!keyword.equals("")) {
                query = "{call SP_FindJobs(?,?,?)}";
                cs = this.connection.prepareCall(query);
                cs.registerOutParameter(1, OracleTypes.CURSOR);
                cs.setString(2, "%"+keyword+"%" );
                cs.setString(3, "%"+keyword+"%");
            }else{
                cs = this.connection.prepareCall(query);
            }
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();
            ResultSet resultSet = (ResultSet)cs.getObject(1);
            while (resultSet.next()) {
                Job j = new Job();
                j.setId(resultSet.getString(1));
                j.setTitle(resultSet.getString(2));
                j.setMin_salary(resultSet.getInt(3));
                j.setMax_salary(resultSet.getInt(4));
                jobs.add(j);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JobDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jobs; 
    }

    /**
     * Method getJob berfungsi untuk mendapatkan sebuah data yang berada pada tabel job
     * @param id = merupakan id job yang akan didapatkan
     * @return = Jika method ini berhasil maka akan mengembalikan object job
     */
    public Job getJob(String id) {
        Job job = new Job();
        String query = "SELECT * FROM jobs WHERE job_id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                job.setId(resultSet.getString(1));
                job.setTitle(resultSet.getString(2));
                job.setMin_salary(resultSet.getInt(3));
                job.setMax_salary(resultSet.getInt(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return job;
    }
    
    public Job getJobSP(String id) {
        Job job = new Job();
        String query = "{ call SP_GetJob(?,?)}";      
        try {
            CallableStatement cs = this.connection.prepareCall(query);
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.setString(2, "%"+id+"%" );
            cs.execute();
            ResultSet resultSet = (ResultSet)cs.getObject(1);
            while (resultSet.next()) {
                job.setId(resultSet.getString(1));
                job.setTitle(resultSet.getString(2));
                job.setMin_salary(resultSet.getInt(3));
                job.setMax_salary(resultSet.getInt(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return job;
    }

    /**
     * Method deleteJob berfungsi untuk menghapus data yang berada pada tabel jobs
     * @param id = merupakan id job yang akan dihapus
     * @return =  Jika query berhasil dijalankan maka akan mengembalikan nilai true
     */
    public boolean deleteJob(String id) {
        boolean result = false;
        String query = "DELETE FROM jobs WHERE job_id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1, id);
            ps.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean deleteJobSP(String id) {
        boolean result = false;
        String query = "{call SP_DeleteJob(?,?)}";
        try {
            CallableStatement cs = this.connection.prepareCall(query);
            cs.setString(1, id);
            cs.registerOutParameter(2, java.sql.Types.INTEGER);
            cs.executeUpdate();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

