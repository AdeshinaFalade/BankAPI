package com.shredder.bank.api.datasource.network

import com.shredder.bank.api.datasource.BankDataSource
import com.shredder.bank.api.datasource.network.dto.BankList
import com.shredder.bank.api.model.Bank
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import java.io.IOException

@Repository("network")
class NetworkDataSource(
    @Autowired private val restTemplate: RestTemplate
): BankDataSource {
    override fun retrieveBanks(): Collection<Bank> {

//        val response = restTemplate.getForEntity<BankList>("https://jamaican-banks.p.rapidapi.com/banks")
//        return response.body?.data
//            ?: throw IOException("Could not fetch banks from network")

        val headers = org.springframework.http.HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.set("X-RapidAPI-Key", "e972a92012msh54c987ab46d9c03p154280jsn08f4400fb39c")
        headers.set("X-RapidAPI-Host", "jamaican-banks.p.rapidapi.com")
        val requestEntity: HttpEntity<String> = HttpEntity(null, headers)

        val response = restTemplate.exchange(
            "https://jamaican-banks.p.rapidapi.com/banks",
            HttpMethod.GET,
            requestEntity,
            BankList::class.java
        )
        return response.body?.data
            ?: throw IOException("Could not fetch banks from network")
    }

    override fun retrieveBank(accountNumber: String): Bank {
        TODO("Not yet implemented")
    }

    override fun createBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun updateBank(bank: Bank): Bank {
        TODO("Not yet implemented")
    }

    override fun deleteBank(accountNumber: String) {
        TODO("Not yet implemented")
    }
}