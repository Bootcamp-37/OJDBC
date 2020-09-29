/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.UserDao;
import models.User;
import tools.Koneksi;

/**
 *
 * @author User
 */
public class UserController {
    private UserDao userDao;

    public UserController() {
        this.userDao = new UserDao(new Koneksi().getConnection());
    }
    
    public String addUserSP(String username, String pass, String name, String email) {
        User user = new User(0, username, pass, name, email);
        return userDao.insertUpdateUser(user,true) ? "Sukses menambahkan" : "Gagal menambahkan";
    }
    
    public String editUserSP(String id,String username, String pass, String name, String email) {
        int uId = Integer.parseInt(id);
        User user = new User(uId, username, pass, name, email);
        return userDao.insertUpdateUser(user,false) ? "Sukses mengubah" : "Gagal mengubah";
    }
    
    public User getUser(String id){
        int uId = Integer.parseInt(id);
        return userDao.getUserSP(uId);
    }
    
    public int getIdUser(String username, String email){
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        return userDao.getUserId(user);
    }
    
    public boolean LoginUser(String username, String password){
        User user = new User(username, password);
        return userDao.loginUser(user) ?  true:false;
    }
    
    public boolean verficationUser(String code, String id){
        int newId = Integer.parseInt(id);
        return userDao.verficationUser(code,newId)? true:false;
    }

    public String newPassUser(String id, String password){
        User user = new User();
        user.setId(Integer.parseInt(id));
        user.setPassword(password);
        return userDao.newPasswordUser(user)?  "Sukses merubah password":"Gagal merubah password";
    }
    
    public int forgotPassUser(String username){
        return userDao.forgotPasswordUser(username);
    }
}
