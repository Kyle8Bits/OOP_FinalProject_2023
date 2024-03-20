package com.example.finalproject_oop_2023.controller;

import com.example.finalproject_oop_2023.Items.Item;
import com.example.finalproject_oop_2023.Utils.ItemUtils;
import com.example.finalproject_oop_2023.Main;
import com.example.finalproject_oop_2023.Rental.ItemRenting;
import com.example.finalproject_oop_2023.Rental.RentingHistory;
import com.example.finalproject_oop_2023.User.CustomerAccount;
import com.example.finalproject_oop_2023.Utils.RentingHistoryUtils;
import com.example.finalproject_oop_2023.supporter.Alertsup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.*;

public class AdminGUIController implements Initializable {
    Stage stage;
    Scene scene;
    Parent root;
    @FXML
    private Alertsup alertsup = new Alertsup();
    @FXML
    private HBox accountSearchBar;
    @FXML
    private HBox itemSearchBar;
    @FXML
    private HBox rentalSearchBar;
    @FXML
    private TextField rentalSearchField;
    @FXML
    private Label RentalLabel;
    @FXML
    private TextField itemSearchField;
    @FXML
    private TextField searchField;
    @FXML
    private Label accountManageLabel;
    @FXML
    private Label ItemManageLabel;
    @FXML
    private TableView<Item> itemTable= new TableView<>();
    @FXML
    private TableView<CustomerAccount> accountTable= new TableView<>();
    @FXML
    private TableView<ItemRenting> rentalTable = new TableView<>();

    @FXML
    private TableColumn<CustomerAccount, String> ID;
    @FXML
    private TableColumn<CustomerAccount, String> name;
    @FXML
    private TableColumn<CustomerAccount, String> role;
    @FXML
    private TableColumn<CustomerAccount, String> address;
    @FXML
    private TableColumn<CustomerAccount, String> phone;
    @FXML
    private TableColumn<CustomerAccount, String> username;
    @FXML
    private TableColumn<CustomerAccount, String> password;
    @FXML
    private TableColumn<CustomerAccount, Integer> point;

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
    private Button editButton;
    String itemSelect;
    String accountSelect;
    ItemUtils itemUtils = new ItemUtils();

    @FXML
    private TableView<RentingHistory> historyTV;
    @FXML
    private TableColumn<RentingHistory,String> hisId= new TableColumn<>("ID");
    @FXML
    private TableColumn<RentingHistory,String> hisTitle= new TableColumn<>("Title");
    @FXML
    private TableColumn<RentingHistory,String> hisUsername= new TableColumn<>("Rent by");
    @FXML
    private TableColumn<RentingHistory,String> hisFee= new TableColumn<>("Fee");
    @FXML
    private TableColumn<RentingHistory,String> hisStatus= new TableColumn<>("Status");
    @FXML
    private TableColumn<RentingHistory,String> hisTime= new TableColumn<>("Time");
    private final RentingHistoryUtils rentingHistoryUtils= new RentingHistoryUtils();

    @FXML
    private ComboBox<String> sortingCopyBox;

