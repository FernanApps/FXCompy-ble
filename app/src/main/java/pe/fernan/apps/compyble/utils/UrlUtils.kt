package pe.fernan.apps.compyble.utils

import pe.fernan.apps.compyble.utils.UrlUtils.buildUrl
import pe.fernan.apps.compyble.utils.UrlUtils.regexExtractParametersUrlV2
import java.io.Serializable

object UrlUtils {

    /*
        Version 1
        Version 2
         */

    const val regexExtractParametersUrl = "(\\||&|)([^=]+)=([^&]+)"
    const val regexExtractParametersUrlV2 = "(\\||&|\\?)([^=]+)=(?:\\{?)([^}&]+)"

    fun findPathsInUrl(url: String): Map<String, String> {
        return findPathsInUrlBase(
            regexExtractParametersUrlV2,
            url
        )
    }
    private fun findPathsInUrlBase(regex: String, url: String): Map<String, String> {
        val headerMap = HashMap<String, String>()
        regex.toRegex().findAll(url).forEach {
            val (math, key, value) = it.destructured
            headerMap[key] = value
        }
        return headerMap
    }


    fun buildUrl(baseUrl: String, vararg paths: Path): String {
        val urlBuilder = StringBuilder(baseUrl)

        if (!baseUrl.endsWith("?")) {
            urlBuilder.append("?")
        }

        for ((index, path) in paths.withIndex()) {
            urlBuilder.append(path.key)
            urlBuilder.append("=")
            urlBuilder.append(path.value)

            if (index < paths.size - 1) {
                urlBuilder.append("&")
            }
        }

        return urlBuilder.toString()
    }

}


data class Path(
    public val key: String,
    public val value: String
) : Serializable {
    override fun toString(): String = "($key, $value)"
}

fun main() {
    val url = "https://compy.pe/galeria?pagesize=24&page=1&sort=offer&category=Celulares&brand=APPLE"

    println(url.replace(regexExtractParametersUrlV2.toRegex(), ""))

    println("Url $url")

    val baseUrl = "https://compy.pe/galeria"
    val paths = arrayOf(
        Path("pagesize", "24"),
        Path("page", "1"),
        Path("sort", "offer"),
        Path("category", "Celulares"),
        Path("brand", "APPLE")
    )

    val finalUrl = buildUrl(baseUrl, *paths)
    println("finalUrl : $finalUrl")

}