package com.segu.demorxjava;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.reactivestreams.Subscriber;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "MainActivity";
    private Disposable disposable;
    private Button btFlowable,btSingle,btMaybe,btCompletable,btObservable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btCompletable= findViewById(R.id.bt_completable);
        btFlowable= findViewById(R.id.bt_flowable);
        btMaybe= findViewById(R.id.bt_maybe);
        btSingle= findViewById(R.id.bt_clic_single);
        btFlowable.setOnClickListener(this);
        btSingle.setOnClickListener(this);
        btMaybe.setOnClickListener(this);
        btCompletable.setOnClickListener(this);


//        btnRun.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                demoObservableFrom();
//            }
//
//
//        });
       // getSingleObservable().subscribe(getSingleObserver());
//        Note note = new Note(1, "Home work!");
//
//        Completable completableObservable = updateNote(note);
//
//        CompletableObserver completableObserver = completableObserver();
//
//        completableObservable
////                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(completableObserver);
    }

    private void demoObservableFrom() {

        Observable observable = Observable.just("A", "B", "C", "D", "E", "F");
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe: " + d);
            }

            @Override
            public void onNext(Object o) {
                Log.e(TAG, "onNext: " + o);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete: ");
            }
        };
        observable.map(new Function() {
            @Override
            public Object apply(Object o) throws Exception {
                return null;
            }
        })
                .subscribe(observer);

    }

    Single<String> getSingleObservable() {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> emitter) throws Exception {
                if (!emitter.isDisposed()) {
                    emitter.onSuccess("Amiit");
                }
            }
        });
    }

    SingleObserver<String> getSingleObserver() {
        return new SingleObserver<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe: " + d);
            }

            @Override
            public void onSuccess(String s) {
                Log.e(TAG, "onSuccess: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e);
            }
        };
    }

    public void testCompletable() {
        Note note = new Note(1, "Home work!");

        Completable completableObservable = updateNote(note);

        CompletableObserver completableObserver = completableObserver();

        completableObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completableObserver);

    }

    private Completable updateNote(Note note) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                if (!emitter.isDisposed()) {
                   // Thread.sleep(1000);
                    emitter.onComplete();
                }
            }
        });
    }

    private CompletableObserver completableObserver() {
        return new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
                disposable = d;
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: Note updated successfully!");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }
        };
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_clic_single:
                Intent intent = new Intent(this,SingleObserverActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_completable:
                Intent intent1 = new Intent(this,CompletableObserverActivity.class);
                startActivity(intent1);
                break;
            case R.id.bt_flowable:
                Intent intent2 = new Intent(this,FlowableObserverActivity.class);
                startActivity(intent2);
                break;
            case R.id.bt_maybe:
                Intent intent3 = new Intent(this,MaybeObserverActivity.class);
                startActivity(intent3);
                break;


        }
    }
}
