package pe.fernan.apps.compyble.data.local.db

import android.content.ClipData
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pe.fernan.apps.compyble.data.TestDispatcherRule


@RunWith(AndroidJUnit4::class)
@SmallTest
class ProductFavoriteDaoTest {

    @get: Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var itemDb: CompyDb
    private lateinit var itemDao: ProductFavoriteDao


    @Before
    fun create() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        itemDb = Room
            .inMemoryDatabaseBuilder(context, CompyDb::class.java)
            .build()
        itemDao = itemDb.favoriteDao()
    }

    @Test
    fun addItem_shouldReturn_theItem_inFlow() = runTest {
        val keyHref = "123456789"
        val item1 = ProductEntity(
            title = "title",
            brand = "brand",
            description = "description",
            imageUrl = "imageUrl",
            discount = "discount",
            price = "100",
            currency = "USD",
            href = keyHref
        )

        itemDao.saveProduct(item1)

        itemDao.getAllProducts().test {
            val list = awaitItem()
            assert(list.contains(item1))
            cancel()
        }
    }


    @Test
    fun deletedItem_shouldNot_be_present_inFlow() = runTest {
        val keyHref = "123456789"
        val item1 = ProductEntity(
            title = "title",
            brand = "brand",
            description = "description",
            imageUrl = "imageUrl",
            discount = "discount",
            price = "100",
            currency = "USD",
            href = keyHref
        )

        itemDao.saveProduct(item1)
        itemDao.deleteProduct(item1)

        itemDao.getAllProducts().test {
            val list = awaitItem()
            println("HelloWorld")
            println(list)
            assert(list.isEmpty())
            assert(list.contains(item1))
            cancel()
        }
    }



    @After
    fun cleanup() {
        itemDb.close()
    }
}