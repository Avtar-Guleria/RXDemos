package room.asg.com.rxdemo.rxtests;

import android.util.Log;

import org.reactivestreams.Subscription;

import java.util.Arrays;
import java.util.List;

import io.reactivex.BackpressureOverflowStrategy;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import room.asg.com.rxdemo.RxDemoActivity;

/**
 * Created by AGularia on 28/01/18.
 */

public class FlowableDemo {

    private static  final String LOCAL_TAG =  FlowableDemo.class.getSimpleName()+"\t";


    public static void testRxFlowable(final String TAG) {

        //Backpressure is a means of handling the situation where data is generated faster than it can processed.
        // Flowable has backpressure support allowing downstream to request items.

        FlowableSubscriber<Integer> subscriber = new FlowableSubscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.e(TAG, LOCAL_TAG+"subscriber onSubscribe: "+s.toString());
                s.request(500);
            }

            @Override
            public void onNext(Integer value) {

                Log.e(TAG, LOCAL_TAG+" subscriber onNext Thread Name: " + Thread.currentThread().getName()+" value="+value);

            }

            @Override
            public void onError(Throwable t) {
                Log.e(TAG, LOCAL_TAG+"onError: "+t.getMessage());
                t.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.e(TAG, LOCAL_TAG+"onComplete: All Done!");
            }
        };

        Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                //Use onNext to emit each item in the stream

                for (int index =10; index<150;index++){
                    Log.e(TAG, LOCAL_TAG+index+" Flowable Thread Name: " + Thread.currentThread().getName());
                    e.onNext(index);
                }
                e.onComplete();
            }

        }, BackpressureStrategy.LATEST);

        //Create our subscription//
        flowable = flowable.subscribeOn(Schedulers.io());
        flowable = flowable.observeOn(AndroidSchedulers.mainThread());
        flowable.subscribe(subscriber);

    }

}
