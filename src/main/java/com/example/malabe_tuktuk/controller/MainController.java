package com.example.malabe_tuktuk.controller;

import com.example.malabe_tuktuk.model.Cart;
import com.example.malabe_tuktuk.model.Part;
import com.example.malabe_tuktuk.model.Dealer;
import com.example.malabe_tuktuk.parser.DataParser;
import com.example.malabe_tuktuk.parser.DealerParser;
import com.example.malabe_tuktuk.service.InventoryService;
import com.example.malabe_tuktuk.service.DealerService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.List;

public class MainController {

    private static final String Clean_Parts = "data/inventory_clean.txt";
    private static final String Legacy_Parts = "data/inventory_legacy.txt";

    private static final String Legacy_dealers = "data/dealers_legacy.txt";

    @FXML
    private TableView<Part> posTable;
    @FXML
    private TableColumn<Part, String> posCode;
    @FXML
    private TableColumn<Part, String> posDescription;
    @FXML
    private TableColumn<Part, String> posBrand;
    @FXML
    private TableColumn<Part, String> posPrice;
    @FXML
    private TableColumn<Part, String> posStock;
    @FXML
    private TableColumn<Part, String> posCategory;



    @FXML
    private Label selectAnItemLabel;
    @FXML
    private TextField cartQuantityField;
    @FXML
    private Button addToCartButton;

    @FXML
    private Label cartLabel;
    @FXML
    private TableView<Cart> cartTable;
    @FXML
    private TableColumn<Cart, String> colCartId;
    @FXML
    private TableColumn<Cart, String> colCartProduct;
    @FXML
    private TableColumn<Cart, String> colCartQty;
    @FXML
    private TableColumn<Cart, String> colCartUnitPrice;
    @FXML
    private TableColumn<Cart, String> colCartDisc;
    @FXML
    private TableColumn<Cart, String> colCartTotal;



    @FXML
    private Label cartSubtotalLabel;
    @FXML
    private Label cartBulkDiscount;
    @FXML
    private Label cartSynergyDiscount;
    @FXML
    private Label cartFinalTotal;



    @FXML
    private Button removeSelectedButton;
    @FXML
    private Button clearCartButton;
    @FXML
    private Button checkoutButton;
    @FXML
    private Label cartStatusLabel;

    private InventoryService inventoryService;
    private DealerService dealerService;

    @FXML
    private Label inventoryTotalsLabel;

    @FXML
    public TableView<Part> inventoryTable;

    @FXML
    public TableColumn<Part, String> colCode;

    @FXML
    public TableColumn<Part, String> colDescription;

    @FXML
    public TableColumn<Part, String> colBrand;

    @FXML
    public TableColumn<Part, String> colPrice;

    @FXML
    public TableColumn<Part, String> colQuantity;

    @FXML
    public TableColumn<Part, String> colCategory;

    @FXML
    public TableColumn<Part, String> colDate;

    @FXML
    public TableColumn<Part, String> colPhoto;



    @FXML
    private TextField thresholdField;

    @FXML
    private Button setThresholdButton;

    @FXML
    private ListView <String> lowStockListView;



    @FXML
    private TextField codeField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField brandField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField categoryField;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField photoField;



    @FXML
    private Button addPartButton;

    @FXML
    private Button updatePartButton;

    @FXML
    private Button deletePartButton;

    @FXML
    private Button clearFormButton;



    @FXML
    private TableView<Dealer> dealerTable;

    @FXML
    private TableColumn<Dealer, String> colDealerCode;

    @FXML
    private TableColumn<Dealer, String> colDealerName;

    @FXML
    private TableColumn<Dealer, String> colDealerPhone;

    @FXML
    private TableColumn<Dealer, String> colDealerCity;

    @FXML
    private Button pickDealersButton;

    @FXML
    private Label dealerStatusLabel;


    @FXML
    private Label inventoryStatusLabel;


