package com.casey.thesortinghat.dto

data class ElixirDTO(
    val name: String,
    val effect: String,
    val sideEffects: String,
    val characteristics: String,
    val time: String,
    val difficulty: String,
    val ingredients: List<Ingredient>,
    val inventors: List<Inventor>,
    val manufacturer: String
)

data class Inventor(
    val firstName: String,
    val lastName: String
)

data class Ingredient(val name: String) // maybe value class
