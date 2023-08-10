package Service;

import DAO.MessageDAO;
import Model.Account;
import Model.Message;
import java.util.List;
//import Util.ConnectionUtil;
//import java.sql.Connection;

public class MessageService {
    private  MessageDAO messageDAO;
    private  AccountService accountService;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountService = new AccountService();
    }

    public MessageService(MessageDAO messageDAO, AccountService accountService) {
        this.messageDAO = messageDAO;
        this.accountService = accountService;
    }

    public Message createMessage(Message message) {
        if (message.getMessage_text().isBlank() || message.getMessage_text().length() > 254) {
            throw new IllegalArgumentException("");
        }

        Account postedByUser = accountService.getAccountById(message.getPosted_by());
        if (postedByUser == null) {
            throw new IllegalArgumentException("");
        }

        return messageDAO.createMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    public Message deleteMessageById(int message_id) {
        Message message = messageDAO.getMessageById(message_id);
        if (message != null) {
            boolean deletedmess = messageDAO.deleteMessage(message_id);
            if (deletedmess) {
                return message;
            }
        }
        return null;
    }


public Message updateMessage(int message_id, Message message) {
    
    if (message.getMessage_text() == null || message.getMessage_text().isEmpty()|| message.getMessage_text().length() > 254) {
        return null; 
    }
    Message upMess = messageDAO.updateMessagebyId( message_id, message);
    return upMess;
  
}

    public List<Message> getAllMessagesByAccountId(int acct_id) {
        return messageDAO.getMessagesByAccountId(acct_id);
    }

}