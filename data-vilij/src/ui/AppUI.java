// Patrick Wszeborowski
// SBU ID: 111007547
// CSE 219 Spring 2018

package ui;

import actions.AppActions;
import classification.DataSet;
import classification.RandomClassifier;
import clustering.KMeansClusterer;
import dataprocessors.AppData;
import dataprocessors.TSDProcessor;
import static java.io.File.separator;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vilij.templates.ApplicationTemplate;
import vilij.templates.UITemplate;
import static settings.AppPropertyTypes.DISPLAY_TEXT;
import static settings.AppPropertyTypes.SCREENSHOT_ICON;
import static settings.AppPropertyTypes.SCREENSHOT_TOOLTIP;
import static vilij.settings.PropertyTypes.CSS_RESOURCE_FILENAME;
import static vilij.settings.PropertyTypes.GUI_RESOURCE_PATH;
import static vilij.settings.PropertyTypes.ICONS_RESOURCE_PATH;
import static vilij.settings.PropertyTypes.IS_WINDOW_RESIZABLE;
import static vilij.settings.PropertyTypes.WINDOW_HEIGHT;
import static vilij.settings.PropertyTypes.WINDOW_WIDTH;

/**
 * This is the application's user interface implementation.
 *
 * @author Ritwik Banerjee
 */
public final class AppUI extends UITemplate {

    /** The application to which this class of actions belongs. */
    ApplicationTemplate applicationTemplate;

    @SuppressWarnings("FieldCanBeLocal")
    private Button                       scrnshotButton; // toolbar button to take a screenshot of the data
    private LineChart<Number, Number> chart;          // the chart where data will be displayed
    private Button                       displayButton;  // workspace button to display data on the chart
    private TextArea                     textArea;       // text area for new data input
    private boolean                      hasNewText;     // whether or not the text area has any new data since last display
    private boolean                      newButtonCheck; // checks whether the new button has been pressed
    private CheckBox                     checkBox;       // tick to change text ara to read-only
    private VBox                         vbox;
    private HBox                         hbox;
    private Label                        infoLabel;
    private Button                       doneButton;
    private GridPane                     gridPane;
    private boolean                      algorithmCheck;
    private Button                       classificationButton;
    private Button                       clusteringButton;
    private Label                        algA;
    private Label                        algB;
    private Label                        algC;
    private RadioButton                  algAchoice;
    private RadioButton                  algBchoice;
    private RadioButton                  algCchoice;
    private RadioButton                  algAchoice2;
    private RadioButton                  algBchoice2;
    private RadioButton                  algCchoice2;
    private Button                       algAbtn;
    private Button                       algBbtn;
    private Button                       algCbtn;
    private Button                       algAbtn2;
    private Button                       algBbtn2;
    private Button                       algCbtn2;
    private Button                       runBtn;
    private Label                        iterations;
    private Label                        iterations2;
    private Label                        iterations3;
    private Label                        iterations4;
    private Label                        iterations5;
    private Label                        iterations6;
    private Label                        updateInterval;
    private Label                        updateInterval2;
    private Label                        updateInterval3;
    private Label                        updateInterval4;
    private Label                        updateInterval5;
    private Label                        updateInterval6;
    private Label                        continuousRun;
    private Label                        continuousRun2;
    private Label                        continuousRun3;
    private Label                        continuousRun4;
    private Label                        continuousRun5;
    private Label                        continuousRun6;
    private GridPane                     classificationPane;
    private GridPane                     clusteringPane;
    private TextField                    updateTextA;
    private TextField                    iterationsTextA;
    private CheckBox                     continuousRunCheckA;
    private GridPane                     algorithmSettingsA;
    private TextField                    updateTextB;
    private TextField                    iterationsTextB;
    private CheckBox                     continuousRunCheckB;
    private GridPane                     algorithmSettingsB;
    private TextField                    updateTextC;
    private TextField                    iterationsTextC;
    private CheckBox                     continuousRunCheckC;
    private GridPane                     algorithmSettingsC;
    private TextField                    updateTextD;
    private TextField                    iterationsTextD;
    private CheckBox                     continuousRunCheckD;
    private GridPane                     algorithmSettingsD;
    private TextField                    updateTextE;
    private TextField                    iterationsTextE;
    private CheckBox                     continuousRunCheckE;
    private GridPane                     algorithmSettingsE;
    private TextField                    updateTextF;
    private TextField                    iterationsTextF;
    private CheckBox                     continuousRunCheckF;
    private GridPane                     algorithmSettingsF;
    private Label                        numLabels;
    private Label                        numLabels2;
    private Label                        numLabels3;
    private Label                        numClusters;
    private Label                        numClusters2;
    private Label                        numClusters3;
    private TextField                    numClustersA;
    private TextField                    numClustersB;
    private TextField                    numClustersC;
    private TextField                    numLabelsD;
    private TextField                    numLabelsE;
    private TextField                    numLabelsF;
    private boolean                      runButtonFlag;
    private boolean                      runButtonFlag2;
    
