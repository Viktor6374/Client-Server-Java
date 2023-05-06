package ru.subbotin.banks;

/**
 * It is used to notify users about a change in any value
 */
public class SimpleNotification implements Notification{
  public SimpleNotification(String changingValue_, Object oldValue_, Object newValue_){
    if (changingValue_.isEmpty() || changingValue_ == null || newValue_ == null){
      throw new IllegalArgumentException();
    }

    changingValue = changingValue_;
    oldValue = oldValue_;
    newValue = newValue_;
  }
  private String changingValue;
  private Object oldValue;
  private Object newValue;
  /**
   * {@inheritDoc}
   */
  public String getChangingValue(){
    return changingValue;
  }

  /**
   * {@inheritDoc}
   */
  public Object getOldValue(){
    return oldValue;
  }

  /**
   * {@inheritDoc}
   */
  public Object getNewValue(){
    return newValue;
  }
}