package Service;

import DAO.AccountDAO;
import Model.Account;
import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account createAccount(Account account) {
        if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
            throw new IllegalArgumentException("");
        }

        Account existingAccount = accountDAO.getAccountByUsername(account.getUsername());
        if (existingAccount != null) {
            throw new IllegalArgumentException("");
        }

        return accountDAO.createAccount(account);
    }

    public Account getAccountById(int accountId) {
        return accountDAO.getAccountById(accountId);
    }

    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }


    public Account loginAccount(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("");
        }

        Account account = accountDAO.getAccountByUsername(username);
        if (account != null && account.getPassword().equals(password)) {
            return account;
        } else {
            throw new IllegalArgumentException("");
        }
    }
    

    public Account registerAccount(Account newAcct) {
      
        String username = newAcct.getUsername();
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("");
        }

        String password = newAcct.getPassword();
        if (password == null || password.length() < 4) {
            throw new IllegalArgumentException("");
        }

       
        Account acctpresent = accountDAO.getAccountByUsername(username);
        if (acctpresent != null) {
            throw new IllegalArgumentException("");
        }

        Account registeracct = accountDAO.createAccount(newAcct);

        return registeracct;
    }

}