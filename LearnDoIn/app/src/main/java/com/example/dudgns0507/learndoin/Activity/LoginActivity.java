package com.example.dudgns0507.learndoin.Activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.dudgns0507.learndoin.R;
import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by pyh42 on 2016-09-26.
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    void init() {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.hide();

        setFont("NanumBarunGothicUltraLight.otf", R.id.main_title);
        setFont("NanumBarunGothicUltraLight.otf", R.id.sub_title);
        setFont("NanumBarunGothicLight.otf", R.id.login_btn);
        setFont("NanumBarunGothicBold.otf", R.id.google_login_btn);
        setFont("NanumBarunGothicBold.otf", R.id.faceook_login_btn);
        setFont("NanumBarunGothicLight.otf", R.id.registration_btn);
        setFont("NanumBarunGothicLight.otf", R.id.finduser_btn);
    }

    void setFont(String path, int res) {
        Typeface font = Typeface.createFromAsset(this.getAssets(), path);

        if(findViewById(res) instanceof TextView) {
            TextView mTextView = (TextView) findViewById(res);
            mTextView.setTypeface(font);
        }
        if(findViewById(res) instanceof Button) {
            Button mButton = (Button)findViewById(res);
            mButton.setTypeface(font);
        }
    }
}
