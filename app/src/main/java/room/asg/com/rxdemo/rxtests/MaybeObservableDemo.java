package room.asg.com.rxdemo.rxtests;

import android.Manifest;
import android.util.Log;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by AGularia on 28/01/18.
 */

public class MaybeObservableDemo {
    private static  final String LOCAL_TAG =  MaybeObservableDemo.class.getSimpleName()+"\t";

    public static void testRxMaybeObservable(final String TAG) {

        //RxJava2 added new type called Maybe, Maybe is combination of Completable and Single.
        //With Maybe we can either have some value exactly like Single, or have return nothing - just like Completable. additionaly like both we can have error.


        Maybe<Integer> observable = Maybe.create(new MaybeOnSubscribe<Integer>() {
            @Override
            public void subscribe(MaybeEmitter<Integer> emitter) throws Exception {

                emitter.onSuccess(10); // either onSuccess or onComplete will be called.
                emitter.onComplete();
            }
        });

        MaybeObserver<Integer> observer = new MaybeObserver<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, LOCAL_TAG+"onSubscribe: ");
            }

            @Override
            public void onSuccess(Integer value) {
                Log.e(TAG, LOCAL_TAG+"onSuccess: " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, LOCAL_TAG+"onError: ");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, LOCAL_TAG+"onComplete: All Done!");
            }

        };

        observable.subscribe(observer);

    }
}
