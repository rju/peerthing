package de.peerthing.scenarioeditor.model.impl;

import de.peerthing.scenarioeditor.model.IDistribution;
import de.peerthing.scenarioeditor.model.IScenario;

/**
 * Default implementation of IDistribution.
 * 
 * @author Michael Gottschalk, Patrik Schulz
 * @reviewer Hendrik Angenendt
 *
 */
public class Distribution implements IDistribution {
    private DistributionType type;

    private double min;

    private double max;

    private double mean;

    private double variance;
    
    private IScenario scenario;

    /**
     * the standard constructor (which is used when the user add an element
     * of this kind. The handed scenario is set so the object knows to
     * which element this object belongs.     
     */
    public Distribution(IScenario scenario) {
    	this.scenario = scenario;
        this.type = IDistribution.DistributionType.uniform;
    }
    
    public Distribution(IDistribution original, IScenario scenario) {
    	this.scenario = scenario;
    	this.min = original.getMin();
    	this.max = original.getMax();    	
        this.type = original.getType();
        this.mean = original.getMean();
        this.variance = original.getVariance();
    }
    
    /**
     * creates an copy of the handed original object. the copy object saves
     * the old values of the original object so the undo operation can
     * work
     * @param original
     */
    public Distribution (IDistribution original){
    	this.scenario = original.getScenario();
    	this.min = original.getMin();
    	this.max = original.getMax();
    	this.mean = original.getMean();
    	this.type = original.getType();
    	this.variance = original.getVariance();
    }

    public void setType(DistributionType type) {
        this.type = type;
    }

    public DistributionType getType() {
        return type;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMin() {
        return min;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMax() {
        return max;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getMean() {
        return mean;
    }

    public void setVariance(double variance) {
        this.variance = variance;
    }

    public double getVariance() {
        return variance;
    }

    public void setScenario(IScenario scenario) {
        this.scenario = scenario;
    }

    public IScenario getScenario() {
        return scenario;
    }

}
