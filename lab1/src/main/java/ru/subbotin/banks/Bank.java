package ru.subbotin.banks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class that the bank issues. Contains operations that the bank can do
 * @author Subbotin Viktor
 */
public class Bank {

  public Bank(String name_) throws IllegalArgumentException{
    if (name_ == null || name_ == "") {
      throw new IllegalArgumentException("Name cant be empty or null");
    }

    clients = new ArrayList<>();
    name = name_;
  }

  private ArrayList<Client> clients;
  private final String name;
  private Integer interestForDebit;
  private InterestForDeposit interestForDeposit;
  private Double commissionForCredit;
  private Double limitForCredit;
  private Double limitWithdrawForDoubtful;
  private Double limitTransactionForDoubtful;

  /**
   * @return All clients in bank
   */
  public List<Client> getClients() {
    return Collections.unmodifiableList(clients);
  }

  /**
   * @return Name of this bank
   */
  public String getName() {
    return name;
  }

  /**
   * @return Interest for all Debit accounts in this bank
   */
  public Integer getInterestForDebit() {
    return interestForDebit;
  }

  /**
   * Change interest for all Debit accounts in this bank
   * @param newValue new value for interest
   */
  public void setInterestForDebit(int newValue) {
    var notification = new SimpleNotification("InterestForDebit", interestForDebit, newValue);

    for (Client client : clients) {
      if (client.getAccounts().stream().anyMatch(x -> x instanceof Debit)) {
        client.newNotification(notification);
      }

      for (Account debit : client.getAccounts().stream().filter(x -> x instanceof Debit).collect(
          Collectors.toList())) {
        ((Debit) debit).setInterest(newValue);
      }
    }

    interestForDebit = newValue;
  }

  /**
   * @return Interest for all Deposit accounts in this bank
   */
  public InterestForDeposit getInterestForDeposit() {
    return interestForDeposit;
  }

