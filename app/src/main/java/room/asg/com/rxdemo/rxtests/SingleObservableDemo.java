package room.asg.com.rxdemo.rxtests;

import android.util.Log;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by AGularia on 31/01/18.
 */

public class SingleObservableDemo {
    private static  final String LOCAL_TAG =  CompletableObservableDemo.class.getSimpleName()+"\t";

    public static void testRxSingleObservable(final String TAG) {


        //Single represent Observable that emit single value or error.
        //Single can be appropriate when you have task oriented Observable and you expect single value,
        // like Network request which is performed once and return value (or error), network call is operated in one time fashion,
        // meaning you don't expect it to return additional values over time. Another example is DB fetch data operation.


        Single<Integer> singleObservable = Single.create(new SingleOnSubscribe<Integer>() {
            @Override
            public void subscribe(SingleEmitter<Integer> emitter) throws Exception {
                Log.e(TAG, LOCAL_TAG+"singleObservable onSubscribe: "+emitter.toString());

                emitter.onSuccess(30);
                emitter.onSuccess(10);//only one onSuccess will be returned as its a single observable any other calls will be ignored
                emitter.onSuccess(20);
            }
        });

        SingleObserver singleObserver = new SingleObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, LOCAL_TAG+"observer onSubscribe: "+d.toString());

            }

            @Override
            public void onSuccess(Object o) {
                Log.e(TAG, LOCAL_TAG+"observer onSuccess "+o.toString());

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, LOCAL_TAG + "observer onError: ");
                e.printStackTrace();
            }
        };

        singleObservable = singleObservable.subscribeOn(Schedulers.io());
        singleObservable = singleObservable.observeOn(AndroidSchedulers.mainThread());
        singleObservable.subscribe(singleObserver);
    }
}
