
package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    
    public Message createMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        if (connection != null) {
            String sql = "INSERT INTO Message (posted_by, message_text) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, message.getPosted_by());
                preparedStatement.setString(2, message.getMessage_text());
                preparedStatement.executeUpdate();
    
                ResultSet genkeys = preparedStatement.getGeneratedKeys();
                if (genkeys.next()) {
                    message.setMessage_id(genkeys.getInt(1));
                }
    
                return message;
            } catch (SQLException e) {
                e.printStackTrace();
                
            }
        }
        return null; 
    }

    
    public Message getMessageById(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        if (connection != null) {
            String sql = "SELECT * FROM Message WHERE message_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, message_id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return new Message(
                            resultSet.getInt("message_id"),
                            resultSet.getInt("posted_by"),
                            resultSet.getString("message_text"),
                            resultSet.getLong("time_posted_epoch")
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
               
            }
        }
        return null;
    }

    
    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        if (connection != null) {
            String sql = "SELECT * FROM Message";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int message_id = resultSet.getInt("message_id");
                    int posted_By = resultSet.getInt("posted_by");
                    String message_Text = resultSet.getString("message_text");
                    long time_Posted_Epoch = resultSet.getLong("time_posted_epoch");
                    Message message = new Message(message_id, posted_By, message_Text, time_Posted_Epoch);
                    messages.add(message);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                
            }
        }
        return messages;
    }

    
    public List<Message> getMessagesByAccountId(int account_id) {
        List<Message> messages = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        if (connection != null) {
            String sql = "SELECT * FROM Message WHERE posted_by = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, account_id);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int message_id = resultSet.getInt("message_id");
                    int posted_By = resultSet.getInt("posted_by");
                    String message_Text = resultSet.getString("message_text");
                    long time_Posted_Epoch = resultSet.getLong("time_posted_epoch");
                    Message message = new Message(message_id, posted_By, message_Text, time_Posted_Epoch);
                    messages.add(message);
                }
            } catch (SQLException e) {
                e.printStackTrace();
               
            }
        }
        return messages;
    }


public Message updateMessagebyId (int message_id, Message message) {

    Connection connection = ConnectionUtil.getConnection();
    
    if(connection != null) {
        String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
    try (
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

        preparedStatement.setString(1, message.getMessage_text());
        preparedStatement.setInt(2, message_id);

        preparedStatement.executeUpdate();
        Message upMess = getMessageById(message_id);

        return upMess;
          
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    return null;
}

    public boolean deleteMessage(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        if (connection != null) {
            String sql = "DELETE FROM Message WHERE message_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, message_id);
                int inc_rows = preparedStatement.executeUpdate();
                return inc_rows > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                
            }
        }
        return false;
    }
}
