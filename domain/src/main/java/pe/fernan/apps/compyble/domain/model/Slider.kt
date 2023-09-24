package pe.fernan.apps.compyble.domain.model

data class Slider(
    val title: String,
    val description: String,
    val image: String,
    val href: String = "",
    val buttonColor: String = "",
    val backgroundColor: String = ""
){
    override fun toString(): String {
        return "Slider(title='$title', description='$description', image='$image', href='$href', buttonColor='$buttonColor', backgroundColor='$backgroundColor')"
    }
}