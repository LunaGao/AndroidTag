package com.luna.gao.androidtag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.flexbox.FlexboxLayout;
import com.luna.gao.androidtag_core.AndroidTag;

public class MainActivity extends AppCompatActivity {

    AndroidTag tag_1;
    FlexboxLayout flexbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flexbox = (FlexboxLayout) findViewById(R.id.flexbox);

//        tag_1 = (AndroidTag) findViewById(R.id.tag_1);
//        tag_1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("onclick", "click");
//            }
//        });

        AndroidTag tag = null;
        try {
            tag = new AndroidTag(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //默认颜色和背景
        tag.setTagTitleText("create by code default");
        tag.setTagBorderColor(this.getResources().getColor(R.color.main_blue));
        tag.setTagBorderWidth(1);
        tag.setTagTitleColor(this.getResources().getColor(R.color.text_gray));


        //选中后颜色和背景
        tag.setTagSelectedTitleText("create by code selected");
        tag.setTagSelectedBackgroundColor(this.getResources().getColor(R.color.main_blue));
        tag.setTagSelectedTitleColor(this.getResources().getColor(R.color.main_white));

        //点击颜色和背景
        tag.setTagOnClickTitleText("create by code click");
        tag.setTagOnClickBackgroundColor(this.getResources().getColor(R.color.text_gray));
        tag.setTagOnClickTitleColor(this.getResources().getColor(R.color.main_blue));

        flexbox.addView(tag);
    }
}
