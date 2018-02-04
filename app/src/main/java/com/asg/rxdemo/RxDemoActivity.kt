package com.asg.rxdemo;

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.asg.rxdemo.rxtests.*
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by AGularia on 28/01/18.
 */

class RxDemoActivity : Activity() {

    private val TAG = RxDemoActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testRxMethods()
    }


    private fun testRxMethods() {

        clockButton.setOnClickListener(View.OnClickListener { updateClock() })

        observable.setOnClickListener(View.OnClickListener {
            ObservableDemo.testRxObservable(TAG);
        })

        flowable.setOnClickListener(View.OnClickListener {
            FlowableDemo.testRxFlowable(TAG);
        });

        singleable.setOnClickListener(View.OnClickListener {
            SingleObservableDemo.testRxSingleObservable(TAG);
        });


        completeable.setOnClickListener(View.OnClickListener {
            CompletableObservableDemo.testRxCompletableObservable(TAG);
        });


        maybeable.setOnClickListener(View.OnClickListener {
            MaybeObservableDemo.testRxMaybeObservable(TAG);
        });

    }

    private fun updateClock() {
        clockEditText.visibility = View.VISIBLE

        var clockObservable: Observable<Date> = Observable.create(ObservableOnSubscribe {
            while (true) {
                Thread.sleep(1000);
                it.onNext(Calendar.getInstance().time)
            }
        })

        clockObservable = clockObservable.subscribeOn(Schedulers.io());
        clockObservable = clockObservable.observeOn(AndroidSchedulers.mainThread());

        clockObservable.subscribe(
                { next ->
                    var formatter: SimpleDateFormat = SimpleDateFormat("HH:mm:ss")
                    clockEditText.setText(formatter.format(next))
                },
                { error -> Log.e("TAG", "{$error.message}") },
                { Log.d("TAG", "completed") })

    }

}
