package com.casey.thesortinghat.api

import com.casey.thesortinghat.model.Elixir
import com.casey.thesortinghat.model.House
import com.casey.thesortinghat.model.Ingredient
import com.casey.thesortinghat.model.WWSpell
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface WizardingWorldApi {

    @GET("Elixirs")
    suspend fun getAllElixirs(): List<Elixir>

    @GET("Elixirs/{id}")
    suspend fun getElixirById(@Path("id") id: String): Elixir

    @GET("Houses")
    suspend fun getAllHouses(): List<House>

    @GET("Houses/{id}")
    suspend fun getHouseById(@Path("id") id: String): House

    @GET("Ingredients")
    suspend fun getAllIngredients(): List<Ingredient>

    @GET("Spells")
    suspend fun getAllSpells(): List<WWSpell>

    @GET("Spells/{id}")
    suspend fun getSpellById(@Path("id") id: String)

    @GET("Wizards")
    suspend fun getAllWizards()

    @GET("Wizards/{id}")
    suspend fun getWizardById(@Path("id") id: String)

    @POST
    suspend fun createFeedback()
}