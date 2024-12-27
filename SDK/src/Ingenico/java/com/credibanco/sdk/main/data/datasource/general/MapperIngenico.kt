package com.credibanco.sdk.main.data.datasource.general

import com.credibanco.sdk.domain.dto.CandidateAppInfo
import com.usdk.apiservice.aidl.emv.CandidateAID

object MapperIngenico {
    fun candidateAppInfoIngenicoToCandidateAppInfo(candidateAppInfoEntity: CandidateAID): CandidateAppInfo {
        return CandidateAppInfo(
            candidateAppInfoEntity.aid,
            candidateAppInfoEntity.appLabel,
            candidateAppInfoEntity.apn,
            candidateAppInfoEntity.apid,
            candidateAppInfoEntity.langPref,
            candidateAppInfoEntity.issCTIndex,
        )
    }

    fun candidateAppInfoEntityToListCandidateAppInfoIngenico(list: List<CandidateAID>): List<CandidateAppInfo>{
        val candidateAppInfoList = list.map {
            candidateAppInfoIngenicoToCandidateAppInfo(it)
        }

        return candidateAppInfoList
    }

}