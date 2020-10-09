package mvi.finance.referential.request

import javax.validation.constraints.NotEmpty

class InstrumentDto(@field:NotEmpty(message = "Isin must be present") val isin: String,
                    @field:NotEmpty(message = "LongName must be present") val longName: String)