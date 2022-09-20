package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import sample.dataModel.Dish;
import sample.dataModel.DishData;
import sample.dataModel.DishHistory;
import sample.dataModel.DishHistoryData;


import java.io.IOException;
import java.util.Random;


public class Controller {

    @FXML
    private Label randomDishLabel;

    @FXML
    private Label ingredientsLabel;

    @FXML
    private TableView<DishHistory> dishesHistoryTable = new TableView<>();

    @FXML
    private ListView<String> mainIngredientsListView = new ListView<>();

    @FXML
    private Button showDishListSceneButton;

    @FXML
    private Button selectButton;

    private DishHistoryData dishHistoryData;

    private DishData dishData;

    public void initialize() {
        selectButton.setVisible(false);
        randomDishLabel.setVisible(false);
        ingredientsLabel.setVisible(false);
        mainIngredientsListView.setVisible(false);
        dishesHistoryTable.setItems(dishHistoryData.getHistory());
    }

    public Controller() {
        dishHistoryData = new DishHistoryData();
        dishHistoryData.loadHistory();

        dishData = new DishData();
        dishData.loadDishes();
    }

    @FXML
    public void showDishListScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("dishListScene.fxml"));

        Stage stage = (Stage) showDishListSceneButton.getScene().getWindow();
        stage.setTitle("SceneTwo");
        stage.setScene(new Scene(root));
    }

    @FXML
    public void selectRandomDish() {

        int maxBound = dishData.getDishes().size();
        Random random = new Random();
        Dish randomDish = dishData.getDishes().get(random.nextInt(maxBound));

        mainIngredientsListView.setVisible(true);
        randomDishLabel.setVisible(true);
        ingredientsLabel.setVisible(true);
        selectButton.setVisible(true);

        randomDishLabel.setText(randomDish.getName());
        mainIngredientsListView.setItems(randomDish.getIngredients());

    }

    @FXML
    public void selectDish() {
        dishHistoryData.addDishHistory(new DishHistory(randomDishLabel.getText()));
        dishesHistoryTable.refresh();
    }
}
