package com.example.dudgns0507.learndoin.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.dudgns0507.learndoin.R;

/**
 * Created by pyh42 on 2016-11-12.
 */

public class AddItemDialog extends Dialog {

    private View view;

    public AddItemDialog(Context context) {
        super(context);
    }

    public AddItemDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected AddItemDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    void init() {
        Button add_file_btn = (Button) view.findViewById(R.id.add_item_btn);
        add_file_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(this.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount=0.5f;

        this.getWindow().setAttributes(lp);
        this.getWindow().setBackgroundDrawableResource(R.color.white);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        Runtime.getRuntime().gc();
    }
}
