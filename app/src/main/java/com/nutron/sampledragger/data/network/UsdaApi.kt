package com.nutron.sampledragger.data.network

import com.nutron.sampledragger.base.API_KEY
import com.nutron.sampledragger.base.SUGAR_NUTRIENT
import com.nutron.sampledragger.data.entity.FoodResponse
import com.nutron.sampledragger.data.entity.FoodzListResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface UsdaApi {

    @GET("ndb/list?api_key=" + API_KEY)
    fun getFoodzList(): Observable<FoodzListResponse>

    @GET("ndb/nutrients?api_key=$API_KEY&nutrients=$SUGAR_NUTRIENT")
    fun getFoodItem(@Query("ndbno") foodId: String): Observable<FoodResponse>

}
