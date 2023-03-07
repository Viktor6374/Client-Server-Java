package ru.subbotin.banks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The class responsible for calculating compound interest for the deposit
 * @author Subbotin Viktor
 */
public class InterestForDeposit {
  private ArrayList<IntervalInterest> intervals;
  private InterestForDeposit(ArrayList<IntervalInterest> intervals_){
    intervals = intervals_;
  }
  private static BuilderInterest builder;

  /**
   * @return instance for BuilderInterest
   */
  public static BuilderInterest getBuilderInterest(){
    if (builder == null){
      builder = new BuilderInterest();
    }

    return builder;
  }

  /**
   * @return all possible intervals for determining the percentage
   */
  public List<IntervalInterest> getIntervals(){
    return Collections.unmodifiableList(intervals);
  }

  /**
   * Determines the percentage at which the deposit works for a given amount
   * @param amount The amount for which you need to determine the percentage
   * @return The percentage that was determined
   */
  public int getInterest(double amount){
    return intervals.stream().filter(x -> x.getUpperBound() > amount && x.getLowerBound() <= amount).findFirst().get().getValue();
  }

  /**
   * The class responsible for creating the instance InterestForDeposit (pattern Builder)
   */
  public static class BuilderInterest{
    private ArrayList<IntervalInterest> intervals= new ArrayList<>();

    /**
     * Adds a new level of interest accrual
     * @param upperBound Determines up to what amount this percentage works
     * @param value percentage used
     */
    public void addLevel(double upperBound, int value){
      if (intervals.size() == 0){
        intervals.add(new IntervalInterest(0, upperBound, value));
      }

      intervals.add(new IntervalInterest(intervals.get(intervals.size() - 1).getUpperBound(), upperBound, value));
    }

    /**
     * Finishes creating an instance
     * @return ready-made instance
     */
    public InterestForDeposit build(){
      var result = new InterestForDeposit(intervals);
      intervals = new ArrayList<IntervalInterest>();

      return result;
    }
  }
}
