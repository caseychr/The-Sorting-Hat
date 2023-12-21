package com.casey.thesortinghat.repository

import arrow.core.Either
import com.casey.thesortinghat.dto.ElixirDTO

interface ElixirRepository {

    suspend fun getAllElixirs(): Either<Error, List<ElixirDTO>>
}