    private ObservableList<Item> items;
    private final File file= new File("src/main/java/com/example/finalproject_oop_2023/SavingText/Account.txt");
    private ObservableList<CustomerAccount> list;
    @FXML
    protected void onExitButton(MouseEvent e) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-GUI.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        scene = new Scene(root);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    private ObservableList<RentingHistory> getRentingHistory(){
        ObservableList<RentingHistory> rentingHistories= FXCollections.observableArrayList();
        rentingHistories.addAll(rentingHistoryUtils.readFromDatabaseAdmin());
        return rentingHistories;
    }
    public ObservableList<CustomerAccount> readAccount(File file) throws IOException {
        RandomAccessFile randomAccessFile= new RandomAccessFile(file,"r");
        String line;
        list= FXCollections.observableArrayList();
        while((line= randomAccessFile.readLine()) != null){
            String[] tokens= line.split(",");
            String id= tokens[0];
            String username= tokens[1];
            String password= tokens[2];
            String name= tokens[3];
            String address= tokens[4];
            String phone= tokens[5];
            String role= tokens[6];
            int point= Integer.parseInt(tokens[7]);
            CustomerAccount user= new CustomerAccount(id,username,password,name,address,phone,role,point);
            list.add(user);
        }
        randomAccessFile.close();
        return list;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RandomAccessFile randomAccessFile;
        try {
            randomAccessFile = new RandomAccessFile(file,"r");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String line;
        list= FXCollections.observableArrayList();
        while(true) {
            try {
                if (((line = randomAccessFile.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String[] tokens = line.split(",");
            String id = tokens[0];
            String username = tokens[1];
            String password = tokens[2];
            String name = tokens[3];
            String address = tokens[4];
            String phone = tokens[5];
            String role = tokens[6];
            int point = Integer.parseInt(tokens[7]);
            CustomerAccount user = new CustomerAccount(id, username, password, name, address, phone, role, point);
            list.add(user);
        }
        try {
            randomAccessFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ID.setCellValueFactory(new PropertyValueFactory<CustomerAccount, String>("id"));
        username.setCellValueFactory(new PropertyValueFactory<CustomerAccount, String>("username"));
        password.setCellValueFactory(new PropertyValueFactory<CustomerAccount, String>("password"));
        name.setCellValueFactory(new PropertyValueFactory<CustomerAccount, String>("name"));
        address.setCellValueFactory(new PropertyValueFactory<CustomerAccount, String>("address"));
        phone.setCellValueFactory(new PropertyValueFactory<CustomerAccount, String>("phone"));
        role.setCellValueFactory(new PropertyValueFactory<CustomerAccount, String>("role"));
        point.setCellValueFactory(new PropertyValueFactory<CustomerAccount, Integer>("point"));
        try {
            accountTable.setItems(readAccount(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //for item table

        Collections.sort(list);
        itemSearchBar.setVisible(false);
        rentalSearchBar.setVisible(false);
        onCustomerSearchBar();
        onRentalHistorySearchBar();
        sortingCustomer();
        sortingCopy();

    }

    protected void onCustomerSearchBar(){
        searchField.textProperty().addListener(((observableValue, oldvalue, newValue) -> {
            String filter= newValue.toLowerCase();
            String filteredName= newValue.toUpperCase();
            ObservableList<CustomerAccount> filteredData= FXCollections.observableArrayList();

            for(CustomerAccount customerAccount: list){
                if(customerAccount.getUsername().toLowerCase().contains(filter) || customerAccount.getName().toLowerCase().contains(filter) || customerAccount.getName().toUpperCase().contains(filteredName)){
                    filteredData.add(customerAccount);
                    accountTable.setItems(filteredData);
                }
            }

        }));
    }
    protected void onRentalHistorySearchBar(){
        rentalSearchField.textProperty().addListener(((observableValue, oldvalue, newValue) -> {
            String filter= newValue.toLowerCase();
            String filteredName= newValue.toUpperCase();
            ObservableList<RentingHistory> filteredData= FXCollections.observableArrayList();

            for(RentingHistory rentingHistory: getRentingHistory() ){
                if(rentingHistory.getUsernameRenting().toLowerCase().contains(filter) ){
                    filteredData.add(rentingHistory);
                    historyTV.setItems(filteredData);
                }
            }

        }));
    }

    @FXML
    public void onAccountManage(){
        sortingCustomerBox.setVisible(true);
        accountTable.setVisible(true);
        itemTable.setVisible(false);
        rentalTable.setVisible(false);
        accountManageLabel.setText("Account Manage(On)");
        ItemManageLabel.setText("Item Manage");
        RentalLabel.setText("Rental");
        itemSearchBar.setVisible(false);
        accountSearchBar.setVisible(true);
        rentalSearchBar.setVisible(false);
    }

    @FXML
    public void onRentalHistory(){
        accountTable.setVisible(false);
        itemTable.setVisible(false);
        rentalTable.setVisible(true);

        RentalLabel.setText("Rental(ON)");
        ItemManageLabel.setText("Item Manage");
        accountManageLabel.setText("Account Manage");
        hisId.setCellValueFactory(new PropertyValueFactory<>("idRenting"));
        hisTitle.setCellValueFactory(new PropertyValueFactory<>("titleRenting"));
        hisUsername.setCellValueFactory(new PropertyValueFactory<>("usernameRenting"));
        hisFee.setCellValueFactory(new PropertyValueFactory<>("feeRenting"));
        hisStatus.setCellValueFactory(new PropertyValueFactory<>("rentingStatus"));
        hisTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        historyTV.setItems(getRentingHistory());

        accountSearchBar.setVisible(false);
        itemSearchBar.setVisible(false);
        rentalSearchBar.setVisible(true);

    }

    private ObservableList<Item> getItem() {
        ObservableList<Item> itemList = FXCollections.observableArrayList();
        itemList.addAll(itemUtils.readFromDatabase());

        return itemList;
    }
    @FXML
    public void onItemManage(){

        items = getItem();

        idColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("title"));
        rentalTypeColumn.setCellValueFactory(new PropertyValueFactory<Item, Item.RentalType>("rentalType"));
        loanTypeColumn.setCellValueFactory(new PropertyValueFactory<Item, Item.LoanType>("loanType"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<Item, Item.Genre>("genre"));
        copiesColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("copies"));
        feeColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("fee"));

        itemTable.setItems(items);

        accountTable.setVisible(false);
        itemTable.setVisible(true);
        rentalTable.setVisible(false);


        Collections.sort(items);
        sortingCustomerBox.setVisible(false);
//        sortingCopyBox.setVisible(false);
        accountManageLabel.setText("Account Manage");
        ItemManageLabel.setText("Item Manage(On)");
        RentalLabel.setText("Rental");
        accountSearchBar.setVisible(false);
        itemSearchBar.setVisible(true);
        rentalSearchBar.setVisible(false);
        onItemSearchBar();
    }

    public void toAddItemScene(MouseEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("add-item.fxml"));
        root= fxmlLoader.load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    protected void onItemSearchBar(){

        itemSearchField.textProperty().addListener(((observableValue, oldvalue, newValue) -> {
            String lowercaseNewValue= newValue.toLowerCase();
            String uppercaseNewValue= newValue.toUpperCase();
            ObservableList<Item> filteredData= FXCollections.observableArrayList();

            for(Item item: items){
                if(item.getTitle().toLowerCase().contains(lowercaseNewValue) ||item.getTitle().toUpperCase().contains(uppercaseNewValue)){
                    filteredData.add(item);
                    itemTable.setItems(filteredData);
                }
            }

        }));
    }

    @FXML
    public void editItemPress(ActionEvent event) throws IOException {
        Item item = itemTable.getSelectionModel().getSelectedItem();
        itemTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        if (item != null) {
            itemSelect = itemTable.getItems().get(itemTable.getSelectionModel().getSelectedIndex()).getId();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("edit-item.fxml"));
            root = fxmlLoader.load();
            EditItemUIController editItemUIController = fxmlLoader.getController();
            editItemUIController.setInfoOnField(itemSelect);
            scene = new Scene(root);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            alertsup.alertError("No item Selection","Please choose an item to start edit");
        }
    }

    @FXML
    public void editAccountPress(ActionEvent event) throws IOException {
        CustomerAccount account = accountTable.getSelectionModel().getSelectedItem();
        accountTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        if (account != null) {
            accountSelect = accountTable.getItems().get(accountTable.getSelectionModel().getSelectedIndex()).getId();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("edit-account.fxml"));
            root = fxmlLoader.load();
            EditAccountGUIController editAccountGUIController = fxmlLoader.getController();
            editAccountGUIController.setInfoOnField(accountSelect);
            scene = new Scene(root);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            Alertsup alertsup = new Alertsup();
            alertsup.alertError("No account selection","Please choose account to start edit");
        }
    }

    @FXML
    ComboBox<String> sortingCustomerBox = new ComboBox<>();
    public void sortingCustomer(){
        sortingCustomerBox.getItems().addAll("Guest", "Regular", "VIP","All");

        sortingCustomerBox.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            ObservableList<CustomerAccount> sortedData = FXCollections.observableArrayList();

            if(newValue != null && newValue.equals("All")){
                accountTable.setItems(list);
            } else{
                for (CustomerAccount customerAccount : list) {
                    if (newValue != null && newValue.equals(customerAccount.getRole())) {
                        sortedData.add(customerAccount);
                    }
                }
                accountTable.setItems(sortedData);
            }
        });
    }

    public void sortingCopy(){
        sortingCopyBox.getItems().addAll("No Copy", "All");

        sortingCopyBox.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            ObservableList<Item> sortedData = FXCollections.observableArrayList();

            if(newValue != null && newValue.equals("All")){
                itemTable.setItems(items);
            } else{
                for (Item item: items){
                    if(newValue != null && newValue.equals("No Copy") && item.getCopies() == 0){
                        sortedData.add(item);
                    }
                }
                itemTable.setItems(sortedData);
            }
        });
    }

}

