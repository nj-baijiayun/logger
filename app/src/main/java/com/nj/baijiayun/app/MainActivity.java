package com.nj.baijiayun.app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

    private static final int REQ_WRITE_STORAGE = 222;
    private Button btnTestLog;
    private Button btnTestHttp;
    private Button btnTestCrash;
    private Call call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQ_WRITE_STORAGE);
        }else{
            Logger.init(this, "[Logger]", BuildConfig.DEBUG, true);
        }

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(Logger.getOkHttpInterceptor())
                .build();
        final Request request = new Request.Builder()
                .url("http://test.api.hdjy.zhaoyongkang.com/api/app/home/course?page=1&limit=15&grade_id=15&subject_id=&title=0&course_type=")
                .get()
                .build();
        btnTestLog = findViewById(R.id.btn_test_log);
        btnTestHttp = findViewById(R.id.btn_test_http);
        btnTestCrash = findViewById(R.id.btn_test_crash);
        btnTestLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.e("test log");
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQ_WRITE_STORAGE == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Logger.init(this, "[Logger]", BuildConfig.DEBUG, true);
            } else {
            }
            return;
        }
    }
}
