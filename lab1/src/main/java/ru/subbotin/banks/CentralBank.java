package ru.subbotin.banks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * The class that the central bank issues. There can be only one
 * @author Subbotin Viktor
 */
public class CentralBank {
  private static CentralBank instance;
  private ArrayList<Bank> banks;
  private CentralBank(){
    banks = new ArrayList<>();
  }

  /**
   * @return All banks in central bank
   */
  public List<Bank> getBanks(){
    return Collections.unmodifiableList(banks);
  }

  /**
   * Return the only one instance Central bank
   */
  public static CentralBank getCentralBank(){
    if (instance == null){
      instance = new CentralBank();
    }

    return instance;
  }

  /**
   * Add bank in system
   * @param name
   * @return Adding bank
   */
  public Bank addBank(String name){
    var bank = new Bank(name);
    banks.add(bank);
    return bank;
  }

  /**
   * Performs a transaction between the specified banks for the specified amount
   * @param sourceAccount The account from which the money is debited
   * @param targetAccount The account to which the money is credited money
   * @param amount Amount to transfer
   */
  public void transaction (Account sourceAccount, Account targetAccount, double amount){
    var transaction = new Transaction(sourceAccount, targetAccount, amount);

    sourceAccount.transaction(transaction);
    targetAccount.transaction(transaction);
  }

  /**
   * Canceling the previous method
   * @param transaction Transaction to be cancelled
   */
  public void cancellationTransaction(Transaction transaction){
    transaction.getAccountFrom().cancellationTransaction(transaction);
    transaction.getAccountTo().cancellationTransaction(transaction);
  }

  /**
   * Time acceleration mechanism
   * @param changeOfMonth Flag indicating whether the accumulated interest should be credited to the account
   */
  public void notifyObservers(boolean changeOfMonth){
    for (Account account: banks.stream().flatMap(x -> x.getClients().stream()).flatMap(x -> x.getAccounts().stream()).collect(
        Collectors.toList())){
      account.update(changeOfMonth);
    }
  }
}
