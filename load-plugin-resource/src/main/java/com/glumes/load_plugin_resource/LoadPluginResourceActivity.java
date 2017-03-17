package com.glumes.load_plugin_resource;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.glumes.load_plugin_resource.util.AttrTypeConst;

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
    @BindView(R.id.loadDialog)
    Button mLoadDialog;

    private PluginResourceManager mResourceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        mResourceManager = new PluginResourceManager(mContext, apkPath);
    }


    @OnClick({R.id.image, R.id.text, R.id.laodBasicAttr, R.id.loadDrawable, R.id.loadDialog})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image:
                break;
            case R.id.text:
                break;
            case R.id.laodBasicAttr:
                changeTextAttr();
                break;
            case R.id.loadDrawable:
                changeImageAttr();
                break;
            case R.id.loadDialog:

                break;
            default:
                break;
        }
    }

    public void changeTextAttr(){
        int textColor = mResourceManager.getPluginColor(
                mResourceManager.getResourceId("plugin_color_red", AttrTypeConst.ATTRTYPE_COLOR)
        );
        float textSize = mResourceManager.getPluginDimension(
                mResourceManager.getResourceId("plugin_dimension_text_size", AttrTypeConst.ATTRTYPE_DIMEN)
        );
        String text = mResourceManager.getPluginString(
                mResourceManager.getResourceId("plugin_string_text", AttrTypeConst.ATTRTYPE_STRING)
        );
        mText.setTextColor(textColor);
        mText.setTextSize(textSize);
        mText.setText(text);
    }

    public void changeImageAttr(){
        Drawable background = mResourceManager.getPluginDrawable(
                mResourceManager.getResourceId("image_bg", AttrTypeConst.ATTRTYPE_DRAWABLE)
        );
        mImage.setImageDrawable(background);
    }

    public void showPluginDialog(){
        View view = mResourceManager.getPluginLayoutView(
                mResourceManager.getResourceId("")
        )
    }
}
