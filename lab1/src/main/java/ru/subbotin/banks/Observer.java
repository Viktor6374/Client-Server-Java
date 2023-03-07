package ru.subbotin.banks;

/**
 * Classes inheriting the interface have the ability to receive notifications about time changes. Is
 * part of the time acceleration mechanism
 */
public interface Observer {

  /**
   * A method that notifies classes about the change of day
   *
   * @param changeOfMonth If true, there is a change of month (some accounts charge interest once a
   *                      month)
   */
  public void update(boolean changeOfMonth);
}