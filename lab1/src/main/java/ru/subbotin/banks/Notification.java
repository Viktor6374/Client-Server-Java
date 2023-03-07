package ru.subbotin.banks;

/**
 * Sets the methods that the system uses for notifications
 */
public interface Notification {

  /**
   * Sets the value (in string format) that the notification notifies about the change
   */
  public String getChangingValue();

  /**
   * Shows the value of the value that has been changed
   */
  public Object getOldValue();

  /**
   * Shows the new value of the value
   */
  public Object getNewValue();
}