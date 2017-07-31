package com.nutron.sampledagger.extensions

import io.reactivex.Notification
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


inline fun Disposable.addTo(disposable: CompositeDisposable): Boolean = disposable.add(this)

inline fun <T> Observable<Notification<T>>.elements() = this.filter { it.value != null }.map { it.value!! }

inline fun <T> Observable<Notification<T>>.error() = this.filter { it.error != null }.map { it.error!! }