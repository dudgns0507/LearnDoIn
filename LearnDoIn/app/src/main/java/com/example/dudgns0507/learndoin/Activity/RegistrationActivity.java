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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dudgns0507.learndoin.R;

import retrofit2.Retrofit;

/**
 * Created by pyh42 on 2016-10-24.
 */

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener, AsyncResponse {

    private int cnt;
    private EditText reg_name_edit, reg_id_edit, reg_pw_edit, reg_pwcheck_edit;
    private TextView reg_name_text, reg_id_text, reg_pw_text, reg_pwcheck_text;
    private ProgressDialog asyncDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        init();
    }

    void init() {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.hide();

        setFont("NanumBarunGothicUltraLight.otf", R.id.registration_title);
        setFont("NanumBarunGothicLight.otf", R.id.registration_confirm_btn);

        reg_name_edit = (EditText)findViewById(R.id.registration_name_edittext);
        reg_id_edit = (EditText)findViewById(R.id.registration_id_edittext);
        reg_pw_edit = (EditText)findViewById(R.id.registration_pw_edittext);
        reg_pwcheck_edit = (EditText)findViewById(R.id.registration_pwcheck_edittext);
        reg_name_text = (TextView)findViewById(R.id.registration_name_text);
        reg_id_text = (TextView)findViewById(R.id.registration_id_text);
        reg_pw_text = (TextView)findViewById(R.id.registration_pw_text);
        reg_pwcheck_text = (TextView)findViewById(R.id.registration_pwcheck_text);

        Button reg_confirm_btn = (Button)findViewById(R.id.registration_confirm_btn);
        reg_confirm_btn.setOnClickListener(this);
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
            case R.id.registration_confirm_btn :
                registration();
                break;
        }
    }

    private void registration() {
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

            String json;
            DbConnection dbConnection = new DbConnection();
            dbConnection.delegate = this;
            dbConnection.getData("http://0hoon.xyz/registration.php?user_name=" + reg_name_edit.getText().toString() + "&user_id=" + reg_id_edit.getText().toString() + "&user_pw=" + reg_pw_edit.getText().toString());
        }
    }

    @Override
    public void processFinish(String output) {
        asyncDialog.dismiss();

        if(output.equals("1")) {
            Snackbar.make(getWindow().getDecorView().getRootView(), "이미 가입된 계정입니다.", Snackbar.LENGTH_SHORT).show();
        }
        else
        {
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
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
