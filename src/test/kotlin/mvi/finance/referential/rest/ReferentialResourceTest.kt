package mvi.finance.referential.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.Matcher
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import mvi.finance.referential.data.InstrumentRepository
import mvi.finance.referential.domain.Instrument
import mvi.finance.referential.request.InstrumentDto
import mvi.finance.referential.service.InstrumentService
import org.hamcrest.core.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.put
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
@AutoConfigureMockMvc
internal class ReferentialResourceTest(@Autowired val mockMvc: MockMvc,
                                       @Autowired val objectMapper: ObjectMapper) {

    @MockkBean
    private lateinit var service: InstrumentService

    @Test
    fun `Get all instruments`() {
        val instrument1 = InstrumentDto("FR01145678", "Societé Particuliere")
        val instrument2 = InstrumentDto("GR21241245", "ZIEmens")

        every { service.getAllInstruments() } returns listOf(instrument1, instrument2)
        mockMvc.perform(get("/instrument/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect { MockMvcResultMatchers.jsonPath("\$.[0].isin").value(instrument1.isin) }
                .andExpect { MockMvcResultMatchers.jsonPath("\$.[0].longName").value(instrument1.longName) }
                .andExpect { MockMvcResultMatchers.jsonPath("\$.[1].isin").value(instrument2.isin) }
                .andExpect { MockMvcResultMatchers.jsonPath("\$.[1].longName").value(instrument2.longName) }
    }

    @Test
    fun `Get instrument by id`() {
        val instrument1 = InstrumentDto("FR01145678", "Societé Particuliere")
        val instrument2 = InstrumentDto("GR21241245", "ZIEmens")

        every { service.getInstrument(1) } returns instrument1
        every { service.getInstrument(2) } returns instrument2
        mockMvc.perform(get("/instrument/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect { MockMvcResultMatchers.jsonPath("\$.isin").value(instrument1.isin) }
                .andExpect { MockMvcResultMatchers.jsonPath("\$.longName").value(instrument1.longName) }
        mockMvc.perform(get("/instrument/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect { MockMvcResultMatchers.jsonPath("\$.isin").value(instrument2.isin) }
                .andExpect { MockMvcResultMatchers.jsonPath("\$.longName").value(instrument2.longName) }
    }

    @Test
    fun `Delete instrument by Id`() {
        every { service.deleteInstrument(1) } just Runs
        mockMvc.perform(delete("/instrument/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }

    @Test
    fun `Test put valid instrument results in OK`() {
        val instrument1 = InstrumentDto("FR01145678", "Societé Particuliere")
        every { service.addInstrument(any()) } returns 1
        mockMvc.perform(put("/instrument/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(instrument1)))
                .andExpect(status().isOk)
                .andExpect { content().string("1") }
                .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `Test that an Instrument without isin will get a BAD_REQUEST`() {
        mockMvc.put("/instrument/"){
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsBytes(InstrumentDto("", "long name"))
        }.andExpect {
            status { isBadRequest }
            jsonPath("$.message", StringContains("Isin must be present"))
            jsonPath("$.origin", IsEqual("Referential Rest Service"))
        }
    }

    @Test
    fun `Test that an Instrument without longName will get a BAD_REQUEST`() {
        mockMvc.put("/instrument/") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsBytes(InstrumentDto("Fofo", ""))
        }.andExpect {
            status { isBadRequest }
            jsonPath("$.message", StringContains("LongName must be"))
            jsonPath("$.origin", IsEqual("Referential Rest Service"))
        }
    }

}