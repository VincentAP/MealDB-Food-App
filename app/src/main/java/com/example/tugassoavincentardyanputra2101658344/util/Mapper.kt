package com.example.tugassoavincentardyanputra2101658344.util

import com.example.tugassoavincentardyanputra2101658344.api.model.*
import com.example.tugassoavincentardyanputra2101658344.db.entity.*

fun List<MealItemResponse>.toMostPopularFood(): List<MostPopularFood> {
    val items = mutableListOf<MostPopularFood>()
    this.forEach {
        items.add(
            MostPopularFood(
                idMeal = it.idMeal,
                strMeal = it.strMeal,
                strDrinkAlternate = it.strDrinkAlternate,
                strCategory = it.strCategory,
                strArea = it.strArea,
                strInstructions = it.strInstructions,
                strMealThumb = it.strMealThumb,
                strTags = it.strTags,
                strYoutube = it.strYoutube,
                strIngredient1 = it.strIngredient1,
                strIngredient2 = it.strIngredient2,
                strIngredient3 = it.strIngredient3,
                strIngredient4 = it.strIngredient4,
                strIngredient5 = it.strIngredient5,
                strIngredient6 = it.strIngredient6,
                strIngredient7 = it.strIngredient7,
                strIngredient8 = it.strIngredient8,
                strIngredient9 = it.strIngredient9,
                strIngredient10 = it.strIngredient10,
                strIngredient11 = it.strIngredient11,
                strIngredient12 = it.strIngredient12,
                strIngredient13 = it.strIngredient13,
                strIngredient14 = it.strIngredient14,
                strIngredient15 = it.strIngredient15,
                strIngredient16 = it.strIngredient16,
                strIngredient17 = it.strIngredient17,
                strIngredient18 = it.strIngredient18,
                strIngredient19 = it.strIngredient19,
                strIngredient20 = it.strIngredient20,
                strMeasure1 = it.strMeasure1,
                strMeasure2 = it.strMeasure2,
                strMeasure3 = it.strMeasure3,
                strMeasure4 = it.strMeasure4,
                strMeasure5 = it.strMeasure5,
                strMeasure6 = it.strMeasure6,
                strMeasure7 = it.strMeasure7,
                strMeasure8 = it.strMeasure8,
                strMeasure9 = it.strMeasure9,
                strMeasure10 = it.strMeasure10,
                strMeasure11 = it.strMeasure11,
                strMeasure12 = it.strMeasure12,
                strMeasure13 = it.strMeasure13,
                strMeasure14 = it.strMeasure14,
                strMeasure15 = it.strMeasure15,
                strMeasure16 = it.strMeasure16,
                strMeasure17 = it.strMeasure17,
                strMeasure18 = it.strMeasure18,
                strMeasure19 = it.strMeasure19,
                strMeasure20 = it.strMeasure20,
                strSource = it.strSource,
                strImageSource = it.strImageSource,
                strCreativeCommonsConfirmed = it.strCreativeCommonsConfirmed,
                dateModified = it.dateModified,
                isFavorite = false
            )
        )
    }
    return items
}

fun List<MealCategoryItemResponse>.toCategory(): List<Category> {
    val items = mutableListOf<Category>()
    this.forEach {
        items.add(
            Category(
                categoryName = it.strCategory ?: "",
                imageUrl = it.strCategoryThumb
            )
        )
    }
    return items
}

