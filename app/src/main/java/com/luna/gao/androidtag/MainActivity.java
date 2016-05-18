package com.luna.gao.androidtag;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.luna.gao.androidtag_core.AndroidTag;

public class MainActivity extends AppCompatActivity {

    AndroidTag tag_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tag_1 = (AndroidTag) findViewById(R.id.tag_1);
        tag_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("onclick", "click");
            }
        });
    }
}