    @FXML
    public void initialize() {
        System.out.println("MainController connected - initialize() was called");

        List<Part> loadedParts = DataParser.loadInventory(Clean_Parts, Legacy_Parts);
        inventoryService = new InventoryService(loadedParts);
        setupInventoryColumns();

        List<Dealer> loadedDealers = DealerParser.readDealerFile(Legacy_dealers);
        dealerService = new DealerService(loadedDealers);
        setupDealerColumns();

        inventoryService.sortByCate_Code();
        displayInventoryTable();

        inventoryTable.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Part>() {
                    @Override
                    public void changed (ObservableValue<? extends Part> observable, Part oldValue, Part newValue) {
                        if (newValue != null) {
                            codeField.setText(newValue.getCode());
                            descriptionField.setText(newValue.getDescription());
                            brandField.setText(newValue.getBrand());
                            priceField.setText(String.valueOf(newValue.getPrice()));
                            quantityField.setText(String.valueOf(newValue.getQuantity()));
                            categoryField.setText(newValue.getCategory());
                            dateField.setValue(LocalDate.parse(newValue.getDateAdded()));
                            photoField.setText(newValue.getPhotoFileName());
                        }
                    }
                }
        );
    }

    private void setupInventoryColumns() {

        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
        colPhoto.setCellValueFactory(new PropertyValueFactory<>("photoFileName"));

        colPrice.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Part, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Part, String> data) {
                double price = data.getValue().getPrice();
                String formatted = "Rs. " + price;
                return new SimpleStringProperty(formatted);
            }
        });
    }

    private void setupDealerColumns() {

        colDealerCode.setCellValueFactory(new PropertyValueFactory<>("dealerCode"));
        colDealerName.setCellValueFactory(new PropertyValueFactory<>("dealerName"));
        colDealerPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colDealerCity.setCellValueFactory(new PropertyValueFactory<>("city"));

    }

    private void displayInventoryTable(){
        ObservableList<Part> tableData = FXCollections.observableArrayList(inventoryService.getInventory());
        inventoryTable.setItems(tableData);
        inventoryTable.refresh();

        updateTotalsLabel();
        updateLowStockList();

    }

    private void updateTotalsLabel(){
        int totalParts = inventoryService.getTotalPart();
        double totalValue = inventoryService.getTotalPrice();

        inventoryTotalsLabel.setText("No. of item types: "+ totalParts + "  |  Total value: Rs. " + totalValue);
    }

    private void updateLowStockList(){

        List<Part> lowStockParts = inventoryService.getLowStockParts();

        ObservableList <String>  displayLines = FXCollections.observableArrayList();

        for (int i = 0; i < lowStockParts.size(); i++) {

            Part part = lowStockParts.get(i);
            String line = part.getCode() + " - " + part.getDescription() + " (Stock: " +  part.getQuantity() + ")";
            displayLines.add(line);
        }

        lowStockListView.setItems(displayLines);
    }

    @FXML
    private void handleAddPart(){

        String code = codeField.getText().trim();
        String description = descriptionField.getText().trim();
        String brand = brandField.getText().trim();
        String priceText = priceField.getText().trim();
        String quantityText = quantityField.getText().trim();
        String category = categoryField.getText().trim().toUpperCase();
        String photoFileName = photoField.getText().trim();

        LocalDate selectedDate = dateField.getValue();
        String dateAdded;

        if (selectedDate == null) {
            dateAdded = LocalDate.now().toString();
        }
        else {
            dateAdded = selectedDate.toString();
        }

        if (photoFileName.isEmpty()){
            photoFileName = "default_image.jpg";
        }

        if (priceText.isEmpty() || quantityText.isEmpty()) {

            inventoryTotalsLabel.setText("Error: Price and Quantity are required");
            return;
        }

        double price;
        int quantity;

        try {
            price = Double.parseDouble(priceText);
            quantity = Integer.parseInt(quantityText);
        }
        catch (NumberFormatException e) {
            inventoryTotalsLabel.setText("Error: Price must be a number and quantity must be a whole number.");
            return;
        }

        Part newPart = new Part(code, description, brand, price, quantity, category, dateAdded, photoFileName);
        String result =  inventoryService.addPart(newPart);
        inventoryTotalsLabel.setText(result);

        refreshTableView();
    }

    @FXML
    private void handleDeletePart(){

        String code = codeField.getText().trim();

        if (code.isEmpty()) {
            inventoryStatusLabel.setText("Error: Select a part or enter a code to delete");
            return;
        }

        String result = inventoryService.deletePart(code);
        inventoryStatusLabel.setText(result);

        refreshTableView();
        clearForm();
    }

    @FXML
    private void clearForm(){
        codeField.clear();
        descriptionField.clear();
        brandField.clear();
        priceField.clear();
        quantityField.clear();
        categoryField.clear();
        dateField.setValue(null);
        photoField.clear();

        inventoryTable.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleUpdatePart(){

        String code = codeField.getText().trim();
        String description = descriptionField.getText().trim();
        String brand = brandField.getText().trim();
        String priceText = priceField.getText().trim();
        String quantityText = quantityField.getText().trim();
        String category = categoryField.getText().trim().toUpperCase();
        String photoFileName = photoField.getText().trim();


        if (code.isEmpty()) {
            inventoryStatusLabel.setText("Error: Select a part to update");
            return;
        }

        if (photoFileName.isEmpty()) {
            photoFileName = "default_image.jpg";
        }

        LocalDate selectedDate = dateField.getValue();
        String dateAdded;
        if (selectedDate == null) {
            dateAdded = LocalDate.now().toString();
        }
        else {
            dateAdded = selectedDate.toString();
        }

        if (priceText.isEmpty() ||  quantityText.isEmpty()) {
            inventoryTotalsLabel.setText("Error: Price and Quantity are required");
            return;
        }

        double price;
        int quantity;

        try {
            price = Double.parseDouble(priceText);
            quantity = Integer.parseInt(quantityText);
        }
        catch (NumberFormatException e) {
            inventoryStatusLabel.setText("Error: Price must be a number and quantity must be a whole number.");
            return;
        }

        String result = inventoryService.updatePart(code, description, brand, price, quantity, category, dateAdded, photoFileName);
        inventoryStatusLabel.setText(result);

        refreshTableView();
    }

    private void refreshTableView() {

        inventoryService.sortByCate_Code();
        DataParser.saveParts(inventoryService.getInventory(), Clean_Parts);
        displayInventoryTable();

    }

    @FXML
    private void handleSetThreshold(){

        String thresholdText = thresholdField.getText().trim();

        if (thresholdText.isEmpty()) {
            inventoryStatusLabel.setText("Error: Threshold must be a whole number.");
            return;
        }

        int newThreshold;

        try {
            newThreshold = Integer.parseInt(thresholdText);
        }
        catch (NumberFormatException e) {
            inventoryStatusLabel.setText("Error: Threshold must be a whole number.");
            return;
        }

        if (newThreshold < 0) {
            inventoryStatusLabel.setText("Error: Threshold cannot be negative.");
            return;
        }

        inventoryService.setLowStockThreshold(newThreshold);

        updateLowStockList();
        inventoryStatusLabel.setText("Low-stock threshold set to " + newThreshold + ".");
    }

    @FXML
    private void handlePickDealers() {

        List<Dealer> selectedDealers = dealerService.getRandomDealers();

        if (selectedDealers.isEmpty()) {
            dealerStatusLabel.setText("Error: Not enough dealers to select four.");
            return;
        }

        ObservableList<Dealer> tableData = FXCollections.observableArrayList(selectedDealers);
        dealerTable.setItems(tableData);
        dealerStatusLabel.setText("4 dealers are randomly sorted.");
    }
}