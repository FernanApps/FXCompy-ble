package pe.fernan.apps.compyble.data.remote

import app.cash.turbine.test
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pe.fernan.apps.compyble.domain.repository.CompyRepository
import javax.inject.Inject

@HiltAndroidTest
class CompyRepositoryImpTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var compyRepositoryImp: CompyRepository

    @Before
    fun setUp() {
        hiltRule.inject()
    }


    @After
    fun tearDown() {
    }

    @Test
    fun getDetails() = runTest {
        val href="/galeria/producto/65168e8cf0ffaae7a32f6d84/lavadora-oster-os-pwsmk0014b-14kg-negro?utm_campaign_store=P-031023-061023-oechsle"
        compyRepositoryImp.getDetails(href).test {
            val details = awaitItem()
            println("Details $details")

            cancel()
        }
    }

    @Test
    fun getMain() = runTest {
        compyRepositoryImp.getMain().test {
            val data = awaitItem()
            println("Data $data")

            cancel()
        }
    }
}