package ninja.jwillis.service

import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.kotlin.core.deployVerticleAwait
import kotlinx.coroutines.runBlocking
import me.koddle.config.Config
import me.koddle.service.buildAutoModule
import me.koddle.tools.DatabaseAccess
import me.koddle.tools.JWTHelper
import ninja.jwillis.repos.CampaignRepo
import ninja.jwillis.verticles.HttpVerticle
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun main() {
    start()
}

fun start(overrideModule: Module? = null) {
    val vertxOptions = VertxOptions()
    val vertx = Vertx.vertx(vertxOptions)
    val config = Config.config(vertx)

    val module = module(override = true) {
        single { vertx }
        single { config }
        single { JWTHelper(config, vertx) }
        single { DatabaseAccess(config, vertx) }
        single { CampaignRepo("public") }
    }
    startKoin {
        modules(buildAutoModule(HttpVerticle::class.java))
        modules(module)
        overrideModule?.let {
            modules(it)
        }
    }

    runBlocking {
        vertx.deployVerticleAwait(HttpVerticle())
    }
}
