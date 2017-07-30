package com.nutron.sampledagger.extensions

import android.view.View


@Suppress("UNCHECKED_CAST")
fun <T : View> View.findView(id: Int) = this.findViewById(id) as T