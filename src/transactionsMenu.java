import components.moneyTransfer;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import util.CurrentAccount;
import model.Account;
import dao.AccountDAO;
import components.transactions;
import components.transactionHistory;

import java.util.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.scene.control.TableRow;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;

public class transactionsMenu extends Application
{
    // Coordinates for dragging
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage)
    {
        Account account = CurrentAccount.currentAccount; // Logged user information

        if (account == null)
        {
            System.out.println("No login has been made.");
            return;
        }

        // Title
        primaryStage.setTitle("Transactions Menu");

        // Special title bar
        Label title = new Label("   Transaction Menu | thunderbolt&blacksmith");
        title.setFont(Font.font("Roboto", 16));
        title.setTextFill(Color.web("#ECECEC"));
        title.setPrefHeight(30);
        title.setMaxWidth(Double.MAX_VALUE);
        title.setStyle("-fx-background-color: #111;");
        title.setAlignment(Pos.CENTER_LEFT);

        // "_" button top bar
        Button minimizeButton = new Button("_");
        minimizeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14;");
        minimizeButton.setOnAction(e -> primaryStage.setIconified(true));

        // "x" button top bar
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
        topBar.setOnMousePressed((MouseEvent e) -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        topBar.setOnMouseDragged((MouseEvent e) -> {
            primaryStage.setX(e.getScreenX() - xOffset);
            primaryStage.setY(e.getScreenY() - yOffset);
        });

        // Modern title
        Label titleLabel = new Label("thunderbolt&blacksmith");
        titleLabel.setFont(Font.font("Roboto", FontWeight.BOLD, 30));
        titleLabel.setTextFill(Color.web("#ECECEC"));
        titleLabel.setPadding(new Insets(0, 0, 25, 0));

        // Welcome Label
        Label titleLabel2 = new Label("Welcome: " + account.getFullName());
        titleLabel2.setFont(Font.font("Roboto", FontWeight.BOLD, 24));
        titleLabel2.setTextFill(Color.web("#ECECEC"));
        titleLabel2.setPadding(new Insets(0, 0, 20, 0));

        // Left Buttons
        VBox leftColumn = new VBox(20);
        Button btn1 = createButton("View Balance");
        Button btn2 = createButton("Deposit Money");
        Button btn3 = createButton("Withdraw Money");
        Button btn4 = createButton("Transfer Money");
        leftColumn.getChildren().addAll(btn1, btn2, btn3, btn4);
        leftColumn.setAlignment(Pos.CENTER);

        // Right Buttons
        VBox rightColumn = new VBox(20);
        Button btn5 = createButton("Transaction History");
        Button btn6 = createButton("Edit");
        Button btn7 = createButton("Log out");
        Button btn8 = createButton("Exit Application");
        rightColumn.getChildren().addAll(btn5, btn6, btn7, btn8);
        rightColumn.setAlignment(Pos.CENTER);

        // HBox to hold both columns
        HBox buttonColumns = new HBox(275, leftColumn, rightColumn);
        buttonColumns.setAlignment(Pos.CENTER);

        // VBox to hold everything below the top bar
        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(50, 0, 0, 0)); // top padding
        vbox.getChildren().addAll(titleLabel, titleLabel2, buttonColumns);

        VBox root = new VBox(topBar, vbox);
        root.setStyle("-fx-background-color: black;");

        // View Balance
        btn1.setOnAction(e -> {
            AccountDAO dao = new AccountDAO();
            Account refreshedAccount = dao.getAccountById(account.getId());

            Stage dialog = new Stage();
            dialog.setTitle("Current Balance");

            // ==== Top Bar ====
            Label titleB = new Label("   Current Balance");
            titleB.setFont(Font.font("Roboto", FontWeight.BOLD, 14));
            titleB.setTextFill(Color.web("BLACK"));
            titleB.setPrefHeight(30);
            titleB.setMaxWidth(Double.MAX_VALUE);
            titleB.setStyle("-fx-background-color: #47b347;");
            titleB.setAlignment(Pos.CENTER_LEFT);

            // ==== Content ====
            VBox contentBox = new VBox(10);
            contentBox.setPadding(new Insets(20));
            contentBox.setAlignment(Pos.CENTER);
            contentBox.setStyle("-fx-background-color: #222;");

            Label balanceLabel, balanceLabel2;

            if (refreshedAccount != null) {
                double balance = refreshedAccount.getBalance();
                balanceLabel = new Label("Your current balance is:");
                balanceLabel2 = new Label(balance + "₺");
            } else {
                balanceLabel = new Label("Failed to retrieve balance from database.");
                balanceLabel2 = new Label("500 - Internal Server Error");
            }

            balanceLabel.setTextFill(Color.WHITE);
            balanceLabel.setFont(Font.font("Roboto", FontWeight.NORMAL, 12));
            balanceLabel2.setTextFill(Color.WHITE);
            balanceLabel2.setFont(Font.font("Roboto", FontWeight.BOLD, 20));

            VBox.setMargin(balanceLabel2, new Insets(1, 0, 0, 0));

            Button dialogCloseButton = new Button("Close");
            dialogCloseButton.setStyle(
                    "-fx-background-color: #d94e4e;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 0;" +
                            "-fx-cursor: hand;"
            );
            VBox.setMargin(dialogCloseButton, new Insets(3, 0, 0, 0));
            dialogCloseButton.setOnAction(event -> dialog.close());

            contentBox.getChildren().addAll(balanceLabel, balanceLabel2, dialogCloseButton);

            VBox rootBox = new VBox(titleB, contentBox);
            rootBox.setSpacing(0);
            rootBox.setPadding(Insets.EMPTY);
            rootBox.setStyle("-fx-background-color: #222;");


            Scene dialogScene = new Scene(rootBox, 300, 150);
            dialogScene.setFill(Color.DARKGRAY);

            dialog.setScene(dialogScene);
            dialog.initOwner(primaryStage);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.centerOnScreen();
            dialog.show();
        });

        // Deposit Money
        btn2.setOnAction(e ->
        {
            Stage dialog = new Stage();
            dialog.setTitle("Deposit Money");

            // ==== Top Bar ====
            Label titleB = new Label("   Deposit");
            titleB.setFont(Font.font("Roboto", FontWeight.BOLD, 14));
            titleB.setTextFill(Color.web("BLACK"));
            titleB.setPrefHeight(30);
            titleB.setMaxWidth(Double.MAX_VALUE);
            titleB.setStyle("-fx-background-color: #47b347;");
            titleB.setAlignment(Pos.CENTER_LEFT);

            VBox contentBox = new VBox(10);
            contentBox.setPadding(new Insets(20));
            contentBox.setAlignment(Pos.CENTER);
            contentBox.setStyle("-fx-background-color: #222;");

            Label label = new Label("Enter amount to deposit:");
            label.setTextFill(Color.WHITE);
            TextField amountField = new TextField();
            amountField.setStyle(
                    "-fx-background-color: #555555;" +
                            "-fx-text-fill: white;" +
                            "-fx-prompt-text-fill: #888;" +
                            "-fx-border-color: #444;" +
                            "-fx-border-radius: 0;" +
                            "-fx-background-radius: 0;"
            );
            amountField.setPromptText("e.g. 200");

            Button okButton = new Button("OK");
            Button closeButtonD = new Button("Close");

            okButton.setStyle(
                    "-fx-background-color: #47b347;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 0;" +
                            "-fx-cursor: hand;"
            );

            closeButtonD.setStyle(
                    "-fx-background-color: #d94e4e;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 0;" +
                            "-fx-cursor: hand;"
            );

            Label statusLabel = new Label();
            statusLabel.setTextFill(Color.WHITE);

            okButton.setOnAction(event ->
            {
                String input = amountField.getText();
                try
                {
                    double amount = Double.parseDouble(input);

                    if (amount <= 0)
                    {
                        statusLabel.setText("Please enter a positive amount.");
                    }
                    else
                    {
                        String result = transactions.deposit(account, amount);
                        statusLabel.setText(result);
                    }
                }
                catch (NumberFormatException ex)
                {
                    statusLabel.setText("Invalid number format.");
                }
            });

            closeButtonD.setOnAction(event -> dialog.close());

            HBox buttonBox = new HBox(10);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.getChildren().addAll(okButton, closeButtonD);

            contentBox.getChildren().addAll(label, amountField, buttonBox, statusLabel);

            VBox dialogVBox = new VBox(titleB, contentBox);
            dialogVBox.setSpacing(0);
            dialogVBox.setPadding(Insets.EMPTY);
            dialogVBox.setStyle("-fx-background-color: #222;");

            Scene dialogScene = new Scene(dialogVBox, 300, 180);
            dialogScene.setFill(Color.DARKGRAY);

            dialog.setScene(dialogScene);
            dialog.initOwner(primaryStage);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.show();
        });

        // Withdraw Money
        btn3.setOnAction(e ->
        {
            Stage dialog = new Stage();
            dialog.setTitle("Withdraw Money");

            // ==== Top Bar ====
            Label titleB = new Label("   Withdraw");
            titleB.setFont(Font.font("Roboto", FontWeight.BOLD, 14));
            titleB.setTextFill(Color.web("BLACK"));
            titleB.setPrefHeight(30);
            titleB.setMaxWidth(Double.MAX_VALUE);
            titleB.setStyle("-fx-background-color: #47b347;");
            titleB.setAlignment(Pos.CENTER_LEFT);

            VBox contentBox = new VBox(10);
            contentBox.setPadding(new Insets(20));
            contentBox.setAlignment(Pos.CENTER);
            contentBox.setStyle("-fx-background-color: #222;");

            Label label = new Label("Enter amount to withdraw:");
            label.setTextFill(Color.WHITE);
            TextField amountField = new TextField();
            amountField.setStyle(
                    "-fx-background-color: #555555;" +
                            "-fx-text-fill: white;" +
                            "-fx-prompt-text-fill: #888;" +
                            "-fx-border-color: #444;" +
                            "-fx-border-radius: 0;" +
                            "-fx-background-radius: 0;"
            );

            amountField.setPromptText("e.g. 200");

            Button okButton = new Button("OK");
            Button closeButtonW = new Button("Close");

            okButton.setStyle(
                    "-fx-background-color: #47b347;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 0;" +
                            "-fx-cursor: hand;"
            );

            closeButtonW.setStyle(
                    "-fx-background-color: #d94e4e;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 0;" +
                            "-fx-cursor: hand;"
            );

            Label statusLabel = new Label();
            statusLabel.setTextFill(Color.WHITE);

            okButton.setOnAction(event ->
            {
                String input = amountField.getText();
                try
                {
                    double amount = Double.parseDouble(input);

                    if (amount <= 0)
                    {
                        statusLabel.setText("Please enter a positive amount.");
                    }
                    else if (amount > account.getBalance())
                    {
                        statusLabel.setText("Insufficient balance. Current: ₺" + account.getBalance());
                    }
                    else
                    {
                        String result = transactions.withdraw(account, amount);
                        statusLabel.setText(result);
                    }
                }
                catch (NumberFormatException ex)
                {
                    statusLabel.setText("Invalid number format.");
                }
            });

            closeButtonW.setOnAction(event -> dialog.close());

            HBox buttonBox = new HBox(10);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.getChildren().addAll(okButton, closeButtonW);

            contentBox.getChildren().addAll(label, amountField, buttonBox, statusLabel);

            VBox dialogVBox = new VBox(titleB, contentBox);
            dialogVBox.setSpacing(0);
            dialogVBox.setPadding(Insets.EMPTY);
            dialogVBox.setStyle("-fx-background-color: #222;");

            Scene dialogScene = new Scene(dialogVBox, 300, 180);
            dialogScene.setFill(Color.DARKGRAY);
            dialog.setScene(dialogScene);
            dialog.initOwner(primaryStage);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.show();
        });


        // Transfer Money
        btn4.setOnAction(e ->
        {
            Stage dialog = new Stage();
            dialog.setTitle("Money Transfer");

            // ==== Top Bar ====
            Label titleB = new Label("   Money Transfer");
            titleB.setFont(Font.font("Roboto", FontWeight.BOLD, 14));
            titleB.setTextFill(Color.web("BLACK"));
            titleB.setPrefHeight(30);
            titleB.setMaxWidth(Double.MAX_VALUE);
            titleB.setStyle("-fx-background-color: #47b347;");
            titleB.setAlignment(Pos.CENTER_LEFT);

            VBox contentBox = new VBox(10);
            contentBox.setPadding(new Insets(15));
            contentBox.setAlignment(Pos.CENTER);
            contentBox.setStyle("-fx-background-color: #222;");

            Label receiverLabel = new Label("Receiver account number:");
            receiverLabel.setTextFill(Color.WHITE);
            TextField receiverField = new TextField();
            receiverField.setPromptText("e.g. TR1000001");
            receiverField.setStyle(
                    "-fx-background-color: #555555;" +
                            "-fx-text-fill: white;" +
                            "-fx-prompt-text-fill: #888;" +
                            "-fx-border-color: #444;" +
                            "-fx-border-radius: 0;" +
                            "-fx-background-radius: 0;"
            );

            Label nameLabel = new Label("Receiver name and surname:");
            nameLabel.setTextFill(Color.WHITE);
            TextField nameField = new TextField();
            nameField.setPromptText("e.g. John Smith");
            nameField.setStyle(
                    "-fx-background-color: #555555;" +
                            "-fx-text-fill: white;" +
                            "-fx-prompt-text-fill: #888;" +
                            "-fx-border-color: #444;" +
                            "-fx-border-radius: 0;" +
                            "-fx-background-radius: 0;"
            );

            Label amountLabel = new Label("Amount to transfer:");
            amountLabel.setTextFill(Color.WHITE);
            TextField amountField = new TextField();
            amountField.setPromptText("e.g. 250");
            amountField.setStyle(
                    "-fx-background-color: #555555;" +
                            "-fx-text-fill: white;" +
                            "-fx-prompt-text-fill: #888;" +
                            "-fx-border-color: #444;" +
                            "-fx-border-radius: 0;" +
                            "-fx-background-radius: 0;"
            );

            Button transferButton = new Button("Transfer");
            Button closeButtonT = new Button("Close");

            transferButton.setStyle(
                    "-fx-background-color: #47b347;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 0;" +
                            "-fx-cursor: hand;"
            );

            closeButtonT.setStyle(
                    "-fx-background-color: #d94e4e;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 0;" +
                            "-fx-cursor: hand;"
            );

            Label statusLabel = new Label();
            statusLabel.setTextFill(Color.WHITE);


            transferButton.setOnAction(event ->
            {
                String receiverAccStr = receiverField.getText();
                String receiverFullName = nameField.getText();
                String amountStr = amountField.getText();

                try
                {
                    int receiverAccNum = Integer.parseInt(receiverAccStr);
                    double amount = Double.parseDouble(amountStr);

                    AccountDAO dao = new AccountDAO();
                    Account receiver = dao.getAccountByNumber(receiverAccNum);

                    if(receiver != null)
                    {
                        // Name surname control
                        if(receiver.getFullName().equalsIgnoreCase(receiverFullName))
                        {
                            String result = moneyTransfer.transfer(account, receiver, amount);
                            statusLabel.setText(result);
                        }
                        else
                        {
                            statusLabel.setText("Receiver name and account number do not match.");
                        }
                    }
                    else
                    {
                        statusLabel.setText("Receiver account not found.");
                    }
                }
                catch(NumberFormatException ex)
                {
                    // Ignore invalid inputs
                }
            });

            closeButtonT.setOnAction(event -> dialog.close());

            HBox buttonBox = new HBox(10);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.getChildren().addAll(transferButton, closeButtonT);

            contentBox.getChildren().addAll(receiverLabel, receiverField, amountLabel, amountField, nameLabel, nameField, buttonBox, statusLabel);

            VBox dialogVBox = new VBox(titleB, contentBox);
            dialogVBox.setSpacing(0);
            dialogVBox.setPadding(Insets.EMPTY);
            dialogVBox.setStyle("-fx-background-color: #222;");


            Scene scene = new Scene(dialogVBox, 300, 300);
            scene.setFill(Color.DARKGRAY);
            dialog.setScene(scene);
            dialog.initOwner(primaryStage);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.show();
        });

        // Transaction History
        btn5.setOnAction(e ->
        {
            transactionHistory his = new transactionHistory();
            List<List<String>> historyList = his.getStructuredHistoryByAccountId(account.getId());

            Stage dialog = new Stage();
            dialog.setTitle("Transaction History");

            // ==== Top Bar ====
            Label titleB = new Label("   Transactions History");
            titleB.setFont(Font.font("Roboto", FontWeight.BOLD, 14));
            titleB.setTextFill(Color.web("BLACK"));
            titleB.setPrefHeight(30);
            titleB.setMaxWidth(Double.MAX_VALUE);
            titleB.setStyle("-fx-background-color: #47b347;");
            titleB.setAlignment(Pos.CENTER_LEFT);

            // Label for Recent Bank Transactions
            Label inLabel = new Label("Recent Bank Transactions:");
            inLabel.setTextFill(Color.WHITE);
            inLabel.setAlignment(Pos.CENTER);  // Center the label

            // Create a VBox to center the label above the table with equal top and bottom padding
            VBox labelBox = new VBox(inLabel);
            labelBox.setAlignment(Pos.CENTER);  // Center the label
            labelBox.setSpacing(10);  // Optional: Adjust spacing between label and table
            labelBox.setPadding(new Insets(20, 0, 10, 0));  // Add padding: 20px top and bottom

            // ==== Table ====
            TableView<List<String>> table = new TableView<>();
            table.setPrefWidth(600);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            TableColumn<List<String>, String> idCol = new TableColumn<>("Transaction No");
            idCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
            idCol.setSortable(false);

            TableColumn<List<String>, String> typeCol = new TableColumn<>("Transaction Type");
            typeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
            typeCol.setSortable(false);

            TableColumn<List<String>, String> amountCol = new TableColumn<>("Amount");
            amountCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
            amountCol.setSortable(false);

            TableColumn<List<String>, String> dateCol = new TableColumn<>("Date");
            dateCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
            dateCol.setSortable(false);

            TableColumn<List<String>, String> timeCol = new TableColumn<>("Time");
            timeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(4)));
            timeCol.setSortable(false);

            Collections.addAll(table.getColumns(), idCol, typeCol, amountCol, dateCol, timeCol);
            table.setItems(FXCollections.observableArrayList(historyList));

            final List<TableRow<List<String>>> selectedRow = new ArrayList<>(1);

            table.setRowFactory(param ->
            {
                TableRow<List<String>> row = new TableRow<>();
                row.setStyle("-fx-background-color: #333; -fx-text-fill: white;");

                row.indexProperty().addListener((obs, oldIndex, newIndex) ->
                {
                    if (newIndex.intValue() % 2 == 0)
                    {
                        row.setStyle("-fx-background-color: #444; -fx-text-fill: white;");
                    } else {
                        row.setStyle("-fx-background-color: #555; -fx-text-fill: white;");
                    }
                });

                row.setOnMouseClicked(event ->
                {
                    if (!row.isEmpty())
                    {
                        if (!selectedRow.isEmpty())
                        {
                            TableRow<List<String>> previousRow = selectedRow.get(0);
                            previousRow.setStyle("-fx-background-color: #333; -fx-text-fill: white;");
                        }

                        row.setStyle("-fx-background-color: #47b347; -fx-text-fill: white;");

                        selectedRow.clear();
                        selectedRow.add(row);
                    }
                });
                return row;
            });

            Platform.runLater(() ->
            {
                table.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                table.lookupAll(".column-header").forEach(header -> header.setStyle("-fx-background-color: black;"));
                table.lookupAll(".column-header .label").forEach(label -> label.setStyle("-fx-text-fill: white;"));
            });

            table.setStyle("-fx-border-color: transparent; -fx-border-width: 0;");
            table.getColumns().forEach(col -> col.setStyle(
                    "-fx-border-color: transparent;" +
                            "-fx-text-fill: #FFF;")
            );

            // ==== Close Button ====
            Button closeButtonH = new Button("Close");
            closeButtonH.setStyle(
                    "-fx-background-color: #d94e4e;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 0;" +
                            "-fx-cursor: hand;"
            );
            closeButtonH.setOnAction(event -> dialog.close());

            HBox buttonBox = new HBox(10);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.getChildren().addAll(closeButtonH);

            VBox contentBox = new VBox(10, buttonBox);
            contentBox.setAlignment(Pos.CENTER);
            contentBox.setStyle("-fx-background-color: #222;");

            // ==== Root Layout ====
            VBox dialogVBox = new VBox();
            dialogVBox.setStyle("-fx-background-color: #222;");

            VBox.setMargin(table, new Insets(10, 15, 10, 15));
            VBox.setMargin(contentBox, new Insets(0, 15, 15, 15));

            dialogVBox.getChildren().addAll(titleB, labelBox, table, contentBox);

            Scene scene = new Scene(dialogVBox, 600, 400);
            scene.setFill(Color.DARKGRAY);
            dialog.setScene(scene);
            dialog.initOwner(primaryStage);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.show();
        });




        // Edit Profile
        btn6.setOnAction(e ->
        {

        });

        // Log out to login screen
        btn7.setOnAction(e ->
        {
            login log = new login();
            Stage logStage = new Stage();
            log.start(logStage);
            primaryStage.close();

        });

        // Exit to windows
        btn8.setOnAction(e -> System.exit(0));

        // Scene setup
        Scene scene = new Scene(root, 800, 600);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Function to create styled buttons
    private Button createButton(String text)
    {
        Button button = new Button(text);
        button.setPrefWidth(200);
        button.setPrefHeight(60);
        button.setStyle(
                "-fx-background-color: #47b347;" +
                        "-fx-text-fill: black;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 0;" +
                        "-fx-border-radius: 0;"
        );
        return button;
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
