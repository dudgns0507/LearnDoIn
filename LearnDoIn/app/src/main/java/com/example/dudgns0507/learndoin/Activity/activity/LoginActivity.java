package com.example.dudgns0507.learndoin.Activity.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dudgns0507.learndoin.Activity.Login;
import com.example.dudgns0507.learndoin.Activity.model.LocalData;
import com.example.dudgns0507.learndoin.Activity.model.UserData;
import com.example.dudgns0507.learndoin.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pyh42 on 2016-09-26.
 */

public class LoginActivity extends AppCompatActivity {

    private final static String TAG = "LoginActivity";
    private ProgressDialog asyncDialog;

    @BindView(R.id.login_btn) TextView login_btn;
    @BindView(R.id.faceook_login_btn) TextView facebook_login_btn;
    @BindView(R.id.google_login_btn) TextView google_login_btn;
    @BindView(R.id.registration_btn) TextView registration_btn;
    @BindView(R.id.finduser_btn) TextView finduser_btn;
    @BindView(R.id.login_id) EditText login_id;
    @BindView(R.id.login_pw) EditText login_pw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        init();
    }

    void init() {

        // Check permission
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Log.w(TAG, "Permission Granted");
                SharedPreferences sp = getSharedPreferences("dudgns0507.learndoin", MODE_PRIVATE);
                if(sp.getString("id", null) != null && sp.getString("pw", null) != null) {
                    login(sp.getString("id", null), sp.getString("pw", null));
                }
//                Toast.makeText(LoginActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Log.w(TAG, "Permission Denied");
//                Toast.makeText(LoginActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    @OnClick(R.id.login_btn) void login() {
        final String id = login_id.getText().toString();
        final String pw = login_pw.getText().toString();

        if(id.equals("")) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "ID를 입력해주십시오.", Snackbar.LENGTH_SHORT).show();
        } else if(pw.equals("")) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "PW를 입력해주십시오.", Snackbar.LENGTH_SHORT).show();
        } else {
            login(id, pw);
        }
    }

    void login(final String id, final String pw) {
        asyncDialog = new ProgressDialog(LoginActivity.this);
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage("로그인중입니다.. ");

        asyncDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://0hoon.xyz")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Login login = retrofit.create(Login.class);

        Call<UserData> call = login.login(id, pw);
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                asyncDialog.dismiss();
                UserData res = response.body();

                if(response.body().getType()) {
                    LocalData mData = (LocalData) getApplicationContext();
                    mData.setCreate_date(res.getCreate_date());
                    mData.setEmail(res.getEmail());
                    mData.setId(res.getId());
                    mData.setName(res.getName());
                    mData.setStudy_time(res.getStudy_time());
                    mData.setWordLists(res.getWordLists());

                    SharedPreferences sp = getSharedPreferences("dudgns0507.learndoin", MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();

                    edit.putString("id", id);
                    edit.putString("pw", pw);

                    edit.commit();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    },500);
                } else {
                    Toast.makeText(LoginActivity.this, "ID 및 PW를 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                Log.w(TAG, "Login Failed");
            }
        });
    }

    @OnClick(R.id.faceook_login_btn) void facebookLogin() {

    }

    @OnClick(R.id.google_login_btn) void googleLogin() {

    }

    @OnClick(R.id.registration_btn) void registration() {
        Log.w(TAG, "Load RegistrationActivity");
        Intent intentRegistrationActivity = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intentRegistrationActivity);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
