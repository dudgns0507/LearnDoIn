package com.example.dudgns0507.learndoin.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dudgns0507.learndoin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pyh42 on 2016-09-26.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, AsyncResponse {

    private final static String TAG = "LoginActivity";
    private ProgressDialog asyncDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    void init() {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.hide();

        // 글씨체 변경
        setFont("NanumBarunGothicUltraLight.otf", R.id.main_title);
        setFont("NanumBarunGothicUltraLight.otf", R.id.sub_title);
        setFont("NanumBarunGothicLight.otf", R.id.login_btn);
        setFont("NanumBarunGothicBold.otf", R.id.google_login_btn);
        setFont("NanumBarunGothicBold.otf", R.id.faceook_login_btn);
        setFont("NanumBarunGothicLight.otf", R.id.registration_btn);
        setFont("NanumBarunGothicLight.otf", R.id.finduser_btn);

        TextView registration_btn = (TextView)findViewById(R.id.registration_btn);
        registration_btn.setOnClickListener(this);

        Button login_btn = (Button)findViewById(R.id.login_btn);
        Button google_login_btn = (Button)findViewById(R.id.google_login_btn);
        Button facebook_login_btn = (Button)findViewById(R.id.faceook_login_btn);

        login_btn.setOnClickListener(this);
        google_login_btn.setOnClickListener(this);
        facebook_login_btn.setOnClickListener(this);
    }

    void setFont(String path, int res) {
        Typeface font = Typeface.createFromAsset(this.getAssets(), path);

        if (findViewById(res) instanceof TextView) {
            TextView mTextView = (TextView) findViewById(res);
            mTextView.setTypeface(font);
        }
        if (findViewById(res) instanceof Button) {
            Button mButton = (Button) findViewById(res);
            mButton.setTypeface(font);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registration_btn :
                Log.w(TAG, "Load RegistrationActivity");
                Intent intentRegistrationActivity = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intentRegistrationActivity);
                break;
            case R.id.login_btn :
                EditText login_id = (EditText)findViewById(R.id.login_id);
                EditText login_pw = (EditText)findViewById(R.id.login_pw);
                login(login_id.getText().toString(), login_pw.getText().toString());
                break;
            case R.id.google_login_btn :
                googleLogin();
                break;
            case R.id.faceook_login_btn :
                facebookLogin();
                break;
        }
    }

    @Override
    public void processFinish(String output) {
        if(output != null) {
            Log.w(TAG, output);
            asyncDialog.dismiss();
            jsonParse(output);
        }
    }

    void jsonParse(String json) {

        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            String state = jsonObject.getString("state");

            if(state.equals("success")) {
                UserInfo userInfo = (UserInfo)getApplicationContext();
                userInfo.setName(jsonObject.getString("user_name"));
                userInfo.setId(jsonObject.getString("user_id"));

                Snackbar.make(getWindow().getDecorView().getRootView(), "로그인 성공", Snackbar.LENGTH_SHORT)
                        .setActionTextColor(Color.GREEN)
                        .setDuration(1000)
                        .setAction("확인", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(LoginActivity.this, ListActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                finish();
                            }
                        })
                        .show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(LoginActivity.this, ListActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                    }
                },1000);
            } else {
                Snackbar.make(getWindow().getDecorView().getRootView(), "로그인 실패", Snackbar.LENGTH_SHORT)
                    .setActionTextColor(Color.GREEN)
                    .setAction("확인", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    })
                    .show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void login(String id, String pw) {
        Log.w(TAG, "Connect DB");
        asyncDialog = new ProgressDialog(LoginActivity.this);
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage("로딩중입니다..");

        asyncDialog.show();

        DbConnection dbConnection = new DbConnection();
        dbConnection.delegate = this;
        dbConnection.getData("http://0hoon.xyz/login.php?user_id=" + id + "&user_pw=" + pw);
    }

    void googleLogin() {

    }

    void facebookLogin() {

    }
}