  /**
   * Change interest for all Deposit accounts in this bank
   * @param newValue new value for interest
   */
<<<<<<< HEAD
  public void setInterestForDeposit(InterestForDeposit newValue) throws IllegalArgumentException {
=======
  public void setInterestForDeposit(InterestForDeposit newValue) {
>>>>>>> 2153359 (add: all classes)
    if (newValue == null) {
      throw new IllegalArgumentException("InterestForDeposit cant be null");
    }

    var notification = new SimpleNotification("InterestForDeposit", interestForDeposit, newValue);

    for (Client client : clients) {
      if (client.getAccounts().stream().anyMatch(x -> x instanceof Deposit)) {
        client.newNotification(notification);
      }

      for (Account deposit : client.getAccounts().stream().filter(x -> x instanceof Deposit)
          .collect(
              Collectors.toList())) {
        ((Deposit) deposit).setInterest(newValue);
      }
    }

    interestForDeposit = newValue;
  }

  /**
   * @return Commission for all Credit accounts in this bank
   */
  public Double getCommissionForCredit() {
    return commissionForCredit;
  }

  /**
   * Change commission for all Credit accounts in this bank
   * @param newValue new value for commission
   */
  public void setCommissionForCredit(double newValue) {
    notificationForCredit(commissionForCredit, newValue, "CommissionForCredit");
    for (Client client : clients) {
      for (Account credit : client.getAccounts().stream().filter(x -> x instanceof Credit).collect(
          Collectors.toList())) {
        ((Credit) credit).setCommission(newValue);
      }
    }

    commissionForCredit = newValue;
  }

  /**
   * @return Limit for all Credit accounts in this bank
   */
  public Double getLimitForCredit() {
    return limitForCredit;
  }

  /**
   * Change limit for all Credit accounts in this bank
   * @param newValue new value for limit
   */
  public void setLimitForCredit(double newValue) {
    notificationForCredit(limitForCredit, newValue, "LimitForCredit");
    for (Client client : clients) {
      for (Account credit : client.getAccounts().stream().filter(x -> x instanceof Credit).collect(
          Collectors.toList())) {
        ((Credit) credit).setLimit(newValue);
      }
    }

    limitForCredit = newValue;
  }

  /**
   * @return Limit withdraw for all Doubtful accounts in this bank
   */
  public Double getLimitWithdrawForDoubtful() {
    return limitWithdrawForDoubtful;
  }

  /**
   * Change limit withdraw for all Doubtful accounts in this bank
   * @param newValue new value for limit
   */
  public void setLimitWithdrawForDoubtful(double newValue) {
    notificationForDoubtful(limitWithdrawForDoubtful, newValue, "LimitWithdrawForDoubtful");
    for (Client client : clients) {
      for (Account doubtful : client.getAccounts().stream().filter(x -> x instanceof Doubtful).collect(
          Collectors.toList())) {
        ((Doubtful) doubtful).setLimitWithdraw(newValue);
      }
    }

    limitWithdrawForDoubtful = newValue;
  }

  /**
   * @return Limit for all Doubtful accounts in this bank
   */
  public Double getLimitTransactionForDoubtful() {
    return limitTransactionForDoubtful;
  }

  /**
   * Change limit transaction for all Doubtful accounts in this bank
   * @param newValue new value for limit
   */
  public void setLimitTransactionForDoubtful(double newValue) {
    notificationForDoubtful(limitTransactionForDoubtful, newValue, "LimitTransactionForDoubtful");
    for (Client client : clients) {
      for (Account doubtful : client.getAccounts().stream().filter(x -> x instanceof Doubtful).collect(
          Collectors.toList())) {
        ((Doubtful) doubtful).setLimitTransaction(newValue);
      }
    }

    limitTransactionForDoubtful = newValue;
  }

  /**
   * Create a new client in this bank and add this in system
   * @return created client
   */
  public Client createClient(String name, String surname, String address, Long passportNumber){
    var client = new Client(name, surname, address, passportNumber);
    clients.add(client);

    return client;
  }

  /**
   * Create new Debit account for concrete client
   * @param client the client for which the account is being created
   */
<<<<<<< HEAD
  public void addDebit(Client client) throws IllegalArgumentException{
=======
  public void addDebit(Client client){
>>>>>>> 2153359 (add: all classes)
    if (client == null){
      throw new IllegalArgumentException("client cant be null");
    }

    if (client.isDoubtful()){
      if (interestForDebit == null || limitWithdrawForDoubtful == null || limitTransactionForDoubtful == null){
        throw new IllegalArgumentException("Please, add necessary argument");
      }

      client.addAccount(new DoubtfulDebit(interestForDebit, limitWithdrawForDoubtful, limitTransactionForDoubtful));
    } else {
      if (interestForDebit == null){
        throw new IllegalArgumentException("Please, add necessary argument");
      }

      client.addAccount(new Debit(interestForDebit));
    }
  }

  /**
   * Create new Deposit account for concrete client
   * @param client the client for which the account is being created
   * @param clothingDate date when account will be closed
   */
<<<<<<< HEAD
  public void addDeposit(Client client, LocalDate clothingDate) throws IllegalArgumentException{
=======
  public void addDeposit(Client client, LocalDate clothingDate){
>>>>>>> 2153359 (add: all classes)
    if (client == null){
      throw new IllegalArgumentException("Client cant be null");
    }
    if (interestForDeposit == null){
      throw new IllegalArgumentException("Please, add necessary argument");
    }

    client.addAccount(new Deposit(interestForDeposit, clothingDate));
  }

  /**
   * Create new Credit account for concrete client
   * @param client the client for which the account is being created
   */
<<<<<<< HEAD
  public void addCredit(Client client) throws IllegalArgumentException{
=======
  public void addCredit(Client client){
>>>>>>> 2153359 (add: all classes)
    if (client == null){
      throw new IllegalArgumentException("client cant be null");
    }

    if (client.isDoubtful()){
      if (commissionForCredit == null || limitForCredit == null || limitWithdrawForDoubtful == null || limitTransactionForDoubtful == null){
        throw new IllegalArgumentException("Please, add necessary argument");
      }

      client.addAccount(new DoubtfulCredit(commissionForCredit, limitForCredit, limitWithdrawForDoubtful, limitTransactionForDoubtful));
    } else {
      if (commissionForCredit == null || limitForCredit == null){
        throw new IllegalArgumentException("Please, add necessary argument");
      }

      client.addAccount(new Credit(commissionForCredit, limitForCredit));
    }
  }

  /**
   * used to create notifications for everyone Credit accounts
   */
  private void notificationForCredit(Double param, double value, String name) {
    var notification = new SimpleNotification(name, param, value);

    for (Client client : clients) {
      if (client.getAccounts().stream().anyMatch(x -> x instanceof Credit)) {
        client.newNotification(notification);
      }
    }
  }

  /**
   * used to create notifications for everyone Doubtful accounts
   */
  private void notificationForDoubtful(Double param, double value, String name) {
    var notification = new SimpleNotification(name, param, value);

    for (Client client : clients) {
      if (client.getAccounts().stream().anyMatch(x -> x instanceof Doubtful)) {
        client.newNotification(notification);
      }
    }
  }
}
