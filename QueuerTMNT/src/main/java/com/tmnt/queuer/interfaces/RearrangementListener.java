package com.tmnt.queuer.interfaces;

/**
 * Created by billzito on 1/18/14.
 */
public interface RearrangementListener {
    public void onStartedRearranging();
    public void swapElements(int indexOne, int indexTwo);
    public void onFinishedRearranging();
}
