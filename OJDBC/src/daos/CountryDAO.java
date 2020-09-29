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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Country;

import oracle.jdbc.OracleTypes;

/**
 *
 * @author Laila
 */
public class CountryDAO {

    private Connection connection;

    public CountryDAO(Connection connection) {
        this.connection = connection;
    }

    public CountryDAO() {
    }
    
    public Account findByTokenAccount(String token) {
        Account account = null;
        String query = "select * from account where token = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                account = new Account(resultSet.getInt(1), resultSet.getString(2), 
                        resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }
    
    public String findByUsernameAccount(String username) {
        String email= "";
        Account account = null;
        String query = "select email from account where username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               email= resultSet.getString("email");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return email;
    }
    
    
    public String findTokenByUsernameAccount(String username) {
        String token= "";
        Account account = null;
        String query = "select token from account where username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               token= resultSet.getString("token");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }
    
    
//       public boolean pass(Account account) {
//        String query = "select * from account where (username=?) and (password=?)";
//        try {
//            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
//
//            preparedStatement.setString(1, account.getA_username());
//            preparedStatement.setString(2, account.getA_password());
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                String hash = BCrypt.hashpw(account.getA_password(), BCrypt.gensalt());
//                if (BCrypt.checkpw(account.getA_password(), hash)) {
//                    return true;
//                }
//
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//
//        return false;
//    }
    
    /**
     * This method is called to show all the data in table countries, Your data
     * will be showed in console,
     *
     * @return countries, which is datas in countries this data is form of list
     * type
     */
    public List<Country> getCountries() {
        List<Country> countries = new ArrayList<>();
        String query = "select * from COUNTRIES";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Country country = new Country();
                country.setId(resultSet.getString(1));
                country.setName(resultSet.getString(2));
                country.setRegion(resultSet.getInt(3));
                countries.add(country);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countries;
    }

    public List<Country> getCountriesSP() {
        List<Country> countries = new ArrayList<>();
        String query = "{call GETALL(?)}";
        try {
            CallableStatement cs = this.connection.prepareCall(query);
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();
            ResultSet resultSet = (ResultSet) cs.getObject(1);
            while (resultSet.next()) {
                Country country = new Country();
                country.setId(resultSet.getString(1));
                country.setName(resultSet.getString(2));
                country.setRegion(resultSet.getInt(3));
                countries.add(country);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countries;
    }

    /**
     * This method is called to save or edit all data you insert in, Your saved
     * or edited data will be showed in console,
     *
     * @return result, which is success or not the process of saving or editing
     * the data
     * @param country, is a whole package of country's object you insert in
     */
    public boolean insertUpdateCountry(Country country, boolean isInsert) {
        boolean result = false;
        String query = "UPDATE Countries SET country_name=?, region_id=? WHERE (country_id = ?)";
        if (isInsert) {
            query = "INSERT INTO Countries ( country_name, region_id, country_id) VALUES (?,?,?)";
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(3, country.getId());
            preparedStatement.setString(1, country.getName());
            preparedStatement.setInt(2, country.getRegion());
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return result;

    }

    public boolean insertUpdateCountrySP(Country country, boolean isInsert) {
        String query = "{call SP_UPDATECOUNTRY(?,?,?,?)}";
        try {
            if (isInsert) {
                query = "{call SP_INSERTCOUNTRY(?,?,?,?)}";
            }
            CallableStatement cs = this.connection.prepareCall(query);
            cs.setString(1, country.getId());
            cs.setString(2, country.getName());
            cs.setInt(3, country.getRegion());
            cs.registerOutParameter(4, java.sql.Types.INTEGER);
            cs.executeUpdate();
            return (cs.getInt(4) == 0) ? false : true;
        } catch (SQLException ex) {
            Logger.getLogger(CountryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * This method is called to save all data you insert in, Your saved data
     * will be showed in console,
     *
     * @return result, which is success or not the process of saving the data
     * @param country, is a whole package of country's object you insert in
     */
    public boolean insertCountry(Country country) {
        boolean result = false;

        String query = "INSERT INTO Countries (country_id, country_name, region_id) VALUES (?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, country.getId());
            preparedStatement.setString(2, country.getName());
            preparedStatement.setInt(3, country.getRegion());
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return result;

    }

    /**
     * This method is called to edit all data you insert in, Your edited data
     * will be showed in console,
     *
     * @return result, which is success or not the process of editing the data
     * @param country, is a whole package of country's object you insert in
     */
    public boolean updateCountry(Country country) {
        boolean result = false;

        String query = "UPDATE Countries SET country_name=?, region_id=? WHERE (country_id = ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, country.getName());
            preparedStatement.setInt(2, country.getRegion());
            preparedStatement.setString(3, country.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return result;

    }

    /**
     * This method is called to show all data that matches your search's clue is
     * Process all the input you want to search, you can enter either id's data,
     * name's data, or region's data
     *
     * @return countries, which is datas in countries that matches your clue
     * this data is form of list type
     * @param key, is the keyword that you want to search
     */
    public List<Country> searchCountry(String key) {
        List<Country> countries = new ArrayList<>();
        String query = "select * from COUNTRIES where lower(country_id) LIKE ? OR lower(country_name) LIKE ? OR lower(region_id) LIKE ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + key.toLowerCase() + "%");
            preparedStatement.setString(2, "%" + key.toLowerCase() + "%");
            preparedStatement.setString(3, "%" + key.toLowerCase() + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Country country = new Country();
                country.setId(resultSet.getString(1));
                country.setName(resultSet.getString(2));
                country.setRegion(resultSet.getInt(3));
                countries.add(country);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countries;
    }

    public List<Country> searchCountrySP(String key) {
        List<Country> countries = new ArrayList<>();
        String query = "{call SP_findcountry(?,?)}";
        try {
            CallableStatement cs = this.connection.prepareCall(query);
            cs.setString(1, "%" + key.toLowerCase() + "%");
            cs.registerOutParameter(2, OracleTypes.CURSOR);
            cs.execute();
            ResultSet resultSet = (ResultSet) cs.getObject(2);
            while (resultSet.next()) {
                Country country = new Country();
                country.setId(resultSet.getString(1));
                country.setName(resultSet.getString(2));
                country.setRegion(resultSet.getInt(3));
                countries.add(country);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countries;
    }

    /**
     * This method is called to delete the data you insert in, Your deleted data
     * will be showed in console, that your data is deleted,
     *
     * @return result, which is success or not the process of deleting the data
     * @param country, is a whole package of country's object you insert in
     */
    public boolean deleteCountry(Country country) {
        boolean result = false;

        String query = "DELETE FROM Countries WHERE (country_id = ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, country.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return result;

    }

    public boolean deleteCountrySP(Country country) {

        boolean result = false;

        String query = "{call SP_deletecountry(?,?)}";
        try {
            CallableStatement cs = this.connection.prepareCall(query);
            cs.setString(1, country.getId());
            cs.registerOutParameter(2, java.sql.Types.INTEGER);
            cs.executeUpdate();
            return (cs.getInt(2) == 0) ? false : true;

        } catch (SQLException ex) {
            Logger.getLogger(CountryDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;

    }

    /**
     * This method is called to show the data that matches the id you inserted
     * in, Process the id that you inputed,
     *
     * @return country, which is data in countries that matches your clue, this
     * is a whole object of a Country.
     * @param id, is the id of your data that you want to search.
     */
    public Country findByIdCountry(String id) {
        Country country = null;
        String query = "select * from COUNTRIES where country_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                country = new Country(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return country;
    }

    public Country findByIdCountrySP(String id) {

        Country country = null;
        String query = "{call SP_GETBYID(?,?)}";
        try {
            CallableStatement cs = this.connection.prepareCall(query);
            cs.setString(1, id);
            cs.registerOutParameter(2, OracleTypes.CURSOR);
            cs.execute();
            ResultSet resultSet = (ResultSet) cs.getObject(2);
            while (resultSet.next()) {
                country = new Country(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return country;
    }
}
