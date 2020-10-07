package mvi.finance.referential.service

import liquibase.pro.packaged.i
import mvi.finance.referential.data.InstrumentRepository
import mvi.finance.referential.domain.Instrument
import mvi.finance.referential.request.InstrumentDto
import mvi.finance.referential.util.ReferentialRuntimeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
class InstrumentService(@Autowired val repository: InstrumentRepository) {

    fun addInstrument(instrumentDto: InstrumentDto): Long {
        val entity = Instrument(instrumentDto.isin, instrumentDto.longName)
        val instrument = repository.save(entity)
        return instrument.id ?: throw ReferentialRuntimeException("Could not insert instrument ${entity.isin}",
                                                                  "Persistence",
                                                                  "INSERT")
    }

    fun getAll(): List<InstrumentDto> {
        val allInstruments = repository.findAll()
        return allInstruments.stream().map { t -> InstrumentDto(t.isin, t.longName) }.collect(Collectors.toList())
    }
}