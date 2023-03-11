package ru.subbotin.banks;

/**
 * It is a Debit for doubtful clients.
 */
public class DoubtfulDebit extends Debit implements Doubtful {

  public DoubtfulDebit(int interest, double limitWithdraw, double limitTransaction) {
    super(interest);
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
   * {@inheritDoc}
   */
  public void setLimitWithdraw(double newValue) {
    if (newValue <= 0) {
      throw new IllegalArgumentException("Limit cant be <= 0");
    }

    limitWithdraw = newValue;
  }

  public double getLimitTransaction() {
    return limitTransaction;
  }

  /**
   * {@inheritDoc}
   */
  public void setLimitTransaction(double newValue) {
    if (newValue <= 0) {
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
   * {@inheritDoc}
   */
  @Override
  public void withdraw(double amount) {
    if (limitWithdraw < counterWithdrawInMonth + amount) {
      throw new IllegalArgumentException("The limit has been reached");
    }

    counterWithdrawInMonth += amount;
    super.withdraw(amount);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void transaction(Transaction transaction) {
    if (transaction.getAccountFrom() == this
        && limitTransaction < counterTransactionInMonth + getAmount()) {
      throw new IllegalArgumentException("The limit has been reached");
    }

    counterTransactionInMonth += transaction.getAmount();
    super.transaction(transaction);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void update(boolean changeOfMonth) {
    if (changeOfMonth) {
      counterTransactionInMonth = 0;
      counterWithdrawInMonth = 0;
    }

    super.update(changeOfMonth);
  }
}
