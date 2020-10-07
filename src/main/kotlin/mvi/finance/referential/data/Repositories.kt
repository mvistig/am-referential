package mvi.finance.referential.data

import mvi.finance.referential.domain.Instrument
import org.springframework.data.jpa.repository.JpaRepository

interface InstrumentRepository : JpaRepository<Instrument, Long>{
    fun findByIsin(isin : String)
}