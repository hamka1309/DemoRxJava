package com.segu.demorxjava;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.MaybeSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MaybeObserverActivity extends AppCompatActivity {

    private static final String TAG = MaybeObserverActivity.class.getSimpleName();
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        Maybe<String[]> noteObservable = getNoteObservable();

        Maybe<Integer> integerMaybe = noteObservable.flatMap(new Function<String[], MaybeSource<Integer>>() {
            @Override
            public MaybeSource<Integer> apply(String[] strings) throws Exception {
                return getMaybeInt(strings);
            }
        });
        MaybeObserver<String[]> noteObserver = getNoteObserver();

        noteObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(noteObserver);
    }

    private Maybe<Integer> getMaybeInt(String[] str) {
        return Maybe.empty();
    }
    private MaybeObserver<String[]> getNoteObserver() {
        return new MaybeObserver<String[]>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(String[] strings) {
                Log.e(TAG, "onSuccess: " + strings);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
            }
        };
    }

    private Maybe<String[]> getNoteObservable() {
//        return Maybe.create(new MaybeOnSubscribe<Note>() {
//            @Override
//            public void subscribe(MaybeEmitter<Note> emitter) throws Exception {
//                Note note = new Note(1, "Call!");
//                if (!emitter.isDisposed()) {
//                    emitter.onSuccess(note);
//                }
//            }
//        });
        return Maybe.just(new String[]{"1","2","3","4"});
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
