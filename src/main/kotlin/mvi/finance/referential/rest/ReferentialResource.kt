package mvi.finance.referential.rest

import mvi.finance.referential.data.InstrumentRepository
import mvi.finance.referential.request.InstrumentDto
import mvi.finance.referential.service.InstrumentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class ReferentialResource (@Autowired private val instrumentService: InstrumentService){

    @GetMapping("/instrument/")
    @ResponseStatus(HttpStatus.OK)
    fun getAllInstruments() : List<InstrumentDto> = instrumentService.getAll()

    @PutMapping("/instrument")
    @ResponseStatus(HttpStatus.OK)
    fun putInstrument(instrumentDto: InstrumentDto): Long = instrumentService.addInstrument(instrumentDto)

}