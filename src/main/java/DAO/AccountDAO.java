package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Util.ConnectionUtil;
import Model.Account;

public class AccountDAO {
    
    public Account createAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        if (connection != null) { 
            String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, account.getUsername());
                preparedStatement.setString(2, account.getPassword());
                preparedStatement.executeUpdate();
           
                ResultSet gen_keys = preparedStatement.getGeneratedKeys();
                if (gen_keys.next()) {
                    account.setAccount_id(gen_keys.getInt(1));
                }
                
                return account;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null; 
    }

    public Account getAccountById(int accountId) {
        Connection connection = ConnectionUtil.getConnection();
        if (connection != null) {
            String sql = "SELECT * FROM Account WHERE account_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, accountId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return new Account(
                            resultSet.getInt("account_id"),
                            resultSet.getString("username"),
                            resultSet.getString("password")
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
               
            }
        }
        return null;
    }

    
    public Account getAccountByUsername(String username) {
        Connection connection = ConnectionUtil.getConnection();
        if (connection != null) {
            String sql = "SELECT * FROM Account WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return new Account(
                            resultSet.getInt("account_id"),
                            resultSet.getString("username"),
                            resultSet.getString("password")
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
               
            }
        }
        return null;
    }

 
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        if (connection != null) {
            String sql = "SELECT * FROM Account";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int acct_id = resultSet.getInt("account_id");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    Account account = new Account(acct_id, username, password);
                    accounts.add(account);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                
            }
        }
        return accounts;
    }
}
