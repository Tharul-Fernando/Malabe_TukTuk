package com.example.malabe_tuktuk.controller;

import com.example.malabe_tuktuk.model.Part;

import com.example.malabe_tuktuk.parser.DataParser;
import com.example.malabe_tuktuk.service.InventoryService;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.util.List;

public class MainController {

    private static final String Clean_Parts = "data/inventory_clean.txt";
    private static final String Legacy_Parts = "data/inventory_legacy.txt";

    private InventoryService inventoryService;

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
    private TextField dateField;

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



    @FXML private Label inventoryStatusLabel;


    @FXML
    public void initialize() {
        System.out.println("MainController connected - initialize() was called");

        List<Part> loadedParts = DataParser.loadInventory(Clean_Parts, Legacy_Parts);
        inventoryService = new InventoryService(loadedParts);

        setupInventoryColumns();
        displayInventoryTable();
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

    private void displayInventoryTable(){
        ObservableList<Part> tableData = FXCollections.observableArrayList(inventoryService.getInventory());
        inventoryTable.setItems(tableData);
    }
}