fun List<MealItemResponse>.toBestFoodOfTheDay(): List<BestFoodOfTheDay> {
    val items = mutableListOf<BestFoodOfTheDay>()
    this.forEach {
        items.add(
            BestFoodOfTheDay(
                idMeal = it.idMeal,
                strMeal = it.strMeal,
                strDrinkAlternate = it.strDrinkAlternate,
                strCategory = it.strCategory,
                strArea = it.strArea,
                strInstructions = it.strInstructions,
                strMealThumb = it.strMealThumb,
                strTags = it.strTags,
                strYoutube = it.strYoutube,
                strIngredient1 = it.strIngredient1,
                strIngredient2 = it.strIngredient2,
                strIngredient3 = it.strIngredient3,
                strIngredient4 = it.strIngredient4,
                strIngredient5 = it.strIngredient5,
                strIngredient6 = it.strIngredient6,
                strIngredient7 = it.strIngredient7,
                strIngredient8 = it.strIngredient8,
                strIngredient9 = it.strIngredient9,
                strIngredient10 = it.strIngredient10,
                strIngredient11 = it.strIngredient11,
                strIngredient12 = it.strIngredient12,
                strIngredient13 = it.strIngredient13,
                strIngredient14 = it.strIngredient14,
                strIngredient15 = it.strIngredient15,
                strIngredient16 = it.strIngredient16,
                strIngredient17 = it.strIngredient17,
                strIngredient18 = it.strIngredient18,
                strIngredient19 = it.strIngredient19,
                strIngredient20 = it.strIngredient20,
                strMeasure1 = it.strMeasure1,
                strMeasure2 = it.strMeasure2,
                strMeasure3 = it.strMeasure3,
                strMeasure4 = it.strMeasure4,
                strMeasure5 = it.strMeasure5,
                strMeasure6 = it.strMeasure6,
                strMeasure7 = it.strMeasure7,
                strMeasure8 = it.strMeasure8,
                strMeasure9 = it.strMeasure9,
                strMeasure10 = it.strMeasure10,
                strMeasure11 = it.strMeasure11,
                strMeasure12 = it.strMeasure12,
                strMeasure13 = it.strMeasure13,
                strMeasure14 = it.strMeasure14,
                strMeasure15 = it.strMeasure15,
                strMeasure16 = it.strMeasure16,
                strMeasure17 = it.strMeasure17,
                strMeasure18 = it.strMeasure18,
                strMeasure19 = it.strMeasure19,
                strMeasure20 = it.strMeasure20,
                strSource = it.strSource,
                strImageSource = it.strImageSource,
                strCreativeCommonsConfirmed = it.strCreativeCommonsConfirmed,
                dateModified = it.dateModified,
                isFavorite = false
            )
        )
    }
    return items
}

