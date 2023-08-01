package com.shredder.bank.api.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Bank(
    //@JsonProperty("name")
    val accountNumber: String,

    //@JsonProperty("trust")
    val trust: Double,

    //@JsonProperty("id")
    val transactionFee: Int
)