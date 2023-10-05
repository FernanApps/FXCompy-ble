package pe.fernan.apps.compyble.domain.model

class Popup(
    val href: String,
    val imageUrl: String
){
    override fun toString(): String {
        return "Popup(href='$href', imageUrl='$imageUrl')"
    }
}