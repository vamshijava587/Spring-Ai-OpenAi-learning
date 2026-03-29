package com.vamshi.springopenai.model;

public record NdcRecord(
        String brandName,
        String genericName,
        String ndc,
        String dosageForm,
        String strength,
        String unitOfMeasure,
        String packageQuantity,
        String routeOfAdministration,
        String orangeBookRating,
        String manufacturer
) {}