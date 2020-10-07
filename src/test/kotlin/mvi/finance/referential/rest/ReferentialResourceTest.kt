package mvi.finance.referential.rest

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import mvi.finance.referential.data.InstrumentRepository
import mvi.finance.referential.domain.Instrument
import mvi.finance.referential.request.InstrumentDto
import mvi.finance.referential.service.InstrumentService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest
@AutoConfigureMockMvc
internal class ReferentialResourceTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var service: InstrumentService

    @BeforeAll
    fun setup() {
        println(">> Setup")
    }

    @Test
    fun `Get all instruments`() {
        val instrument1 = InstrumentDto("FR01145678", "Societé Particuliere")
        val instrument2 = InstrumentDto("GR21241245", "ZIEmens")

        every { service.getAll() } returns listOf(instrument1, instrument2)
        mockMvc.perform(get("/instrument/").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect { MockMvcResultMatchers.jsonPath("\$.[0].isin").value(instrument1.isin) }
                .andExpect { MockMvcResultMatchers.jsonPath("\$.[0].longName").value(instrument1.longName) }
                .andExpect { MockMvcResultMatchers.jsonPath("\$.[1].isin").value(instrument2.isin) }
                .andExpect { MockMvcResultMatchers.jsonPath("\$.[1].longName").value(instrument2.longName) }
    }

}