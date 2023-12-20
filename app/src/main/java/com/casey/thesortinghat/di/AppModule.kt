package com.casey.thesortinghat.di

import com.casey.thesortinghat.SortingHatRepository
import com.casey.thesortinghat.CharacterViewModel
import com.casey.thesortinghat.api.CharactersApi
import com.casey.thesortinghat.api.WizardingWorldApi
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
    ): SortingHatRepository {
        return SortingHatRepository(charactersApi, wizardingWorldApi)
    }

    @Provides
    fun provideViewModel(repository: SortingHatRepository): CharacterViewModel {
        return CharacterViewModel(repository)
    }
}