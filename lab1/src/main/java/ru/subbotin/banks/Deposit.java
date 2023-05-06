package ru.subbotin.banks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * It is a bank account. The money cannot be withdrawn before the deposit expires. Has a compound
 * interest on the balance.
 */
public class Deposit implements Account {

  private final int daysInYear = 365;

  public Deposit(InterestForDeposit interest_, LocalDate closingDate_) {
    interest = interest_;
    closingDate = closingDate_;
    id = UUID.randomUUID();
    transactions = new ArrayList<>();
    amount = 0;
  }

  private ArrayList<Transaction> transactions;
  public LocalDate closingDate;
  private double accumulatedInterest;
  private InterestForDeposit interest;
  private final UUID id;
  private double amount;

  /**
   * @return The money that has accumulated during this billing month
   */
  public double getAccumulatedInterest() {
    return accumulatedInterest;
  }

  /**
   * @return Interest for this account
   */
  public InterestForDeposit getInterest() {
    return interest;
  }

  /**
   * Change interest for this account
   *
   * @param newValue for interest
   */
  public void setInterest(InterestForDeposit newValue) {
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
  public void cancellationTransaction(Transaction transaction) {
    if (transaction.getAccountFrom() == this) {
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
    if (closingDate.isAfter(LocalDate.now())) {
      throw new UnsupportedOperationException(
          "You cannot transfer money from the deposit until the expiration date");
    }
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
    closingDate = closingDate.minusDays(1);
    if (closingDate.isBefore(LocalDate.now())) {
      return;
    }

    accumulatedInterest += amount * interest.getInterest(amount) / 100 / daysInYear;
    if (changeOfMonth) {
      amount += accumulatedInterest;
      accumulatedInterest = 0;
    }
  }
}
