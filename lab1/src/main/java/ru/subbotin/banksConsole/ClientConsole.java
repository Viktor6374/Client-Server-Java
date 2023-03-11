package ru.subbotin.banksConsole;

import static ru.subbotin.banksConsole.AccountConsole.account;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.SplittableRandom;
import ru.subbotin.banks.Bank;
import ru.subbotin.banks.CentralBank;
import ru.subbotin.banks.Client;

public class ClientConsole {

  public static void client(CentralBank centralBank, Bank bank, Client client) {
    if (centralBank == null || bank == null || client == null) {
      throw new IllegalArgumentException("Arguments cant be null");
    }
    System.out.println("Client " + client.getName() + " " + client.getSurname());
    System.out.println("Select an action:");
    System.out.println("1) Get Info");
    System.out.println("2) Get notification");
    System.out.println("3) Clear notifications");
    System.out.println("4) Add address");
    System.out.println("5) Add passport number");
    System.out.println("6) Create Debit");
    System.out.println("7) Create Deposit");
    System.out.println("8) Create Credit");
    System.out.println("9) Menu Account");
    System.out.println("10) Exit");

    Scanner in = new Scanner(System.in);
    String choose = in.nextLine();

    switch (choose) {
      case "1":
        System.out.println("Name: " + client.getName());
        System.out.println("Surname: " + client.getSurname());
        if (client.getPassportNumber() != null) {
          System.out.println("Passport number: " + client.getPassportNumber());
        }
        if (client.getAddress() != null) {
          System.out.println("Address: " + client.getAddress());
        }
      case "2":
        System.out.println("Select notification:");

        for (int i = 1; i <= client.getNotifications().size(); i++) {
          System.out.println(i + ")");
        }

        try {
          int ansver = in.nextInt();
          System.out.println(
              "Changing value: " + client.getNotifications().get(ansver).getChangingValue());
          System.out.println("Old value: " + client.getNotifications().get(ansver).getOldValue());
          System.out.println("New value: " + client.getNotifications().get(ansver).getNewValue());
        } catch (Exception e) {
          System.out.println("Invalid name. Try again");
        }

        client(centralBank, bank, client);
        break;
      case "3":
        client.clearNotifications();

        client(centralBank, bank, client);
      case "4":
        System.out.println("Enter address: ");
        String address = in.nextLine();

        try {
          client.setAddress(address);
        } catch (Exception e) {
          System.out.println("Invalid address");
        }

        client(centralBank, bank, client);
        break;
      case "5":
        System.out.println("Enter passport number: ");

        try {
          Long passportNumber = in.nextLong();
          client.setPassportNumber(passportNumber);
        } catch (Exception e) {
          System.out.println("Invalid passport number");
        }

        client(centralBank, bank, client);
        break;
      case "6":
        bank.addDebit(client);

        client(centralBank, bank, client);
      case "7":
        try {
          System.out.println("Enter year of clothing:");
          int year = in.nextInt();

          System.out.println("Enter month of clothing:");
          int month = in.nextInt();

          System.out.println("Enter day of clothing:");
          int day = in.nextInt();

          bank.addDeposit(client, LocalDate.of(year, month, day));
        } catch (Exception e) {
          System.out.println("Invalid parameter");
        }

        client(centralBank, bank, client);
        break;
      case "8":
        bank.addCredit(client);

        client(centralBank, bank, client);
        break;
      case "9":
        System.out.println("Select account:");

        for (int i = 1; i <= client.getAccounts().size(); i++) {
          System.out.println(i + ") " + client.getAccounts().get(i - 1).getId());
        }
        try {
          int answer = in.nextInt();
          account(centralBank, bank, client, client.getAccounts().get(answer));
        }catch (Exception e){
          System.out.println("Invalid name. Try again");
        }

        client(centralBank, bank, client);
        break;
      case "10":
        return;
      default:
        System.out.println("Invalid command. Try again.");
        client(centralBank, bank, client);
        break;
    }
  }
}
