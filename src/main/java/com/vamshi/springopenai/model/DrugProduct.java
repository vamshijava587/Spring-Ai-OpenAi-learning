package com.vamshi.springopenai.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DrugProduct {
    @JsonProperty("product_ndc")     public String productNdc;
    @JsonProperty("generic_name")    public String genericName;
    @JsonProperty("brand_name")      public String brandName;
    @JsonProperty("labeler_name")    public String labelerName;
    @JsonProperty("dosage_form")     public String dosageForm;
    @JsonProperty("product_type")    public String productType;
    @JsonProperty("marketing_category") public String marketingCategory;
    @JsonProperty("route")           public List<String> route;
    @JsonProperty("active_ingredients") public List<Map<String, String>> activeIngredients;

    /** Flatten to a single string for embedding */
    public String toEmbeddingText() {
        return String.format(
            "Product: %s. Generic name: %s. Brand: %s. Labeler: %s. " +
            "Form: %s. Type: %s. Category: %s. Route: %s.",
            productNdc, genericName, brandName, labelerName,
            dosageForm, productType, marketingCategory,
            route != null ? String.join(", ", route) : ""
        );
    }
}