package com.segu.demorxjava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ActivityRX extends AppCompatActivity {
    Button btnRun;
    TextView textView;
    EditText editText;
    Observable<String> observable;
    Observer<String> observer;
    private final String TAG = "ActivityRX";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createObserveable();
        btnRun = findViewById(R.id.bt_click);
        textView = findViewById(R.id.tv);
        editText = findViewById(R.id.et);

        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                observable.subscribe(observer);
            }
        });
    }

    private void createObserveable() {
        Log.e(TAG, "createObserveable: "+editText.getText().toString() );
        observable = Observable.just(editText.getText().toString());//create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext(editText.getText().toString());
//                emitter.onComplete();
//            }
//        });

        observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext: "+s );
                textView.setText(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }
}
