package ru.subbotin.banksConsole;

import static ru.subbotin.banksConsole.ClientConsole.client;

import java.util.Scanner;
import ru.subbotin.banks.Bank;
import ru.subbotin.banks.CentralBank;
import ru.subbotin.banks.InterestForDeposit;

public class BankConsole {

  public static void bank(CentralBank centralBank, Bank bank) {
    if (centralBank == null || bank == null) {
      throw new IllegalArgumentException("Arguments cant be null");
    }

    System.out.format("Bank %s\n", bank.getName());
    System.out.println("Select an action:");
    System.out.println("1) Update InterestForDebit");
    System.out.println("2) Update InterestForDeposit");
    System.out.println("3) Update CommissionForCredit");
    System.out.println("4) Update LimitForCredit");
    System.out.println("5) Update LimitWithdrawForDoubtful");
    System.out.println("6) Update LimitTransactionForDoubtful");
    System.out.println("7) Create client");
    System.out.println("8) Select client");
    System.out.println("9) Exit");

    Scanner in = new Scanner(System.in);
    String choose = in.nextLine();

    switch (choose) {
      case "1":
        System.out.println("Enter new value: ");

        try {
          int newValue = in.nextInt();
          bank.setInterestForDebit(newValue);
        } catch (Exception e) {
          System.out.println("Invalid value");
        }

        bank(centralBank, bank);
        break;
      case "2":
        updateInterestForDeposit(bank, in);

        bank(centralBank, bank);
        break;
      case "3":
        System.out.println("Enter new value: ");
        try {
          double newValue = in.nextDouble();
          bank.setCommissionForCredit(newValue);
        } catch (Exception e) {
          System.out.println("Invalid value");
        }

        bank(centralBank, bank);
        break;
      case "4":
        System.out.println("Enter new value: ");
        try {
          double newValue = in.nextDouble();
          bank.setLimitForCredit(newValue);
        } catch (Exception e) {
          System.out.println("Invalid value");
        }

        bank(centralBank, bank);
        break;
      case "5":
        System.out.println("Enter new value: ");
        try {
          double newValue = in.nextDouble();
          bank.setLimitWithdrawForDoubtful(newValue);
        } catch (Exception e) {
          System.out.println("Invalid value");
        }

        bank(centralBank, bank);
        break;
      case "6":
        System.out.println("Enter new value: ");
        try {
          double newValue = in.nextDouble();
          bank.setLimitTransactionForDoubtful(newValue);
        } catch (Exception e) {
          System.out.println("Invalid value");
        }

        bank(centralBank, bank);
        break;
      case "7":
        System.out.println("Enter name: ");
        String name = in.nextLine();

        System.out.println("Enter surname: ");
        String surname = in.nextLine();

        System.out.println("Do you want to specify an address? y/n ");
        String address = null;
        String answer = in.nextLine();
        if (answer == "y") {
          System.out.println("Enter address: ");
          address = in.nextLine();
        }

        System.out.println("Do you want to specify an passport number? y/n ");
        Long passportNumber = null;
        answer = in.nextLine();
        try {
          if (answer == "y") {
            System.out.println("Enter passpotr number: ");
            passportNumber = in.nextLong();
          }

          bank.createClient(name, surname, address, passportNumber);
        } catch (Exception e) {
          System.out.println("Invalid parameter");
        }

        bank(centralBank, bank);
        break;
      case "8":
        System.out.println("Select client:");
        for (int i = 1; i < bank.getClients().size(); i++) {
          System.out.println(
              i + ") " + bank.getClients().get(i - 1).getName() + " " + bank.getClients().get(i - 1)
                  .getSurname());
        }
        try {
          int answer1 = in.nextInt();
          client(centralBank, bank, bank.getClients().get(answer1));
        } catch (Exception e) {
          System.out.println("Invalid name. Try again");
        }

        bank(centralBank, bank);
        break;
      case "9":
        return;
      default:
        System.out.println("Invalid command. Try again.");
        bank(centralBank, bank);
        break;
    }
  }

  private static void updateInterestForDeposit(Bank bank, Scanner in) {
    InterestForDeposit.BuilderInterest builderInterest = InterestForDeposit.getBuilderInterest();
    Double upperBound;
    Integer value;

    while (true) {
      try {
        System.out.println("Enter upper bound: ");
        upperBound = in.nextDouble();

        System.out.println("Enter value: ");
        value = in.nextInt();

        builderInterest.addLevel(upperBound, value);
      } catch (Exception e) {
        System.out.println("Invalid parameter");
      }

      System.out.println("Do you want add another level? y/n");
      if (in.nextLine() == "n"){
        break;
      }
    }

    bank.setInterestForDeposit(builderInterest.build());
  }
}
