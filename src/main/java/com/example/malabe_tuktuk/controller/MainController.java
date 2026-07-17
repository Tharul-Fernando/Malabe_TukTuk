package com.example.malabe_tuktuk.controller;

import com.example.malabe_tuktuk.model.Part;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class MainController {


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
    }
}