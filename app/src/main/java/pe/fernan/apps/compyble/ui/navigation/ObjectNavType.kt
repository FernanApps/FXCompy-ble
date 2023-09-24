package pe.fernan.apps.compyble.ui.navigation

import android.content.Intent
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import pe.fernan.apps.compyble.domain.model.Product
import java.io.Serializable

class ObjectNavType<T>(private val clazz: Class<T>) : NavType<T>(isNullableAllowed = false) {

    private val gson = Gson()

    override fun get(bundle: Bundle, key: String): T? =
        bundle.getString(key)?.let { parseValue(it) }

    override fun parseValue(value: String): T {
// Fixing Parse Value Json """{{"brand":"LG","currency":"PEN"}}"""
//        var finalValue = value
//        val regexFinal = """^(\{\{)|(\}\})$"""
//        val regexFinalJson = """^(\{)|(\})$"""
//        if(Regex(regexFinal).containsMatchIn(finalValue)){
//            finalValue = finalValue.replace(regexFinalJson.toRegex(), "")
//        }
        return gson.fromJson(value, clazz)
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, gson.toJson(value))
    }
}




inline fun <reified T : Serializable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}

inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
}