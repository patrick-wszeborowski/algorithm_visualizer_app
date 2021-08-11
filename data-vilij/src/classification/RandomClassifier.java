// Patrick Wszeborowski
// SBU ID: 111007547
// CSE 219 Spring 2018

package classification;

import dataprocessors.AppData;
import dataprocessors.TSDProcessor;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;
import ui.AppUI;
import vilij.templates.ApplicationTemplate;

/**
 * @author Ritwik Banerjee
 */
public class RandomClassifier extends Classifier {

    private static final Random RAND = new Random();
    protected List<Integer> output;
    ApplicationTemplate applicationTemplate;
    
    @SuppressWarnings("FieldCanBeLocal")
    // this mock classifier doesn't actually use the data, but a real classifier will
    private DataSet dataset;

    private final int maxIterations;
    private final int updateInterval;

    // currently, this value does not change after instantiation
    private final AtomicBoolean tocontinue;

    @Override
    public List<Integer> getOutput() { return output; }
    
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

    public RandomClassifier(DataSet dataset,
                            int maxIterations,
                            int updateInterval,
                            boolean tocontinue,
                            ApplicationTemplate applicationTemplate) {
        this.dataset = dataset;
        this.maxIterations = maxIterations;
        this.updateInterval = updateInterval;
        this.tocontinue = new AtomicBoolean(tocontinue);
        this.applicationTemplate = applicationTemplate;
    }

    @Override
    public void run() {
        synchronized(this) {
            for (int i = 1; i <= maxIterations; i++) {
            int xCoefficient = new Long(-1 * Math.round((2 * RAND.nextDouble() - 1) * 10)).intValue();
            int yCoefficient = 10;
            int constant = RAND.nextInt(11);
            // this is the real output of the classifier
            output = Arrays.asList(xCoefficient, yCoefficient, constant);
            double minXValueInChart = Double.MAX_VALUE;
            double maxXValueInChart = Double.MIN_VALUE;
            TSDProcessor p = new TSDProcessor();
            for (final XYChart.Series<Number, Number> series : ((AppUI) applicationTemplate.getUIComponent()).getChart().getData()) {
                for (final XYChart.Data<Number, Number> data : series.getData()) {
                    if (data.getXValue().doubleValue() < minXValueInChart) {
                        minXValueInChart = data.getXValue().doubleValue();
                    }
                    if (data.getXValue().doubleValue() > maxXValueInChart) {
                        maxXValueInChart = data.getXValue().doubleValue();
                    }
                }
            }
            Integer A = getOutput().get(0);
            Integer B = getOutput().get(1);
            Integer C = getOutput().get(2);
            double y1 = (C - (A * minXValueInChart)) / B;
            double y2 = (C - (A * maxXValueInChart)) / B;
            XYChart.Data<Number, Number> data = new XYChart.Data(minXValueInChart, y1);
            XYChart.Data<Number, Number> data2 = new XYChart.Data(maxXValueInChart, y2);
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setNode(((AppUI) applicationTemplate.getUIComponent()).getChart());
            series.setName("Classification");
            series.getNode().setId("Classification");
            series.getData().addAll(data, data2);
            Platform.runLater(() -> {
                ((AppUI) applicationTemplate.getUIComponent()).getChart().getData().remove(series);
                ((AppUI) applicationTemplate.getUIComponent()).getChart().getData().clear();
                ((AppUI) applicationTemplate.getUIComponent()).getChart().getData().add(series);
                ((AppData) applicationTemplate.getDataComponent()).displayData();
            });      
            // everything below is just for internal viewing of how the output is changing
            // in the final project, such changes will be dynamically visible in the UI
            if (i % updateInterval == 0) {
                System.out.printf("Iteration number %d: ", i); //
                flush();
            }
            if (i > maxIterations * .6 && RAND.nextDouble() < 0.05) {
                System.out.printf("Iteration number %d: ", i);
                flush();
                break;
            }
            if (tocontinue.get() == false) {
                try {
                    System.out.print(i);
                    if (i == maxIterations)
                        Platform.runLater(() -> {
                        ((AppUI) applicationTemplate.getUIComponent()).setRunButton(true);
                        });
                    this.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(RandomClassifier.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                try {
                    Thread.sleep(updateInterval * 1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(RandomClassifier.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        }
    }

    // for internal viewing only
    protected void flush() {
        System.out.printf("%d\t%d\t%d%n", output.get(0), output.get(1), output.get(2));
    }

    /** A placeholder main method to just make sure this code runs smoothly
     * @param args */
    public static void main(String... args) throws IOException {
        DataSet          dataset    = DataSet.fromTSDFile(Paths.get("data-vilij\\resources\\data\\sample.tsd"));
//        RandomClassifier classifier = new RandomClassifier(dataset, 100, 5, true, applicationTemplate);
//        classifier.run(); // no multithreading yet
    }
}