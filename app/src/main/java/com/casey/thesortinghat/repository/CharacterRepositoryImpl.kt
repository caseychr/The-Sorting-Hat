package com.casey.thesortinghat.repository

import arrow.core.Either
import com.casey.thesortinghat.api.CharactersApi
import com.casey.thesortinghat.api.WizardingWorldApi
import com.casey.thesortinghat.dto.CharacterDTO
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val charactersApi: CharactersApi,
    private val wizardingWorldApi: WizardingWorldApi
): CharacterRepository {

    override suspend fun getAllCharacters(): Either<Error, List<CharacterDTO>> {
        return Either.catch {
            charactersApi.getCharacters()
        }.mapLeft {
            java.lang.Error()
        }
    }

    override suspend fun getCharacterById(characterId: String): Either<Error, CharacterDTO> {
        return Either.catch {
            charactersApi.getCharacterById(characterId)
        }.mapLeft { java.lang.Error() }
    }

    override suspend fun getCharacterByName(characterName: String): Either<Error, CharacterDTO> {
        // TODO update to find character By name from cache
        return Either.catch {
            charactersApi.getCharacterById(characterName)//doesn't work now
        }.mapLeft { java.lang.Error() }
    }
}