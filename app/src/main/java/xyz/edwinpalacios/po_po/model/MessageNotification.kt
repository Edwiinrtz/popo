package xyz.edwinpalacios.po_po.model

data class MessageNotification(
    val title:String,
    val message: String,
    val tokens: List<String>
)
