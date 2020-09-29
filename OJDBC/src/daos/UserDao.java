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
import models.User;
import oracle.jdbc.OracleTypes;
import tools.BCrypt;
import tools.JavaMail;

/**
 *
 * @author User
 */
public class UserDao {
    private Connection connection;

    public UserDao() {
    }

    public UserDao(Connection connection) {
        this.connection = connection;
    }
    
    public boolean loginUser (User user){
        String query = "SELECT * FROM users WHERE username = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setString(1,user.getUsername());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {                
                User p = new User();
                p.setPassword(resultSet.getString(3));
                if (BCrypt.checkpw(user.getPassword(), p.getPassword())){
                return true;
                }
                
              }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean insertUpdateUser(User user, boolean isInsert){
        String query = "{call SP_InsertUser(?,?,?,?,?,?)}";
        CallableStatement cs = null;
        String newPass = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
        String code = JavaMail.getRandom();
        boolean result = false;
        try {
            if (!isInsert) {
                query = "{call SP_UpdateUser(?,?,?,?,?,?,?)}";
                cs = this.connection.prepareCall(query);
                cs.setInt(7, user.getId());
            }else{
                cs = this.connection.prepareCall(query);
            }
            cs.registerOutParameter(1, java.sql.Types.INTEGER);
            cs.setString(2, user.getUsername());
            cs.setString(3, newPass);
            cs.setString(4, user.getName());
            cs.setString(5, user.getEmail());
            cs.setString(6, code);
            cs.executeUpdate();
            if (cs.getInt(1)== 1 && isInsert) {
                JavaMail.sendMail(user.getEmail(), "Verification","To verify your account, please insert this code : "+code);
                result = true;
            }else if(cs.getInt(1)==1 && !isInsert){
                result = true;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean newPasswordUser(User user){
        String query = "{call SP_NewPassUser(?,?,?)}";
        CallableStatement cs = null;
        String newPass = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
        try {
            cs = this.connection.prepareCall(query);
            cs.registerOutParameter(1, java.sql.Types.INTEGER);
            cs.setString(2, newPass);
            cs.setInt(3, user.getId());
            cs.executeUpdate();
            if (cs.getInt(1)== 1) {
                User u = this.getUserSP(user.getId());
                JavaMail.sendMail(u.getEmail(), "Reset password","Your account "+u.getUsername()+" password has successfuly changed.");
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean verficationUser(String code, int id){
        String query = "{? = call FN_Verification(?,?)}";
        try {
            CallableStatement cs = this.connection.prepareCall(query);
            cs.registerOutParameter(1,  java.sql.Types.INTEGER);
            cs.setString(2, code);
            cs.setInt(3, id);
            cs.execute();
            return cs.getInt(1)!=0? true:false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public int forgotPasswordUser(String username){
        User user = new User();
        String query = "{ call SP_ForgotPass(?,?)}"; 
        try {
            CallableStatement cs = this.connection.prepareCall(query);
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.setString(2,username );
            cs.execute();
            ResultSet resultSet = (ResultSet)cs.getObject(1);
            while (resultSet.next()) {                
                user.setEmail(resultSet.getString(5));
                user.setCode(resultSet.getString(6));
                user.setId(resultSet.getInt(1));
            }
            if (!resultSet.next()) {
                JavaMail.sendMail(user.getEmail(), "Verification","To verify your account, please insert this code : "+user.getCode());
                return user.getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int getUserId(User user){
        String query = "{? = call getId(?,?)}";
         try {
            CallableStatement cs = this.connection.prepareCall(query);
            cs.registerOutParameter(1,  java.sql.Types.INTEGER);
            cs.setString(2, user.getUsername());
            cs.setString(3, user.getEmail());
            cs.execute();
            return cs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public User getUserSP(int id){
        User user = new User();
        String query = "{ call SP_GetUser(?,?)}"; 
        try {
            CallableStatement cs = this.connection.prepareCall(query);
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.setInt(2,id );
            cs.execute();
            ResultSet resultSet = (ResultSet)cs.getObject(1);
            while (resultSet.next()) {                
                user.setId(resultSet.getInt(1));
                user.setUsername(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setName(resultSet.getString(4));
                user.setEmail(resultSet.getString(5));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    
}
