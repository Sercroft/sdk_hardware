package com.credibanco.sdk.util

object TagsEmvUtil {

    const val EMG_TAG_57 = "57"
    const val EMG_TAG_CRYPTOGRAM = "9F26"
    const val EMG_TAG_TSI = "9B"
    const val EMG_TAG_TVR = "95"
    const val EMG_TAG_DF_NAME = "84"
    const val EMG_TAG_82 = "82"
    const val EMG_TAG_CARD_HOLDER_NAME = "5F20"
    const val EMG_TAG_CARD_COUNTRY_CODE = "5F28"
    const val EMG_TAG_5F2A = "5F2A"
    const val EMG_TAG_CVM_RESULT = "9F34"
    const val EMV_TAG_9F5A = "9F5A"
    const val EMV_TAG_9F1E = "9F1E"



    fun tagsEmv(): ArrayList<String> {
        val emvTags = ArrayList<String>()
        emvTags.add(EMG_TAG_57)
        emvTags.add("9F02")
        emvTags.add("82")
        emvTags.add("8E")
        emvTags.add("9F36")
        emvTags.add(EMG_TAG_CRYPTOGRAM)
        emvTags.add("9F27")
        emvTags.add(EMG_TAG_CVM_RESULT)
        emvTags.add("9F10")
        emvTags.add("9F33")
        emvTags.add("9F35")
        emvTags.add(EMG_TAG_TVR)
        emvTags.add(EMG_TAG_TSI)
        emvTags.add("9F21")
        emvTags.add("9F37")
        emvTags.add("9F03")
        emvTags.add("5F25")
        emvTags.add("5F24")
        emvTags.add("5F34")
        emvTags.add("9F39")
        emvTags.add("9F1A")
        emvTags.add(EMG_TAG_5F2A)
        emvTags.add("9A")
        emvTags.add("9C")
        emvTags.add("9F07")
        emvTags.add(EMV_TAG_9F1E)
        emvTags.add(EMG_TAG_CARD_COUNTRY_CODE)
        emvTags.add(EMG_TAG_DF_NAME)
        emvTags.add("9F08")
        emvTags.add(EMV_TAG_9F5A)

        return emvTags
    }
}