package com.casey.thesortinghat.dto

data class WWSpellDTO(
    val id: String,
    val name: String,
    val incantation: String,
    val effect: String,
    val canBeVerbal: Boolean,
    val type: String,
    val light: String,
    val creator: String
)
