package com.shredder.bank.api.datasource.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MockBankDataSourceTest{
    private val mockBankDataSource: MockBankDataSource = MockBankDataSource()

    @Test
    fun `should provide a collection of banks`() {
        //when
        val banks = mockBankDataSource.retrieveBanks()

        //then
        assertThat(banks.size).isGreaterThanOrEqualTo(3)
        //check for duplicate account number
        assertEquals(banks.size, banks.distinctBy { it.accountNumber }.size)

    }

    @Test
    fun `should provide some mock data`(){
        //when
        val banks = mockBankDataSource.retrieveBanks()

        //then
        assertThat(banks).allMatch{ it.accountNumber.isNotBlank() }
        assertThat(banks).anyMatch{ it.trust != 0.0 }
        assertThat(banks).anyMatch{ it.transactionFee != 0 }

    }
}