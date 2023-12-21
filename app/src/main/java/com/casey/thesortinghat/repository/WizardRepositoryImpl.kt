package com.casey.thesortinghat.repository

import arrow.core.Either
import com.casey.thesortinghat.api.WizardingWorldApi
import com.casey.thesortinghat.dto.WizardDTO
import javax.inject.Inject

class WizardRepositoryImpl @Inject constructor(
    private val wizardingWorldApi: WizardingWorldApi
): WizardRepository {

    override suspend fun getAllWizards(): Either<Error, List<WizardDTO>> {
        return Either.catch {
            wizardingWorldApi.getAllWizards()
        }.mapLeft { java.lang.Error() }
    }
}