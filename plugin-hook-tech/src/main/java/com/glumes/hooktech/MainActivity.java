package com.glumes.hooktech;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import hook_activity_manager_service.ActivityManagerServiceHookUtil;
import hook_activity_thread.ActivityThreadHookUtil;
import hook_package_manager_service.PackageManagerServiceHookUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.HookAms)
    Button mHookAms;
    @BindView(R.id.HookPms)
    Button mHookPms;

    private Context mContext ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mHookAms.setOnClickListener(this);
        mHookPms.setOnClickListener(this);

        mContext = this ;
        ActivityManagerServiceHookUtil.hookAms();
        ActivityThreadHookUtil atHookUtil = new ActivityThreadHookUtil();
        atHookUtil.hookActivityThreadHandler();

        PackageManagerServiceHookUtil.hookPms(mContext);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.HookAms:
                startActivity(new Intent(MainActivity.this, HookedActivity.class));
                break;
            case R.id.HookPms:
                getPackageManager().getInstalledApplications(0);
                break;
            default:
                break;
        }
    }
}
