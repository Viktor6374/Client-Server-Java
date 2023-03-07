package ru.subbotin.banks;

/**
 * A class containing information about the transaction performed
 */
public class Transaction {
  public Transaction(Account accountFrom_, Account accountTo_, double amount_){
    if (amount_ <= 0){
      throw new IllegalArgumentException("Amount can't be <= 0");
    }

    amount = amount_;
    if (accountFrom_ == null || accountTo_ == null){
      throw new IllegalArgumentException("Account can't be null");
    }

    accountFrom = accountFrom_;
    accountTo = accountTo_;
  }

  /**
   * The account from which the money was debited
   */
  private Account accountFrom;

  /**
   * The account to which the money was credited
   */
  private Account accountTo;

  /**
   * The amount that was transferred
   */
  private double amount;
  public Account getAccountFrom(){
    return accountFrom;
  }

  public Account getAccountTo(){
    return accountTo;
  }

  public double getAmount(){
    return amount;
  }
}