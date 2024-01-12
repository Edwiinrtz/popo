package xyz.edwinpalacios.po_po.model

import java.time.LocalDate

data class User(
    val name : String? = null,
    var kks: MutableMap<String,Int>? = null,
    var profileUrl: String? = null,
    var totalKks: Int? = null
)
