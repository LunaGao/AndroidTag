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
        tag.setTagTitleText("hello");
        tag.setTagOnClickTitleText("click");
        tag.setTagSelectedTitleText("selected");
        flexbox.addView(tag);
    }
}
