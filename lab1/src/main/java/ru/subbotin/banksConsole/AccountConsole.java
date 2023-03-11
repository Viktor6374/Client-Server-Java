package ru.subbotin.banksConsole;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ru.subbotin.banks.Account;
import ru.subbotin.banks.Bank;
import ru.subbotin.banks.CentralBank;
import ru.subbotin.banks.Client;

public class AccountConsole {

  public static void account(CentralBank centralBank, Bank bank, Client client, Account account) {
    if (centralBank == null || bank == null || client == null || account == null) {
      throw new IllegalArgumentException("Arguments cant be null");
    }

    System.out.println("Account " + account.getId());
    System.out.println("Select an action:");
    System.out.println("1) Transaction");
    System.out.println("2) CancellationTransaction");
    System.out.println("3) Withdraw");
    System.out.println("4) Replenish");
    System.out.println("5) Close account");
    System.out.println("6) Exit");

    Scanner in = new Scanner(System.in);
    String choose = in.nextLine();

    switch (choose) {
      case "1":
        System.out.println("Enter target bank: ");

        for (int i = 1; i < centralBank.getBanks().size(); i++) {
          System.out.println(i + ") " + centralBank.getBanks().get(i - 1));
        }

        int numberBank;
        try {
          numberBank = in.nextInt();
        } catch (Exception e) {
          System.out.println("Invalid number");
          account(centralBank, bank, client, account);
          break;
        }

        System.out.println("Enter target client: ");

        for (int i = 1; i < centralBank.getBanks().get(numberBank).getClients().size(); i++) {
          Client client1 = centralBank.getBanks().get(numberBank).getClients().get(i - 1);
          System.out.println(i + ") " + client1.getName() + " " + client1.getSurname());
        }

        int numberClient;
        try {
          numberClient = in.nextInt();
        } catch (Exception e){
          System.out.println("Invalid number");
          account(centralBank, bank, client, account);
          break;
        }

        System.out.println("Enter target account: ");

        List<Account> accounts = centralBank.getBanks().get(numberBank).getClients().get(numberClient).getAccounts();
        for (int i = 1; i < accounts.size(); i++) {
          System.out.println(i + ") " + accounts.get(i - 1).getId());
        }

        int numberAccount;
        try {
          numberAccount = in.nextInt();
        } catch (Exception e) {
          System.out.println("Invalid number");
          account(centralBank, bank, client, account);
          break;
        }

        System.out.println("Enter amount: ");
        int amount;

        try {
          amount = in.nextInt();
          Account targetAccount = centralBank.getBanks().get(numberBank).getClients().get(numberClient).getAccounts()
              .get(numberAccount);
          centralBank.transaction(account, targetAccount, amount);
        } catch (Exception e){
          System.out.println(e.getMessage());
        }

        account(centralBank, bank, client, account);
        break;
      case "2":
        System.out.println("Enter transaction: ");

        for (int i = 1; i < account.getTransactions().size(); i++) {
          System.out.println(i + ") Account: " + account.getTransactions().get(i - 1).getAccountTo() + ", amount: " + account.getTransactions().get(i - 1).getAmount());
        }

        int numberTransaction;
        try {
          numberTransaction = in.nextInt();
        } catch (Exception e) {
          System.out.println("Invalid number");
          account(centralBank, bank, client, account);
          break;
        }

        centralBank.cancellationTransaction(account.getTransactions().get(numberTransaction));

        account(centralBank, bank, client, account);
        break;
      case "3":
        System.out.println("Enter amount: ");

        try {
          amount = in.nextInt();
          account.withdraw(amount);
        } catch (Exception e) {
          System.out.println(e.getMessage());
        } finally {
          account(centralBank, bank, client, account);
        }

        break;
      case "4":
        System.out.println("Enter amount: ");

        try {
          amount = in.nextInt();
          account.replenish(amount);
        } catch (Exception e) {
          System.out.println(e.getMessage());
        } finally {
          account(centralBank, bank, client, account);
        }

        break;
      case "5":
        if (account.getAmount() != 0){
          System.out.println("Withdraw money from the account");
        } else {
          client.clouseAccount(account);
        }

        account(centralBank, bank, client, account);
        break;
      case "6":
        return;
      default:
        System.out.println("Invalid command. Try again.");
        account(centralBank, bank, client, account);
        break;
    }
  }
}