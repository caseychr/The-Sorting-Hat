package com.casey.thesortinghat.dto

data class HouseDTO(
    val id: String,
    val name: String, //view
    val houseColors: String, //view
    val founder: String, //view
    val animal: String, //view
    val element: String, //view
    val ghost: String, //view
    val commonRoom: String, //view
    val heads: List<Head>, //view
    val traits: List<Trait> //view
)

data class Head(
    val id: String,
    val firstName: String,
    val lastName: String
)

data class Trait(
    val name: String
)
