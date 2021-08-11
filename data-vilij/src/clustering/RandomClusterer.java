/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author Patrick
 */
public class RandomClusterer extends Clusterer {
    
    private int numberOfClusters;
    
    private final int maxIterations;
    private final int updateInterval;
    
    private final AtomicBoolean tocontinue;
    
    public RandomClusterer() {
        numberOfClusters = 0;
        maxIterations = 0;
        updateInterval = 0;
        tocontinue = new AtomicBoolean(false);
    }
    
    public RandomClusterer(int k) {
        if (k < 2)
            k = 2;
        else if (k > 4)
            k = 4;
        numberOfClusters = k;
        maxIterations = 0;
        updateInterval = 0;
        tocontinue = new AtomicBoolean(false);
    }
    
    @Override
    public int getMaxIterations() {
        return maxIterations;
    }

    @Override
    public int getUpdateInterval() {
        return updateInterval;
    }

    @Override
    public boolean tocontinue() {
        return tocontinue.get();
    }

    @Override
    public void run() {
         for (int i = 1; i <= maxIterations; i++) {
         
         }
    }
    
}
