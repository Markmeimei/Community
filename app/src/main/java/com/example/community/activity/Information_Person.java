package com.example.community.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.community.R;
import com.example.community.bean.Submit_Person;
import com.example.community.constant.ConstantString;
import com.example.community.constant.ConstantURL;
import com.example.community.dialog.ActionSheetDialog;
import com.example.community.dialog.CityDialog;
import com.example.community.utils.DemoUtils;
import com.example.community.utils.ToolUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.PostFormRequest;
import com.zhy.http.okhttp.request.RequestCall;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Author：Mark
 * Date：2016/4/1 0001
 * Tell：15006330640
 */
public class Information_Person extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    //    @Bind(R.id.info_header)
//    AppBarLayout infoHeader;
    @Bind(R.id.bottom_btn)
    Button bottomBtn;
    @Bind(R.id.bottom_ll)
    LinearLayout bottomLl;
    //    @Bind(R.id.info_img_right2)
//    ImageView infoImgRight2;
    @Bind(R.id.info_person_relation) // 关系
            EditText infoPersonRelation;
    @Bind(R.id.info_person_name) // 姓名
            EditText infoPersonName;
    @Bind(R.id.info_person_m)
    RadioButton infoPersonM;  // 性别
    @Bind(R.id.info_person_w)
    RadioButton infoPersonW;
    @Bind(R.id.info_person_sex_rad)
    RadioGroup infoPersonSexRad;
    @Bind(R.id.info_person_nation) // 民族
    Spinner infoPersonNation;
    @Bind(R.id.info_person_accent)  // 口音
            EditText infoPersonAccent;
    @Bind(R.id.info_person_id)  // 身份证号
            EditText infoPersonId;
    //    @Bind(R.id.info_img_right3)
//    ImageView infoImgRight3;
    @Bind(R.id.info_person_census) // 户籍
            TextView infoPersonCensus;
    @Bind(R.id.info_person_company)  // 单位
            EditText infoPersonCompany;
    //    @Bind(R.id.info_img_right5)
//    ImageView infoImgRight5;
    @Bind(R.id.info_person_employing_txt) // 用工方式
            TextView infoPersonEmployingTxt;
    @Bind(R.id.info_person_employing_rel)
    RelativeLayout infoPersonEmployingRel;
    @Bind(R.id.info_person_tel) // 联系方式
            EditText infoPersonTel;
    @Bind(R.id.info_person_flow_y)
    RadioButton infoPersonFlowY;
    @Bind(R.id.info_person_flow_n)
    RadioButton infoPersonFlowN;
    @Bind(R.id.info_person_flow_rad)
    RadioGroup infoPersonFlowRad;
    @Bind(R.id.info_person_foreign_y)
    RadioButton infoPersonForeignY;
    @Bind(R.id.info_person_foreign_n)
    RadioButton infoPersonForeignN;
    @Bind(R.id.info_person_foreign_rad)
    RadioGroup infoPersonForeignRad;
    @Bind(R.id.info_person_attention_y)
    RadioButton infoPersonAttentionY;
    @Bind(R.id.info_person_attention_n)
    RadioButton infoPersonAttentionN;
    @Bind(R.id.info_person_attention_rad)
    RadioGroup infoPersonAttentionRad;
    @Bind(R.id.info_person_comment)
    EditText infoPersonComment;
    @Bind(R.id.info_person_census_rel)
    RelativeLayout infoPersonCensusRel;


    //
    private Context context;
    private SharedPreferences preferences;
    private String sex = "1"; // 性别
    private String person_flow = "0"; // 流动人口
    private String person_foreign = "0"; // 外籍人员
    private String person_attention = "0"; // 关注
    private String person_employing = "0"; // 用工方式
    private String person_nation = "0"; // 民族
    private int lastid = 0;
    private int error_tel = 0; //  错误码
    private int error_id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_info_person);
        ButterKnife.bind(this);
        initConstants();
        initViews();
    }

    private void initConstants() {
        context = Information_Person.this;
        preferences = getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        lastid = super.getIntent().getIntExtra("lastid", 0);
    }

    private void initViews() {
        toolbar.setNavigationIcon(R.mipmap.head_title_return);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle("住户信息");
        setSupportActionBar(toolbar);
        bottomBtn.setText("提交");
        // 民族
        infoPersonNation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                person_nation = String.valueOf(position+1);
                Log.e("选中民族", String.valueOf(position+1));//这里就是获取值

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // 性别
        infoPersonM.setChecked(true);
        infoPersonSexRad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.info_person_m) {
                    sex = "1";
                } else {
                    sex = "2";
                }
            }
        });
        // 流动人口
        infoPersonFlowN.setChecked(true);
        infoPersonFlowRad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.info_person_flow_y) {
                    person_flow = "1";
                } else {
                    person_flow = "0";
                }
            }
        });
        // 外籍人员
        infoPersonForeignN.setChecked(true);
        infoPersonForeignRad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.info_person_foreign_y) {
                    person_foreign = "1";
                } else {
                    person_foreign = "0";
                }
            }
        });
        // 关注
        infoPersonAttentionN.setChecked(true);
        infoPersonAttentionRad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.info_person_attention_y) {
                    person_attention = "1";
                } else {
                    person_attention = "0";
                }
            }
        });

        // 手机号验证
        infoPersonTel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
