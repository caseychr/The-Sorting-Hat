package com.casey.thesortinghat.api

import com.casey.thesortinghat.model.Character
import com.casey.thesortinghat.model.Spell
import retrofit2.http.GET
import retrofit2.http.Path

interface CharactersApi {

    @GET("api/characters")
    suspend fun getCharacters(): List<Character>

    @GET("api/character/{id}")
    suspend fun getCharacter(@Path("id") id: String): Character

    @GET("api/characters/house/{houseid}")
    suspend fun getCharactersInHouse(@Path("houseId") houseId: String): List<Character>

    @GET("api/spells")
    suspend fun getSpells(): List<Spell>
}
