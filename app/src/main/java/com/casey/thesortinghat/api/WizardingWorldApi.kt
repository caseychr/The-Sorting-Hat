package com.casey.thesortinghat.api

import com.casey.thesortinghat.dto.ElixirDTO
import com.casey.thesortinghat.dto.HouseDTO
import com.casey.thesortinghat.dto.Ingredient
import com.casey.thesortinghat.dto.WWSpellDTO
import com.casey.thesortinghat.dto.WizardDTO
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WizardingWorldApi {

    @GET("Elixirs")
    suspend fun getAllElixirs(): List<ElixirDTO>

    @GET("Elixirs/{id}")
    suspend fun getElixirById(@Path("id") id: String): ElixirDTO

    @GET("Houses")
    suspend fun getAllHouses(): List<HouseDTO>

    @GET("Houses/{id}")
    suspend fun getHouseById(@Path("id") id: String): HouseDTO

    @GET("Ingredients")
    suspend fun getAllIngredients(): List<Ingredient>

    @GET("Spells")
    suspend fun getAllSpells(): List<WWSpellDTO>

    @GET("Spells/{id}")
    suspend fun getSpellById(@Path("id") id: String)

    @GET("Wizards")
    suspend fun getAllWizards(): List<WizardDTO>

    @GET("Wizards/{id}")
    suspend fun getWizardById(@Path("id") id: String)

    @POST
    suspend fun createFeedback()
}