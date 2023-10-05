package pe.fernan.apps.compyble.utils

import pe.fernan.apps.compyble.di.baseUrl

class Ext {
}

fun String.fixImage(): String {
    if(this.isEmpty()) return ""
    return if(!this.startsWith("http")){
        baseUrl + this
    } else {
        this
    }
}