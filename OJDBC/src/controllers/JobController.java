/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.JobDao;
import java.util.List;
import models.Job;
import tools.Koneksi;

/**
 *
 * @author User
 */
public class JobController {

    private JobDao jobDao;

    /**
     * Konstruktor untuk membuat object jobcontroller yang mengisi atribut jobdao  
    */
    public JobController() {
        this.jobDao = new JobDao(new Koneksi().getConnection());
    }

    /**
     * Method ini digunakan ketika ingin menambahkan data job.
     * @param id = berisi id pada data yang ingin dimasukkan
     * @param title = berisi title pada data yang ingin dimasukkan
     * @param min = berisi minimial salary pada data yang ingin dimasukkan
     * @param max = berisi maksimal salary pada data yang ingin dimasukkan
     * @return  = method ini akan mengembalikan nilai string berupa sukses jika berhasil mengeksekusi method pada dao. 
     */    
    public String addJob(String id, String title, String min, String max) {
        int jMin = Integer.parseInt(min);
        int jMax = Integer.parseInt(max);
        Job job = new Job(id, title, jMin, jMax);
        return jobDao.insertUpdateJob(job,true) ? "Sukses menambahkan" : "Gagal menambahkan";
    }
    
    public String addJobSP(String id, String title, String min, String max) {
        int jMin = Integer.parseInt(min);
        int jMax = Integer.parseInt(max);
        Job job = new Job(id, title, jMin, jMax);
        return jobDao.insertUpdateJobSP(job,true) ? "Sukses menambahkan" : "Gagal menambahkan";
    }
    
    public String editJobSP(String id, String title, String min, String max) {
        int jMin = Integer.parseInt(min);
        int jMax = Integer.parseInt(max);
        Job job = new Job(id, title, jMin, jMax);
        return jobDao.insertUpdateJobSP(job,false) ? "Sukses mengedit" : "Gagal mengedit";
    }

    /**
     * Method ini digunakan ketika ingin merubah data job
     * @param id = berisi id pada data yang ingin dirubah
     * @param title = berisi data title yang dipakai untuk melakukan perubahan
     * @param min = berisi data minimal salary yang dipakai untuk melakukan perubahan
     * @param max = berisi data maksimal salary yang dipakai untuk melakukan perubahan
     * @return = method ini akan mengembalikan nilai string berupa jika berhasil mengupdate data
     */
    public String editJob(String id, String title, String min, String max) {
        int jMin = Integer.parseInt(min);
        int jMax = Integer.parseInt(max);
        Job job = new Job(id, title, jMin, jMax);
        return jobDao.insertUpdateJob(job,false) ? "Sukses Mengedit" : "Gagal mengedit";
    }
    
    /**
     * Method ini digunakan ketika ingin mencari seluruh data job
     * @return  = method ini mengembalikan list berisi seluruh data job
     */
    public List<Job> getDataJobs() {
        return jobDao.getSearchJobs("");
    }
    public List<Job> getDataJobsSP() {
        return jobDao.getSearchJobsSP("");
    }
    
    /**
     * Method ini digunakan ketika ingin mencari data job dengan menggunakan keyword
     * @param title = berisi keyword yang akan digunakan dalam pencarian
     * @return  = method ini mengembalikan list berisi seluruh data job sesuai dengan keyword
     */
    public List<Job> findJob(String title) {
        return jobDao.getSearchJobs(title);
    }
    
    public List<Job> findJobSP(String title) {
        return jobDao.getSearchJobsSP(title);
    }

    /**
     * Method ini digunakan ketika ingin mencari sebuah data job berdasarkan id
     * @param id = berisi id data yang akan dicari
     * @return  = method ini mengembalikan object job yang sudah dicari
     */
    public Job getDataJob(String id) {
        return jobDao.getJob(id);
    }
    public Job getDataJobSP(String id) {
        return jobDao.getJob(id);
    }

    /**
     * Method ini digunakan ketika ingin menghapus data job berdasarkan id
     * @param id = berisi id data yang akan dihapus
     * @return = method ini mengambilkan string yang berisi jika berhasil maka sukses dan jika tidak berhasil maka gagal
     */
    public String removeJob(String id) {
        return jobDao.deleteJob(id) ? "Sukses hapus data" : "Gagal hapus data";
    }
    public String removeJobSP(String id) {
        return jobDao.deleteJobSP(id) ? "Sukses hapus data" : "Gagal hapus data";
    }

}
