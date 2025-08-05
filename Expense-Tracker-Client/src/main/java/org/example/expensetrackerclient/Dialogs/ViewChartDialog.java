package org.example.expensetrackerclient.Dialogs;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.example.expensetrackerclient.Models.MonthlyFinance;
import org.example.expensetrackerclient.Models.User;

public class ViewChartDialog extends CustomDialog{
    public ViewChartDialog(User user, ObservableList<MonthlyFinance>monthlyFinances) {
        super(user);
        setTitle("View Chart");
        getDialogPane().setPrefSize(500, 650);

        VBox barChartBox=new VBox();

        CategoryAxis xAxis=new CategoryAxis();
        xAxis.setLabel("Month");
        xAxis.setTickLabelFill(Paint.valueOf("#BEB989"));

        NumberAxis yAxis=new NumberAxis();
        yAxis.setLabel("Amount");
        yAxis.setTickLabelFill(Paint.valueOf("#BEB989"));

        BarChart<String,Number> barChart=new BarChart<>(xAxis,yAxis);
        barChartBox.setFillWidth(true);
        barChartBox.setPrefHeight(650);
        getDialogPane().setPadding(Insets.EMPTY);
        VBox.setVgrow(barChart, Priority.ALWAYS);
        barChart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        barChartBox.setPadding(Insets.EMPTY);
        barChartBox.setSpacing(0);
        barChart.getStyleClass().add("text-size-md");

        XYChart.Series<String,Number>incomeSeries=new XYChart.Series<>();
        incomeSeries.setName("income");

        XYChart.Series<String,Number>expenseSeries=new XYChart.Series<>();
        expenseSeries.setName("expense");

        //populate data for income and expense
        for(MonthlyFinance monthlyFinance:monthlyFinances){
            incomeSeries.getData().add(new XYChart.Data<>(monthlyFinance.getMonth(),monthlyFinance.getIncome()));
            expenseSeries.getData().add(new XYChart.Data<>(monthlyFinance.getMonth(),monthlyFinance.getExpense()));
        }
        barChart.getData().addAll(incomeSeries,expenseSeries);

        //update the bar colors for the income
        incomeSeries.getData().forEach(data->data.getNode().setStyle("-fx-bar-fill: #33ba2f"));

        //update the bar colors for the expense
        expenseSeries.getData().forEach(data->data.getNode().setStyle("-fx-bar-fill: #ba2f2f"));

        barChartBox.getChildren().add(barChart);
        getDialogPane().setContent(barChartBox);
    }
}
