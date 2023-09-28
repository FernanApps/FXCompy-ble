package pe.fernan.apps.compyble.data.local.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pe.fernan.apps.compyble.data.Constants

@Entity(tableName = Constants.tableProduct)
data class ProductEntity(
    val title: String,
    val brand: String,
    val description: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    val discount: String,
    val price: String,
    val currency: String,
    @PrimaryKey
    val href: String

)