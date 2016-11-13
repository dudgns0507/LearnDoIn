package com.example.dudgns0507.learndoin.Activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dudgns0507.learndoin.R;

/**
 * Created by pyh42 on 2016-11-13.
 */

public class AddDdayDialog extends Dialog {
    public AddDdayDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected AddDdayDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public AddDdayDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dday_dialog);
        init();
    }

    void init() {
        Button add_dday_btn = (Button)findViewById(R.id.add_dday_btn);
        add_dday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        Runtime.getRuntime().gc();
    }
}
