package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane; // Import for AnchorPane
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ExpenseStatsPie implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private PieChart pieChart;

    @FXML
    private Label noTrans;

    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dbUrl = "jdbc:mysql://localhost:3306/Exp_Tracker"; // Update with your database URL
        dbUser = "root"; // Update with your database username
        dbPassword = "oracle"; // Update with your database password
//        noTrans.setText("");


        // Establish the initial database connection in the initialize method
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            loadData();
            // Use the connection to query the database
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void loadData() {
        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            // Query to retrieve total expenses for the Pie Chart
            String pieChartQuery = "SELECT user_id, category_name, SUM(amount) AS total_amount " +
                    "FROM transactions " +
                    "where transactiontype= 'Expense'"+
                    "GROUP BY category_name, user_id having user_id ="+
                    AlertConnector.user;

            ResultSet pieChartResult = statement.executeQuery(pieChartQuery);

            // Clear existing data from the Pie Chart
            pieChart.getData().clear();

            if(!pieChartResult.next()){
                noTrans.setText("No Transaction To show");
                return;
            }
            pieChartResult.beforeFirst();

            ArrayList<PieChart.Data> pieChartDataA = new ArrayList<>();

            while ((pieChartResult.next())){
                String transactionType = pieChartResult.getString("category_name");
                double totalAmount = pieChartResult.getDouble("total_amount");

                // Add data to the Pie Chart
//                PieChart.Data pieChartData = new PieChart.Data(transactionType, totalAmount);
                PieChart.Data slice = new PieChart.Data(transactionType, totalAmount);
                pieChartDataA.add(slice);
//                pieChart.getData().add(pieChartData);
            }

            double total = pieChartDataA.stream().mapToDouble(data -> data.getPieValue()).sum();

            // Update PieChart with percentages

            for (PieChart.Data data : pieChartDataA) {
                double percentage = (data.getPieValue() / total) * 100;
                data.setName(data.getName() + " (" + String.format("%.2f", percentage) + "%)");

                // Set a tooltip for each PieChart.Data element
                Tooltip tooltip = new Tooltip(String.format("%.2f", percentage) + "%");
                Tooltip.install(data.getNode(), tooltip);

                pieChart.getData().add(data);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void switchToDashBoard(ActionEvent event) throws IOException{         // to switch the scene to dashboard
        root = FXMLLoader.load(getClass().getResource("finalDashboard.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void switchToLoginPage(ActionEvent event) throws IOException{         // to switch the scene to dashboard
        root = FXMLLoader.load(getClass().getResource("finalLoginPage.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void switchToPie(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("finalPieChart.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void switchToBar(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("finalBarChart.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void switchToTransaction(ActionEvent event) throws IOException{        // to switch the scene to transaction
        root = FXMLLoader.load(getClass().getResource("finalTransaction.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void switchToAddTrans(ActionEvent event) throws IOException{        // to switch the scene to add transaction
        root = FXMLLoader.load(getClass().getResource("finalAddTransaction.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToBL(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("finalBorrow&Lend.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToBudget(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("finalBudget.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToSave(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("finalSavings.fxml"));
        scene = new Scene(root);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}