    public LineChart<Number, Number> getChart() { 
        return chart;
    }
    
    public String getText() {
        return textArea.getText();
    }
    
    public void setTextArea(String text) {
        textArea.appendText(text);
    }
    
    public TextArea getTextArea() {
        return textArea;
    }
    
    public void hasNewText(boolean b) {
        hasNewText = b;
    }
    
    public boolean getNewButtonCheck() {
        return newButtonCheck;
    }
    
    public void setNewButtonCheck(boolean b) {
        newButtonCheck = b;
    }
    
    public CheckBox getCheckBox() {
        return checkBox;
    }
    
    public void disableScrollBar() {
        ScrollBar scrollBarv = (ScrollBar)textArea.lookup(".scroll-bar:vertical");
        scrollBarv.setDisable(true);
    }
    
    public AppUI(Stage primaryStage, ApplicationTemplate applicationTemplate) {
        super(primaryStage, applicationTemplate);
        this.applicationTemplate = applicationTemplate;
    }

    @Override
    protected void setResourcePaths(ApplicationTemplate applicationTemplate) {
        super.setResourcePaths(applicationTemplate);
    }

    @Override
    protected void setToolBar(ApplicationTemplate applicationTemplate) {
        super.setToolBar(applicationTemplate);
        scrnshotButton = setToolbarButton(String.join(separator, "/" + String.join(separator,
                                            applicationTemplate.manager.getPropertyValue(GUI_RESOURCE_PATH.name()),
                                            applicationTemplate.manager.getPropertyValue(ICONS_RESOURCE_PATH.name())),
                                            applicationTemplate.manager.getPropertyValue(SCREENSHOT_ICON.name())),
                                            applicationTemplate.manager.getPropertyValue(SCREENSHOT_TOOLTIP.name()), true);
        toolBar = new ToolBar(newButton, saveButton, loadButton, printButton, exitButton, scrnshotButton);
    }
    
    @Override
    protected void setToolbarHandlers(ApplicationTemplate applicationTemplate) {
        applicationTemplate.setActionComponent(new AppActions(applicationTemplate));
        newButton.setOnAction(e -> applicationTemplate.getActionComponent().handleNewRequest());
        saveButton.setOnAction(e -> applicationTemplate.getActionComponent().handleSaveRequest());
        loadButton.setOnAction(e -> applicationTemplate.getActionComponent().handleLoadRequest());
        exitButton.setOnAction(e -> applicationTemplate.getActionComponent().handleExitRequest());
        printButton.setOnAction(e -> applicationTemplate.getActionComponent().handlePrintRequest());
        scrnshotButton.setOnAction(e -> {
            try {
                ((AppActions)applicationTemplate.getActionComponent()).handleScreenshotRequest();
            }
            catch (IOException ex) {
                
            }
        });
    }

    @Override
    public void initialize() {
        layout();
        setWorkspaceActions();
    }

    @Override
    public void clear() {
        textArea.clear();
        AppData a = new AppData(applicationTemplate);
        TSDProcessor p = new TSDProcessor();
        a.clear();
        p.getDataPoints().entrySet().clear();
        p.getDataPoints().keySet().clear();
        p.getDataLabels().entrySet().clear();
        p.getDataLabels().keySet().clear();
        chart.getData().clear();
        newButton.setDisable(true);
        saveButton.setDisable(false);
        scrnshotButton.setDisable(true);
        removeInfo();
    }

