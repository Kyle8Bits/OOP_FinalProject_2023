package com.example.finalproject_oop_2023.controller;

import com.example.finalproject_oop_2023.Items.Item;
import com.example.finalproject_oop_2023.User.RegularAccount;
import com.example.finalproject_oop_2023.User.VIPAccount;
import com.example.finalproject_oop_2023.Utils.*;
import com.example.finalproject_oop_2023.Main;
import com.example.finalproject_oop_2023.Rental.*;
import com.example.finalproject_oop_2023.User.CustomerAccount;
import com.example.finalproject_oop_2023.supporter.Alertsup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.*;



public class UserGUIController implements Initializable {
    Stage stage;
    Scene scene;
    Parent root;
    TransactionUtils transactionUtils = new TransactionUtils();
    Alertsup alertsup = new Alertsup();
    @FXML
    private Button rentButton;
    @FXML
    private Button returnButton;
    @FXML
    private TextField searchbar;
    @FXML
    private TableView<Item> tableView;

    @FXML
    private TableColumn<Item, String> idColumn;

    @FXML
    private TableColumn<Item, String> titleColumn;

    @FXML
    private TableColumn<Item, Item.Genre> genreColumn;

    @FXML
    private TableColumn<Item, Item.RentalType> rentalTypeColumn;

    @FXML
    private TableColumn<Item, Item.LoanType> loanTypeColumn;

    @FXML
    private TableColumn<Item, Integer> copiesColumn;

    @FXML
    private TableColumn<Item, String> feeColumn;
    @FXML
    private Label welcomeLabel;


    //curentborrowTV
    @FXML
    private TableView<ItemRenting> currentBorrowTV;
    @FXML
    private TableColumn<ItemRenting, String> idRentingColumn;
    @FXML
    private TableColumn<ItemRenting, String> titleRentingColumn;
    @FXML
    private TableColumn<ItemRenting, ItemRenting.RentalType> typeRentingColumn;
    @FXML
    private TableColumn<ItemRenting, ItemRenting.LoanType> loanTypeRentingColumn;
    @FXML
    private TableColumn<ItemRenting, ItemRenting.Genre> genreRentingColumn;
    @FXML
    private TableColumn<ItemRenting, String> feeRentingColumn;

    //History tv
    @FXML
    private TableView<RentingHistory> historyTV;
    @FXML
    private TableColumn<RentingHistory, String> hisId = new TableColumn<>("ID");
    @FXML
    private TableColumn<RentingHistory, String> hisTitle = new TableColumn<>("Title");
    @FXML
    private TableColumn<RentingHistory, String> hisUsername = new TableColumn<>("Rent by");
    @FXML
    private TableColumn<RentingHistory, String> hisFee = new TableColumn<>("Fee");
    @FXML
    private TableColumn<RentingHistory, String> hisStatus = new TableColumn<>("Status");
    @FXML
    private TableColumn<RentingHistory, String> hisTime = new TableColumn<>("Time");
    private final RentingHistoryUtils rentingHistoryUtils = new RentingHistoryUtils();


    private String username = null;


    public String getUsername() {
        return username;
    }

    ItemUtils itemUtils = new ItemUtils();
    ItemRentingUtils itemRentingUtils = new ItemRentingUtils();

    private ObservableList<Item> getItem() {
        ObservableList<Item> itemList = FXCollections.observableArrayList();
        itemList.addAll(itemUtils.readFromDatabase());
        return itemList;
    }

    private ObservableList<RentingHistory> getRentingHistory() {
        ObservableList<RentingHistory> rentingHistories = FXCollections.observableArrayList();
        rentingHistories.addAll(rentingHistoryUtils.readFromDatabase(getUsername()));
        return rentingHistories;
    }

