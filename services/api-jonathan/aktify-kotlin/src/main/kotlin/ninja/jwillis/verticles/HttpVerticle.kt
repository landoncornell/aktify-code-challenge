package ninja.jwillis.verticles

import io.vertx.core.http.HttpMethod
import io.vertx.core.http.HttpServerOptions
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.CorsHandler
import io.vertx.ext.web.handler.StaticHandler
import io.vertx.kotlin.coroutines.CoroutineVerticle
import me.koddle.config.Config
import me.koddle.tools.SwaggerMerger
import me.koddle.tools.route


class HttpVerticle : CoroutineVerticle() {

    override suspend fun start() {
        val mainRouter = Router.router(vertx)
        val pkg = this.javaClass.`package`.name.substringBeforeLast('.') + ".controllers"
        val swaggerFile = SwaggerMerger.mergeAllInDirectory("swagger") ?: throw RuntimeException("Unable to process Swagger file")
        val config = Config.config(vertx)
        val staticHandler = StaticHandler.create()
                .setCachingEnabled(false)
                .setDirectoryListing(false)
                .setIncludeHidden(false)

        mainRouter.route(swaggerFile, pkg)
        mainRouter.get().handler(staticHandler)
        mainRouter.get().handler {
            if (!"/".equals(it.normalisedPath())) {
                it.reroute("/")
            } else {
                it.next()
            }
        }
        corsSetup(mainRouter)
        vertx.createHttpServer(HttpServerOptions()
                .setCompressionSupported(true))
                .requestHandler(mainRouter)
                .listen(config.getInteger("PORT", 8080), "0.0.0.0")
    }

    private fun corsSetup(mainRouter: Router) {
        val allowedHeaders: MutableSet<String> = HashSet()
        allowedHeaders.add("x-requested-with")
        allowedHeaders.add("Access-Control-Allow-Origin")
        allowedHeaders.add("origin")
        allowedHeaders.add("Content-Type")
        allowedHeaders.add("accept")
        allowedHeaders.add("X-PINGARUNER")

        val allowedMethods: MutableSet<HttpMethod> = HashSet()
        allowedMethods.add(HttpMethod.GET)
        allowedMethods.add(HttpMethod.POST)
        allowedMethods.add(HttpMethod.OPTIONS)
        allowedMethods.add(HttpMethod.DELETE)
        allowedMethods.add(HttpMethod.PATCH)
        allowedMethods.add(HttpMethod.PUT)

        mainRouter.route().handler(CorsHandler.create("*").allowedHeaders(allowedHeaders).allowedMethods(allowedMethods))
    }
}
