/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.PenggunaDao;
import models.Pengguna;
import tools.Koneksi;
/**
 *
 * @author ZFH
 */
public class PenggunaController {
    private PenggunaDao penggunaDao;

    public PenggunaController() {
        this.penggunaDao = new PenggunaDao(new Koneksi().getConnection());
    }
    
    public String addPenggunaSP(String name_user, String password_user, String user_email) {
        Pengguna pengguna = new Pengguna(0, name_user, password_user, user_email);
        return penggunaDao.PenggunaInsertUpdate(pengguna,true) ? "Sukses menambahkan" : "Gagal menambahkan";
    }
    
    public String updatePenggunaSP(String user_id,String name_user, String password_user, String user_email) {
        int pId = Integer.parseInt(user_id);
        Pengguna pengguna = new Pengguna(pId, name_user, password_user, user_email);
        return penggunaDao.PenggunaInsertUpdate(pengguna,false) ? "Sukses mengubah" : "Gagal mengubah";
    }
    
    public boolean LoginPenggunaGetByID(String name_user, String password_user){
        Pengguna pengguna = new Pengguna(name_user, password_user);
        return penggunaDao.LoginPenggunaGetByID(pengguna) ?  true:false;
    }
        public boolean VerficationCodeUser(String code, String user_id){
        int newId = Integer.parseInt(user_id);
        return penggunaDao.CodeVerifikasiPengguna(code,newId)? true:false;
    }
}