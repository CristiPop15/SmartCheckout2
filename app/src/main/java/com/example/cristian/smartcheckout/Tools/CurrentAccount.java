package com.example.cristian.smartcheckout.Tools;

import com.example.cristian.smartcheckout.Objects.Account;

/**
 * Created by Cristian on 7/7/2017.
 */

public class CurrentAccount {
    private static CurrentAccount currentAccount = null;
    private Account account = new Account();

    public CurrentAccount(Account account) {
        this.account = account;
    }


    public static CurrentAccount getInstance(){
        return currentAccount;
    }

    public static void create(Account a){
        currentAccount = new CurrentAccount(a);
    }

    public void logout(){
        account = null;
        currentAccount = null;
    }

    public Account getAccount(){
        return account;
    }
}
