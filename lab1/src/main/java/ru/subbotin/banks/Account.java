package ru.subbotin.banks;

import java.util.List;
import java.util.UUID;

/**
 * This interface combines all kinds of bank accounts
 * @author Subbotin Viktor
 */
public interface Account extends Observer {

  /**
   * @return Id for this account
   */
  public UUID getId();

  /**
   * @return Amount in this account
   */
  public double getAmount();

  /**
   * @return All transactions in this account
   */
  public List<Transaction> getTransactions();

  /**
   * Add transaction in this account
   * @param transaction Adding transaction
   */
  public void transaction(Transaction transaction);

  /**
   * Cancellation erroneous transaction
   * @param transaction cancelled transaction
   */
  public void cancellationTransaction(Transaction transaction);

  /**
   * Reduces the amount in this account
   * @param amount reduced amount
   */
  public void withdraw(double amount);

  /**
   * addendum the amount in this account
   * @param amount added amount
   */
  public void replenish(double amount);
}
