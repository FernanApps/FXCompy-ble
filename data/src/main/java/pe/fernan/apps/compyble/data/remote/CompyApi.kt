package pe.fernan.apps.compyble.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface CompyApi {
    @GET("/")
    suspend fun getHome(): Response<String>

    // https://compy.pe/galeria?pagesize=1&page=1&sort=offer&category=Computadoras&subcategory=Consolas
    @GET("galeria")
    suspend fun getProducts(
        @Query("pagesize") pageSize: Int = 24,
        @Query("page") page: Int = 1,
        @Query("sort") sort: String = "offer",
        @Query("category") category: String,
        @Query("subcategory") subcategory: String
    ): Response<String>

    @GET("galeria")
    suspend fun getProducts(@QueryMap queries: Map<String, String>): Response<String>


    @GET("{path}")
    suspend fun getDetail(@Path("path", encoded = true) path: String): Response<String>


}



