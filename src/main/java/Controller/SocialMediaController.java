package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.MessageService;
import Service.AccountService;
import java.util.List;
import DAO.MessageDAO;
import DAO.AccountDAO;




/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
    }

    public SocialMediaController(AccountDAO accountDAO, MessageDAO messageDAO) {
        this.accountService = new AccountService(accountDAO);
        this.messageService = new MessageService(messageDAO, accountService);
    }

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerAccountHandler);
        app.post("/login", this::loginAccountHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountIdHandler);


        /** 
        app.exception(IllegalArgumentException.class, (e, ctx) -> {
            ctx.result(e.getMessage());
            ctx.status(400); // Set the response status to 400 Bad Request
        });

        */

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    /**
     private void exampleHandler(Context context) {
        context.json("sample text");
    }
    */

    private void registerAccountHandler(Context context) {
        Account acct = context.bodyAsClass(Account.class);

        try {
            
            Account acct_register = accountService.registerAccount(acct);

            context.json(acct_register).status(200);
        } catch (IllegalArgumentException e) {
  
            context.result(e.getMessage()).status(400);
        }
    }
    

    private void loginAccountHandler(Context ctx) {
        Account acct = ctx.bodyAsClass(Account.class);
    
        try {
            Account acct_login = accountService.loginAccount(acct.getUsername(), acct.getPassword());
            ctx.json(acct_login).status(200); 
        } catch (IllegalArgumentException e) {
            ctx.result(e.getMessage()).status(401); 
        }
    }

    private void createMessageHandler(Context ctx) {
        Message message = ctx.bodyAsClass(Message.class);
    
        try {
            Message createdMessage = messageService.createMessage(message);
            ctx.json(createdMessage).status(200); 
            
        } catch (IllegalArgumentException e) {
            ctx.result(e.getMessage()).status(400); 
            
        }
    }

    private void getAllMessagesHandler(Context ctx) { 
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages); 
        ctx.status(200); 
    }

    private void getMessageByIdHandler(Context context) {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(message_id);

        if (message != null) {
            context.json(message).status(200);
        } else {
            context.status(200); 
        }
    }

    private void deleteMessageByIdHandler(Context ctx) {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));

        
        Message message_delete = messageService.deleteMessageById(message_id);

        if (message_delete != null) {
            ctx.json(message_delete); 
            ctx.status(200); 
        } else {
            ctx.status(200); 
        }
    }

private void updateMessageByIdHandler(Context ctx) {
    Message message = ctx.bodyAsClass(Message.class);
    int message_id = Integer.parseInt(ctx.pathParam("message_id"));
    
    Message message_update = messageService.updateMessage(message_id, message);

    if (message_update != null) {
        ctx.json(message_update);
        ctx.status(200);
    } else {
        ctx.status(400); 
    }
}


private void getMessagesByAccountIdHandler(Context ctx) {
        int acct_id = Integer.parseInt(ctx.pathParam("account_id"));
      
        List<Message> messages = messageService.getAllMessagesByAccountId(acct_id);

        ctx.json(messages); 
        ctx.status(200); 
    }
}



