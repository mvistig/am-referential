package mvi.finance.referential.domain

import javax.persistence.*

@Entity
@SequenceGenerator(name = "SEQ_INSTRUMENT_ID", allocationSize = 1)
class Instrument(var isin: String,
                 var longName: String,
                 @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
                                     generator = "SEQ_INSTRUMENT_ID") var id: Long? = null) {

}