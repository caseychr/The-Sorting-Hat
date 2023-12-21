package com.casey.thesortinghat.dto

import com.squareup.moshi.Json

data class CharacterDTO(
    val name: String, //view
    @Json(name = "alternate_names")
    val alternateNames: List<String>, //view
    val species: String,
    val gender: String, // enum
    val house: String, //view
    val dateOfBirth: String, //view
    val yearOfBirth: String,
    val isWizard: Boolean,
    val ancestry: String, //view
    val eyeColor: String,
    val hairColor: String,
    val wand: Wand, //view
    val patronus: String, //view
    val wasHogwartsStudent: Boolean,
    val wasHogwartsStaff: Boolean,
    val actor: String, //view
    val alternativeActors: List<String>, //view
    val alive: Boolean, //view
    val image: String //view
)

data class Wand(
    val wood: String,
    val core: String,
    val length: Double?
)
