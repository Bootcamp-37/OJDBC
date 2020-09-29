/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author ZFH
 */
public class Pengguna {
    private int user_id;
    private String name_user;
    private String password_user;
    private String user_email;
    private String code;

    public Pengguna(){}

    public Pengguna(String name_user, String password_user) {
        this.name_user = name_user;
        this.password_user = password_user;
    }

    public Pengguna(int user_id, String name_user, String password_user, String user_email) {
        this.user_id = user_id;
        this.name_user = name_user;
        this.password_user = password_user;
        this.user_email = user_email;
    }

    public Pengguna(int user_id, String name_user, String password_user, String user_email, String code) {
        this.user_id = user_id;
        this.name_user = name_user;
        this.password_user = password_user;
        this.user_email = user_email;
        this.code = code;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public String getPassword_user() {
        return password_user;
    }

    public void setPassword_user(String password_user) {
        this.password_user = password_user;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

   
}