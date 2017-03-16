package com.glumes.load_plugin_resource;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoadPluginResourceActivity extends AppCompatActivity {

    String apkPath = Environment.getExternalStorageDirectory() + File.separator + "plugin-package-debug.apk";

    Context mContext;
    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.laodBasicAttr)
    Button mLaodBasicAttr;
    @BindView(R.id.loadDrawable)
    Button mLoadDrawable;

    private PluginResourceManager mResourceManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        mResourceManager = new PluginResourceManager(mContext,apkPath) ;
    }


    @OnClick({R.id.image, R.id.text, R.id.laodBasicAttr, R.id.loadDrawable})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image:
                Drawable drawable = mResourceManager.getPluginDrawable(R.drawable.)
                break;
            case R.id.text:
                break;
            case R.id.laodBasicAttr:
                break;
            case R.id.loadDrawable:
                break;
        }
    }
}
