package mvi.finance.referential

import mvi.finance.referential.data.InstrumentRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
                properties = ["spring.main.web-application-type=none",
                    "spring.jpa.hibernate.ddl-auto=none",
                    "spring.datasource.url=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1",
                    "spring.datasource.username=sa",
                    "spring.datasource.password=sa"])
class ReferentialApplicationTests(@Autowired private var instrumentRepository: InstrumentRepository) {

    @Test
    fun `Test context is loaded`() {
        assertThat(instrumentRepository).isNotNull
    }

}