fun <T> T.toFavorite(): Favorite? {
    return when (this) {
        is BestFoodOfTheDay -> {
            val it = this as BestFoodOfTheDay
            Favorite(
                idMeal = it.idMeal,
                strMeal = it.strMeal,
                strDrinkAlternate = it.strDrinkAlternate,
                strCategory = it.strCategory,
                strArea = it.strArea,
                strInstructions = it.strInstructions,
                strMealThumb = it.strMealThumb,
                strTags = it.strTags,
                strYoutube = it.strYoutube,
                strIngredient1 = it.strIngredient1,
                strIngredient2 = it.strIngredient2,
                strIngredient3 = it.strIngredient3,
                strIngredient4 = it.strIngredient4,
                strIngredient5 = it.strIngredient5,
                strIngredient6 = it.strIngredient6,
                strIngredient7 = it.strIngredient7,
                strIngredient8 = it.strIngredient8,
                strIngredient9 = it.strIngredient9,
                strIngredient10 = it.strIngredient10,
                strIngredient11 = it.strIngredient11,
                strIngredient12 = it.strIngredient12,
                strIngredient13 = it.strIngredient13,
                strIngredient14 = it.strIngredient14,
                strIngredient15 = it.strIngredient15,
                strIngredient16 = it.strIngredient16,
                strIngredient17 = it.strIngredient17,
                strIngredient18 = it.strIngredient18,
                strIngredient19 = it.strIngredient19,
                strIngredient20 = it.strIngredient20,
                strMeasure1 = it.strMeasure1,
                strMeasure2 = it.strMeasure2,
                strMeasure3 = it.strMeasure3,
                strMeasure4 = it.strMeasure4,
                strMeasure5 = it.strMeasure5,
                strMeasure6 = it.strMeasure6,
                strMeasure7 = it.strMeasure7,
                strMeasure8 = it.strMeasure8,
                strMeasure9 = it.strMeasure9,
                strMeasure10 = it.strMeasure10,
                strMeasure11 = it.strMeasure11,
                strMeasure12 = it.strMeasure12,
                strMeasure13 = it.strMeasure13,
                strMeasure14 = it.strMeasure14,
                strMeasure15 = it.strMeasure15,
                strMeasure16 = it.strMeasure16,
                strMeasure17 = it.strMeasure17,
                strMeasure18 = it.strMeasure18,
                strMeasure19 = it.strMeasure19,
                strMeasure20 = it.strMeasure20,
                strSource = it.strSource,
                strImageSource = it.strImageSource,
                strCreativeCommonsConfirmed = it.strCreativeCommonsConfirmed,
                dateModified = it.dateModified
            )
        }

        is MostPopularFood -> {
            val it = this as MostPopularFood
            Favorite(
                idMeal = it.idMeal,
                strMeal = it.strMeal,
                strDrinkAlternate = it.strDrinkAlternate,
                strCategory = it.strCategory,
                strArea = it.strArea,
                strInstructions = it.strInstructions,
                strMealThumb = it.strMealThumb,
                strTags = it.strTags,
                strYoutube = it.strYoutube,
                strIngredient1 = it.strIngredient1,
                strIngredient2 = it.strIngredient2,
                strIngredient3 = it.strIngredient3,
                strIngredient4 = it.strIngredient4,
                strIngredient5 = it.strIngredient5,
                strIngredient6 = it.strIngredient6,
                strIngredient7 = it.strIngredient7,
                strIngredient8 = it.strIngredient8,
                strIngredient9 = it.strIngredient9,
                strIngredient10 = it.strIngredient10,
                strIngredient11 = it.strIngredient11,
                strIngredient12 = it.strIngredient12,
                strIngredient13 = it.strIngredient13,
                strIngredient14 = it.strIngredient14,
                strIngredient15 = it.strIngredient15,
                strIngredient16 = it.strIngredient16,
                strIngredient17 = it.strIngredient17,
                strIngredient18 = it.strIngredient18,
                strIngredient19 = it.strIngredient19,
                strIngredient20 = it.strIngredient20,
                strMeasure1 = it.strMeasure1,
                strMeasure2 = it.strMeasure2,
                strMeasure3 = it.strMeasure3,
                strMeasure4 = it.strMeasure4,
                strMeasure5 = it.strMeasure5,
                strMeasure6 = it.strMeasure6,
                strMeasure7 = it.strMeasure7,
                strMeasure8 = it.strMeasure8,
                strMeasure9 = it.strMeasure9,
                strMeasure10 = it.strMeasure10,
                strMeasure11 = it.strMeasure11,
                strMeasure12 = it.strMeasure12,
                strMeasure13 = it.strMeasure13,
                strMeasure14 = it.strMeasure14,
                strMeasure15 = it.strMeasure15,
                strMeasure16 = it.strMeasure16,
                strMeasure17 = it.strMeasure17,
                strMeasure18 = it.strMeasure18,
                strMeasure19 = it.strMeasure19,
                strMeasure20 = it.strMeasure20,
                strSource = it.strSource,
                strImageSource = it.strImageSource,
                strCreativeCommonsConfirmed = it.strCreativeCommonsConfirmed,
                dateModified = it.dateModified,
                isFromMostPopularFood = true
            )
        }

        is MealItemResponse -> {
            val it = this as MealItemResponse
            Favorite(
                idMeal = it.idMeal,
                strMeal = it.strMeal,
                strDrinkAlternate = it.strDrinkAlternate,
                strCategory = it.strCategory,
                strArea = it.strArea,
                strInstructions = it.strInstructions,
                strMealThumb = it.strMealThumb,
                strTags = it.strTags,
                strYoutube = it.strYoutube,
                strIngredient1 = it.strIngredient1,
                strIngredient2 = it.strIngredient2,
                strIngredient3 = it.strIngredient3,
                strIngredient4 = it.strIngredient4,
                strIngredient5 = it.strIngredient5,
                strIngredient6 = it.strIngredient6,
                strIngredient7 = it.strIngredient7,
                strIngredient8 = it.strIngredient8,
                strIngredient9 = it.strIngredient9,
                strIngredient10 = it.strIngredient10,
                strIngredient11 = it.strIngredient11,
                strIngredient12 = it.strIngredient12,
                strIngredient13 = it.strIngredient13,
                strIngredient14 = it.strIngredient14,
                strIngredient15 = it.strIngredient15,
                strIngredient16 = it.strIngredient16,
                strIngredient17 = it.strIngredient17,
                strIngredient18 = it.strIngredient18,
                strIngredient19 = it.strIngredient19,
                strIngredient20 = it.strIngredient20,
                strMeasure1 = it.strMeasure1,
                strMeasure2 = it.strMeasure2,
                strMeasure3 = it.strMeasure3,
                strMeasure4 = it.strMeasure4,
                strMeasure5 = it.strMeasure5,
                strMeasure6 = it.strMeasure6,
                strMeasure7 = it.strMeasure7,
                strMeasure8 = it.strMeasure8,
                strMeasure9 = it.strMeasure9,
                strMeasure10 = it.strMeasure10,
                strMeasure11 = it.strMeasure11,
                strMeasure12 = it.strMeasure12,
                strMeasure13 = it.strMeasure13,
                strMeasure14 = it.strMeasure14,
                strMeasure15 = it.strMeasure15,
                strMeasure16 = it.strMeasure16,
                strMeasure17 = it.strMeasure17,
                strMeasure18 = it.strMeasure18,
                strMeasure19 = it.strMeasure19,
                strMeasure20 = it.strMeasure20,
                strSource = it.strSource,
                strImageSource = it.strImageSource,
                strCreativeCommonsConfirmed = it.strCreativeCommonsConfirmed,
                dateModified = it.dateModified
            )
        }
        else -> null
    }
}

