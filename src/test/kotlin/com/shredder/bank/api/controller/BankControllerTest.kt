package com.shredder.bank.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.shredder.bank.api.model.Bank
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.*


@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {

    val baseUrl = "/api/banks"

    @Nested
    @DisplayName("DELETE /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteExistingBank{

        @Test
        @DirtiesContext
        fun `should delete bank with given accountNumber`(){
            //given
            val accountNumber = "3422332323"

            //when/then
            mockMvc.delete("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNoContent() } }

            mockMvc.get("$baseUrl/$accountNumber")
                .andExpect { status { isNotFound() } }
        }

        @Test
        fun `should return NOT FOUND if bank with given account number doesn't exist`(){
            //given
            val accountNumber = "does_not_exist"

            //when/then
            mockMvc.delete("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }


        }
    }


    @Nested
    @DisplayName("GET /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks{
        @Test
        fun `should return all banks`(){
            //when/then
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber"){value("0123456789")}
                }

        }
    }

    @Nested
    @DisplayName("GET /api/bank/{accountNumber})")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank {
        @Test
        fun `should return the bank with the given account number`() {
            //given
            val accountNumber = "0123456789"

            //when/then
            mockMvc
                .get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust") { value("50.32") }
                    jsonPath("$.transactionFee") { value("10") }
                }
        }

        @Test
        fun `should return Not Found if account number does not exist`(){
            //given
            val accountNumber = "does_not_exist"

            //when/then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }

        }
    }

    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewBank{

        @Test
        fun `should add the new bank`(){
            //given
            val newBank = Bank("1234",31.415,2)

            //when
            val performPost = mockMvc.post(baseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }

            //then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(newBank))
                    }
                }

        }

        @Test
        fun `should return BAD REQUEST if bank with given account number already exists`(){
            //given
            val invalidBank = Bank("3422332323", 45.4,3)

            //when
            val performPost = mockMvc.post(baseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            //then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }


        }
    }

    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchExistingBank{

        @Test
        fun `should update an existing bank`(){
            //given
            val updatedBank = Bank("3422332323", 450.4,3)

            //when
            val performPatchRequest = mockMvc.patch(baseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank)
            }

            //then
            performPatchRequest
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(updatedBank))
                    }
                }

            mockMvc.get("$baseUrl/${updatedBank.accountNumber}")
                .andExpect {
                    content { json(objectMapper.writeValueAsString(updatedBank)) }
                }
        }

        @Test
        fun `should return NOT FOUND if bank with given account number doesn't exist`(){
            //given
            val invalidBank = Bank("does_not_exist",8.0,2)

            //when
            val performPatchRequest = mockMvc.patch(baseUrl){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            //then
            performPatchRequest
                .andDo { print() }
                .andExpect {
                    status { isNotFound()}
                }


        }
    }

    
}