package com.asg.rxdemo.rxtests;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by AGularia on 28/01/18.
 */

public class ObservableDemo {
    private static final String LOCAL_TAG = ObservableDemo.class.getSimpleName() + "\t";



    public static void testRxObservable(final String TAG) {

        Observer<Integer> observer = new Observer<Integer>() {


            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, LOCAL_TAG + "onSubscribe: ");
            }


            @Override
            public void onNext(Integer value) {
                Log.e(TAG, LOCAL_TAG + "onNext Thread Name: " + Thread.currentThread().getName());

                Log.e(TAG, LOCAL_TAG + "onNext: " + value);
            }


            @Override
            public void onError(Throwable e) {
                Log.e(TAG, LOCAL_TAG + "onError: ");
            }


            @Override
            public void onComplete() {
                Log.e(TAG, LOCAL_TAG + "onComplete: All Done!");
            }
        };




        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
                                                               @Override

           public void subscribe(ObservableEmitter<Integer> e) throws Exception {

               Log.e(TAG, LOCAL_TAG + "observable1 Thread Name: " + Thread.currentThread().getName());
               e.onNext(1);

               Log.e(TAG, LOCAL_TAG + "observable2 Thread Name: " + Thread.currentThread().getName());
               e.onNext(2);

               Log.e(TAG, LOCAL_TAG + "observable3 Thread Name: " + Thread.currentThread().getName());
               e.onNext(3);

               Log.e(TAG, LOCAL_TAG + "observable4 Thread Name: " + Thread.currentThread().getName());
               e.onNext(4);

               Log.e(TAG, LOCAL_TAG + "observable5 Thread Name: " + Thread.currentThread().getName());
               //Once the Observable has emitted all items in the sequence, call onComplete//
               e.onComplete();

           }
        }
        );

        observable = observable.subscribeOn(Schedulers.io());
        observable = observable.observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(observer);
    }

}