fun <T> T.toMealItemResponse(): MealItemResponse? {
    return when (this) {
        is BestFoodOfTheDay -> {
            val it = this as BestFoodOfTheDay
            MealItemResponse(
                idMeal = it.idMeal,
                strMeal = it.strMeal,
                strDrinkAlternate = it.strDrinkAlternate,
                strCategory = it.strCategory,
                strArea = it.strArea,
                strInstructions = it.strInstructions,
                strMealThumb = it.strMealThumb,
                strTags = it.strTags,
                strYoutube = it.strYoutube,
                strIngredient1 = it.strIngredient1,
                strIngredient2 = it.strIngredient2,
                strIngredient3 = it.strIngredient3,
                strIngredient4 = it.strIngredient4,
                strIngredient5 = it.strIngredient5,
                strIngredient6 = it.strIngredient6,
                strIngredient7 = it.strIngredient7,
                strIngredient8 = it.strIngredient8,
                strIngredient9 = it.strIngredient9,
                strIngredient10 = it.strIngredient10,
                strIngredient11 = it.strIngredient11,
                strIngredient12 = it.strIngredient12,
                strIngredient13 = it.strIngredient13,
                strIngredient14 = it.strIngredient14,
                strIngredient15 = it.strIngredient15,
                strIngredient16 = it.strIngredient16,
                strIngredient17 = it.strIngredient17,
                strIngredient18 = it.strIngredient18,
                strIngredient19 = it.strIngredient19,
                strIngredient20 = it.strIngredient20,
                strMeasure1 = it.strMeasure1,
                strMeasure2 = it.strMeasure2,
                strMeasure3 = it.strMeasure3,
                strMeasure4 = it.strMeasure4,
                strMeasure5 = it.strMeasure5,
                strMeasure6 = it.strMeasure6,
                strMeasure7 = it.strMeasure7,
                strMeasure8 = it.strMeasure8,
                strMeasure9 = it.strMeasure9,
                strMeasure10 = it.strMeasure10,
                strMeasure11 = it.strMeasure11,
                strMeasure12 = it.strMeasure12,
                strMeasure13 = it.strMeasure13,
                strMeasure14 = it.strMeasure14,
                strMeasure15 = it.strMeasure15,
                strMeasure16 = it.strMeasure16,
                strMeasure17 = it.strMeasure17,
                strMeasure18 = it.strMeasure18,
                strMeasure19 = it.strMeasure19,
                strMeasure20 = it.strMeasure20,
                strSource = it.strSource,
                strImageSource = it.strImageSource,
                strCreativeCommonsConfirmed = it.strCreativeCommonsConfirmed,
                dateModified = it.dateModified
            )
        }

        is MostPopularFood -> {
            val it = this as MostPopularFood
            MealItemResponse(
                idMeal = it.idMeal,
                strMeal = it.strMeal,
                strDrinkAlternate = it.strDrinkAlternate,
                strCategory = it.strCategory,
                strArea = it.strArea,
                strInstructions = it.strInstructions,
                strMealThumb = it.strMealThumb,
                strTags = it.strTags,
                strYoutube = it.strYoutube,
                strIngredient1 = it.strIngredient1,
                strIngredient2 = it.strIngredient2,
                strIngredient3 = it.strIngredient3,
                strIngredient4 = it.strIngredient4,
                strIngredient5 = it.strIngredient5,
                strIngredient6 = it.strIngredient6,
                strIngredient7 = it.strIngredient7,
                strIngredient8 = it.strIngredient8,
                strIngredient9 = it.strIngredient9,
                strIngredient10 = it.strIngredient10,
                strIngredient11 = it.strIngredient11,
                strIngredient12 = it.strIngredient12,
                strIngredient13 = it.strIngredient13,
                strIngredient14 = it.strIngredient14,
                strIngredient15 = it.strIngredient15,
                strIngredient16 = it.strIngredient16,
                strIngredient17 = it.strIngredient17,
                strIngredient18 = it.strIngredient18,
                strIngredient19 = it.strIngredient19,
                strIngredient20 = it.strIngredient20,
                strMeasure1 = it.strMeasure1,
                strMeasure2 = it.strMeasure2,
                strMeasure3 = it.strMeasure3,
                strMeasure4 = it.strMeasure4,
                strMeasure5 = it.strMeasure5,
                strMeasure6 = it.strMeasure6,
                strMeasure7 = it.strMeasure7,
                strMeasure8 = it.strMeasure8,
                strMeasure9 = it.strMeasure9,
                strMeasure10 = it.strMeasure10,
                strMeasure11 = it.strMeasure11,
                strMeasure12 = it.strMeasure12,
                strMeasure13 = it.strMeasure13,
                strMeasure14 = it.strMeasure14,
                strMeasure15 = it.strMeasure15,
                strMeasure16 = it.strMeasure16,
                strMeasure17 = it.strMeasure17,
                strMeasure18 = it.strMeasure18,
                strMeasure19 = it.strMeasure19,
                strMeasure20 = it.strMeasure20,
                strSource = it.strSource,
                strImageSource = it.strImageSource,
                strCreativeCommonsConfirmed = it.strCreativeCommonsConfirmed,
                dateModified = it.dateModified
            )
        }
        is AllMealItem -> {
            val it = this as AllMealItem
            MealItemResponse(
                idMeal = it.idMeal,
                strMeal = it.strMeal,
                strDrinkAlternate = it.strDrinkAlternate,
                strCategory = it.strCategory,
                strArea = it.strArea,
                strInstructions = it.strInstructions,
                strMealThumb = it.strMealThumb,
                strTags = it.strTags,
                strYoutube = it.strYoutube,
                strIngredient1 = it.strIngredient1,
                strIngredient2 = it.strIngredient2,
                strIngredient3 = it.strIngredient3,
                strIngredient4 = it.strIngredient4,
                strIngredient5 = it.strIngredient5,
                strIngredient6 = it.strIngredient6,
                strIngredient7 = it.strIngredient7,
                strIngredient8 = it.strIngredient8,
                strIngredient9 = it.strIngredient9,
                strIngredient10 = it.strIngredient10,
                strIngredient11 = it.strIngredient11,
                strIngredient12 = it.strIngredient12,
                strIngredient13 = it.strIngredient13,
                strIngredient14 = it.strIngredient14,
                strIngredient15 = it.strIngredient15,
                strIngredient16 = it.strIngredient16,
                strIngredient17 = it.strIngredient17,
                strIngredient18 = it.strIngredient18,
                strIngredient19 = it.strIngredient19,
                strIngredient20 = it.strIngredient20,
                strMeasure1 = it.strMeasure1,
                strMeasure2 = it.strMeasure2,
                strMeasure3 = it.strMeasure3,
                strMeasure4 = it.strMeasure4,
                strMeasure5 = it.strMeasure5,
                strMeasure6 = it.strMeasure6,
                strMeasure7 = it.strMeasure7,
                strMeasure8 = it.strMeasure8,
                strMeasure9 = it.strMeasure9,
                strMeasure10 = it.strMeasure10,
                strMeasure11 = it.strMeasure11,
                strMeasure12 = it.strMeasure12,
                strMeasure13 = it.strMeasure13,
                strMeasure14 = it.strMeasure14,
                strMeasure15 = it.strMeasure15,
                strMeasure16 = it.strMeasure16,
                strMeasure17 = it.strMeasure17,
                strMeasure18 = it.strMeasure18,
                strMeasure19 = it.strMeasure19,
                strMeasure20 = it.strMeasure20,
                strSource = it.strSource,
                strImageSource = it.strImageSource,
                strCreativeCommonsConfirmed = it.strCreativeCommonsConfirmed,
                dateModified = it.dateModified
            )
        }
        else -> null
    }
}

