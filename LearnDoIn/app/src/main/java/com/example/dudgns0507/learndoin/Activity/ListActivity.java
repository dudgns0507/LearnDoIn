package com.example.dudgns0507.learndoin.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dudgns0507.learndoin.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by pyh42 on 2016-10-24.
 */

public class ListActivity extends AppCompatActivity {

    private final static  String TAG = "ListActivity";
    public ListView mListView = null;
    public ListViewAdapter mAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        init();
    }

    void init() {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.hide();

        putBitmap(R.id.list_top, R.drawable.back_1, 1);

        mListView = (ListView)findViewById(R.id.word_list);

        mAdapter = new ListViewAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        mAdapter.addItem("단어장1", "1시간전", 300, 140, 1);
        mAdapter.addItem("단어장1", "1시간전", 300, 140, 1);
        mAdapter.addItem("단어장1", "1시간전", 300, 140, 1);
        mAdapter.addItem("단어장1", "1시간전", 300, 140, 1);

        setFont("NanumBarunGothicLight.otf", R.id.dday_text);
        setFont("NanumBarunGothicLight.otf", R.id.dday_title);
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

    public class ViewHolder {
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

            setFont("NanumBarunGothicLight.otf", R.id.word_process);
            setFont("NanumBarunGothicLight.otf", R.id.word_latest);
            setFont("NanumBarunGothicLight.otf", R.id.word_title);

            Drawable d = null;

            switch (mData.tag) {
                case 1: d = getDrawable(R.drawable.circle_green); break;
                case 2: d = getDrawable(R.drawable.circle_green); break;
                case 3: d = getDrawable(R.drawable.circle_green); break;
                case 4: d = getDrawable(R.drawable.circle_green); break;
                case 5: d = getDrawable(R.drawable.circle_green); break;
                case 6: d = getDrawable(R.drawable.circle_green); break;
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

        void setFont(String path, int res) {
            Typeface font = Typeface.createFromAsset(getAssets(), path);

            if (findViewById(res) instanceof TextView) {
                TextView mTextView = (TextView) findViewById(res);
                mTextView.setTypeface(font);
            }
            if (findViewById(res) instanceof Button) {
                Button mButton = (Button) findViewById(res);
                mButton.setTypeface(font);
            }
        }
    }
}
