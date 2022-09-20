package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.dataModel.Dish;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DishController {

    @FXML
    private TextField dishNameField;

    @FXML
    private TextArea ingredientsField;

    public Dish getNewDish() {
        String name = dishNameField.getText().toLowerCase();
        String ingredients = ingredientsField.getText().toLowerCase();

        name = name.replaceAll("[^a-zA-Z\\s,]", "");
        name = name.substring(0, 1).toUpperCase() + name.substring(1);

        ingredients = ingredients.replaceAll("[^a-zA-Z\\s,]", "");


        if (ingredients.contains(",")) {
            String[] splitIngredients = ingredients.split(",");
            List<String> ingredientsList = Arrays.asList(splitIngredients);

            ingredientsList = ingredientsList.stream()
                    .map(String::trim)
                    .map(ingredient -> ingredient.substring(0, 1).toUpperCase() + ingredient.substring(1))
                    .sorted(String::compareTo)
                    .collect(Collectors.toList());

            return new Dish(name, ingredientsList);
        }
        return new Dish(name, Collections.singletonList(ingredients));
    }

    public void editDish(Dish dish) {
        dishNameField.setText(dish.getName());

        StringBuilder ingredientsString = new StringBuilder();

        if (dish.getIngredients() != null && dish.getIngredients().size() > 0) {
            for (String ingredients : dish.getIngredients()) {
                ingredientsString.append(ingredients);
                ingredientsString.append(",");
            }
            ingredientsField.setText(ingredientsString.toString());
        } else {
            //TODO: Check the case whether there's no ingredients
            ingredientsField.setText("");
        }
    }

    public void updateDish(Dish dish) {
        String name = dishNameField.getText().toLowerCase();
        String ingredients = ingredientsField.getText().toLowerCase();

        name = name.replaceAll("[^a-zA-Z\\s,]", "");
        name = name.substring(0, 1).toUpperCase() + name.substring(1);

        ingredients = ingredients.replaceAll("[^a-zA-Z\\s,]", "");


        if (ingredients.contains(",")) {
            String[] splitIngredients = ingredients.split(",");
            List<String> ingredientsList = Arrays.asList(splitIngredients);

            ingredientsList = ingredientsList.stream()
                    .map(String::trim)
                    .map(ingredient -> ingredient.substring(0, 1).toUpperCase() + ingredient.substring(1))
                    .sorted(String::compareTo)
                    .collect(Collectors.toList());

            dish.setName(name);
            dish.setIngredients(ingredientsList);
        } else {
            dish.setName(name);
            dish.setIngredients(Collections.singletonList(ingredients));
        }
    }

}
