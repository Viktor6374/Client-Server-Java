package ru.subbotin.banks;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * The class that the client issues. Contains operations that the client can do
 * @author Subbotin Viktor
 */
public class Client {

  /**
   * All accounts which client having
   */
  private ArrayList<Account> accounts;
  /**
   * All notifications which client having
   */
  private ArrayList<Notification> notifications;
  private final String name;
  private final String surname;
  private String address;
  private Long passportNumber;
  public Client(String name_, String surname_, String address_, Long passportNumber_){
    if (name_ == null || name_ == "" || surname_ == null || surname_ == ""){
      throw new IllegalArgumentException();
    }
    accounts = new ArrayList<>();
    name = name_;
    surname = surname_;
    address = address_;
    passportNumber = passportNumber_;
    trustAccounts();
  }

  public Long getPassportNumber() {
    return passportNumber;
  }

  public List<Account> getAccounts() {
    return Collections.unmodifiableList(accounts);
  }

  public List<Notification> getNotifications(){
    return Collections.unmodifiableList(notifications);
  }
  public String getName() {
    return name;
  }

  public String getSurname(){
    return surname;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String newValue){
    if (newValue == null || newValue == ""){
      throw new IllegalArgumentException("Address can't be empty");
    }

    address = newValue;
    trustAccounts();
  }

  public void setPassportNumber(Long passportNumber_){
    if (passportNumber_ < 0 || passportNumber_ > 9999999999999999L){
      throw new IllegalArgumentException("Passport number must be 16 number long");
    }

    passportNumber = passportNumber_;
    trustAccounts();
  }

  public void addAccount(Account account){
    if (account == null){
      throw new IllegalArgumentException("Account cant be null");
    }

    accounts.add(account);
  }

  /**
   * Remove empty account. It will not be possible to restore it
   * @param account Account which must be removed
   */
  public void clouseAccount(Account account){
    if (account.getAmount() != 0){
      throw new IllegalStateException("Account is not empty");
    }

    accounts.remove(account);
  }

  /**
   * Adds a new notification to the client
   * @param notification Notification which will be added
   */
  public void newNotification(Notification notification){
    if (notification == null){
      throw new IllegalArgumentException("Notification cant be null");
    }

    notifications.add(notification);
  }

  /**
   * Remove all notifications for this client
   */
  public void clearNotifications(){
    notifications.clear();
  }

  /**
   * Сhecks whether this client is Doubtful
   * @return True if clint is doubtful. False if not
   */
  public boolean isDoubtful(){
    return address == null || address == "" || passportNumber == null;
  }

  /**
   * Makes all the client's accounts trusted if he has provided the missing information about himself
   */
  private void trustAccounts(){
    if (isDoubtful()){
      return;
    }

    for (Account account: accounts){
      if (account instanceof DoubtfulCredit){
        var newCredit = new Credit((DoubtfulCredit) account);
        accounts.remove(account);
        accounts.add(newCredit);
      } else if (account instanceof DoubtfulDebit) {
        var newDebit = new Debit((DoubtfulDebit) account);
        accounts.remove(account);
        accounts.add(newDebit);
      }
    }
  }
}
