package ru.subbotin.banks;

/**
 * The interface is inherited by accounts owned by doubtful clients (Customers who have provided
 * incomplete information about themselves)
 * @author Subbotin Viktor
 */
public interface Doubtful extends Account {

  /**
   * Change limit for withdraw for this account
   *
   * @param limitWithdraw new value for limit
   */
  public void setLimitWithdraw(double limitWithdraw);

  /**
   * Change limit for transaction for this account
   *
   * @param limitTransaction new value for limit
   */
  public void setLimitTransaction(double limitTransaction);
}
