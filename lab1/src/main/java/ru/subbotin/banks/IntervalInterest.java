package ru.subbotin.banks;

/**
 * Auxiliary class for InterestForDeposit. Sets the intervals for determining the percentage
 * @author Subbotin Viktor
 */
public class IntervalInterest{
  public IntervalInterest(double lowerBound_, double upperBound_, int value_){
    if (lowerBound_ < 0 || upperBound_ < 0 || value_ < 0){
      throw new IllegalArgumentException("Negative values are not allowed");
    }

    if (lowerBound_ > upperBound_){
      throw new IllegalArgumentException("The lower border cannot be larger than the upper one");
    }

    lowerBound = lowerBound_;
    upperBound = upperBound_;
    value = value_;
  }

  private final double lowerBound;
  private final double upperBound;
  private final int value;

  /**
   * @return The boundary after which the specified percentage works
   */
  public double getLowerBound(){
    return lowerBound;
  }

  /**
   * @return The boundary before which the specified percentage works
   */
  public double getUpperBound(){
    return upperBound;
  }

  /**
   * @return Percentage for a given interval
   */
  public int getValue(){
    return value;
  }
}
