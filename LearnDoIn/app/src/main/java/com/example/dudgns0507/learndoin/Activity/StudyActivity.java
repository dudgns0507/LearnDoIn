package com.example.dudgns0507.learndoin.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.dudgns0507.learndoin.R;

/**
 * Created by pyh42 on 2016-10-24.
 */

public class StudyActivity extends AppCompatActivity {

    public TextView word, mean;
    public int cnt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        init();
    }

    void init() {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.hide();

        getWordList();

        Button next_btn = (Button)findViewById(R.id.study_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextWord();
            }
        });

        word = (TextView)findViewById(R.id.study_text);
        mean = (TextView)findViewById(R.id.study_text2);

        word.setText(ListActivity.wordList.get(0).wordContent);
        mean.setText(ListActivity.wordList.get(0).meaning);

        cnt = 1;
    }

    void getWordList() {
    }

    void nextWord() {
        word.setText(ListActivity.wordList.get(cnt).wordContent);
        mean.setText(ListActivity.wordList.get(cnt).meaning);
        cnt++;
    }

    void savePoint() {

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        AlertDialog dialog = new AlertDialog.Builder(StudyActivity.this)
            .setMessage("학습을 종료하시겠습니까?")
            .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    savePoint();
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            })
            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return ;
                }
            })
            .show();

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount=0.5f;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
    }
}
