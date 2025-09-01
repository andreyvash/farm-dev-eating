package com.analysis.food.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchProductItem {

    // Some responses use "code", others "id". Map both.
    @JsonProperty("code")
    private String code;

    @JsonProperty("id")
    private String id;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("ingredients")
    private List<Ingredient> ingredients;

    @JsonProperty("ingredients_text_en")
    private String ingredients_text_en;

    @JsonProperty("ingredients_text")
    private String ingredients_text;
} 