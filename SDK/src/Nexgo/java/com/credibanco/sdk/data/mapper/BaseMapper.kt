package com.credibanco.sdk.data.mapper

interface BaseMapper<E, D> {
    fun transformFrom(input: E): D
    fun transformTo(input: D): E
}