    private ObservableList<ItemRenting> getRentingItem() {
        ObservableList<ItemRenting> itemRentingsList = FXCollections.observableArrayList();
        itemRentingsList.addAll(itemRentingUtils.readFromDatabase(getUsername()));
        return itemRentingsList;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("title"));
        rentalTypeColumn.setCellValueFactory(new PropertyValueFactory<Item, Item.RentalType>("rentalType"));
        loanTypeColumn.setCellValueFactory(new PropertyValueFactory<Item, Item.LoanType>("loanType"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<Item, Item.Genre>("genre"));
        copiesColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("copies"));
        feeColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("fee"));


        currentBorrowTV.setItems(getRentingItem());
        tableView.setItems(getItem());


        historyTV.setVisible(false);
        returnButton.setVisible(false);

        currentBorrowTV.setVisible(false);
        System.out.println("HELLO"+getUsername());
//        displayWelcomeText();
        onItemSearchBar();
        System.out.println(getUsername());


    }

    protected void onItemSearchBar() {
        searchbar.textProperty().addListener(((observableValue, oldvalue, newValue) -> {
            String lowercaseNewValue = newValue.toLowerCase();
            String uppercaseNewValue = newValue.toUpperCase();
            ObservableList<Item> filteredData = FXCollections.observableArrayList();

            for (Item item : getItem()) {
                if (item.getTitle().toLowerCase().contains(lowercaseNewValue) || item.getTitle().toUpperCase().contains(uppercaseNewValue)) {
                    filteredData.add(item);
                    tableView.setItems(filteredData);
                }
            }
        }));
    }

    private final AccountUtils accountUtils = new AccountUtils();

    @FXML
    void getItemTV() throws IOException {
        System.out.println(rentingHistoryUtils.returnNumberofReting(username));
        Item item = tableView.getSelectionModel().getSelectedItem();
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        ArrayList<Item> items = (ArrayList<Item>) itemUtils.readFromDatabase();
        String role = null;
        int point=0;
        ArrayList<CustomerAccount> users = (ArrayList<CustomerAccount>) accountUtils.readFromDatabase();
        for (CustomerAccount user : users) {
            if (user.getUsername().equals(username)) {
                role = user.getRole();
                point=user.getPoint();
            }
        }
        boolean isFound = false;
        if (item == null) {
            alertsup.alertError("No item Selection", "Please choose an item to start rent");
        } else {
            for (Item item1 : items) {
                String IDselection = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex()).getId();
                if (IDselection.equals(item1.getId()) && item1.getCopies() > 0) {
                    String loanType = item1.getLoanType().toString();
                    System.out.println(loanType);
                    System.out.println(role);
                    if (loanType.equals("TWO_DAYS_LOAN") && Objects.equals(role, "Guest")) {
                        alertsup.alertError("Error!", "You cannot rent this item");
                        isFound = true;
                    } else if(loanType.equals("ONE_WEEK_LOAN") && Objects.equals(role, "Guest") && rentingHistoryUtils.returnNumberofReting(username) > 1){
                        alertsup.alertError("Error!","You cannot rent more than 2 items");
                        isFound = true;
                    } else {
                        assert role != null;
                        if(role.equals("VIP") && point>=100){
                            alertsup.alertSuccess("Congrats!", "You have 100 points you can rent this item for free");
                            Transaction transaction = new Transaction(new CustomerAccount(username), new Item(IDselection));
                            transaction.saveRentItem();
                            if (item1.getCopies() > 0) {
                                item1.setCopies(item1.getCopies() - 1);
                                alertsup.alertSuccess("Congrats!!", "You have successfully rent the item!");
                            }
                            for (CustomerAccount user : users) {
                                if (user.getUsername().equals(username)) {
                                    user.setPoint(user.getPoint()-100);
                                    welcomeLabel.setText("Welcome " + user.getUsername().toUpperCase() + "\n"
                                            + "Your role is: " + user.getRole()+ "\nYour Point is: "+user.getPoint());
                                    break;
                                }
                            }
                        } else {
                            Transaction transaction = new Transaction(new CustomerAccount(username), new Item(IDselection));
                            transaction.saveRentItem();
                            if (item1.getCopies() > 0) {
                                item1.setCopies(item1.getCopies() - 1);
                                alertsup.alertSuccess("Congrats!!", "You have successfully rent the item!");
                            }
                        }
                        isFound = true;
                    }

                    break;
                }
            }
            if (!isFound) {
                alertsup.alertError("Error", "This item is not available");
            }
        }

        itemUtils.saveItem(items);
        accountUtils.saveAccount(users);
        tableView.setItems(getItem());
    }


    public void setLabelWelcome(String role, String name) {
        welcomeLabel.setText("Welcome " + name.toUpperCase() + "\n"
                + "Your role is: " + role);
    }
    public void setLabelWelcome(String role, String name,int point) {
        welcomeLabel.setText("Welcome " + name.toUpperCase() + "\n"
                + "Your role is: " + role+"\nYour point is: "+point);
    }

    @FXML
    protected void onProfile(MouseEvent e) throws IOException {
        System.out.println(username + "On profile");
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("user-edit-info.fxml"));
        root = fxmlLoader.load();
        Edit_InfoGUIController editInfoGUIController = fxmlLoader.getController();
        editInfoGUIController.setUsername(username);
        editInfoGUIController.compareToUsername();
        scene = new Scene(root);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    protected void setUsername(String username) {
        this.username = username;
    }

    @FXML
    protected void onExit(MouseEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-GUI.fxml"));
        root = fxmlLoader.load();
        scene = new Scene(root);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onCurrentBorrow() {
        tableView.setVisible(false);
        rentButton.setVisible(false);
        historyTV.setVisible(false);
        idRentingColumn.setCellValueFactory(new PropertyValueFactory<ItemRenting, String>("idRenting"));
        titleRentingColumn.setCellValueFactory(new PropertyValueFactory<ItemRenting, String>("titleRenting"));
        typeRentingColumn.setCellValueFactory(new PropertyValueFactory<ItemRenting, ItemRenting.RentalType>("typeRenting"));
        loanTypeRentingColumn.setCellValueFactory(new PropertyValueFactory<ItemRenting, ItemRenting.LoanType>("loanTypeRenting"));
        genreRentingColumn.setCellValueFactory(new PropertyValueFactory<ItemRenting, ItemRenting.Genre>("genreRenting"));
        feeRentingColumn.setCellValueFactory(new PropertyValueFactory<ItemRenting, String>("feeRenting"));

        returnButton.setVisible(true);
        currentBorrowTV.setVisible(true);
        currentBorrowTV.setItems(getRentingItem());
    }

    @FXML
    public void onHistory() {
        tableView.setVisible(false);
        currentBorrowTV.setVisible(false);

        rentButton.setVisible(false);
        returnButton.setVisible(false);

        historyTV.setVisible(true);
        hisId.setCellValueFactory(new PropertyValueFactory<>("idRenting"));
        hisTitle.setCellValueFactory(new PropertyValueFactory<>("titleRenting"));
        hisUsername.setCellValueFactory(new PropertyValueFactory<>("usernameRenting"));
        hisFee.setCellValueFactory(new PropertyValueFactory<>("feeRenting"));
        hisStatus.setCellValueFactory(new PropertyValueFactory<>("rentingStatus"));
        hisTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        historyTV.setItems(getRentingHistory());
    }

    @FXML
    public void onReturnDashboard() {
        tableView.setVisible(true);
        rentButton.setVisible(true);

        returnButton.setVisible(false);
        currentBorrowTV.setVisible(false);
        historyTV.setVisible(false);
    }


    @FXML
    public void onReturnButton() throws IOException {
        ItemRenting renting = currentBorrowTV.getSelectionModel().getSelectedItem();
        currentBorrowTV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        if (renting == null) {
            alertsup.alertError("Error", "Choose an item to return");
        } else {
            if (currentBorrowTV.getItems().size() != 0) {
            String IDselection = currentBorrowTV.getItems().get(currentBorrowTV.getSelectionModel().getSelectedIndex()).getIdRenting();
            ArrayList<Item> items = (ArrayList<Item>) itemUtils.readFromDatabase();
            ArrayList<CustomerAccount> users = (ArrayList<CustomerAccount>) accountUtils.readFromDatabase();
            String role;
            ItemRenting selectedRenting = currentBorrowTV.getSelectionModel().getSelectedItem();
            currentBorrowTV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

            //Get the role of current account
            boolean returnSucess = false;
            ArrayList<Transaction> transactionArrayList = (ArrayList<Transaction>) transactionUtils.readFromDatabase();
            //Make the renting item become rented
            if (selectedRenting == null) {
                alertsup.alertError("Error!", "Choose an item to return");
            } else {
                for (Transaction transaction : transactionArrayList) {
                    if (transaction.getItem().getId().equals(IDselection) && transaction.getRentingStatus().equals("Rent")) {
                        transaction.setRentingStatus("Rented");
                        break;
                    }
                    returnSucess = true;
                }


                for (CustomerAccount customerAccount : users) {
                    if (username.equals(customerAccount.getUsername())) {
                        System.out.println(customerAccount);
                        role = customerAccount.getRole();
                        if (returnSucess) {
                            if (role.equals("Guest") && customerAccount.getPoint() < 3) {
                                customerAccount.setPoint(customerAccount.getPoint() + 1);
                                break;
                            }
                            if (role.equals("Guest") && customerAccount.getPoint() == 3) {
                                alertsup.alertSuccess("Congrats!", "Your account has been updated to regular. Now you can rent every item");
                                customerAccount.setRole("Regular");
                                customerAccount.setPoint(customerAccount.getPoint() + 1);
                                welcomeLabel.setText("Welcome " + customerAccount.getUsername().toUpperCase() + "\n"
                                        + "Your role is: " + customerAccount.getRole());
                                users.add(new RegularAccount(customerAccount.getId(),
                                        customerAccount.getUsername(),
                                        customerAccount.getPassword(),
                                        customerAccount.getName(),
                                        customerAccount.getAddress(),
                                        customerAccount.getPhone(),
                                        customerAccount.getPoint()));
                                users.remove(customerAccount);

                                break;
                            }
                            if (role.equals("Regular") && customerAccount.getPoint() < 5) {
                                customerAccount.setPoint(customerAccount.getPoint() + 1);
                                break;
                            }
                            if (role.equals("Regular") && customerAccount.getPoint() == 5) {
                                alertsup.alertSuccess("Congrats!", "Your account has been updated to VIP. Rent more to receive 1 free rent");
                                customerAccount.setRole("VIP");
                                customerAccount.setPoint(customerAccount.getPoint() + 1);
                                customerAccount.setPoint(0);
                                welcomeLabel.setText("Welcome " + customerAccount.getUsername().toUpperCase() + "\n"
                                        + "Your role is: " + customerAccount.getRole());
                                users.add(new VIPAccount(customerAccount.getId(),
                                        customerAccount.getUsername(),
                                        customerAccount.getPassword(),
                                        customerAccount.getName(),
                                        customerAccount.getAddress(),
                                        customerAccount.getPhone(),
                                        customerAccount.getPoint()));
                                users.remove(customerAccount);
                                break;
                            }
                            if (role.equals("VIP")) {
                                customerAccount.setPoint(customerAccount.getPoint() + 10);
                                welcomeLabel.setText("Welcome " + customerAccount.getUsername().toUpperCase() + "\n"
                                        + "Your role is: " + customerAccount.getRole() + "\nYour Point is: " + customerAccount.getPoint());
                                break;
                            }


                        }
                    }
                }
                //Update the account
                accountUtils.saveAccount(users);
                itemRentingUtils.updateItem(transactionArrayList);

                // update the item copies
                for (Item item : items) {
                    if (item.getId().equals(IDselection)) {
                        item.setCopies(item.getCopies() + 1);
                        Transaction transaction = new Transaction(new CustomerAccount(username), new Item(IDselection));
                        transaction.saveRentItem("Return");
                    }
                }
                itemUtils.saveItem(items);
                currentBorrowTV.getItems().remove(selectedRenting);
                currentBorrowTV.refresh();
                tableView.setItems(getItem());
            }
        }
        }
    }
}



