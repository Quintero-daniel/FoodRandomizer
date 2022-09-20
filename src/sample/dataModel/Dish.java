package sample.dataModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Dish {
    private SimpleStringProperty name = new SimpleStringProperty("");
    private ObservableList<String> ingredients;

    public Dish(String name) {
        this.name.set(name);
        this.ingredients = FXCollections.observableArrayList();
    }

    public Dish(String name, List<String> ingredients) {
        this.name.set(name);
        this.ingredients = FXCollections.observableArrayList(ingredients);
    }

    public String getName() {
        return name.get();
    }

    public ObservableList<String> getIngredients() {
        return ingredients;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients.setAll(ingredients);
    }

    @Override
    public String toString() {
        return this.name.get();
    }
}
