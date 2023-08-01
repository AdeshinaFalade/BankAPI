package com.shredder.bank.api.service

import com.shredder.bank.api.datasource.BankDataSource
import com.shredder.bank.api.model.Bank
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service //or @Component if you are not sure, but you want it to be available for dependency injection
class BankService(@Qualifier("mock") private val dataSource: BankDataSource) {

    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()

    fun getBank(accountNumber: String): Bank = dataSource.retrieveBank(accountNumber)

    fun addBank(bank: Bank): Bank = dataSource.createBank(bank)

    fun updateBank(bank: Bank): Bank = dataSource.updateBank(bank)

    fun deleteBank(accountNumber: String) = dataSource.deleteBank(accountNumber)


}