package com.example.community.activity.Information;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.example.community.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author：Mark
 * Date：2016/4/7 0007
 * Tell：15006330640
 * <p/>
 * 行业场所、单位信息
 */
public class Information_Company extends AppCompatActivity {

    @Bind(R.id.toolbar_company)
    Toolbar companyToolbar;

    private String[] areas = new String[]{"全部", "玉兰香苑", "张江地铁站", "金科路", "张江路", "紫薇路", "香楠小区"};
    private boolean[] areaState = new boolean[]{true, false, false, false, false, false, false};
    private ListView areaCheckListView;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉ToolBar
        super.setContentView(R.layout.activity_info_company);
        ButterKnife.bind(this);

        initConstant();
        initData();
        initView();
        initEvent();
    }

    private void initConstant() {
        mContext = Information_Company.this;
    }

    private void initData() {

    }

    private void initView() {
        companyToolbar.setNavigationIcon(R.mipmap.head_title_return);
        companyToolbar.setTitle("场所单位");
    }

    private void initEvent() {
        companyToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 多选框弹出菜单窗口
     *
     * @author xmz
     **/
    class CheckBoxClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            AlertDialog ad = new AlertDialog.Builder(Information_Company.this)
                    .setTitle("选择区域")
                    .setMultiChoiceItems(areas, areaState, new DialogInterface.OnMultiChoiceClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton, boolean isChecked) {
                            //点击某个区域
                        }
                    }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String s = "您选择了:";
                            for (int i = 0; i < areas.length; i++) {
                                if (areaCheckListView.getCheckedItemPositions().get(i)) {
                                    s += i + ":" + areaCheckListView.getAdapter().getItem(i) + " ";
                                } else {
                                    areaCheckListView.getCheckedItemPositions().get(i, false);
                                }
                            }
                            if (areaCheckListView.getCheckedItemPositions().size() > 0) {
                                Toast.makeText(Information_Company.this, s, Toast.LENGTH_LONG).show();
                            } else {
                                //没有选择
                            }
                            dialog.dismiss();
                        }
                    }).setNegativeButton("取消", null).create();
            areaCheckListView = ad.getListView();
            ad.show();
        }
    }

}
