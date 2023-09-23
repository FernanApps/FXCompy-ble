package pe.fernan.apps.compyble.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface CompyApi {
    @GET("/")
    suspend fun getHome(): Response<String>
}