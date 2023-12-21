package com.casey.thesortinghat.api

import com.casey.thesortinghat.dto.CharacterDTO
import com.casey.thesortinghat.dto.SpellDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface CharactersApi {

    @GET("api/characters")
    suspend fun getCharacters(): List<CharacterDTO>

    @GET("api/character/{id}")
    suspend fun getCharacterById(@Path("id") id: String): CharacterDTO

    @GET("api/characters/house/{houseid}")
    suspend fun getCharactersInHouse(@Path("houseId") houseId: String): List<CharacterDTO>

    @GET("api/spells")
    suspend fun getSpells(): List<SpellDTO>
}
