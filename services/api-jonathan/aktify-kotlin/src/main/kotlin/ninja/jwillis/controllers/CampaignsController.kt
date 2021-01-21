package ninja.jwillis.controllers

import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import me.koddle.annotations.Body
import me.koddle.controllers.BaseController
import me.koddle.json.jObj
import ninja.jwillis.models.Campaign.Companion.ACTIVE
import ninja.jwillis.models.Campaign.Companion.DESCRIPTION
import ninja.jwillis.models.Campaign.Companion.NAME
import ninja.jwillis.repos.CampaignRepo

class CampaignsController(val campaignRepo: CampaignRepo) : BaseController() {

    suspend fun index(routingContext: RoutingContext) : JsonArray {
        return campaignRepo.all();
    }

    suspend fun show(routingContext: RoutingContext, id: String) : JsonObject {
        return campaignRepo.find(id);
    }

    suspend fun post(routingContext: RoutingContext, @Body jsonBody: JsonObject) : JsonObject {
        return campaignRepo.insert(jObj(NAME to jsonBody.getString(NAME),
                                        DESCRIPTION to jsonBody.getString(DESCRIPTION),
                                        ACTIVE to jsonBody.getBoolean(ACTIVE, false)))
    }
}
