package ru.subbotin.banks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * The balance can go into negative within the specified limit, a fixed commission is charged for this.
 * @author Subbotin Viktor
 */
public class Credit implements Account{
  public Credit(double commision, double limit) {
    setCommission(commision);
    setLimit(limit);
    id = UUID.randomUUID();
    transactions = new ArrayList<>();
    amount = 0;
  }

  /**
   * Changes a doubtful account to a regular one
   */
  public Credit(DoubtfulCredit doubtfulCredit) {
    transactions.addAll(doubtfulCredit.getTransactions());
    commission = doubtfulCredit.getCommission();
    limit = doubtfulCredit.getLimit();
    id = doubtfulCredit.getId();
    amount = doubtfulCredit.getAmount();
  }
  private ArrayList<Transaction> transactions;
  private double commission;
  private double limit;
  private final UUID id;
  private double amount;

  public double getCommission() {
    return commission;
  }

  protected void setCommission(double newValue) {
    if (newValue < 0) {
      throw new IllegalArgumentException("Commission can't be less 0");
    }

    commission = newValue;
  }

  public double getLimit() {
    return limit;
  }

  protected void setLimit(double newValue) {
    if(newValue < 0) {
      throw new IllegalArgumentException("The limit can't be less than 0");
    }

    limit = newValue;
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
      double summTransactions = 0;
      for (int i = transactions.size() - 1; i >=0; i--) {
        if (transactions.get(i) == transaction) {
          break;
        }

        summTransactions += transactions.get(i).getAmount();
      }

      if (amount + summTransactions < 0) {
        amount += commission;
      }

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
      if (amount_ + commission > amount + limit) {
        throw new IllegalArgumentException("There are not enough funds in the account");
      }

      amount -= amount_ + commission;
      return;
    }

    amount -= amount_;
  }

  /**
   * {@inheritDoc}
   */
  public void update(boolean changeOfMonth) { }
}

