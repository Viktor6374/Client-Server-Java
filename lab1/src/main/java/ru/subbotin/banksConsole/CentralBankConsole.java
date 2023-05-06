package ru.subbotin.banksConsole;

import static ru.subbotin.banksConsole.BankConsole.bank;

import java.util.Scanner;
import ru.subbotin.banks.CentralBank;

public class CentralBankConsole {

  public static void centralBank(CentralBank centralBank) {
    if (centralBank == null) {
      throw new IllegalArgumentException("Arguments cant be null");
    }

    System.out.println("Select an action:");
    System.out.println("1) Create Bank");
    System.out.println("2) Select Bank");
    System.out.println("3) Change date");
    System.out.println("4) Change date and month");
    System.out.println("5) Exit");

    Scanner in = new Scanner(System.in);
    String choose = in.nextLine();

    switch (choose) {
      case "1":
        System.out.print("Enter bank name: ");

        String name = in.nextLine();
        try {
          centralBank.addBank(name);
        } catch (IllegalArgumentException e) {
          System.out.println("Invalid name");
        }

        centralBank(centralBank);
        break;
      case "2":
        selectBank(centralBank, in);
        centralBank(centralBank);
        break;
      case "3":
        centralBank.notifyObservers(false);

        centralBank(centralBank);
        break;
      case "4":
        centralBank.notifyObservers(true);

        centralBank(centralBank);
        break;
      case "5":
        return;
      default:
        System.out.println("Invalid command. Try again.");
        centralBank(centralBank);
        break;
    }
  }

  private static void selectBank(CentralBank centralBank, Scanner in){
    System.out.println("Select bank:");

    for (int i = 1; i <= centralBank.getBanks().size(); i++) {
      System.out.println(i + ") " + centralBank.getBanks().get(i - 1).getName());
    }

    try {
      int answer = in.nextInt();
      bank(centralBank, centralBank.getBanks().get(answer));
    } catch (Exception e) {
      System.out.println("Invalid name. Try again");
    }
  }
}
