package com.nutron.sampledragger.extensions

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


inline fun Disposable.addTo(disposable: CompositeDisposable): Boolean = disposable.add(this)
