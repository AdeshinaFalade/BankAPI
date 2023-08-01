package com.shredder.bank.api.datasource.mock

import com.shredder.bank.api.datasource.BankDataSource
import com.shredder.bank.api.model.Bank
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository("mock")
class MockBankDataSource: BankDataSource {
    val banks = mutableListOf(
        Bank("0123456789",50.32,10),
        Bank("3422332323",17.32,0),
        Bank("2343342224",0.0,100),
    )

    override fun retrieveBanks(): Collection<Bank> = banks

    override fun retrieveBank(accountNumber: String): Bank =
        banks.firstOrNull { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")

    override fun createBank(bank: Bank): Bank {
        if(banks.any {it.accountNumber == bank.accountNumber }) {
            throw IllegalArgumentException("Bank with account number ${bank.accountNumber} already exists.")
        }

        banks.add(bank)
        return bank
    }

    override fun updateBank(bank: Bank): Bank {
        val currentBank = retrieveBank(bank.accountNumber)

        banks.remove(currentBank)
        banks.add(bank)

        return bank
    }

    override fun deleteBank(accountNumber: String) {
        val currentBank = retrieveBank(accountNumber)

        banks.remove(currentBank)
    }

}