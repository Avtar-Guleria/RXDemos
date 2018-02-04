package com.asg.rxdemo.rxtests;

import android.util.Log;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by AGularia on 28/01/18.
 */

public class CompletableObservableDemo {
    private static  final String LOCAL_TAG =  CompletableObservableDemo.class.getSimpleName()+"\t";

    public static void testRxCompletableObservable(final String TAG) {

        //Completable represent Observable that emits no value, but only terminal events, either onError or onCompleted
        //Completableis appropriate when you have Observable that you don't care about the value resulted from the operation,
        // or there is any, examples are updating cache for instance, the operation can either succeed/failed, but there is no value.
        // or some long running init operation that don't return something. it can be UPDATE/PUT network call that resulted with success indication only.
        Completable completable = Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                Log.e(TAG, LOCAL_TAG+"subscriber onSubscribe: "+emitter.toString());
                emitter.onComplete();
            }
        });

        CompletableObserver observer = new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, LOCAL_TAG+"observer onSubscribe: "+d.toString());

            }

            @Override
            public void onComplete() {
                Log.e(TAG, LOCAL_TAG+"observer onComplete ");

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, LOCAL_TAG + "observer onError: ");
                e.printStackTrace();

            }
        };

        completable = completable.subscribeOn(Schedulers.io());
        completable = completable.observeOn(AndroidSchedulers.mainThread());
        completable.subscribe(observer);

    }
}
