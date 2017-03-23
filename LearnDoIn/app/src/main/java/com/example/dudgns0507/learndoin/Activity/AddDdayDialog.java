package com.example.dudgns0507.learndoin.Activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import com.example.dudgns0507.learndoin.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pyh42 on 2016-11-13.
 */

public class AddDdayDialog extends Dialog {

    private static final String TAG = "AddDdayDialog";

    @BindView(R.id.add_dday_btn) Button add_dday_btn;

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
        ButterKnife.bind(this);

        init();
    }

    void init() {
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(this.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount=0.5f;

        int width = (int)(getContext().getResources().getDisplayMetrics().widthPixels*0.90);
        int height = (int)(getContext().getResources().getDisplayMetrics().heightPixels*0.60);

        this.getWindow().setAttributes(lp);
        this.getWindow().setBackgroundDrawableResource(R.color.white);
        this.getWindow().setLayout(width, height);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        Runtime.getRuntime().gc();
    }
}
