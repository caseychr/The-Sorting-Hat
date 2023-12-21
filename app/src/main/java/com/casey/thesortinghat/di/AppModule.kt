package com.casey.thesortinghat.di

import com.casey.thesortinghat.repository.CharacterRepository
import com.casey.thesortinghat.repository.CharacterRepositoryImpl
import com.casey.thesortinghat.viewmodel.CharacterViewModel
import com.casey.thesortinghat.repository.HouseRepository
import com.casey.thesortinghat.repository.HouseRepositoryImpl
import com.casey.thesortinghat.repository.SpellsRepository
import com.casey.thesortinghat.repository.SpellsRepositoryImpl
import com.casey.thesortinghat.api.CharactersApi
import com.casey.thesortinghat.api.WizardingWorldApi
import com.casey.thesortinghat.repository.ElixirRepository
import com.casey.thesortinghat.repository.ElixirRepositoryImpl
import com.casey.thesortinghat.repository.WizardRepository
import com.casey.thesortinghat.repository.WizardRepositoryImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class AppModule {

    private val CHARACTER_BASE_URL = "https://hp-api.onrender.com/"

    private val WIZARDING_WORLD_BASE_URL = "https://wizard-world-api.herokuapp.com/"

    @Provides
    fun provideOkHttp(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    fun provideCharacterRetrofit(
        okHttpClient: OkHttpClient
    ): CharactersApi {
        return Retrofit.Builder()
            .baseUrl(CHARACTER_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build().create(CharactersApi::class.java)
    }

    @Provides
    fun provideWizardingWorldRetrofit(
        okHttpClient: OkHttpClient
    ): WizardingWorldApi {
        return Retrofit.Builder()
            .baseUrl(WIZARDING_WORLD_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build().create(WizardingWorldApi::class.java)
    }

    @Provides
    fun provideCharacterRepository(
        charactersApi: CharactersApi,
        wizardingWorldApi: WizardingWorldApi
    ): CharacterRepository {
        return CharacterRepositoryImpl(charactersApi, wizardingWorldApi)
    }

    @Provides
    fun provideSpellsRepository(
        wizardingWorldApi: WizardingWorldApi
    ): SpellsRepository {
        return SpellsRepositoryImpl(wizardingWorldApi)
    }

    @Provides
    fun provideHouseRepository(
        wizardingWorldApi: WizardingWorldApi
    ): HouseRepository {
        return HouseRepositoryImpl(wizardingWorldApi)
    }

    @Provides
    fun provideElixirRepository(
        wizardingWorldApi: WizardingWorldApi
    ): ElixirRepository {
        return ElixirRepositoryImpl(wizardingWorldApi)
    }

    @Provides
    fun provideWizardRepository(
        wizardingWorldApi: WizardingWorldApi
    ): WizardRepository {
        return WizardRepositoryImpl(wizardingWorldApi)
    }

    @Provides
    fun provideCharacterViewModel(repository: CharacterRepository): CharacterViewModel {
        return CharacterViewModel(repository)
    }
}