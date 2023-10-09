package pe.fernan.apps.compyble.data.local.db

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import pe.fernan.apps.compyble.data.Constants
import pe.fernan.apps.compyble.domain.model.Product

@Entity(tableName = Constants.tableProduct)
data class ProductEntity(
    val title: String,
    val brand: String,
    val description: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    val discount: String,
    val price: String,
    val currency: String,
    @PrimaryKey val href: String
)

fun ProductEntity.toDomain() = Product(
    title = title,
    brand = brand,
    description = description,
    imageUrl = imageUrl,
    discount = discount,
    price = price,
    currency = currency,
    href = Uri.decode(href)
)


fun Product.toEntity() = ProductEntity(
    title = title,
    brand = brand,
    description = description,
    imageUrl = imageUrl,
    discount = discount,
    price = price,
    currency = currency,
    href = Uri.encode(href)
)
