package com.casey.thesortinghat.repository

import arrow.core.Either
import com.casey.thesortinghat.api.WizardingWorldApi
import com.casey.thesortinghat.dto.WWSpellDTO
import javax.inject.Inject

class SpellsRepositoryImpl @Inject constructor(
    private val wizardingWorldApi: WizardingWorldApi
): SpellsRepository {

    override suspend fun getAllSpells(): Either<Error, List<WWSpellDTO>> {
        return Either.catch {
            wizardingWorldApi.getAllSpells()
        }.mapLeft { java.lang.Error() }
    }
}