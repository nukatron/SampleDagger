package com.nutron.sampledagger.presentation.food.rxmvvm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.nutron.sampledagger.MainApplication
import com.nutron.sampledagger.R
import com.nutron.sampledagger.data.entity.Food
import com.nutron.sampledagger.domain.FoodLevel
import com.nutron.sampledagger.domain.ResourceManager
import com.nutron.sampledagger.extensions.addTo
import com.nutron.sampledagger.extensions.component
import com.nutron.sampledagger.extensions.stripPrefix
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_food.*
import javax.inject.Inject


class RxFoodDetailActivity : AppCompatActivity() {

    @Inject lateinit var viewModel: RxFoodViewModel
    @Inject lateinit var resourceManager: ResourceManager

    private val disposaBag = CompositeDisposable()

    companion object {
        private const val EXTRA_FOOD_ID = "EXTRA_FOOD_ID"
        fun getStartIntent(context: Context, foodId: String): Intent {
            val intent = Intent(context, RxFoodDetailActivity::class.java)
            intent.putExtra(EXTRA_FOOD_ID, foodId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        component.inject(this)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initOutput()
        initInput()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposaBag.clear()
    }

    private fun initInput() {
        val foodId = intent.getStringExtra(EXTRA_FOOD_ID)
        viewModel.input.active.accept(foodId)
    }


    private fun initOutput() {
        // observe green
        viewModel.output.greenReport
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { food ->
                    showFood(food, FoodLevel.GREEN)
                }.addTo(disposaBag)

        // observe red
        viewModel.output.redReport
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { food ->
                    showFood(food, FoodLevel.RED)
                }.addTo(disposaBag)

        // observe yellow
        viewModel.output.yellowReport
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { food ->
                    showFood(food, FoodLevel.YELLOW)
                }.addTo(disposaBag)

        // observe unknown
        viewModel.output.unknownReport
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { food ->
                    showFood(food, FoodLevel.UNKNOWN)
                }.addTo(disposaBag)

        viewModel.output.showProgress
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if(it) showLoading() else hideLoading()
                }.addTo(disposaBag)

        viewModel.output.error
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    showErrorMessage(it)
                }.addTo(disposaBag)
    }

    fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    private fun showFood(food: Food, level: FoodLevel) {
        val foodNameString = food.name.stripPrefix()
        title = foodNameString
        foodName.text = foodNameString
        foodMeasure.text = String.format(getString(R.string.foodItemMeasure), food.measure)
        foodNutrient.text = food.nutrients[0].toString()
        foodNutrient.setTextColor(ContextCompat.getColor(this, resourceManager.getFoodColor(level)))
        foodImageView.setImageDrawable(ContextCompat.getDrawable(this, resourceManager.getFoodImage(level)))
    }

    fun showErrorMessage(t: Throwable) {
        val message = "${getString(R.string.foodItemError)}: ${t.message}"
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        Log.e("RxFoodDetailActivity", t.message)
    }
}