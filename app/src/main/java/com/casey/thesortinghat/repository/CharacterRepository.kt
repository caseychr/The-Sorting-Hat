package com.casey.thesortinghat.repository

import arrow.core.Either
import com.casey.thesortinghat.dto.CharacterDTO

interface CharacterRepository {

    suspend fun getAllCharacters(): Either<Error, List<CharacterDTO>>

    suspend fun getCharacterById(characterId: String): Either<Error, CharacterDTO>

    suspend fun getCharacterByName(characterName: String): Either<Error, CharacterDTO>
}