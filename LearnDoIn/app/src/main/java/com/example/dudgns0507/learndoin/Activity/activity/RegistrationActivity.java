package com.example.dudgns0507.learndoin.Activity.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dudgns0507.learndoin.Activity.Registration;
import com.example.dudgns0507.learndoin.Activity.model.Result;
import com.example.dudgns0507.learndoin.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pyh42 on 2016-10-24.
 */

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = "RegistrationActivity";

    private int cnt;

    @BindView(R.id.registration_name_edittext) EditText reg_name_edit;
    @BindView(R.id.registration_id_edittext) EditText reg_id_edit;
    @BindView(R.id.registration_pw_edittext) EditText reg_pw_edit;
    @BindView(R.id.registration_pwcheck_edittext) EditText reg_pwcheck_edit;
    @BindView(R.id.registration_name_text) TextView reg_name_text;
    @BindView(R.id.registration_id_text) TextView reg_id_text;
    @BindView(R.id.registration_pw_text) TextView reg_pw_text;
    @BindView(R.id.registration_pwcheck_text) TextView reg_pwcheck_text;

    @BindView(R.id.registration_confirm_btn) TextView reg_confirm_btn;

    private ProgressDialog asyncDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.registration_confirm_btn) void registration() {

        cnt = 0;
        if (reg_name_edit.getText() == null) {
            reg_name_edit.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.circle_red), null);
            reg_name_text.setText("이름을 입력해주세요.");
            cnt++;
        } else {
            reg_name_edit.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.circle_green), null);
            reg_name_text.setText("");
        }

        if (reg_id_edit.getText() == null) {
            reg_id_edit.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.circle_red), null);
            reg_id_text.setText("ID를 입력해주세요.");
            cnt++;
        } else {
            reg_id_edit.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.circle_green), null);
            reg_id_text.setText("");
        }

        if(reg_pw_edit.getText() == null) {
            reg_pw_edit.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.circle_red), null);
            reg_pw_text.setText("PW를 입력해 주십시오.");
            cnt++;
        }  else if (!reg_pw_edit.getText().toString().equals(reg_pwcheck_edit.getText().toString())) {
            reg_pwcheck_edit.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.circle_red), null);
            reg_pwcheck_text.setText("입력한 패스워드와 다릅니다.");
            cnt++;
        } else {
            reg_pwcheck_edit.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.circle_green), null);
            reg_pwcheck_text.setText("");
        }

        if (cnt == 0) {
            asyncDialog = new ProgressDialog(RegistrationActivity.this);
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩중입니다..");

            asyncDialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://0hoon.xyz")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Registration registration = retrofit.create(Registration.class);

            Call<Result> call = registration.registration(reg_id_edit.getText().toString(), reg_pw_edit.getText().toString(), "", reg_name_edit.getText().toString());
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    Result res = response.body();
                    if(res.getResult() == 1) {
                        Snackbar.make(getWindow().getDecorView().getRootView(), "가입 완료", Snackbar.LENGTH_SHORT)
                                .setActionTextColor(Color.GREEN)
                                .setDuration(500)
                                .setAction("확인", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                    }
                                }).show();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            }
                        },500);
                    } else if(res.getResult() == 2) {
                        Snackbar.make(getWindow().getDecorView().getRootView(), "이미 가입된 계정입니다.", Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(getWindow().getDecorView().getRootView(), "가입 도중 오류가 발생했습니다.", Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Log.w(TAG, "error" + t.getStackTrace());
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
