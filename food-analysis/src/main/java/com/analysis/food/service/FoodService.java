package com.analysis.food.service;

import com.analysis.food.client.AIClient;
import com.analysis.food.client.OpenFoodClient;
import com.analysis.food.model.ProductIngredients;
import com.analysis.food.model.SearchProductItem;
import com.analysis.food.model.SearchProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final OpenFoodClient openFoodClient;
    private final AIClient aiClient;

    public ProductIngredients getProductByBarcode(String barcode) {
        return openFoodClient.getProductByBarcode(barcode);
    }

    public String searchProductByName(String foodName) {
        var searchProductResponse = openFoodClient.searchProduct(foodName);

        var ingredients = searchProductResponse.getProducts()
                            .stream()
                            .map(SearchProductItem::getIngredients_text_en)
                            .filter(i -> i != null && !i.isEmpty())
                            .findFirst()
                            .orElse("");
        var input = "Generate a summary for every ingredient, why is it used and why it may be bad for human: " + ingredients;
        return aiClient.generation(input);
    }
} 