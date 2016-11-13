package com.example.dudgns0507.learndoin.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dudgns0507.learndoin.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by pyh42 on 2016-10-24.
 */

public class ListActivity extends AppCompatActivity implements AsyncResponse {

    private final static  String TAG = "ListActivity";
    public ListView mListView = null;
    public ListViewAdapter mAdapter = null;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    public static List<WordClass> wordList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        init();
    }

    void init() {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.hide();

        ImageView list_menu = (ImageView)findViewById(R.id.list_menu);
        list_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOptionsMenu();
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DbConnection dbConnection = new DbConnection();
                dbConnection.getData("");
            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );

        putBitmap(R.id.list_top, R.drawable.back_1, 2);

        mListView = (ListView)findViewById(R.id.word_list);
        mAdapter = new ListViewAdapter(this);
        loadList();

        setFont("NanumBarunGothic.otf", R.id.dday_text);
        setFont("NanumBarunGothic.otf", R.id.dday_title);

        readFile("Day21-35.txt", "Day21-35");
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

            edit.putString("title_" + sp.getInt("size", 0), name);
            edit.putInt("size_" + sp.getInt("size", 0), wordList.size());
            edit.putBoolean(name, true);

            for (int i = 0;i<wordList.size();i++) {
                edit.putString(name + "_" + i + "_w", wordList.get(i).wordContent);
                edit.putString(name + "_" + i + "_m", wordList.get(i).meaning);
            }
        }
    }
    @Override
    public void openOptionsMenu() {

        final Window window = getWindow();
        final View decor = window.getDecorView();

        final View view = decor.findViewById(R.id.action_bar);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        if (view instanceof Toolbar) {
            final Toolbar toolbar = (Toolbar) view;
            toolbar.setX(size.x - view.getWidth() + 10);
            toolbar.showOverflowMenu();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_setting :
                break;
            case R.id.menu_add_dday :
                AddDdayDialog addDdayDialog = new AddDdayDialog(ListActivity.this);
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
            case R.id.menu_add_wrodlist :
                AddItemDialog addItemDialog = new AddItemDialog(ListActivity.this);
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
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(String output) {
        cleanList();
        loadList();
    }

    void cleanList() {
        for(int i = 0 ;i < mAdapter.mListData.size() ; i++) {
            mAdapter.remove(0);
        }
        loadList();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    void loadList() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mListView.setAdapter(mAdapter);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ListData listData = mAdapter.mListData.get(position);
                        Intent mIntent = new Intent(ListActivity.this, StudyActivity.class);
                        mIntent.putExtra("title", listData.word_title);
                        startActivity(mIntent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });

                mAdapter.addItem("Day21-25", "", 300, 140, 1);

                SharedPreferences sp = getSharedPreferences("wordlist", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();

                int size = sp.getInt("size", 0);
                Log.w(TAG, size + "");
                for (int i = 1; i <= size; i++) {
                    String title = sp.getString("title_" + i, "");
                    if (sp.getBoolean(title, false) == true) {
                        mAdapter.addItem(title, "", sp.getInt("size_" + size, 0), 0, 1);
                    }
                }
            }
        });

        thread.start();
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
    protected void onDestroy() {
        Log.w(TAG, "Destroy background");
        recycleView(R.id.list_top);
        super.onDestroy();
    }

    private void recycleView(int id) {
        ImageView view = (ImageView)findViewById(id);

        Drawable d = view.getDrawable();
        if(d instanceof BitmapDrawable) {
            Bitmap b = ((BitmapDrawable) d).getBitmap();
            view.setImageBitmap(null);
            b.recycle();
            b = null;
        }
        d.setCallback(null);
        System.gc();
        Runtime.getRuntime().gc();
    }

    private void putBitmap(int imageViewId, int drawableId, int scale) {
        ImageView imageView = (ImageView)findViewById(imageViewId);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = scale;

        imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), drawableId, options));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
            holder.latest.setText(" - " + mData.latest);
            holder.study_process.setText(mData.all + "개의 단어중 " + mData.process + "개 학습");

            Typeface font_light = Typeface.createFromAsset(getAssets(), "NanumBarunGothicLight.otf");
            Typeface font_normal = Typeface.createFromAsset(getAssets(), "NanumBarunGothic.otf");
            holder.word_title.setTypeface(font_normal);
            holder.latest.setTypeface(font_light);
            holder.study_process.setTypeface(font_light);

            Drawable d = null;

            switch (mData.tag) {
                case 1: d = getDrawable(R.drawable.circle_1); break;
                case 2: d = getDrawable(R.drawable.circle_2); break;
                case 3: d = getDrawable(R.drawable.circle_3); break;
                case 4: d = getDrawable(R.drawable.circle_4); break;
                case 5: d = getDrawable(R.drawable.circle_5); break;
                case 6: d = getDrawable(R.drawable.circle_gray); break;
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
