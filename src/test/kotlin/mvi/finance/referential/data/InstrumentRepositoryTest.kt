package mvi.finance.referential.data

import liquibase.pro.packaged.i
import mvi.finance.referential.domain.Instrument
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.util.function.Predicate

@DataJpaTest
class InstrumentRepositoryTest @Autowired constructor(val entityManager: TestEntityManager,
                                                      val repository: InstrumentRepository) {

    @Test
    fun `Test findByIsin of repository`() {
        val i1 = Instrument("foo", "bar_foo")
        val i2 = Instrument("fifi", "toto_fifi")
        entityManager.persist(i1)
        entityManager.persist(i2)

        val actualInstrument = repository.findByIsin("foo")
        assertThat(actualInstrument).isEqualTo(i1)
    }

    @Test
    fun `Test findByAll order by id of repository`() {
        val i1 = Instrument("foo", "bar_foo")
        val i2 = Instrument("fifi", "toto_fifi")
        entityManager.persist(i1)
        entityManager.persist(i2)

        val foundCollection = repository.findAllByOrderByIdDesc()
        assertThat(foundCollection).isNotEmpty
        assertThat(foundCollection.size).isEqualTo(2)
        assertThat(foundCollection.any { it == i1 })
        assertThat(foundCollection.any { it == i2 })
    }
}