fun MealItemResponse.toAllMealItem(): AllMealItem {
    val it = this
    return AllMealItem(
        idMeal = it.idMeal,
        strMeal = it.strMeal,
        strDrinkAlternate = it.strDrinkAlternate,
        strCategory = it.strCategory,
        strArea = it.strArea,
        strInstructions = it.strInstructions,
        strMealThumb = it.strMealThumb,
        strTags = it.strTags,
        strYoutube = it.strYoutube,
        strIngredient1 = it.strIngredient1,
        strIngredient2 = it.strIngredient2,
        strIngredient3 = it.strIngredient3,
        strIngredient4 = it.strIngredient4,
        strIngredient5 = it.strIngredient5,
        strIngredient6 = it.strIngredient6,
        strIngredient7 = it.strIngredient7,
        strIngredient8 = it.strIngredient8,
        strIngredient9 = it.strIngredient9,
        strIngredient10 = it.strIngredient10,
        strIngredient11 = it.strIngredient11,
        strIngredient12 = it.strIngredient12,
        strIngredient13 = it.strIngredient13,
        strIngredient14 = it.strIngredient14,
        strIngredient15 = it.strIngredient15,
        strIngredient16 = it.strIngredient16,
        strIngredient17 = it.strIngredient17,
        strIngredient18 = it.strIngredient18,
        strIngredient19 = it.strIngredient19,
        strIngredient20 = it.strIngredient20,
        strMeasure1 = it.strMeasure1,
        strMeasure2 = it.strMeasure2,
        strMeasure3 = it.strMeasure3,
        strMeasure4 = it.strMeasure4,
        strMeasure5 = it.strMeasure5,
        strMeasure6 = it.strMeasure6,
        strMeasure7 = it.strMeasure7,
        strMeasure8 = it.strMeasure8,
        strMeasure9 = it.strMeasure9,
        strMeasure10 = it.strMeasure10,
        strMeasure11 = it.strMeasure11,
        strMeasure12 = it.strMeasure12,
        strMeasure13 = it.strMeasure13,
        strMeasure14 = it.strMeasure14,
        strMeasure15 = it.strMeasure15,
        strMeasure16 = it.strMeasure16,
        strMeasure17 = it.strMeasure17,
        strMeasure18 = it.strMeasure18,
        strMeasure19 = it.strMeasure19,
        strMeasure20 = it.strMeasure20,
        strSource = it.strSource,
        strImageSource = it.strImageSource,
        strCreativeCommonsConfirmed = it.strCreativeCommonsConfirmed,
        dateModified = it.dateModified
    )
}

fun <T> T.toFilterType(): List<FilterType>? {
    return when (this) {
        is MealCategoryFilterResponse -> {
            val items: MutableList<FilterType> = mutableListOf()
            val filterType = "Categories"
            this.meals?.forEach {
                items.add(
                    FilterType(
                        it.strCategory ?: "",
                        filterType
                    )
                )
            }
            items
        }
        is MealAreaFilterResponse -> {
            val items: MutableList<FilterType> = mutableListOf()
            val filterType = "Area"
            this.meals?.forEach {
                items.add(
                    FilterType(
                        it.strArea ?: "",
                        filterType
                    )
                )
            }
            items
        }
        is MealIngredientsFilterResponse -> {
            val items: MutableList<FilterType> = mutableListOf()
            val filterType = "Ingredients"
            this.meals?.forEach {
                items.add(
                    FilterType(
                        it.strIngredient ?: "",
                        filterType
                    )
                )
            }
            items
        }
        else -> null
    }
}