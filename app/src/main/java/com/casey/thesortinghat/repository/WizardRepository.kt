package com.casey.thesortinghat.repository

import arrow.core.Either
import com.casey.thesortinghat.dto.WizardDTO

interface WizardRepository {

    suspend fun getAllWizards(): Either<Error, List<WizardDTO>>
}