package com.casey.thesortinghat.repository

import arrow.core.Either
import com.casey.thesortinghat.api.WizardingWorldApi
import com.casey.thesortinghat.dto.ElixirDTO
import javax.inject.Inject

class ElixirRepositoryImpl @Inject constructor(
    private val wizardingWorldApi: WizardingWorldApi
): ElixirRepository {

    override suspend fun getAllElixirs(): Either<Error, List<ElixirDTO>> {
        return Either.catch {
            wizardingWorldApi.getAllElixirs()
        }.mapLeft { java.lang.Error() }
    }
}