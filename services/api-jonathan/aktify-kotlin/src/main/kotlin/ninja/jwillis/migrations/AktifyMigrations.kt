package ninja.jwillis.migrations

import io.vertx.core.Vertx
import me.koddle.config.Config
import me.koddle.migrations.migrate

fun main() {
    val vertx = Vertx.vertx()
    val dbConfig = Config.config(vertx)
    migrate(dbConfig)
    vertx.close()
}
