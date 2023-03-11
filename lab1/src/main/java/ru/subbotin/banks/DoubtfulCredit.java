package ru.subbotin.banks;

/**
 * It is a Credit for doubtful clients.
 */
public class DoubtfulCredit extends Credit implements Doubtful{
  public DoubtfulCredit(double commission, double limit, double limitWithdraw, double limitTransaction) {
    super(commission, limit);
    setLimitWithdraw(limitWithdraw);
    setLimitTransaction(limitTransaction);
  }

  private double limitWithdraw;
  private double limitTransaction;
  private double counterWithdrawInMonth = 0;
  private double counterTransactionInMonth = 0;

  public double getLimitWithdraw() {
    return limitWithdraw;
  }

  /**
   * It is a Debit for doubtful clients.
   */
  public void setLimitWithdraw(double newValue) {
    if (newValue <= 0){
      throw new IllegalArgumentException("Limit cant be <= 0");
    }

    limitWithdraw = newValue;
  }

  public double getLimitTransaction() {
    return limitTransaction;
  }

  /**
   * It is a Debit for doubtful clients.
   */
  public void setLimitTransaction(double newValue){
    if (newValue <= 0){
      throw new IllegalArgumentException("Limit cant be <= 0");
    }

    limitTransaction = newValue;
  }

  public double getCounterWithdrawInMonth() {
    return counterWithdrawInMonth;
  }

  public double getCounterTransactionInMonth() {
    return counterTransactionInMonth;
  }

  /**
   * It is a Debit for doubtful clients.
   */
  @Override
  public void withdraw(double amount){
    if (limitWithdraw < counterWithdrawInMonth + amount){
      throw new IllegalArgumentException("The limit has been reached");
    }

    counterWithdrawInMonth += amount;
    super.withdraw(amount);
  }

  /**
   * It is a Debit for doubtful clients.
   */
  @Override
  public void transaction(Transaction transaction){
    if (transaction.getAccountFrom() == this && limitTransaction < counterTransactionInMonth + getAmount()) {
      throw new IllegalArgumentException("The limit has been reached");
    }

    counterTransactionInMonth += transaction.getAmount();
    super.transaction(transaction);
  }

  /**
   * It is a Debit for doubtful clients.
   */
  @Override
  public void update(boolean changeOfMonth){
    if (changeOfMonth){
      counterTransactionInMonth = 0;
      counterWithdrawInMonth = 0;
    }

    super.update(changeOfMonth);
  }
}
