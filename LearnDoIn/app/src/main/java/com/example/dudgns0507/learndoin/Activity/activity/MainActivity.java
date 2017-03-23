package com.example.dudgns0507.learndoin.Activity.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dudgns0507.learndoin.Activity.AddDdayDialog;
import com.example.dudgns0507.learndoin.Activity.AddItemDialog;
import com.example.dudgns0507.learndoin.Activity.FileManage;
import com.example.dudgns0507.learndoin.Activity.model.ListData;
import com.example.dudgns0507.learndoin.Activity.model.Word;
import com.example.dudgns0507.learndoin.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pyh42 on 2016-10-24.
 */

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private final static  String TAG = "MainActivity";
    public ListView mListView = null;
    public ListViewAdapter mAdapter = null;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    public List<Word> wordList;

    @BindView(R.id.list_menu) ImageView list_menu;
    @BindView(R.id.list_top) ImageView list_top;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }

    void init() {

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.stopNestedScroll();
            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );

        Glide.with(this).load(R.drawable.back_1).into(list_top);
//
//        putBitmap(R.id.list_top, R.drawable.back_1, 2);
//        readFile("Day21-35.txt", "Day21-35");
//
//        mListView = (ListView)findViewById(R.id.word_list);
//        mAdapter = new ListViewAdapter(this);
//        mListView.setAdapter(mAdapter);
//        loadList();
//
//        setFont("NanumBarunGothicBold.otf", R.id.dday_text);
//        setFont("NanumBarunGothic.otf", R.id.dday_title);
    }

    @OnClick(R.id.list_menu) void openMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.list_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    void readFile(String fileName, String name) {
        String text = null;

        try {
            InputStream inputStream = getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            text = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        wordList = FileManage.parse(text, "\t");

        SharedPreferences sp = getSharedPreferences("wordlist", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();

        if(sp.getBoolean(name, false) == false) {
            edit.putInt("size", sp.getInt("size", 0) + 1);
        }
        int k = sp.getInt("size", 0);
        edit.putString("title_" + k, name);
        edit.putInt("size_" + k, wordList.size());
        edit.putBoolean(name, true);

        for (int i = 0;i<wordList.size();i++) {
            edit.putString(name + "_" + i + "_w", wordList.get(i).getWordContent());
            edit.putString(name + "_" + i + "_m", wordList.get(i).getMeaning());
        }
        edit.commit();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_setting :
                break;
            case R.id.menu_add_dday :
                AddDdayDialog addDdayDialog = new AddDdayDialog(MainActivity.this);
                addDdayDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

                WindowManager.LayoutParams lp2 = new WindowManager.LayoutParams();
                lp2.copyFrom(addDdayDialog.getWindow().getAttributes());
                lp2.width = (int) (getWindowManager().getDefaultDisplay().getWidth() * 0.9);
                lp2.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp2.dimAmount=0.5f;

                addDdayDialog.getWindow().setAttributes(lp2);
                addDdayDialog.getWindow().setBackgroundDrawableResource(R.color.white);
                addDdayDialog.show();
                break;
            case R.id.menu_add_wordlist :
                AddItemDialog addItemDialog = new AddItemDialog(MainActivity.this);
                addItemDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(addItemDialog.getWindow().getAttributes());
                lp.width = (int) (getWindowManager().getDefaultDisplay().getWidth() * 0.9);
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.dimAmount=0.5f;

                addItemDialog.getWindow().setAttributes(lp);
                addItemDialog.getWindow().setBackgroundDrawableResource(R.color.white);
                addItemDialog.show();
                break;
            case R.id.menu_help :
                break;
            case R.id.menu_logout :
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("로그아웃 하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences sp = getSharedPreferences("dudgns0507.learndoin", MODE_PRIVATE);
                                SharedPreferences.Editor edit = sp.edit();

                                edit.remove("id");
                                edit.remove("pw");

                                edit.commit();

                                finish();
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }

        return false;
    }

    void cleanList() {
        while(true) {
            if(mAdapter.mListData.isEmpty()) {
                break;
            }
            mAdapter.remove(0);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    void loadList() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ListData listData = mAdapter.mListData.get(position);
                        Intent mIntent = new Intent(MainActivity.this, StudyActivity.class);
                        mIntent.putExtra("title", listData.word_title);
                        startActivityForResult(mIntent, 0);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });

                SharedPreferences sp = getSharedPreferences("wordlist", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();

                int size = sp.getInt("size", 0);
                for (int i = 1; i <= size; i++) {
                    String title = sp.getString("title_" + i, null);
                    if (sp.getBoolean(title, false) == true) {
                        mAdapter.addItem(title, "", sp.getInt("size_" + size, 0), sp.getInt(title + "_checkpoint", 0), 1);
                    }
                }
                edit.commit();
            }
        });

        thread.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            cleanList();
            loadList();
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("앱을 종료하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        moveTaskToBack(true);
                        finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static class ViewHolder {
        public TextView word_title;
        public TextView study_process;
        public TextView latest;
        public ImageView state;
        public LinearLayout layout;
    }

    public class ListViewAdapter extends BaseAdapter {

        private Context mContext = null;
        private ArrayList<ListData> mListData = new ArrayList<ListData>();

        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;

            if(convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.wordlist_item, null);

                holder.word_title = (TextView)convertView.findViewById(R.id.word_title);
                holder.state = (ImageView)convertView.findViewById(R.id.item_state);
                holder.latest = (TextView)convertView.findViewById(R.id.word_latest);
                holder.study_process = (TextView)convertView.findViewById(R.id.word_process);
                holder.layout = (LinearLayout)convertView.findViewById(R.id.list_item_root);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            ListData mData = mListData.get(position);

            holder.word_title.setText(mData.word_title);
            if(!mData.latest.equals("")) {
                holder.latest.setText(" - " + mData.latest);
            }
            holder.study_process.setText(mData.all + "개의 단어중 " + mData.process + "개 학습");

            Typeface font_light = Typeface.createFromAsset(getAssets(), "NanumBarunGothicLight.otf");
            Typeface font_normal = Typeface.createFromAsset(getAssets(), "NanumBarunGothic.otf");
            holder.word_title.setTypeface(font_normal);
            holder.latest.setTypeface(font_light);
            holder.study_process.setTypeface(font_light);

            Drawable d = null;

            switch (mData.tag) {
                case 1: d = getResources().getDrawable(R.drawable.circle_1); break;
                case 2: d = getResources().getDrawable(R.drawable.circle_2); break;
                case 3: d = getResources().getDrawable(R.drawable.circle_3); break;
                case 4: d = getResources().getDrawable(R.drawable.circle_4); break;
                case 5: d = getResources().getDrawable(R.drawable.circle_5); break;
                case 6: d = getResources().getDrawable(R.drawable.circle_gray); break;
            }
            
            holder.state.setImageDrawable(d);
            return convertView;
        }

        public void addItem(String title, String latest, int total, int now, int tag){
            ListData addInfo = null;
            addInfo = new ListData();

            addInfo.tag = tag;
            addInfo.all = total;
            addInfo.process = now;
            addInfo.latest = latest;
            addInfo.word_title = title;

            mListData.add(addInfo);
        }

        public void remove(int position){
            mListData.remove(position);
            dataChange();
        }

        public void dataChange(){
            mAdapter.notifyDataSetChanged();
        }
    }
}
