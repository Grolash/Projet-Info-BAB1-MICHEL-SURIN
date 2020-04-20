package tests;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.Observable;


public class SixteenthTest extends Application{

    Stage window;
    Button button;
    TableView<Product> table;
    TextField nameInput, priceInput, quantityInput;


    public static void main(String[] args){

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Tree");

        window.setOnCloseRequest(e -> {
            e.consume(); //Say to Java "Hey, we're handling this ourselves."
            closeProgram();
        });

        //Name column
        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name")); //has to be the exact name of the property

        //Price column
        TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Quantity column
        TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setMinWidth(100);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        //Inputs
        nameInput = new TextField();
        nameInput.setPromptText("Name");
        nameInput.setMinWidth(100);

        priceInput = new TextField();
        priceInput.setPromptText("Price");
        priceInput.setMinWidth(100);

        quantityInput= new TextField();
        quantityInput.setPromptText("Quantity");
        quantityInput.setMinWidth(100);

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addButtonClicked());
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction((e -> deleteButtonClicked()));

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(nameInput, priceInput, quantityInput, addButton, deleteButton);


        table = new TableView();
        table.setItems(getProducts());
        table.getColumns().addAll(nameColumn, priceColumn, quantityColumn);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table, hBox);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();

    }
    //addButton clicked
    public void addButtonClicked(){
        Product product = new Product();
        product.setName(nameInput.getText());
        product.setPrice(Double.parseDouble(priceInput.getText()));
        product.setQuantity(Integer.parseInt(quantityInput.getText()));
        table.getItems().addAll(product);
        nameInput.clear();
        priceInput.clear();
        quantityInput.clear();
    }

    //deleteButton clicked
    public void deleteButtonClicked(){
        ObservableList<Product> productSelected, allProducts;
        allProducts = table.getItems();
        productSelected = table.getSelectionModel().getSelectedItems();
        productSelected.forEach(allProducts::remove);
    }

    //get all of the products
    public ObservableList<Product> getProducts() {
        ObservableList<Product> products = FXCollections.observableArrayList();
        products.add(new Product("Zombie", 20, 5));
        products.add(new Product("Clacker", 250, 1));
        products.add(new Product("Tank", 500, 1));
        products.add(new Product("Chancre", 75, 1));
        return products;
    }


    private void closeProgram(){
        boolean answer = ConfirmBox.Display("I'm gonna stop you right there!",
                "Are your really sure you want to quit? You might want to save first.");
        if (answer == true) {
            window.close();
        }
    }


}