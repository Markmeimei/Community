package com.example.community.activity.Information;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.community.R;

/**
 * Author：Mark
 * Date：2016/4/7 0007
 * Tell：15006330640
 *
 * 行业场所、单位信息
 */
public class Information_Company extends AppCompatActivity{
    private String[] areas = new String[]{"全部","玉兰香苑", "张江地铁站", "金科路", "张江路", "紫薇路", "香楠小区" };
    private boolean[] areaState=new boolean[]{true, false, false, false, false, false,false };
    private ListView areaCheckListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_info_company);
    }
    /** 多选框弹出菜单窗口
    * @author xmz
    **/
            class CheckBoxClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            AlertDialog ad = new AlertDialog.Builder(Information_Company.this)
            .setTitle("选择区域")
            .setMultiChoiceItems(areas,areaState,new DialogInterface.OnMultiChoiceClickListener(){
                public void onClick(DialogInterface dialog,int whichButton, boolean isChecked){
                    //点击某个区域
                    }
                }).setPositiveButton("确定",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog,int whichButton){
                    String s = "您选择了:";
                    for (int i = 0; i < areas.length; i++){
                        if (areaCheckListView.getCheckedItemPositions().get(i)){
                            s += i + ":"+ areaCheckListView.getAdapter().getItem(i)+ " ";
                            }else{
                            areaCheckListView.getCheckedItemPositions().get(i,false);
                            }
                        }
                    if (areaCheckListView.getCheckedItemPositions().size() > 0){
                        Toast.makeText(Information_Company.this, s, Toast.LENGTH_LONG).show();
                        }else{
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
