package com.shredder.bank.api.datasource.network.dto

import com.shredder.bank.api.model.Bank

data class BankList(
    val data: Collection<Bank>
)
