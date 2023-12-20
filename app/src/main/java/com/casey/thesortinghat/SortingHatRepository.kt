package com.casey.thesortinghat

import arrow.core.Either
import com.casey.thesortinghat.api.CharactersApi
import com.casey.thesortinghat.api.WizardingWorldApi
import com.casey.thesortinghat.model.Character
import com.casey.thesortinghat.model.Spell
import java.lang.Error
import javax.inject.Inject

class SortingHatRepository @Inject constructor(
    private val charactersApi: CharactersApi,
    private val wizardingWorldApi: WizardingWorldApi
) {

    suspend fun getAllSpells(): Either<Error, List<Spell>> {
        return Either.catch {
            charactersApi.getSpells()
        }.mapLeft { Error() }
    }

    suspend fun getAllCharacters(): Either<Error, List<Character>> {
        return Either.catch {
            charactersApi.getCharacters()
        }.mapLeft {
            Error() }
    }

    suspend fun getCharacterById(id: String): Either<Error, Character> {
        return Either.catch {
            charactersApi.getCharacter(id)
        }.mapLeft { Error() }
    }

    suspend fun getCharacterInHouse(houseId: String): Either<Error, List<Character>> {
        return Either.catch {
            charactersApi.getCharactersInHouse(houseId)
        }.mapLeft { Error() }
    }
}