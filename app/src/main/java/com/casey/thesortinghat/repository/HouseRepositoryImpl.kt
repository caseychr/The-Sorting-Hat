package com.casey.thesortinghat.repository

import arrow.core.Either
import com.casey.thesortinghat.api.WizardingWorldApi
import com.casey.thesortinghat.dto.HouseDTO
import javax.inject.Inject

class HouseRepositoryImpl @Inject constructor(
    private val wizardingWorldApi: WizardingWorldApi
): HouseRepository {

    override suspend fun getAllHouses(): Either<Error, List<HouseDTO>> {
        return Either.catch {
            wizardingWorldApi.getAllHouses()
        }.mapLeft { java.lang.Error() }
    }
}