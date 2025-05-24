import util.CurrentAccount;
import dao.AccountDAO;
import model.Account;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class login extends Application
{
    // Cordinates for dragging
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Login");

        // Special tittle bar
        Label title = new Label("   Login | thunderbolt&blacksmith");
        title.setFont(Font.font("Roboto", 16));
        title.setTextFill(Color.web("#ECECEC"));
        title.setPrefHeight(30);
        title.setMaxWidth(Double.MAX_VALUE);
        title.setStyle("-fx-background-color: #111;");
        title.setAlignment(Pos.CENTER_LEFT);

        Button minimizeButton = new Button("_");
        minimizeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14;");
        minimizeButton.setOnAction(e -> primaryStage.setIconified(true));

        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14;");
        closeButton.setOnAction(e -> primaryStage.close());

        HBox windowButtons = new HBox(10, minimizeButton, closeButton);
        windowButtons.setAlignment(Pos.CENTER_RIGHT);
        windowButtons.setPadding(new Insets(0, 10, 0, 0));
        windowButtons.setStyle("-fx-background-color: #111;");

        HBox topBar = new HBox(title, windowButtons);
        HBox.setHgrow(title, Priority.ALWAYS);

        // Drag
        topBar.setOnMousePressed((MouseEvent e) ->
        {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });

        topBar.setOnMouseDragged((MouseEvent e) ->
        {
            primaryStage.setX(e.getScreenX() - xOffset);
            primaryStage.setY(e.getScreenY() - yOffset);
        });

        // Modern title
        Label titleLabel = new Label("thunderbolt&blacksmith");
        titleLabel.setFont(Font.font("Roboto", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.web("#ECECEC"));
        titleLabel.setPadding(new Insets(10, 0, 40, 0));

        // AccountNumber field
        TextField AccountNumberField = new TextField();
        AccountNumberField.setPromptText("Account Number");
        AccountNumberField.setStyle(
                "-fx-background-color: #222;" +
                        "-fx-text-fill: white;" +
                        "-fx-prompt-text-fill: #888;" +
                        "-fx-border-color: #444;" +
                        "-fx-border-radius: 0;" +
                        "-fx-background-radius: 0;"
        );
        AccountNumberField.setPrefWidth(250);
        AccountNumberField.setPrefHeight(40);

        // Password field
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle(
                "-fx-background-color: #222;" +
                        "-fx-text-fill: white;" +
                        "-fx-prompt-text-fill: #888;" +
                        "-fx-border-color: #444;" +
                        "-fx-border-radius: 0;" +
                        "-fx-background-radius: 0;"
        );
        passwordField.setPrefWidth(250);
        passwordField.setPrefHeight(40);

        // Login button
        Button loginButton = new Button("Login");
        loginButton.setStyle(
                "-fx-background-color: #47b347;" +
                        "-fx-text-fill: black;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 0;" +
                        "-fx-border-radius: 0;"
        );
        loginButton.setPrefWidth(180);
        loginButton.setPrefHeight(44);

        // Message label
        Label messageLabel = new Label();
        messageLabel.setTextFill(Color.RED);

        // Footer label
        Label footerLabel = new Label("April, 2025.");
        footerLabel.setFont(Font.font("Arial", 12));
        footerLabel.setTextFill(Color.web("#AAAAAA"));
        footerLabel.setAlignment(Pos.CENTER);
        footerLabel.setPadding(new Insets(0, 0, 0, 0));

        Label footerLabel2 = new Label("(C) All rights reserved to thunderbolt&blacksmith.");
        footerLabel2.setFont(Font.font("Arial", 12));
        footerLabel2.setTextFill(Color.web("#AAAAAA"));
        footerLabel2.setAlignment(Pos.CENTER);
        footerLabel2.setPadding(new Insets(1, 0, 0, 0));

        // Content layout
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(40));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(titleLabel, AccountNumberField, passwordField, loginButton, messageLabel, footerLabel, footerLabel2);

        VBox root = new VBox(topBar, vbox);
        root.setStyle("-fx-background-color: black;");

        // Login Process
        loginButton.setOnAction(e ->
        {
            String account_number = AccountNumberField.getText();
            String password = passwordField.getText();

            AccountDAO accountDAO = new AccountDAO();
            Account account = accountDAO.getAccountByUsernameAndPassword(account_number, password);

            if (account != null)
            {
                CurrentAccount.currentAccount = account;
                messageLabel.setTextFill(Color.LIGHTGREEN);
                messageLabel.setText("Login successful!");

                try
                {
                    transactionsMenu transactionsWindow = new transactionsMenu();
                    Stage transactionsStage = new Stage();
                    transactionsWindow.start(transactionsStage);
                    primaryStage.close();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            else
            {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Invalid account number or password.");
            }
        });

        Scene scene = new Scene(root, 400, 430);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
