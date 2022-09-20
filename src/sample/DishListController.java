package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.dataModel.Dish;
import sample.dataModel.DishData;


import java.io.IOException;
import java.util.Optional;


public class DishListController {

    @FXML
    private AnchorPane dishListWindow;

    @FXML
    private ListView<Dish> dishListView = new ListView<>();

    @FXML
    private ListView<String> ingredientsListView = new ListView<>();

    @FXML
    private Button showMainSceneButton;

    private DishData dishData;

    public void initialize() {
        dishListView.setItems(dishData.getDishes());
        dishListView.getSelectionModel().selectFirst();

        Dish initialDish = dishListView.getSelectionModel().getSelectedItem();
        ingredientsListView.setItems(initialDish.getIngredients());
    }

    public DishListController() {
        dishData = new DishData();
        dishData.loadDishes();
    }

    public void showMainScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainScene.fxml"));

        Stage stage = (Stage) showMainSceneButton.getScene().getWindow();
        stage.setTitle("SceneOne");
        stage.setScene(new Scene(root));
    }

    @FXML
    public void handleClickListView() {
        Dish dish = dishListView.getSelectionModel().getSelectedItem();
        ingredientsListView.setItems(dish.getIngredients());
    }

    @FXML
    public void showAddDishDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(dishListWindow.getScene().getWindow());
        dialog.setTitle("Add New Dish");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("dishDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DishController dishController = fxmlLoader.getController();
            //TODO: Add check for null name or ingredients
            dishData.addDish(dishController.getNewDish());

            // TODO: Change the save contacts method to be done in application close
            dishData.saveDishes();
        }
    }

    @FXML
    public void showEditDishDialog() {
        Dish selectedDish = dishListView.getSelectionModel().getSelectedItem();

        if (selectedDish == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Dish selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the dish you want to edit.");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(dishListWindow.getScene().getWindow());
        dialog.setTitle("Edit Dish");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("dishDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        DishController dishController = fxmlLoader.getController();
        dishController.editDish(selectedDish);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            dishController.updateDish(selectedDish);
            dishListView.refresh();
            ingredientsListView.refresh();

            // TODO: Change the save contacts method to be done in application close
            dishData.saveDishes();
        }
    }

    @FXML
    public void deleteDish() {
        Dish selectedDish = dishListView.getSelectionModel().getSelectedItem();

        if (selectedDish == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Dish selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the dish you want to delete.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Dish");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the Dish: " + selectedDish.getName());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            dishData.deleteDish(selectedDish);

            dishListView.refresh();
            dishListView.getSelectionModel().clearSelection();
            dishListView.getSelectionModel().selectLast();
            ingredientsListView.setItems(dishListView.getSelectionModel().getSelectedItem().getIngredients());

            // TODO: Change the save contacts method to be done in application close
            dishData.saveDishes();
        }
    }
}
