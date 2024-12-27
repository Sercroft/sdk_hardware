package com.credibanco.sdk.datasource

import com.credibanco.sdk.domain.dto.CertEmv
import com.credibanco.sdk.domain.dto.IdEmv

interface InitializationDataSource {

    suspend fun setIdEmvData(
        idEmvs: List<IdEmv>
    )

    suspend fun setCertEmvData(
        certEmvs: List<CertEmv>
    )
}