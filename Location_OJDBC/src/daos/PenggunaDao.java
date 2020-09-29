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
import java.util.Random;
import models.Pengguna;
import oracle.jdbc.OracleTypes;
import tools.BCrypt;
import tools.JavaMailJar;
/**
 *
 * @author ZFH
 */
public class PenggunaDao {
    private Connection connection;

public PenggunaDao(){}

public PenggunaDao(Connection connection){
    this.connection = connection;
}
public boolean PenggunaInsertUpdate(Pengguna pengguna, boolean isInsert){
        String query = "{call HR.SP_Insert_User(?,?,?,?)}";
        CallableStatement cs = null;
        String newPass = BCrypt.hashpw(pengguna.getPassword_user(), BCrypt.gensalt(10));
        try {
            if (!isInsert) {
                query = "{call HR.SP_Update_User(?,?,?,?,?)}";
                cs = this.connection.prepareCall(query);
                cs.setInt(5, pengguna.getUser_id());
            }else{
                cs = this.connection.prepareCall(query);
            }
            cs.registerOutParameter(1, java.sql.Types.INTEGER);
            cs.setString(2, pengguna.getName_user());
            cs.setString(3, newPass);
            cs.setString(4, pengguna.getUser_email());
            cs.executeUpdate();
            return (cs.getInt(1)==0)? false:true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
public String getRandom(){
    Random random = new Random();
    int number = random.nextInt(999999999);
    return String.format("%06d", number);
}

     public boolean CodeVerifikasiPengguna(Pengguna pengguna, boolean isInsert){
        boolean result = false;
        String query = "{call HR.SP_Code_Insert(?,?,?,?,?)}";
        CallableStatement cs = null;
        String code = this.getRandom();
        String newPass = BCrypt.hashpw(pengguna.getCode(), BCrypt.gensalt(10));
        try {
            if (!isInsert) {
                query = "{call HR.SP_Code_Update(?,?,?,?,?,?)}";
                cs = this.connection.prepareCall(query);
                cs.setInt(5, pengguna.getUser_id());
            }else{
                cs = this.connection.prepareCall(query);
            }
            cs.registerOutParameter(1, java.sql.Types.INTEGER);
            cs.setString(2, pengguna.getName_user());
            cs.setString(3, newPass);
            cs.setString(4, pengguna.getUser_email());
            cs.setString(5, pengguna.getCode());
            cs.executeUpdate();
            return (cs.getInt(1)==0)? false:true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean LoginPenggunaGetByID(Pengguna pengguna){
        String query = "SELECT * FROM tabel_user WHERE name_user = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1,pengguna.getName_user());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {                
                Pengguna p = new Pengguna();
                p.setPassword_user(resultSet.getString(3));
                if (BCrypt.checkpw(pengguna.getPassword_user(), p.getPassword_user())){
                return true;
                }
                
              }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}