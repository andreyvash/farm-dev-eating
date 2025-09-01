package com.analysis.food.controller;

import com.analysis.food.model.SearchProductResponse;
import com.analysis.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping("/{foodName}")
    public ResponseEntity<String> getProductByBarcode(@PathVariable("foodName") String foodName) {
        String result = foodService.searchProductByName(foodName);
        return ResponseEntity.ok(result);
    }


} 