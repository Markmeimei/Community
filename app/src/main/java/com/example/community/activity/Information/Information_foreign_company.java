package com.example.community.activity.Information;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.example.community.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 境外单位
 * <p>
 * Author:Eron
 * Date: 2016/4/10 0010
 * Time: 8:28
 */
public class Information_foreign_company extends AppCompatActivity {

    @Bind(R.id.toolbar_foreign_company)
    Toolbar foreignCompanyToolbar;

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉ToolBar
        setContentView(R.layout.activity_info_foreign_company);
        ButterKnife.bind(this);

        initConstant();
        initData();
        initView();
        initEvent();

    }

    private void initConstant() {
        mContext = Information_foreign_company.this;
    }

    private void initData() {

    }

    private void initView() {
        foreignCompanyToolbar.setNavigationIcon(R.mipmap.head_title_return);
        foreignCompanyToolbar.setTitle("境外单位");
        foreignCompanyToolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.white_));
    }

    private void initEvent() {
        foreignCompanyToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
