package com.casey.thesortinghat.repository

import arrow.core.Either
import com.casey.thesortinghat.dto.WWSpellDTO

interface SpellsRepository {

    suspend fun getAllSpells(): Either<Error, List<WWSpellDTO>>
}