package com.casey.thesortinghat.repository

import arrow.core.Either
import com.casey.thesortinghat.dto.HouseDTO

interface HouseRepository {
    suspend fun getAllHouses(): Either<Error, List<HouseDTO>>
}