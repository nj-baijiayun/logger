package com.nj.baijiayun.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nj.baijiayun.logger.log.Logger;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Button btnTestLog;
    private Button btnTestHttp;
    private Button btnTestCrash;
    private Call call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.setEnable(BuildConfig.DEBUG);
        Logger.setPriority(Logger.MIN_LOG_PRIORITY);
        Logger.setTag("[Logger]");
        Logger.init(this);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(Logger.getOkHttpInterceptor())
                .build();
        final Request request = new Request.Builder()
                .url("http://test.api.hdjy.zhaoyongkang.com/api/app/home/course?page=1&limit=15&grade_id=15&subject_id=&title=0&course_type=")
                .get()
                .build();
//
//        RequestBody body = new FormBody.Builder()
//                .add("password", "123456")
//                .add("device_id", "")
//                .add("mobile", "18612246157")
//                .add("device_type", "2")
//                .build();
//        final Request request = new Request.Builder()
//                .url("http://39.97.232.134/api/app/login_m")
//                .post(body)
//                .build();

        btnTestLog = findViewById(R.id.btn_test_log);
        btnTestHttp = findViewById(R.id.btn_test_http);
        btnTestCrash = findViewById(R.id.btn_test_crash);
        btnTestLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d("test log");
                Logger.dTag("tag", "test log");
            }
        });

        btnTestHttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                    }
                });
            }
        });
        btnTestCrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new NullPointerException("test crash");
            }
        });

    }
}
