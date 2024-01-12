package xyz.edwinpalacios.po_po.model

data class Group(
    val id:String? = null,
    var members: MutableList<User>? = null,
    var totalKks: Int? = null
)
