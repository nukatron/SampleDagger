package com.nutron.sampledragger.presentation.foodz

import com.nutron.sampledragger.data.entity.FoodzItem


interface FoodzContract {

    interface FoodzView {

        fun showLoading()

        fun hideLoading()

        fun showFoodz(foodzItemList: List<FoodzItem>)

        fun showErrorMessage()

        fun launchFoodDetail(foodzItem: FoodzItem)
    }

    interface FoodzPresenter {
        fun setView(view: FoodzView)

        fun getFoodz()
    }
}