    private void layout() {
        this.applicationTemplate.getUIComponent().getPrimaryWindow().setResizable(Boolean.parseBoolean(applicationTemplate.manager.getPropertyValue(IS_WINDOW_RESIZABLE.name())));
        displayButton = new Button(applicationTemplate.manager.getPropertyValue(DISPLAY_TEXT.name()));
        checkBox = new CheckBox("Read-Only");
        doneButton = new Button("Done");
        newButton.setDisable(false);
        chart = new LineChart<>(new NumberAxis(), new NumberAxis());
        chart.setHorizontalGridLinesVisible(false);
        chart.setVerticalGridLinesVisible(false);
        chart.setHorizontalZeroLineVisible(false);
        chart.setVerticalZeroLineVisible(false);
        textArea = new TextArea();
        textArea.setPrefColumnCount(25);
        textArea.setPrefRowCount(10);
        textArea.setVisible(false);
        displayButton.setVisible(false);
        checkBox.setVisible(false);
        doneButton.setVisible(false);
        gridPane = new GridPane();
        Label algoType = new Label("Algorithm Type");
//        algoType.setStyle("-fx-border-color:blue; -fx-background-color: blue;");
        classificationButton = new Button("Classification");
        algorithmCheck = false;
        clusteringButton = new Button("Clustering");
        gridPane.setGridLinesVisible(true);
        gridPane.addColumn(150, algoType);
        gridPane.addColumn(150, clusteringButton);
        gridPane.addColumn(150, classificationButton);
        algA = new Label("Algorithm A");
        algB = new Label("Algorithm B");
        algC = new Label("Algorithm C");
        algAbtn = new Button("Change");
        algBbtn = new Button("Change");
        algCbtn = new Button("Change");
        algAchoice = new RadioButton();
        algBchoice = new RadioButton();
        algCchoice = new RadioButton();
        algAchoice2 = new RadioButton();
        algBchoice2 = new RadioButton();
        algCchoice2 = new RadioButton();
        algAbtn2 = new Button("Change");
        algBbtn2 = new Button("Change");
        algCbtn2 = new Button("Change");
        iterations = new Label("Max. Iterations:");
        iterations2 = new Label("Max. Iterations:");
        iterations3 = new Label("Max. Iterations:");
        iterations4 = new Label("Max. Iterations:");
        iterations5 = new Label("Max. Iterations:");
        iterations6 = new Label("Max. Iterations:");
        updateInterval = new Label("Update Interval:");
        updateInterval2 = new Label("Update Interval:");
        updateInterval3 = new Label("Update Interval:");
        updateInterval4 = new Label("Update Interval:");
        updateInterval5 = new Label("Update Interval:");
        updateInterval6 = new Label("Update Interval:");
        continuousRun = new Label("Continuous Run?:");
        continuousRun2 = new Label("Continuous Run?:");
        continuousRun3 = new Label("Continuous Run?:");
        continuousRun4 = new Label("Continuous Run?:");
        continuousRun5 = new Label("Continuous Run?:");
        continuousRun6 = new Label("Continuous Run?:");
        numLabels = new Label("# of labels:");
        numLabels2 = new Label("# of labels:");
        numLabels3 = new Label("# of labels:");
        numClusters = new Label("# of clusters:");
        numClusters2 = new Label("# of clusters:");
        numClusters3 = new Label("# of clusters:");
        iterationsTextA = new TextField();
        updateTextA = new TextField();
        continuousRunCheckA = new CheckBox();
        algorithmSettingsA = new GridPane();
        iterationsTextB = new TextField();
        updateTextB = new TextField();
        continuousRunCheckB = new CheckBox();
        algorithmSettingsB = new GridPane();
        iterationsTextC = new TextField();
        updateTextC = new TextField();
        continuousRunCheckC = new CheckBox();
        algorithmSettingsC = new GridPane();
        iterationsTextD = new TextField();
        updateTextD = new TextField();
        continuousRunCheckD = new CheckBox();
        algorithmSettingsD = new GridPane();
        iterationsTextE = new TextField();
        updateTextE = new TextField();
        continuousRunCheckE = new CheckBox();
        algorithmSettingsE = new GridPane();
        iterationsTextF = new TextField();
        updateTextF = new TextField();
        continuousRunCheckF = new CheckBox();
        algorithmSettingsF = new GridPane();
        iterationsTextA.setText("0");
        updateTextA.setText("0");
        iterationsTextB.setText("0");
        updateTextB.setText("0");
        iterationsTextC.setText("0");
        updateTextC.setText("0");
        iterationsTextD.setText("0");
        updateTextD.setText("0");
        iterationsTextE.setText("0");
        updateTextE.setText("0");
        iterationsTextF.setText("0");
        updateTextF.setText("0");
        numClustersA = new TextField();
        numClustersA.setText("0");
        numClustersB = new TextField();
        numClustersB.setText("0");
        numClustersC = new TextField();
        numClustersC.setText("0");
        numLabelsD = new TextField();
        numLabelsD.setText("0");
        numLabelsE = new TextField();
        numLabelsE.setText("0");
        numLabelsF = new TextField();
        numLabelsF.setText("0");
        algorithmSettingsA.addRow(0, iterations, iterationsTextA);
        algorithmSettingsA.addRow(1, updateInterval, updateTextA);
        algorithmSettingsA.addRow(2, continuousRun, continuousRunCheckA);
        algorithmSettingsA.addRow(3, numLabels, numLabelsD);
        algorithmSettingsB.addRow(0, iterations2, iterationsTextB);
        algorithmSettingsB.addRow(1, updateInterval2, updateTextB);
        algorithmSettingsB.addRow(2, continuousRun2, continuousRunCheckB);
        algorithmSettingsB.addRow(3, numLabels3, numLabelsF);
        algorithmSettingsC.addRow(0, iterations3, iterationsTextC);
        algorithmSettingsC.addRow(1, updateInterval3, updateTextC);
        algorithmSettingsC.addRow(2, continuousRun3, continuousRunCheckC);
        algorithmSettingsC.addRow(3, numLabels2, numLabelsE);
        algorithmSettingsD.addRow(0, iterations4, iterationsTextD);
        algorithmSettingsD.addRow(1, updateInterval4, updateTextD);
        algorithmSettingsD.addRow(2, continuousRun4, continuousRunCheckD);
        algorithmSettingsD.addRow(3, numClusters, numClustersA);
        algorithmSettingsE.addRow(0, iterations5, iterationsTextE);
        algorithmSettingsE.addRow(1, updateInterval5, updateTextE);
        algorithmSettingsE.addRow(2, continuousRun5, continuousRunCheckE);
        algorithmSettingsE.addRow(3, numClusters3, numClustersC);
        algorithmSettingsF.addRow(0, iterations6, iterationsTextF);
        algorithmSettingsF.addRow(1, updateInterval6, updateTextF);
        algorithmSettingsF.addRow(2, continuousRun6, continuousRunCheckF);
        algorithmSettingsF.addRow(3, numClusters2, numClustersB);
        clusteringButton.setDisable(true);
        runBtn = new Button("Run");
        Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);
        vbox = new VBox(10, textArea, displayButton, checkBox, doneButton);
        hbox = new HBox(vbox, separator, chart);
        this.appPane.getScene().getStylesheets().add(getClass().getResource(cssPath.replace(applicationTemplate.manager.getPropertyValue(CSS_RESOURCE_FILENAME.name()),
                "ChartFormatting.css")).toExternalForm());
        this.appPane.setMaxHeight(Double.parseDouble(applicationTemplate.manager.getPropertyValue(WINDOW_HEIGHT.name())));
        this.appPane.setMaxWidth(Double.parseDouble(applicationTemplate.manager.getPropertyValue(WINDOW_WIDTH.name())));  
        this.appPane.getChildren().add(hbox);
    }

    private void setWorkspaceActions() {
        displayButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                AppData appTemp = new AppData(applicationTemplate);
                try {
                    removeInfo();
                    AppData a = new AppData(applicationTemplate);
                    a.clear();
                    chart.getData().clear();
                    appTemp.loadData(getText());
                } catch (Exception ex) {
                    Logger.getLogger(AppUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                appTemp.displayData();
                TSDProcessor p = appTemp.getProcessor();
                if (p.getDataPoints().entrySet().isEmpty() && p.getDataPoints().keySet().isEmpty())
                    scrnshotButton.setDisable(false);
                else
                    scrnshotButton.setDisable(true);
                double minXValueInChart = Double.MAX_VALUE;
                double averageYValue = 0;
                double maxXValueInChart = 0;
                int counter = 0;
                for (final Series<Number, Number> series : chart.getData()) {
                    for (final Data<Number, Number> data : series.getData()) {
                        if (data.getXValue().doubleValue() < minXValueInChart)
                            minXValueInChart = data.getXValue().doubleValue();
                        if (data.getXValue().doubleValue() > maxXValueInChart)
                            maxXValueInChart = data.getXValue().doubleValue();
                        averageYValue += data.getYValue().doubleValue();
                        counter++;
                        for (String key : p.getDataPoints().keySet()) {
                            Node n = data.getNode();
                            Tooltip tooltip = new Tooltip();
                            tooltip.setText(key);
                            Tooltip.install(n.getParent(), tooltip);
                            
                            n.setOnMouseEntered((Event event) -> {
                                appPane.getScene().setCursor(javafx.scene.Cursor.CROSSHAIR);
                            });
                            n.setOnMouseExited((Event event) -> {
                                appPane.getScene().setCursor(javafx.scene.Cursor.DEFAULT);
                            });
                        }
                    }
                }
                averageYValue /= counter;
                try {
                    Series<Number, Number> series = new Series<>();
                    Data<Number, Number> data = new Data<>(minXValueInChart, averageYValue);
                    Data<Number, Number> data2 = new Data<>(maxXValueInChart, averageYValue);
                    series.setName("Average");
                    series.setNode(chart);
                    series.getNode().setId("average");
                    series.getData().add(data);
                    series.getData().add(data2);
                    chart.getData().add(series);
                }
                catch (Exception ex) {
                    Logger.getLogger(AppUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        textArea.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
            disableScrollBar();
            hasNewText = true;
            if (getText().equals("")) {
                saveButton.setDisable(false);
            }
            else {
                newButton.setDisable(false);
            }
        });
        checkBox.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            readOnly();
        });
        doneButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            removeInfo();
            if (doneButton.getText().equals("Done")) {
                try {
                    ((AppData)applicationTemplate.getDataComponent()).loadData(getText());
                    readOnly();
                    if (algorithmCheck == false)
                    doneButton.setText("Edit");
                } catch (Exception ex) {
                    Logger.getLogger(AppUI.class.getName()).log(Level.SEVERE, null, ex);
                }   
            }
            else {
                readOnly();
                vbox.getChildren().remove(infoLabel);
                vbox.getChildren().remove(gridPane);
                doneButton.setText("Done");
            }
        });
        classificationButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            classificationShow();
        });
        clusteringButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            clusteringShow();
        });
        algAchoice.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            runBtn.setDisable(true);
            algAchoice.setSelected(true);
            algBchoice.setSelected(false);
            algCchoice.setSelected(false);
            if (runButtonFlag && runButtonFlag2)
                runBtn.setDisable(false);
        });
        algBchoice.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            runBtn.setDisable(true);
            algAchoice.setSelected(false);
            algBchoice.setSelected(true);
            algCchoice.setSelected(false);
            if (runButtonFlag && runButtonFlag2)
                runBtn.setDisable(false);
        });
        algCchoice.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            runBtn.setDisable(true);
            algAchoice.setSelected(false);
            algBchoice.setSelected(false);
            algCchoice.setSelected(true);
            if (runButtonFlag && runButtonFlag2)
                runBtn.setDisable(false);
        });
        algAchoice2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            runBtn.setDisable(true);
            algAchoice2.setSelected(true);
            algBchoice2.setSelected(false);
            algCchoice2.setSelected(false);
            if (runButtonFlag && runButtonFlag2)
                runBtn.setDisable(false);
        });
        algBchoice2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            runBtn.setDisable(true);
            algAchoice2.setSelected(false);
            algBchoice2.setSelected(true);
            algCchoice2.setSelected(false);
            if (runButtonFlag && runButtonFlag2)
                runBtn.setDisable(false);
        });
        algCchoice2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            runBtn.setDisable(true);
            algAchoice2.setSelected(false);
            algBchoice2.setSelected(false);
            algCchoice2.setSelected(true);
            if (runButtonFlag && runButtonFlag2)
                runBtn.setDisable(false);
        });
        algAbtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            vbox.getChildren().removeAll(algorithmSettingsA, algorithmSettingsB, algorithmSettingsC, algorithmSettingsD, algorithmSettingsE, algorithmSettingsF);
            iterationsTextA.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
                try {
                    if (iterationsTextA.getText().equals("")) {
                        runButtonFlag = false;
                    }
                    else if (Integer.parseInt(iterationsTextA.getText()) < 1) {
                        iterationsTextA.setText("1");
                        runButtonFlag = false;
                    }
                    else if (Integer.parseInt(updateTextA.getText()) >= 1)
                        runButtonFlag = true;
                    } 
                catch (NumberFormatException w) {
                    iterationsTextA.setText("1");
                }
                try {
                    if (updateTextA.getText().equals("")) {
                         runButtonFlag = false;
                        }
                    else if (Integer.parseInt(updateTextA.getText()) < 1) {
                        updateTextA.setText("1");
                        runButtonFlag2 = false;
                    }
                    else if (Integer.parseInt(updateTextA.getText()) >= 1)
                        runButtonFlag2 = true;
                    }
                catch (NumberFormatException w) {
                    updateTextA.setText("1");
                }
                finally {
                    if (runButtonFlag && runButtonFlag2)
                        runBtn.setDisable(false);
                    else
                        runBtn.setDisable(true);
                }
            });
            vbox.getChildren().add(algorithmSettingsA);
        });
        algBbtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            vbox.getChildren().removeAll(algorithmSettingsA, algorithmSettingsB, algorithmSettingsC, algorithmSettingsD, algorithmSettingsE, algorithmSettingsF);
            iterationsTextB.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
                try {
                    if (iterationsTextB.getText().equals("")) {   
                        runButtonFlag = false;
                    }
                    else if (Integer.parseInt(iterationsTextB.getText()) < 1) {
                        iterationsTextB.setText("1");
                        runButtonFlag = false;
                    }
                    else if (Integer.parseInt(updateTextB.getText()) >= 1)
                        runButtonFlag = true;
                    }
                catch (NumberFormatException w) {
                    iterationsTextB.setText("1");
                }
                try {
                    if (updateTextB.getText().equals("")) {  
                         runButtonFlag = false;
                        }
                    else if (Integer.parseInt(updateTextB.getText()) < 1) {
                        updateTextB.setText("1");
                        runButtonFlag2 = false;
                    }
                    else if (Integer.parseInt(updateTextB.getText()) >= 1)
                        runButtonFlag2 = true;
                    }
            
                catch (NumberFormatException w) {
                    updateTextB.setText("1");
                }
                finally {
                    if (runButtonFlag && runButtonFlag2)
                        runBtn.setDisable(false);
                    else
                        runBtn.setDisable(true);
                }
            });
            vbox.getChildren().add(algorithmSettingsB);
        });
        algCbtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            vbox.getChildren().removeAll(algorithmSettingsA, algorithmSettingsB, algorithmSettingsC, algorithmSettingsD, algorithmSettingsE, algorithmSettingsF);
            iterationsTextC.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
                try {
                    if (iterationsTextC.getText().equals("")) {
                        runButtonFlag = false;
                    }
                    else if (Integer.parseInt(iterationsTextC.getText()) < 1) {
                        iterationsTextC.setText("1");
                        runButtonFlag = false;
                    }
                    else if (Integer.parseInt(updateTextC.getText()) >= 1)
                        runButtonFlag = true;
                    } 
                catch (NumberFormatException w) {
                    iterationsTextC.setText("1");
                }
                try {
                    if (updateTextC.getText().equals("")) {
                        runButtonFlag = false;
                    }
                    else if (Integer.parseInt(updateTextC.getText()) < 1) {
                        updateTextC.setText("1");
                        runButtonFlag2 = false;
                    }
                    else if (Integer.parseInt(updateTextC.getText()) >= 1)
                        runButtonFlag2 = true;
                    }
            
                catch (NumberFormatException w) {
                    updateTextC.setText("1");
                }
                finally {
                    if (runButtonFlag && runButtonFlag2)
                        runBtn.setDisable(false);
                    else
                        runBtn.setDisable(true);
                }
            });  
            vbox.getChildren().add(algorithmSettingsC);
        });
        algAbtn2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            vbox.getChildren().removeAll(algorithmSettingsA, algorithmSettingsB, algorithmSettingsC, algorithmSettingsD, algorithmSettingsE, algorithmSettingsF);
            iterationsTextD.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
                try {
                    if (iterationsTextD.getText().equals("")) {  
                         runButtonFlag = false;
                    }
                    else if (Integer.parseInt(iterationsTextD.getText()) < 1) {
                        iterationsTextD.setText("1");
                        runButtonFlag = false;
                    }
                    else if (Integer.parseInt(updateTextD.getText()) >= 1)
                        runButtonFlag = true;
                    } 
                catch (NumberFormatException w) {
                    iterationsTextD.setText("1");
                }
                try {
                    if (updateTextD.getText().equals("")) {
                        runButtonFlag = false;
                        }
                    else if (Integer.parseInt(updateTextD.getText()) < 1) {
                        updateTextD.setText("1");
                        runButtonFlag2 = false;
                    }
                    else if (Integer.parseInt(updateTextD.getText()) >= 1)
                        runButtonFlag2 = true;
                    }
            
                catch (NumberFormatException w) {
                    updateTextD.setText("1");
                }
                finally {
                    if (runButtonFlag && runButtonFlag2)
                        runBtn.setDisable(false);
                    else
                        runBtn.setDisable(true);
                }
            });
            vbox.getChildren().add(algorithmSettingsD);
        });
        algBbtn2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            vbox.getChildren().removeAll(algorithmSettingsA, algorithmSettingsB, algorithmSettingsC, algorithmSettingsD, algorithmSettingsE, algorithmSettingsF);
            iterationsTextE.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
                try {
                    if (iterationsTextE.getText().equals("")) {
                         runButtonFlag = false;
                    }
                    else if (Integer.parseInt(iterationsTextE.getText()) < 1) {
                        iterationsTextE.setText("1");
                        runButtonFlag = false;
                    }
                    else if (Integer.parseInt(updateTextE.getText()) >= 1)
                        runButtonFlag = true;
                    } 
                catch (NumberFormatException w) {
                    iterationsTextE.setText("1");
                }
                try {
                    if (updateTextE.getText().equals("")) {
                        runButtonFlag = false;
                    }
                    else if (Integer.parseInt(updateTextE.getText()) < 1) {
                        updateTextE.setText("1");
                        runButtonFlag2 = false;
                    }
                    else if (Integer.parseInt(updateTextE.getText()) >= 1)
                        runButtonFlag2 = true;
                    }
            
                catch (NumberFormatException w) {
                    updateTextE.setText("1");
                }
                finally {
                    if (runButtonFlag && runButtonFlag2)
                        runBtn.setDisable(false);
                    else
                        runBtn.setDisable(true);
                }
            }); 
            vbox.getChildren().add(algorithmSettingsE);
        });
        algCbtn2.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            vbox.getChildren().removeAll(algorithmSettingsA, algorithmSettingsB, algorithmSettingsC, algorithmSettingsD, algorithmSettingsE, algorithmSettingsF);
            iterationsTextF.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
                try {
                    if (iterationsTextF.getText().equals("")) {  
                         runButtonFlag = false;
                    }
                    else if (Integer.parseInt(iterationsTextF.getText()) < 1) {
                        iterationsTextF.setText("1");
                        runButtonFlag = false;
                    }
                    else if (Integer.parseInt(updateTextF.getText()) >= 1)
                        runButtonFlag = true;
                    } 
                catch (NumberFormatException w) {
                    iterationsTextF.setText("");
                }
                try {
                    if (updateTextF.getText().equals("")) {    
                         runButtonFlag = false;
                    }
                    else if (Integer.parseInt(updateTextF.getText()) < 1) {
                        updateTextF.setText("1");
                        runButtonFlag2 = false;
                    }
                    else if (Integer.parseInt(updateTextF.getText()) >= 1)
                        runButtonFlag2 = true;
                    }
            
                catch (NumberFormatException w) {
                    updateTextF.setText("1");
                }
                finally {
                    if (runButtonFlag && runButtonFlag2)
                        runBtn.setDisable(false);
                    else
                        runBtn.setDisable(true);
                }
            });
            vbox.getChildren().add(algorithmSettingsF);
        });
        runBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            scrnshotButton.setDisable(false);
            if (algAchoice2.isSelected()) {
                KMeansClusterer k;
                try {
                    k = new KMeansClusterer(DataSet.fromTSDFile(Paths.get("data-vilij\\resources\\data\\sample-data.tsd")),
                            Integer.parseInt(iterationsTextD.getText()), Integer.parseInt(updateTextD.getText()), Integer.parseInt(numClustersA.getText()));
                    Thread thread = new Thread(k);
                    thread.start();
                    synchronized(thread) {
                        ((AppData) applicationTemplate.getDataComponent()).displayData();
                        thread.notify();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(AppUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if (algBchoice2.isSelected()) {
                KMeansClusterer k;
                try {
                    k = new KMeansClusterer(DataSet.fromTSDFile(Paths.get("data-vilij\\resources\\data\\sample-data.tsd")),
                            Integer.parseInt(iterationsTextF.getText()), Integer.parseInt(updateTextE.getText()), Integer.parseInt(numClustersB.getText()));
                    Thread thread = new Thread(k);
                    thread.start();
                    synchronized(thread) {
                        thread.notify();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(AppUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if (algCchoice2.isSelected()) {
                KMeansClusterer k;
                try {
                    k = new KMeansClusterer(DataSet.fromTSDFile(Paths.get("data-vilij\\resources\\data\\sample-data.tsd")),
                            Integer.parseInt(iterationsTextC.getText()), Integer.parseInt(updateTextF.getText()), Integer.parseInt(numClustersC.getText()));
                    Thread thread = new Thread(k);
                    thread.start();
                    synchronized(thread) {
                        thread.notify();
                    }
                    
                } catch (IOException ex) {
                    Logger.getLogger(AppUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {}
            if (algAchoice.isSelected()) {
                RandomClassifier r;
                try {
                    r = new RandomClassifier(DataSet.fromTSDFile(Paths.get("data-vilij\\resources\\data\\sample-data.tsd")),
                            Integer.parseInt(iterationsTextA.getText()), Integer.parseInt(updateTextA.getText()), continuousRunCheckA.isSelected(), applicationTemplate);
                    Thread thread = new Thread(r);
                    thread.start();
                    synchronized(thread) {
                        ((AppData) applicationTemplate.getDataComponent()).displayData();
                        thread.notify();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(AppUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if (algBchoice.isSelected()) {
                RandomClassifier r;
                try {
                    r = new RandomClassifier(DataSet.fromTSDFile(Paths.get("data-vilij\\resources\\data\\sample-data.tsd")),
                            Integer.parseInt(iterationsTextB.getText()), Integer.parseInt(updateTextB.getText()), continuousRunCheckB.isSelected(), applicationTemplate);
                    Thread thread = new Thread(r);
                    thread.start();
                    synchronized(thread) {
                        thread.notify();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(AppUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if (algCchoice.isSelected()) {
                RandomClassifier r;
                try {
                    r = new RandomClassifier(DataSet.fromTSDFile(Paths.get("data-vilij\\resources\\data\\sample-data.tsd")),
                            Integer.parseInt(iterationsTextC.getText()), Integer.parseInt(updateTextC.getText()), continuousRunCheckC.isSelected(), applicationTemplate);
                    Thread thread = new Thread(r);
                    thread.start();
                    synchronized(thread) {
                        thread.notify();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(AppUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {}
            AppData a = new AppData(applicationTemplate);
//            a.displayData();
        });
        
    }
    public void setSaveButton(boolean b) {
        saveButton.setDisable(b);
    }
    
    public void setVisibility() {
        textArea.setVisible(true);
        displayButton.setVisible(true);
        checkBox.setVisible(true);
        doneButton.setVisible(true);
    }
    
    public void readOnly() {
        textArea.setEditable(!textArea.isEditable());
        if (!textArea.isEditable()) {
            this.appPane.getScene().getStylesheets().add(getClass().getResource(cssPath.replace(applicationTemplate.manager.getPropertyValue(CSS_RESOURCE_FILENAME.name()),
            "TextFormatting.css")).toExternalForm());
        }
        else {
            this.appPane.getScene().getStylesheets().remove(getClass().getResource(cssPath.replace(applicationTemplate.manager.getPropertyValue(CSS_RESOURCE_FILENAME.name()),
            "TextFormatting.css")).toExternalForm());
        }
    }
    
    public void addInfo(int instances, int labels, String labelNames) {
        String s = instances + " instances with " + labels + " labels.\nThe labels are: \n" + labelNames;
        infoLabel = new Label();
        infoLabel.setText(s);
        infoLabel.setMaxWidth(300);
        infoLabel.setWrapText(true);
        vbox.getChildren().add(infoLabel);
        algorithmChoice(labelNames);
    }
    
    public void addInfo(int instances, int labels, String labelNames, String path) {
        String s = instances + " instances with " + labels + " labels loaded from:\n" + path + "\nThe labels are: \n" + labelNames;
        infoLabel = new Label();
        infoLabel.setText(s);
        infoLabel.setMaxWidth(300);
        infoLabel.setWrapText(true);
        vbox.getChildren().add(infoLabel);
        algorithmChoice(labelNames);
    }
    
    public void removeInfo() {
        vbox.getChildren().remove(infoLabel);
        vbox.getChildren().remove(classificationButton);
        vbox.getChildren().remove(clusteringButton);
        vbox.getChildren().remove(gridPane);
        vbox.getChildren().remove(clusteringPane);
        vbox.getChildren().remove(classificationPane);
    }
    
    public void algorithmChoice(String labelNames) {
        String[] names = labelNames.split(",");
        if (names.length == 2 && names[0] != null && names[1] != null) {
            clusteringButton.setDisable(false);
        }
        vbox.getChildren().add(gridPane);
    }
    
    public void classificationShow() {
        vbox.getChildren().remove(gridPane);
        Label l = new Label("Classification");
        classificationPane = new GridPane();
        classificationPane.addRow(0, algAchoice, algA, algAbtn);
        classificationPane.addRow(1, algBchoice, algB, algBbtn);
        classificationPane.addRow(2, algCchoice, algC, algCbtn);
        runBtn.setDisable(true);
        vbox.getChildren().addAll(l, classificationPane, runBtn);
    }
    
    public void clusteringShow() {
        vbox.getChildren().remove(gridPane);
        Label l = new Label("Clustering");
        clusteringPane = new GridPane();   
        clusteringPane.addRow(0, algAchoice2, algA, algAbtn2);
        clusteringPane.addRow(1, algBchoice2, algB, algBbtn2);
        clusteringPane.addRow(2, algCchoice2, algC, algCbtn2);
        runBtn.setDisable(true);
        vbox.getChildren().addAll(l, clusteringPane, runBtn);
    }
    
    public void setRunButton(boolean value) {
        runBtn.setDisable(value);
    }
}