// 此处为得到焦点时的处理内容
                } else {
// 此处为失去焦点时的处理内容
                    if (DemoUtils.checkPhone(infoPersonTel.getText().toString())) {
                        error_tel = 0;
                    } else {
                        error_tel = 1;
                        infoPersonTel.setError("请检查手机号");
                    }

                }
            }
        });
        // 身份证验证
        infoPersonId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                } else {

                    try {
                        if (DemoUtils.IDCardValidate(infoPersonId.getText().toString())) {
                            error_id = 0;
                        } else {
                            error_id = 2;
                            infoPersonId.setError("请检查身份证号码");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }

    @OnClick({R.id.bottom_btn, R.id.info_person_employing_rel, R.id.info_person_census_rel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_btn:
                // 提交
                if (checkInfo()) {
                    submitInfo();
                }
                break;
            case R.id.info_person_employing_rel:
                // 用工方式
                showProperty();
                break;
            case R.id.info_person_census_rel:
                // 户籍
                showCensus();
                break;
        }
    }


    /**
     * 检测输入
     *
     * @return
     */
    private boolean checkInfo() {
        if (error_tel == 1) {
            Toast.makeText(context, "请检查手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (error_id == 2) {
            Toast.makeText(context, "请检查身份证号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (infoPersonRelation.getText().toString().equals("")) {
            Toast.makeText(context, "请填写关系", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (infoPersonName.getText().toString().equals("")) {
            Toast.makeText(context, "请填写姓名", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (person_nation.equals("0")) {
            Toast.makeText(context, "请选择民族", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (infoPersonId.getText().toString().equals("")) {
            Toast.makeText(context, "请填写身份证号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (infoPersonCensus.getText().toString().equals("")) {
            Toast.makeText(context, "请选择户口地", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (infoPersonCompany.getText().toString().equals("")) {
            Toast.makeText(context, "请填写单位", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (infoPersonEmployingTxt.getText().toString().equals("")) {
            Toast.makeText(context, "请选择用工方式", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (infoPersonTel.getText().toString().equals("")) {
            Toast.makeText(context, "请填写联系方式", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 提交信息
     */
    private void submitInfo() {

        Map<String, String> requestParams = new HashMap<>();
//        requestParams.put("pid", String.valueOf(lastid));
        requestParams.put("relation", infoPersonRelation.getText().toString());
        requestParams.put("name", infoPersonName.getText().toString());
        requestParams.put("sex", sex);
        requestParams.put("minzu", person_nation);
        requestParams.put("jiguan_1", ToolUtils.ProviceName);
        requestParams.put("jiguan_2", ToolUtils.CityName);
        requestParams.put("jiguan_3", ToolUtils.DistrictName);
        requestParams.put("kouyin", infoPersonAccent.getText().toString());
        requestParams.put("sfz", infoPersonId.getText().toString());
        requestParams.put("hkd_1", ToolUtils.ProviceName);
        requestParams.put("hkd_2", ToolUtils.CityName);
        requestParams.put("hkd_3", ToolUtils.DistrictName);
        requestParams.put("gzdw", infoPersonCompany.getText().toString());
        requestParams.put("ygtype", person_employing);
        requestParams.put("mobi", infoPersonTel.getText().toString());
        requestParams.put("is_ld", person_flow);
        requestParams.put("is_wj", person_foreign);
        requestParams.put("is_gz", person_attention);
//        requestParams.put("adduser","1");
//        requestParams.put("addtime", "1");
//        requestParams.put("addip",preferences.getString(ConstantString.UID, ""));
        requestParams.put("bz", infoPersonComment.getText().toString());
//        requestParams.put("Lastid", String.valueOf(lastid));

//        http://dgpt.sdzxkj.cn/json/doing.php?act=addjz&uid=3&lastid=
        RequestCall call = new PostFormRequest(ConstantURL.COMM + "?act=addjz&uid=" + preferences.getString(ConstantString.UID, "") + "&lastid=" + lastid, null, requestParams, null, null).build();
        call.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(context, "上报失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void inProgress(float progress) {
                super.inProgress(progress);
                Log.e("tag", "上传进度" + progress);
            }

            @Override
            public void onResponse(String response) {
                Log.e("tag", "提交数据" + response);
                Gson gson = new Gson();
                Submit_Person submit_person = gson.fromJson(response, Submit_Person.class);
                if (submit_person != null) {
                    if (submit_person.getState().equals("ok")) {
                        Toast.makeText(context, "提交成功！如果继续添加人员请继续填写，如果添加完毕请返回上一层！", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(context, Information_Person.class)
                                .putExtra("lastid", lastid));
                        finish();
                    } else {
                        Toast.makeText(context, "提交失败！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    /**
     * 选择 用工方式
     */
    private void showProperty() {
        new ActionSheetDialog(context)
                .builder()
                .setCancelable(false)
                .setTitle("用工方式")
                .setCanceledOnTouchOutside(false)
                .addSheetItem("固定", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                person_employing = "1";
                                infoPersonEmployingTxt.setText("固定");
                            }
                        })
                .addSheetItem("长期", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                person_employing = "2";
                                infoPersonEmployingTxt.setText("长期");
                            }
                        })
                .addSheetItem("临时", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                person_employing = "3";
                                infoPersonEmployingTxt.setText("临时");
                            }
                        })
                .show();
    }

    /**
     * 选择 户籍
     */
    private void showCensus() {
        CityDialog cityDialog = new CityDialog(context, R.style.city_dialog, infoPersonCensus);
        Window dialogWindow = cityDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.9); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);
        cityDialog.show();
    }
}
