package com.example.dudgns0507.learndoin.Activity.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.dudgns0507.learndoin.Activity.model.Word;
import com.example.dudgns0507.learndoin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pyh42 on 2016-10-24.
 */

public class StudyActivity extends AppCompatActivity {

    private static final String TAG = "StudyActivity";
    private TextView word, mean;
    private int cnt;
    private List<Word> wordList;
    private String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        init();
    }

    void init() {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.hide();

        Intent intent = getIntent();
        String name = intent.getStringExtra("title");
        getWordList(name);

        Button next_btn = (Button)findViewById(R.id.study_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextWord();
            }
        });

        word = (TextView)findViewById(R.id.study_text);
        mean = (TextView)findViewById(R.id.study_text2);

        word.setText(wordList.get(cnt).getWordContent());
        mean.setText(wordList.get(cnt++).getMeaning());
    }

    void getWordList(String name) {
        SharedPreferences sp = getSharedPreferences("wordlist", MODE_PRIVATE);

        int size = sp.getInt("size", 0);
        wordList = new ArrayList<>();

        for(int i = 1; i <= size;i++) {
            title = sp.getString("title_" + i, null);
            if(title.equals(name)) {
                if(sp.getInt("size_" + i, 0) != 0) {
                    for(int j = 0 ; j < sp.getInt("size_" + i, 0);j++) {
                        String word = sp.getString(title + "_" + j + "_w", null);
                        String mean = sp.getString(title + "_" + j + "_m", null);

                        Word wordClass = new Word();
                        wordClass.add(word, mean);
                        wordList.add(wordClass);
                    }
                }
            }
        }

        cnt = sp.getInt(title + "_checkpoint", 0);
    }

    void nextWord() {
        word.setText(wordList.get(cnt).getWordContent());
        mean.setText(wordList.get(cnt++).getMeaning());
    }

    void savePoint() {
        SharedPreferences sp = getSharedPreferences("wordlist", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();

        edit.putInt(title + "_checkpoint", cnt-1);
        edit.commit();
    }

    @Override
    public void onBackPressed() {
        AlertDialog dialog = new AlertDialog.Builder(StudyActivity.this)
            .setMessage("학습을 종료하시겠습니까?")
            .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    savePoint();
                    setResult(RESULT_OK);
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
