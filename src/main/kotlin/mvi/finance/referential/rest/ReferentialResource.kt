package mvi.finance.referential.rest

import mvi.finance.referential.data.InstrumentRepository
import mvi.finance.referential.request.InstrumentDto
import mvi.finance.referential.service.InstrumentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotNull

@RestController
class ReferentialResource (@Autowired private val instrumentService: InstrumentService){

    @GetMapping("/instrument/")
    @ResponseStatus(HttpStatus.OK)
    fun getAllInstruments() : List<InstrumentDto> = instrumentService.getAllInstruments()

    @GetMapping("/instrument/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getInstrument(@PathVariable @NotNull id: Long) : InstrumentDto = instrumentService.getInstrument(id)

    @PutMapping("/instrument")
    @ResponseStatus(HttpStatus.OK)
    fun putInstrument(@Valid @RequestBody instrumentDto: InstrumentDto): Long = instrumentService.addInstrument(instrumentDto)

    @DeleteMapping("/instrument/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteInstrument(@PathVariable @NotNull id: Long) : Unit = instrumentService.deleteInstrument(id)

}