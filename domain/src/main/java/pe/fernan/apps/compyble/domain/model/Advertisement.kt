package pe.fernan.apps.compyble.domain.model

class Advertisement(
    val imageUrl: String,
    val href: String
){
    override fun toString(): String {
        return "Advertisement(imageUrl='$imageUrl', href='$href')"
    }
}
