package mvi.finance.referential

import mvi.finance.referential.data.InstrumentRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ReferentialApplicationTests(@Autowired private var instrumentRepository: InstrumentRepository) {

    @BeforeAll
    fun setup() {
        println(">> Setup")
    }

    @Test
    fun contextLoads() {
        assertThat(instrumentRepository).isNotNull
    }

}
