package ru.subbotin.banks;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.UUID;

/**
 * It is a bank account. Has a fixed percentage on the balance
 */

public class Debit implements Account {
  private final int daysInYear = 365;
  public Debit(int interest) {
    setInterest(interest);
    id = UUID.randomUUID();
    transactions = new ArrayList<>();
    amount = 0;
  }

  /**
   * Changes a doubtful account to a regular one
   */
  public Debit(DoubtfulDebit doubtfulDebit) {
    transactions.addAll(doubtfulDebit.getTransactions());
    accumulatedInterest = doubtfulDebit.getAccumulatedInterest();
    interest = doubtfulDebit.getInterest();
    id = doubtfulDebit.getId();
    amount = doubtfulDebit.getAmount();
  }
  private ArrayList<Transaction> transactions;
  private double accumulatedInterest;
  private int interest;
  private final UUID id;
  private double amount;

  public double getAccumulatedInterest() {
    return accumulatedInterest;
  }

  protected void setAccumulatedInterest(double newValue) {
    accumulatedInterest = newValue;
  }

  public int getInterest() {
    return interest;
  }

  public void setInterest(int newValue) {
    if (newValue < 0) {
      throw new IllegalArgumentException("Interest can't be less 0");
    }
    interest = newValue;
  }

  /**
   * {@inheritDoc}
   */
  public UUID getId() {
    return id;
  }

  /**
   * {@inheritDoc}
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Change amount in this account
   * @param newValue new value for amount
   */
  protected void setAmount(double newValue) {
    amount = newValue;
  }

  /**
   * {@inheritDoc}
   */
  public List<Transaction> getTransactions() {
    return Collections.unmodifiableList(transactions);
  }

  /**
   * {@inheritDoc}
   */
  public void transaction(Transaction transaction) {
    if (transaction.getAccountFrom() == this) {
      withdraw(transaction.getAmount());
    } else if (transaction.getAccountTo() == this) {
      replenish(transaction.getAmount());
    } else {
      throw new IllegalArgumentException("The transaction does not apply to this account");
    }

    transactions.add(transaction);
  }

  /**
   * {@inheritDoc}
   */
  public void cancellationTransaction(Transaction transaction){
    if (transaction.getAccountFrom() == this){
      amount += transaction.getAmount();
    } else if (transaction.getAccountTo() == this) {
      amount -= transaction.getAmount();
    } else {
      throw new IllegalArgumentException("The transaction does not apply to this account");
    }

    transactions.remove(transaction);
  }

  /**
   * {@inheritDoc}
   */
  public void replenish(double amount_) {
    if (amount_ <= 0) {
      throw new IllegalArgumentException("Incorrect amount");
    }

    amount += amount_;
  }

  /**
   * {@inheritDoc}
   */
  public void withdraw(double amount_) {
    if (amount_ <= 0) {
      throw new IllegalArgumentException("Incorrect amount");
    }

    if (amount_ > amount) {
      throw new IllegalArgumentException("There are not enough funds in the account");
    }

    amount -= amount_;
  }

  /**
   * {@inheritDoc}
   */
  public void update(boolean changeOfMonth) {
    accumulatedInterest += amount * interest / 100 / daysInYear;
    if (changeOfMonth) {
      amount += accumulatedInterest;
      accumulatedInterest = 0;
    }
  }
}
