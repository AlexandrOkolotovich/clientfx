package by.bsuir.client.sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ResourceBundle;

import by.bsuir.client.sample.connectoin.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TaskController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane paneChart;

    @FXML
    private GridPane ansPane;

    @FXML
    private TextField av;

    @FXML
    private Pane errorPane;

    @FXML
    private GridPane infPane;

    @FXML
    private GridPane avPane;

    public TaskController() throws IOException {
    }

    @FXML
    void addAv(ActionEvent event) throws IOException {
        Client.getInstance().send("getNum");
        Client.getInstance().send(av_pr1);
        Client.getInstance().send(av_pr2);
        Client.getInstance().send(av_pr3);

        Client.getInstance().send(ans_pr1);
        Client.getInstance().send(ans_pr1);
        Client.getInstance().send(ans_pr1);

        Client.getInstance().send(av.getText());

        String answer = Client.getInstance().get();

        if(answer.equals("error")){
            errorPane.toFront();
        }
        else {
            infPane.toFront();
            Label labelRez = new Label(" Наилучший проект : - " +  answer);
            labelRez.setFont(new Font("Arial", 15));
            infPane.add(labelRez, 0, 0);
        }

    }

    String av_pr1 = Client.getInstance().get();
    String av_pr2 = Client.getInstance().get();
    String av_pr3 = Client.getInstance().get();

    String ans_pr1 = Client.getInstance().get();
    String ans_pr2 = Client.getInstance().get();
    String ans_pr3 = Client.getInstance().get();

    @FXML
    void initialize() throws IOException {

        float a1 = Float.parseFloat(ans_pr1);
        float a2 = Float.parseFloat(ans_pr2);
        float a3 = Float.parseFloat(ans_pr3);


        float[] ress = {a1, a2, a3};

        String [] Names = new String[3];
        Names[0] = ("Проект 1");
        Names[1] = ("Проект 2");
        Names[2] = ("Проект 3");

        PieChart pieChart = new PieChart();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (int i = 0; i < 3; i++) {
            pieChartData.add(new PieChart.Data(Names[i], ress[i] * 100.0));
        }
        pieChart.setData(pieChartData);
        pieChart.setStartAngle(30);
        final Label caption = new Label("");
        caption.setTextFill(Color.BLACK);
        caption.setStyle("-fx-font: 24 arial;");

        for (final PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    e -> {
                        double total = 0;
                        for (PieChart.Data d : pieChart.getData()) {
                            total += d.getPieValue();
                        }
                        caption.setTranslateX(e.getSceneX() - 45);
                        caption.setTranslateY(e.getSceneY() - 330);
                        String text = String.format("%.1f%%", 100 * data.getPieValue() / total);
                        caption.setText(text);
                    });
        }
        paneChart.add(pieChart, 0, 0);
        paneChart.add(caption, 0, 0);

        DecimalFormatSymbols s = new DecimalFormatSymbols();
        s.setDecimalSeparator('.');
        DecimalFormat f = new DecimalFormat("#,##0.00", s);

        Label label1 = new Label(" Проект 1 : - " +  String.format("%.3g%n", ress[0]));
        label1.setFont(new Font("Arial", 14));
        ansPane.add(label1, 0, 0);
        Label label2 = new Label(" Проект 2: - " + String.format("%.3g%n", ress[1]));
        label2.setFont(new Font("Arial", 14));
        ansPane.add(label2, 1, 0);
        Label label3 = new Label(" Проект 3: - " + String.format("%.3g%n", ress[2]));
        label3.setFont(new Font("Arial", 14));
        ansPane.add(label3, 2, 0);

        Label label4 = new Label(" Ср. уд. эф. 1 : - " +  av_pr1);
        label4.setFont(new Font("Arial", 14));
        avPane.add(label4, 0, 0);
        Label label5 = new Label(" Ср. уд. эф 2: - " + av_pr2);
        label5.setFont(new Font("Arial", 14));
        avPane.add(label5, 1, 0);
        Label label6 = new Label(" Ср. уд. эф 3: - " + av_pr3);
        label6.setFont(new Font("Arial", 14));
        avPane.add(label6, 2, 0);
    }
}
