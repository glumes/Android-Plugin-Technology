package com.glumes.hooktech;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import hook_ams.AmsHookUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.HookAms)
    Button mHookAms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mHookAms.setOnClickListener(this);
        AmsHookUtil.hookAms();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.HookAms:
                startActivity(new Intent(MainActivity.this,HookActivity.class));
                break;
            default:
                break;
        }
    }
}
