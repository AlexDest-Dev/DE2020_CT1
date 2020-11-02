package de_task.controller;

import de_task.model.base.Errors;
import de_task.model.base.NumericalMethod;
import de_task.model.base.Point;
import de_task.model.errors.GlobalMethodsErrors;
import de_task.model.errors.GlobalTruncationErrors;
import de_task.model.errors.LocalTruncationErrors;
import de_task.model.methods.EulerMethod;
import de_task.model.methods.ExactSolution;
import de_task.model.methods.ImprovedEulerMethod;
import de_task.model.methods.RungeKutta;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private CheckBox isEulerErrorsVisible,
            isImprovedErrorsVisible, isRungeErrorsVisible;

    public Controller(){ }

    double x0, y0, X;
    int N;

    int nStart;
    int nFinish;

    @FXML
    private Button calculate;
    @FXML
    private Button errorsCalculate;

    @FXML
    private TextField textX0, textY0, textX, textN;
    @FXML
    private TextField textNStart, textNFinish;

    @FXML
    private LineChart<Double, Double> methodChart;
    @FXML
    private LineChart<Double, Double> localErrorsChart;
    @FXML
    private LineChart<Double, Double> globalErrorsChart;

    private LocalTruncationErrors eulerLTE;
    private LocalTruncationErrors improvedLTE;
    private LocalTruncationErrors rungeLTE;

    private GlobalTruncationErrors eulerGTE;
    private GlobalTruncationErrors improvedGTE;
    private GlobalTruncationErrors rungeGTE;

    private GlobalMethodsErrors eulerGlobal;
    private GlobalMethodsErrors improvedGlobal;
    private GlobalMethodsErrors rungeGlobal;

    private EulerMethod eulerMethod;
    private ImprovedEulerMethod improvedEulerMethod;
    private RungeKutta rungeKuttaMethod;
    private ExactSolution exactSolution;

    private final String eulerMethodName = "Euler's Method";
    private final String imrovedEulerName = "Improved Euler's method";
    private final String rungeKuttaName = "Runge-Kutta method";
    private final String exactSolName = "Exact Solution";


    @FXML
    CheckBox isAnalyticalVisible, isEulerVisible, isImprovedVisible, isRungeVisible;

    @FXML
    CheckBox isLTEVisible, isGTEVisible;

    @FXML
    private void buttonClicked() {
        x0 = parseInput(textX0.getText());
        y0 = parseInput(textY0.getText());
        X = parseInput(textX.getText());
        N = Integer.parseInt(textN.getText());
        methodsChartDrawer();
    }

    @FXML
    private void buttonErrorsClicked() {
        x0 = parseInput(textX0.getText());
        y0 = parseInput(textY0.getText());
        X = parseInput(textX.getText());
        nStart = Integer.parseInt(textNStart.getText());
        nFinish = Integer.parseInt(textNFinish.getText());
        eulerMethod.updateInitValues(x0, y0, X, nStart);
        improvedEulerMethod.updateInitValues(x0, y0, X, nStart);
        rungeKuttaMethod.updateInitValues(x0, y0, X, nStart);
        globalErrorsChartDrawer();
    }

    void globalErrorsChartDrawer() {
        globalErrorsChart.getData().clear();

        globalErrorsChartUpdate(eulerMethod, eulerGlobal, eulerMethodName, isEulerErrorsVisible.isSelected());
        globalErrorsChartUpdate(improvedEulerMethod, improvedGlobal, imrovedEulerName, isImprovedErrorsVisible.isSelected());
        globalErrorsChartUpdate(rungeKuttaMethod, rungeGlobal, rungeKuttaName, isRungeErrorsVisible.isSelected());
    }

    void globalErrorsChartUpdate(NumericalMethod method, GlobalMethodsErrors errors, String name, boolean isVisible)
    {
        if (isVisible) {
            errors.setInitialValues(nStart, nFinish);
            errors.calculateErrors(method);
            XYChart.Series tempSeries = new XYChart.Series();
            fillSeries(errors.getErrors(), tempSeries);
            tempSeries.setName(name);
            globalErrorsChart.getData().add(tempSeries);
        }
    }

    void methodsChartDrawer() {
        methodChart.getData().clear();
        localErrorsChart.getData().clear();
        methodChartUpdate(eulerMethod, eulerMethodName, isEulerVisible.isSelected(), eulerLTE, eulerGTE);
        methodChartUpdate(improvedEulerMethod, imrovedEulerName, isImprovedVisible.isSelected(), improvedLTE, improvedGTE);
        methodChartUpdate(rungeKuttaMethod, rungeKuttaName, isRungeVisible.isSelected(), rungeLTE, rungeGTE);
        methodChartUpdate(exactSolution, exactSolName, isAnalyticalVisible.isSelected(), null, null);
    }

    void methodChartUpdate(NumericalMethod method, String name, boolean isVisible, LocalTruncationErrors lteErrors, GlobalTruncationErrors gteErrors) {
        if (isVisible) {
            XYChart.Series tempSeries = new XYChart.Series();
            fillSeries(method.getSolution(x0, y0, X, N), tempSeries);
            tempSeries.setName(name);
            methodChart.getData().add(tempSeries);
            if (lteErrors != null && isLTEVisible.isSelected()) {
                errorsChartUpdate(method, lteErrors, name, true);
            }
            if (gteErrors != null && isGTEVisible.isSelected()) {
                errorsChartUpdate(method, gteErrors, name, false);
            }

        }
    }

    void errorsChartUpdate(NumericalMethod method, Errors errors, String name, boolean isLTE) {
        errors.calculateErrors(method);
        XYChart.Series tempSeries = new XYChart.Series();
        fillSeries(errors.getErrors(), tempSeries);
        if (isLTE) {
            tempSeries.setName("LTE " + name);
        }
        else tempSeries.setName("GTE " + name);
        localErrorsChart.getData().add(tempSeries);
    }

    public void fillSeries(List<Point<Double>> list, XYChart.Series series) {
        for (int i = 0; i < list.size(); i++) {
            XYChart.Data<Double, Double> tempData = new XYChart.Data<>(list.get(i).getX(), list.get(i).getY());
            series.getData().add(tempData);
        }
        this.x0 = this.x0;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eulerMethod = new EulerMethod();
        improvedEulerMethod = new ImprovedEulerMethod();
        rungeKuttaMethod = new RungeKutta();
        exactSolution = new ExactSolution();

        eulerLTE = new LocalTruncationErrors();
        improvedLTE = new LocalTruncationErrors();
        rungeLTE = new LocalTruncationErrors();

        eulerGTE = new GlobalTruncationErrors();
        improvedGTE = new GlobalTruncationErrors();
        rungeGTE = new GlobalTruncationErrors();

        eulerGlobal = new GlobalMethodsErrors();
        improvedGlobal = new GlobalMethodsErrors();
        rungeGlobal = new GlobalMethodsErrors();

        methodChart.setTitle("Charts of Methods");
        methodChart.setCreateSymbols(false);
        methodChart.setAnimated(false);

        localErrorsChart.setTitle("Charts of Errors");
        localErrorsChart.setCreateSymbols(false);
        localErrorsChart.setAnimated(false);

        globalErrorsChart.setTitle("Charts of maximum GTE from n0 to N");
        globalErrorsChart.setCreateSymbols(false);
        globalErrorsChart.setAnimated(false);

        setDoubleTextPropertyWithPI(textX0);
        setDoubleTextPropertyWithPI(textY0);
        setDoubleTextPropertyWithPI(textX);

        setIntegerTextProperty(textN);
        setIntegerTextProperty(textNStart);
        setIntegerTextProperty(textNFinish);

    }

    public void setIntegerTextProperty(TextField text) {
        text.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.compareTo("") != 0 && !newValue.matches("[1-9][0-9]*")) {
                    text.setText(oldValue);
                }
            }
        });
    }

    public void setDoubleTextPropertyWithPI(TextField text) {
        text.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.compareTo("") != 0 && !newValue.matches("-?[0-9]*.[0-9]*[pi]?$")) {
                    text.setText(oldValue);
                }
            }
        });
    }

    private double parseInput(String text) {
        String number = "";
        boolean isPI = false;
        boolean isMinus = false;
        if (text.charAt(0) == '-') isMinus = true;
        for (int i = 0; i < text.length(); i++) {
            if (isMinus && i == 0) continue;
            if (text.charAt(i) == 'p') {
                isPI = true;
                break;
            }
            number += text.charAt(i);
        }
        double minusMultiplier = 1;
        if (isMinus) minusMultiplier = -1;
        if (isPI) {
            if (number.compareTo("") == 0) return minusMultiplier * Math.PI;
            return minusMultiplier * Double.parseDouble(number) * Math.PI;
        }
        else return minusMultiplier * Double.parseDouble(number);
    }
}

