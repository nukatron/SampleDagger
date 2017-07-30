package com.nutron.sampledagger.data.entity

import com.google.gson.annotations.SerializedName


data class FoodzItem(val id: String, val name: String)

data class FoodzList(@SerializedName("item") val items: List<FoodzItem>)

data class FoodzListResponse(val list: FoodzList)