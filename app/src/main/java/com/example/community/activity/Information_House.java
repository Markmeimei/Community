package com.example.community.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.community.R;
import com.example.community.bean.Submit_House;
import com.example.community.constant.ConstantString;
import com.example.community.constant.ConstantURL;
import com.example.community.dao.GetDataDao;
import com.example.community.dao.GetDataDaoImpl;
import com.example.community.dao.LoadCallBack;
import com.example.community.dao.RequestVo;
import com.example.community.dialog.ActionSheetDialog;
import com.example.community.dialog.ActionSheetDialog.OnSheetItemClickListener;
import com.example.community.dialog.ActionSheetDialog.SheetItemColor;
import com.example.community.dialog.CityDialog;
import com.example.community.utils.DateUtils;
import com.example.community.utils.DemoUtils;
import com.example.community.utils.PictureUtils;
import com.example.community.utils.ToolUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
public class Information_House extends AppCompatActivity implements View.OnFocusChangeListener {
    @Bind(R.id.bottom_btn)
    Button bottomBtn;
    @Bind(R.id.bottom_ll)
    LinearLayout bottomLl;
    @Bind(R.id.info_house_end)
    TextView infoHouseEnd;
    @Bind(R.id.info_txt_1)
    TextView infoTxt1;
    @Bind(R.id.info_house_start)
    TextView infoHouseStart;
    @Bind(R.id.info_house_purpose_txt)
    TextView infoHousePurposeTxt;  // 房屋用途
    @Bind(R.id.info_house_purpose_rel)
    RelativeLayout infoHousePurposeRel;
    @Bind(R.id.info_house_type_txt)
    TextView infoHouseTypeTxt; // 使用类型
    @Bind(R.id.info_house_type_rel)
    RelativeLayout infoHouseTypeRel;
    @Bind(R.id.info_house_form_txt)
    TextView infoHouseFormTxt;  // 房屋类型
    @Bind(R.id.info_house_form_rel)
    RelativeLayout infoHouseFormRel;  // 房屋类型

    @Bind(R.id.info_house_structure_txt)
    TextView infoHouseStructureTxt;
    @Bind(R.id.info_house_structure_rel)
    RelativeLayout infoHouseStructureRel;  // 结构类型

    @Bind(R.id.info_house_property_txt)
    TextView infoHousePropertyTxt;  // 产权信息
    @Bind(R.id.info_house_property_lin)
    LinearLayout infoHousePropertyLin;  // 产权
    @Bind(R.id.info_house_property_person)
    LinearLayout infoHousePropertyPerson;
    @Bind(R.id.info_house_property_company)
    LinearLayout infoHousePropertyCompany;
    @Bind(R.id.info_img_right2)
    ImageView infoImgRight2;
    //    @Bind(R.id.info_house_address_road)
//    EditText infoHouseAddressRoad;  // 街道
//    @Bind(R.id.info_house_address_num)
//    EditText infoHouseAddressNum;  // 街道号
//    @Bind(R.id.info_house_address_plot)
//    EditText infoHouseAddressPlot;  // 小区
//    @Bind(R.id.info_house_address_build)
//    EditText infoHouseAddressBuild;  // 楼号
//    @Bind(R.id.info_house_address_unit)
//    EditText infoHouseAddressUnit;  // 单元
//    @Bind(R.id.info_house_address_floor)
//    EditText infoHouseAddressFloor;  // 楼层
//    @Bind(R.id.info_house_address_door)
//    EditText infoHouseAddressDoor;  // 门牌号
    @Bind(R.id.info_house_vulgo)
    EditText infoHouseVulgo;  // 俗称
    @Bind(R.id.info_img_right1)
    ImageView infoImgRight1;
    @Bind(R.id.info_house_room_num)
    EditText infoHouseRoomNum;  // 房间数
    @Bind(R.id.info_img_right3)
    ImageView infoImgRight3;
    @Bind(R.id.info_house_room_area)
    EditText infoHouseRoomArea;  // 面积
    @Bind(R.id.info_house_daily_y)
    RadioButton infoHouseDailyY;  // 日常检查
    @Bind(R.id.info_house_daily_n)
    RadioButton infoHouseDailyN;
    @Bind(R.id.info_house_daily_rad)
    RadioGroup infoHouseDailyRad;
    @Bind(R.id.info_house_img_y)
    RadioButton infoHouseImgY;  // 方位照
    @Bind(R.id.info_house_img_n)
    RadioButton infoHouseImgN;
    @Bind(R.id.info_house_img_rad)
    RadioGroup infoHouseImgRad;
    @Bind(R.id.info_img_right4)
    ImageView infoImgRight4;
    @Bind(R.id.info_img_right5)
    ImageView infoImgRight5;
    @Bind(R.id.info_house_nner_structure)
    EditText infoHouseNnerStructure;   // 内部结构
    @Bind(R.id.info_house_live_y)
    RadioButton infoHouseLiveY;  // 有无居住人员
    @Bind(R.id.info_house_live_n)
    RadioButton infoHouseLiveN;
    @Bind(R.id.info_house_live_rad)
    RadioGroup infoHouseLiveRad;
    @Bind(R.id.info_house_abnormity_y)
    RadioButton infoHouseAbnormityY;  // 有无异常
    @Bind(R.id.info_house_abnormity_n)
    RadioButton infoHouseAbnormityN;
    @Bind(R.id.info_house_abnormity_rad)
    RadioGroup infoHouseAbnormityRad;
    @Bind(R.id.info_img_right8)
    ImageView infoImgRight8;
    @Bind(R.id.info_house_person_name)
    EditText infoHousePersonName;  // 产权人姓名
    @Bind(R.id.info_house_person_id)
    EditText infoHousePersonId;  // 身份证号
    @Bind(R.id.info_house_person_mobile)
    EditText infoHousePersonMobile;  // 联系电话
    @Bind(R.id.info_img_right9)
    ImageView infoImgRight9;
    @Bind(R.id.info_house_live_txt)
    TextView infoHouseLiveTxt;   // 居住地
    @Bind(R.id.info_house_live_rel)
    RelativeLayout infoHouseLiveRel;
    @Bind(R.id.info_img_right10)
    ImageView infoImgRight10;
    @Bind(R.id.info_house_census_txt)
    TextView infoHouseCensusTxt;  // 户籍
    @Bind(R.id.info_house_census_rel)
    RelativeLayout infoHouseCensusRel;
    @Bind(R.id.info_house_serve_add)
    EditText infoHouseServeAdd;  // 服务处所
    @Bind(R.id.info_house_property_id)
    EditText infoHousePropertyId;  // 产权证号

    @Bind(R.id.info_house_company_name)
    EditText infoHouseCompanyName;  // 单位名
    @Bind(R.id.info_house_realname)
    EditText infoHouseRealname;  // 房屋实际名称
    @Bind(R.id.info_house_company_add)
    EditText infoHouseCompanyAdd;  // 单位地址
    @Bind(R.id.info_house_company_tel)
    EditText infoHouseCompanyTel;  // 办公电话
    @Bind(R.id.info_house_company_id)
    EditText infoHouseCompanyId;  // 产权证号
    @Bind(R.id.info_house_legal_name)
    EditText infoHouseLegalName;  // 法人代表姓名
    @Bind(R.id.info_house_legal_id)
    EditText infoHouseLegalId;  // 法人身份证号
    @Bind(R.id.info_house_legal_post)
    EditText infoHouseLegalPost;  // 职务
    @Bind(R.id.info_house_legal_tel)
    EditText infoHouseLegalTel;  // 联系电话
    @Bind(R.id.info_house_person_nation)
    Spinner infoHouseNation;  // 民族
    @Bind(R.id.degree)
    Spinner degree;
    @Bind(R.id.info_house_img) // 照片
            ImageView infoHouseImg;
    @Bind(R.id.info_house_img_card)
    CardView infoHouseImgCard;
    @Bind(R.id.info_house_address_txt)
    TextView infoHouseAddressTxt;
    @Bind(R.id.info_house_line)
    View infoHouseLine;
    @Bind(R.id.info_house_time_rel)
    RelativeLayout infoHouseTimeRel;
    //    @Bind(R.id.info_house_txt1)
//    TextView infoHouseTxt1;
//    @Bind(R.id.info_house_txt2)
//    TextView infoHouseTxt2;
//    @Bind(R.id.info_house_txt3)
//    TextView infoHouseTxt3;
//    @Bind(R.id.info_house_txt4)
//    TextView infoHouseTxt4;
//    @Bind(R.id.info_house_txt5)
//    TextView infoHouseTxt5;
//    @Bind(R.id.info_house_txt6)
//    TextView infoHouseTxt6;
    @Bind(R.id.info_house_abnormity_edt) // 异常信息
            EditText infoHouseAbnormityEdt;
    //    @Bind(R.id.info_house_txt7)
//    TextView infoHouseTxt7;
//    @Bind(R.id.info_house_txt8)
//    TextView infoHouseTxt8;
//    @Bind(R.id.info_house_txt9)
//    TextView infoHouseTxt9;
    @Bind(R.id.info_house_address_street) // 街
            RadioButton infoHouseAddressStreet;
    @Bind(R.id.info_house_address_lane)
    RadioButton infoHouseAddressLane;
    @Bind(R.id.info_house_address_village)
    RadioButton infoHouseAddressVillage;
    @Bind(R.id.info_house_address_rad)
    RadioGroup infoHouseAddressRad;
    @Bind(R.id.info_house_address_edt) // 标准地址
            EditText infoHouseAddressEdt;
    @Bind(R.id.info_house_detailed_edit) // 详细地址
            EditText infoHouseDetailedEdit;
    @Bind(R.id.info_house_live_edt) // 居住地详情
            EditText infoHouseLiveEdt;
    @Bind(R.id.info_house_census_edt) // 户口地详情
            EditText infoHouseCensusEdt;
    @Bind(R.id.info_house_monitor_txt) // 视频监控
            TextView infoHouseMonitorTxt;
    @Bind(R.id.info_house_monitor_rel)
    RelativeLayout infoHouseMonitorRel;
    @Bind(R.id.info_house_monitor_edt)  // 监控个数
    EditText infoHouseMonitorEdt;
    @Bind(R.id.info_house_cover_y)  // 覆盖
    RadioButton infoHouseCoverY;
    @Bind(R.id.info_house_cover_n)
    RadioButton infoHouseCoverN;
    @Bind(R.id.info_house_cover_rad)
    RadioGroup infoHouseCoverRad;
    @Bind(R.id.info_house_store_y) // 存储
    RadioButton infoHouseStoreY;
    @Bind(R.id.info_house_store_n)
    RadioButton infoHouseStoreN;
    @Bind(R.id.info_house_store_rad)
    RadioGroup infoHouseStoreRad;
    @Bind(R.id.info_house_monitor_ip)
    EditText infoHouseMonitorIp;

    //
    private Context context;
    private String selectDate = "";//DialogDatePicker 选择的时间
    private RequestCall mCall;
    private SharedPreferences preferences;
    private String house_img = "1"; // 方位照
    private String house_address = ""; // 街道类别
    private String house_daily = "1";  // 日常检查
    private String house_live = "1";  // 有无人员居住
    private String house_abnormity = "0"; // 有无异常
    private String house_property = "1";  // 产权信息
    private String house_cover = "0"; // 全面覆盖
    private String house_store = "1"; // 是否存储

    private String house_purpose = "0"; // 房屋用途
    private String house_type = "0"; // 使用类型
    private String house_form = "0"; // 房屋类型
    private String house_structure = "0"; // 结构类型
    private String house_monitor = "0"; // 监控类别
    private String person_nation; // 民族
    private int error_tel = 0; //  错误码
    private int error_id = 0;

    private int error_legal_id = 0;
    private int error_legal_tel = 0;

    private static final String IMAGE_FILE_NAME = "bear_image.jpg";
    // 请求码
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;
    // 省略City部分Spinner和ArrayAdapter声明的代码
    private Spinner age = null;// 声明一个Spinner组件，表示年龄
    private ArrayAdapter<CharSequence> ages = null;// 声明一个ArrayAdapter来适配年龄
    private List<CharSequence> age_data = null;// 声明一个放置年龄数据的List


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_info_house);
        ButterKnife.bind(this);
        initConstants();
        readCommunity();
        initViews();
    }

    // 读取社区
    private void readCommunity() {
        mCall = OkHttpUtils.post()
                .url(ConstantURL.COMM)
                .addParams(ConstantString.ACT, "getsq")
                .addParams(ConstantString.UID, preferences.getString(ConstantString.UID, ""))
                .build();
        mCall.execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(context, "连接服务器失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                try {
                    Gson gson = new Gson();
                    Log.e("测试：", response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initConstants() {
        context = Information_House.this;
        preferences = getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
    }

    private void initViews() {
        infoHouseAddressTxt.setText(Html.fromHtml("地址<font color=\"#ff0000\">*</font>"));
        Toolbar toolbar = (Toolbar) super.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.head_title_return);
        toolbar.setNavigationContentDescription("返回");

        toolbar.setTitle("房屋信息");
//        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Information_House.this.onBackPressed();
            }
        });
        bottomBtn.setText("提交");
        infoHouseNation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                person_nation = String.valueOf(position+1);
                Log.e("选中民族", (String) infoHouseNation.getSelectedItem());//这里就是获取值

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // 省略City部分的设置代码
        age = new Spinner(this);// 创建Spinner对象
        age.setPrompt("您的年龄段是:");// 设置Prompt
        age_data = Arrays.asList(new CharSequence[]{"10岁以下", "10-20岁", "20-30岁", "30-40岁", "40-50岁", "50-60岁", "60岁以上"});// 设置年龄段数组并最终转换为List类型
        ages = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, age_data);// 实例化ArrayAdapter
        ages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// 设置列表项显示风格
        age.setAdapter(ages);// 设置显示信息
        LinearLayout layout = (LinearLayout) super.findViewById(R.id.layout);
        TextView ageLabel = new TextView(this);
        ageLabel.setText("请选择您的年龄段");
        layout.addView(ageLabel);
        layout.addView(age);


        // 标准地址
        infoHouseAddressRad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.info_house_address_street) {
                    // 街
                    house_address = "街";
                } else if (checkedId == R.id.info_house_address_road) {
                    // 路
                    house_address = "路";
                } else if (checkedId == R.id.info_house_address_lane) {
                    // 巷
                    house_address = "巷";
                } else {
                    // 村
                    house_address = "村";
                }
            }
        });
        // 日常检查
        infoHouseDailyY.setChecked(true);
        infoHouseDailyRad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.info_house_daily_y) {
                    house_daily = "1";
                } else {
                    house_daily = "0";
                }
                //获取变更后的选中项的ID
                int radioButtonId = group.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) findViewById(checkedId);
                //更新文本内容，以符合选中项
                Log.e("您的选择", rb.getText() + "");
            }
        });
        // 方位照
        infoHouseImgY.setChecked(true);
        infoHouseImgRad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.info_house_img_y) {
                    house_img = "1";
                    infoHouseImgCard.setVisibility(View.VISIBLE);
                } else {
                    infoHouseImgCard.setVisibility(View.GONE);
                    house_img = "0";
                }
                //获取变更后的选中项的ID
                int radioButtonId = group.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) findViewById(checkedId);
                //更新文本内容，以符合选中项
                Log.e("您的选择", rb.getText() + "");
            }
        });

        // 有无居住人员
        infoHouseLiveY.setChecked(true);
        infoHouseLiveRad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.info_house_live_y) {
                    house_live = "1";
                } else {
                    house_live = "0";
                }
                //获取变更后的选中项的ID
                int radioButtonId = group.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) findViewById(checkedId);
                //更新文本内容，以符合选中项
                Log.e("您的选择", rb.getText() + "");
            }
        });
        // 有无异常
        infoHouseAbnormityN.setChecked(true);
        infoHouseAbnormityRad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.info_house_abnormity_y) {
                    house_abnormity = "1";
                    infoHouseAbnormityEdt.setVisibility(View.VISIBLE);
                } else {
                    house_abnormity = "0";
                    infoHouseAbnormityEdt.setVisibility(View.GONE);
                }
                //获取变更后的选中项的ID
                int radioButtonId = group.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) findViewById(checkedId);
                //更新文本内容，以符合选中项
                Log.e("您的选择", rb.getText() + "");
            }
        });
        // 全覆盖
        infoHouseCoverN.setChecked(true);
        infoHouseCoverRad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.info_house_cover_y) {
                    house_cover = "1";
                } else {
                    house_cover = "0";
                }
            }
        });
        // 存储
        infoHouseStoreY.setChecked(true);
        infoHouseStoreRad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.info_house_store_y){
                    house_store = "1";
                }else {
                    house_store = "0";
                }
            }
        });

        // 手机号验证
        infoHouseLegalTel.setOnFocusChangeListener(this);
        infoHousePersonMobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    if (DemoUtils.checkPhone(infoHousePersonMobile.getText().toString())) {
                        error_tel = 0;
                    } else {
                        error_tel = 1;
                        infoHousePersonMobile.setError("请检查手机号");
                    }
                }
            }
        });
        // 身份证验证
        infoHouseLegalId.setOnFocusChangeListener(this);
        infoHousePersonId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                } else {
                    try {
                        if (DemoUtils.IDCardValidate(infoHousePersonId.getText().toString())) {
                            error_id = 0;
                        } else {
                            error_id = 2;
                            infoHousePersonId.setError("请检查身份证号码");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        infoHouseImgCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivityForResult(new Intent(context, PicSelectActivity.class), 0x123);
                Intent intentFromCapture = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                // 判断存储卡是否可以用，可用进行存储
                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    File file = new File(ToolUtils.IMAGE_PATH);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(new File(ToolUtils.IMAGE_PATH + IMAGE_FILE_NAME))
                    );
                }
                startActivityForResult(intentFromCapture,
                        CAMERA_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // 结果码不等于取消时候
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                case CAMERA_REQUEST_CODE:
                    String state = Environment.getExternalStorageState();
                    if (state.equals(Environment.MEDIA_MOUNTED)) {
                        Bitmap bitmap = PictureUtils.getimage(ToolUtils.IMAGE_PATH + IMAGE_FILE_NAME);

                        infoHouseImg.setImageBitmap(bitmap);
                    } else {
                        Toast.makeText(context, "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
                    }
                    break;
                case RESULT_REQUEST_CODE:
                    System.out.println("RESULT_REQUEST_CODE");
//                    if (data != null) {
//                        getImageToView(data);
////                        Bitmap bitmap = getimage(ToolUtils.IMAGE_PATH + IMAGE_FILE_NAME);
//
//                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.bottom_btn, R.id.info_house_end, R.id.info_house_start, R.id.info_house_purpose_rel, R.id.info_house_type_rel, R.id.info_house_form_rel,
            R.id.info_house_structure_rel, R.id.info_house_property_lin, R.id.info_house_live_rel, R.id.info_house_census_rel, R.id.info_house_monitor_rel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_btn:
                // 提交
                if (checkedInput()) {
                    submitInfo();
                }
                break;
            case R.id.info_house_end:
                // 选择日起
                DatePickerDialog datePicker1 = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        selectDate = +year + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                        Log.e("选定时间：", selectDate);
                        infoHouseEnd.setText(selectDate);
                    }
                }, DateUtils.getDateYear(), DateUtils.getDateMonth() - 1, DateUtils.getDateDay());//获取当前时间
                datePicker1.show();
                break;
            case R.id.info_house_start:
                // 选择日起
                DatePickerDialog datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        selectDate = +year + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                        Log.e("选定时间：", selectDate);
                        infoHouseStart.setText(selectDate);
                    }
                }, DateUtils.getDateYear(), DateUtils.getDateMonth() - 1, DateUtils.getDateDay());//获取当前时间
                datePicker.show();
                break;
            case R.id.info_house_purpose_rel:
                // 房屋用途
                showPurpose();
                break;
            case R.id.info_house_type_rel:
                // 使用类型
                showType();
                break;
            case R.id.info_house_form_rel:
                // 房屋类型
                showForm();
                break;
            case R.id.info_house_structure_rel:
                // 结构类型
                showStructure();
                break;
            case R.id.info_house_property_lin:
                // 产权选择
                showProperty();
                break;
            case R.id.info_house_live_rel:
                // 居住地
                showLive();
                break;
            case R.id.info_house_census_rel:
                // 户籍
                showCensus();
                break;
            case R.id.info_house_monitor_rel:
                // 视频监控
                showMonitor();
                break;

        }
    }

    /**
     * 选择视频类别
     */
    private void showMonitor() {
        new ActionSheetDialog(context)
                .builder()
                .setCancelable(false)
                .setTitle("监控类别")
                .setCanceledOnTouchOutside(false)
                .addSheetItem("公安", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_monitor = "1";
                                infoHouseMonitorTxt.setText("公安");
                            }
                        })
                .addSheetItem("政府", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_monitor = "2";
                                infoHouseMonitorTxt.setText("政府");
                            }
                        })
                .addSheetItem("单位", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_monitor = "3";
                                infoHouseMonitorTxt.setText("单位");
                            }
                        })
                .addSheetItem("个人", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_monitor = "4";
                                infoHouseMonitorTxt.setText("个人");
                            }
                        })
                .addSheetItem("其他", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_monitor = "5";
                                infoHouseMonitorTxt.setText("其他");
                            }
                        })
                .show();
    }

    //
    private void submitInfo1() {
        String url = ConstantURL.COMM + "?act=add&uid=" + preferences.getString(ConstantString.UID, "");
        GetDataDao getDataDao = new GetDataDaoImpl();
        RequestVo requestVo = new RequestVo();
        requestVo.setRequestUrl(url);
        requestVo.setContext(context);
        requestVo.setHttpMethod(HttpRequest.HttpMethod.POST);
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("shequ", "2");
        requestParams.addBodyParameter("suname", infoHouseVulgo.getText().toString());
//        requestParams.addBodyParameter("rode", infoHouseAddressRoad.getText().toString());
//        requestParams.addBodyParameter("num", infoHouseAddressNum.getText().toString());
//        requestParams.addBodyParameter("xiaoqu", infoHouseAddressPlot.getText().toString());
//        requestParams.addBodyParameter("dong", infoHouseAddressBuild.getText().toString());
//        requestParams.addBodyParameter("danyuan", infoHouseAddressUnit.getText().toString());
//        requestParams.addBodyParameter("lou", infoHouseAddressFloor.getText().toString());
//        requestParams.addBodyParameter("louhao", infoHouseAddressDoor.getText().toString());
        requestParams.addBodyParameter("yongtu", house_purpose);
        requestParams.addBodyParameter("roomnum", infoHouseRoomNum.getText().toString());
        requestParams.addBodyParameter("utype", house_type);
        if (!house_type.equals("1")) {
            requestParams.addBodyParameter("zl_start", infoHouseStart.getText().toString());
            requestParams.addBodyParameter("zl_end", infoHouseEnd.getText().toString());
        }
        requestParams.addBodyParameter("is_pic", house_img);
        requestParams.addBodyParameter("area", infoHouseRoomArea.getText().toString());
        requestParams.addBodyParameter("is_jc", house_daily);
        requestParams.addBodyParameter("htype", house_form);
        requestParams.addBodyParameter("is_jz", house_live);
        requestParams.addBodyParameter("is_yc", house_abnormity);
        requestParams.addBodyParameter("jgtype", house_structure);
        requestParams.addBodyParameter("nbjg", infoHouseNnerStructure.getText().toString());
        requestParams.addBodyParameter("cqtype", house_property);
        requestParams.addBodyParameter("zuobiao", ToolUtils.LATITUDE + "," + ToolUtils.LONGITUDE);
        requestParams.addBodyParameter("addr", ToolUtils.ADDRESS);
        if (house_property.equals("1")) {
            // 个人
            requestParams.addBodyParameter("gr_name", infoHousePersonName.getText().toString());  //　产权人姓名
            requestParams.addBodyParameter("gr_sfz", infoHousePersonId.getText().toString());  // 身份证号
            requestParams.addBodyParameter("gr_jzd_1", ToolUtils.ProviceName);  // 居住地
            requestParams.addBodyParameter("gr_jzd_2", ToolUtils.CityName);
            requestParams.addBodyParameter("gr_jzd_3", ToolUtils.DistrictName);
            requestParams.addBodyParameter("gr_minzu", person_nation); // 民族
            requestParams.addBodyParameter("gr_tel", infoHousePersonMobile.getText().toString()); // 联系电话
            requestParams.addBodyParameter("gr_hkd_1", ToolUtils.ProviceName);  // 户籍
            requestParams.addBodyParameter("gr_hkd_2", ToolUtils.CityName);
            requestParams.addBodyParameter("gr_hkd_3", ToolUtils.DistrictName);
            requestParams.addBodyParameter("gr_fwcs", infoHouseServeAdd.getText().toString()); // 服务处所
            requestParams.addBodyParameter("gr_cqzh", infoHousePropertyId.getText().toString()); // 产权证号
        } else {
            // 单位
            requestParams.addBodyParameter("dw_name", infoHouseCompanyName.getText().toString()); // 单位名
            requestParams.addBodyParameter("dw_realname", infoHouseRealname.getText().toString()); // 房屋实际名
            requestParams.addBodyParameter("dw_addr", infoHouseCompanyAdd.getText().toString()); // 单位地址
            requestParams.addBodyParameter("dw_tel", infoHouseCompanyTel.getText().toString()); // 办公电话
            requestParams.addBodyParameter("dw_cqzh", infoHouseCompanyId.getText().toString()); // 产权证号
            requestParams.addBodyParameter("dw_frname", infoHouseLegalName.getText().toString()); // 法人代表姓名
            requestParams.addBodyParameter("dw_sfz", infoHouseLegalId.getText().toString()); // 身份证号
            requestParams.addBodyParameter("dw_zw", infoHouseLegalPost.getText().toString()); // 职务
            requestParams.addBodyParameter("dw_mobi", infoHouseLegalTel.getText().toString()); // 联系电话
        }
        File file = new File(ToolUtils.IMAGE_PATH + "faceImage1.jpg");
        Log.e("照片是否存在", file.exists() + "");
        requestParams.addBodyParameter("file", file);
//        requestParams.addBodyParameter("fileName","dddddd");
        requestVo.setRequestParams(requestParams);
        Log.e("请求URL", url);
        getDataDao.getData(requestVo, new LoadCallBack() {
            @Override
            public void onLoadSuccess(String obj) {
                Log.e("tag", "提交数据" + obj);
                Gson gson = new Gson();
                Submit_House submit_house = gson.fromJson(obj, Submit_House.class);
                if (submit_house != null) {
                    if (submit_house.getState().equals("ok")) {
                        Toast.makeText(context, "提交成功！", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context, Information_Person.class)
                                .putExtra("lastid", submit_house.getLastid()));
                    } else {
                        Toast.makeText(context, "提交失败！", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onLoadFail() {
                Toast.makeText(
                        context,
                        context.getResources().getString(
                                R.string.base_server),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoading(int progress) {

            }
        });
    }

    // 提交信息
    private void submitInfo() {
//        addParams(ConstantString.ACT, "add")
//                .addParams(ConstantString.UID, preferences.getString(ConstantString.UID,""))
//                .addParams("shequ","2")
//                .addParams("suname",infoHouseVulgo.getText().toString()) // 俗称
//                .addParams("rode",infoHouseAddressRoad.getText().toString()) // 街道
//                .addParams("num",infoHouseAddressNum.getText().toString()) // 号
//                .addParams("xiaoqu",infoHouseAddressPlot.getText().toString())  // 小区
//                .addParams("dong",infoHouseAddressBuild.getText().toString())  // 楼号
//                .addParams("danyuan",infoHouseAddressUnit.getText().toString())  // 单元
//                .addParams("lou",infoHouseAddressFloor.getText().toString())  // 楼层
//                .addParams("louhao",infoHouseAddressDoor.getText().toString())  // 门号
//                .addParams("yongtu",infoHousePurposeTxt.getText().toString())  // 房屋用途
//                .addParams("roomnum",infoHouseRoomNum.getText().toString()) // 房间数
//                .addParams("utype",infoHouseTypeTxt.getText().toString())  //使用类型
//                .addParams("zl_start",infoHouseStart.getText().toString()) // 租赁时间
//                .addParams("zl_end",infoHouseEnd.getText().toString())
//                .addParams("is_pic",img)
//                .addParams("picaddr","")  // 方位照
//                .addParams("area",infoHouseRoomArea.getText().toString()) // 面积
//                .addParams("is_jc",house_daily)  // 日常检查
//                .addParams("htype",infoHouseFormTxt.getText().toString()) //房屋类型
//                .addParams("is_jz",house_live)  // 有无人员居住
//                .addParams("is_yc",house_abnormity)  // 有无异常
//                .addParams("jgtype",infoHouseStructureTxt.getText().toString())  // 结构类型
//                .addParams("nbjg",infoHouseNnerStructure.getText().toString())  // 内部结构
//                .addParams("cqtype",house_property)  // 产权信息
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("shequ", "2");
        requestParams.put("suname", infoHouseVulgo.getText().toString());
        requestParams.put("rode", infoHouseAddressEdt.getText().toString()); // 标准地址
        requestParams.put("rode_type", house_address); // 街/路/巷/村
        requestParams.put("xxaddr", infoHouseDetailedEdit.getText().toString()); // 详细地址
        requestParams.put("yc_cont", infoHouseAbnormityEdt.getText().toString()); // 异常信息
        requestParams.put("yongtu", house_purpose);
        requestParams.put("roomnum", infoHouseRoomNum.getText().toString());
        requestParams.put("utype", house_type);
        if (!house_type.equals("1")) {
            requestParams.put("zl_start", infoHouseStart.getText().toString());
            requestParams.put("zl_end", infoHouseEnd.getText().toString());
        }
        requestParams.put("is_pic", house_img);
        requestParams.put("area", infoHouseRoomArea.getText().toString());
        requestParams.put("is_jc", house_daily);
        requestParams.put("htype", house_form);
        requestParams.put("is_jz", house_live);
        requestParams.put("is_yc", house_abnormity);
        requestParams.put("jgtype", house_structure);
        requestParams.put("nbjg", infoHouseNnerStructure.getText().toString());
        requestParams.put("cqtype", house_property);
        requestParams.put("zuobiao", ToolUtils.LATITUDE + "," + ToolUtils.LONGITUDE);
        requestParams.put("addr", ToolUtils.ADDRESS);
        requestParams.put("gr_jzd", infoHouseLiveEdt.getText().toString()); // 居住地详情
        requestParams.put("gr_hkd", infoHouseCensusEdt.getText().toString()); // 户口地详情
        requestParams.put("jktype",house_monitor); // 监控类别
        requestParams.put("jknum",infoHouseMonitorEdt.getText().toString()); // 监控个数
        requestParams.put("jkfg",house_cover); // 覆盖
        requestParams.put("jkcc",house_store); //  存储
        requestParams.put("jkip",infoHouseMonitorIp.getText().toString()); // 监控IP
        requestParams.put("jktype",house_monitor);

        if (house_property.equals("1")) {
            // 个人
            requestParams.put("gr_name", infoHousePersonName.getText().toString());  //　产权人姓名
            requestParams.put("gr_sfz", infoHousePersonId.getText().toString());  // 身份证号
            requestParams.put("gr_jzd_1", ToolUtils.ProviceName);  // 居住地
            requestParams.put("gr_jzd_2", ToolUtils.CityName);
            requestParams.put("gr_jzd_3", ToolUtils.DistrictName);
            requestParams.put("gr_minzu", ""); // 民族
            requestParams.put("gr_tel", infoHousePersonMobile.getText().toString()); // 联系电话
            requestParams.put("gr_hkd_1", ToolUtils.ProviceName);  // 户籍
            requestParams.put("gr_hkd_2", ToolUtils.CityName);
            requestParams.put("gr_hkd_3", ToolUtils.DistrictName);
            requestParams.put("gr_fwcs", infoHouseServeAdd.getText().toString()); // 服务处所
            requestParams.put("gr_cqzh", infoHousePropertyId.getText().toString()); // 产权证号
        } else {
            // 单位
            requestParams.put("dw_name", infoHouseCompanyName.getText().toString()); // 单位名
            requestParams.put("dw_realname", infoHouseRealname.getText().toString()); // 房屋实际名
            requestParams.put("dw_addr", infoHouseCompanyAdd.getText().toString()); // 单位地址
            requestParams.put("dw_tel", infoHouseCompanyTel.getText().toString()); // 办公电话
            requestParams.put("dw_cqzh", infoHouseCompanyId.getText().toString()); // 产权证号
            requestParams.put("dw_frname", infoHouseLegalName.getText().toString()); // 法人代表姓名
            requestParams.put("dw_sfz", infoHouseLegalId.getText().toString()); // 身份证号
            requestParams.put("dw_zw", infoHouseLegalPost.getText().toString()); // 职务
            requestParams.put("dw_mobi", infoHouseLegalTel.getText().toString()); // 联系电话
        }

        File file = new File(ToolUtils.IMAGE_PATH + "faceImage1.jpg");
        Log.e("照片是否存在", file.exists() + "");
        mCall = OkHttpUtils.post()
                .url(ConstantURL.COMM + "?act=add&uid=" + preferences.getString(ConstantString.UID, ""))
                .addFile("file", "bearing", file)
                .params(requestParams)
                .build();
        mCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(context, getString(R.string.base_server), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                Log.e("tag", "提交数据" + response);
                Gson gson = new Gson();
                Submit_House submit_house = gson.fromJson(response, Submit_House.class);
                if (submit_house != null) {
                    if (submit_house.getState().equals("ok")) {
                        Toast.makeText(context, "提交成功！", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context, Information_Person.class)
                                .putExtra("lastid", submit_house.getLastid()));
                    } else {
                        Toast.makeText(context, "提交失败！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // 检测输入
    private boolean checkedInput() {
        // 标准地址
        if (infoHouseAddressEdt.getText().toString().equals("")) {
            Toast.makeText(context, "请输入标准地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (house_address.equals("")) {
            Toast.makeText(context, "请选择地址分类", Toast.LENGTH_SHORT).show();
            return false;
        }
        // 详细地址
        if (infoHouseDetailedEdit.getText().toString().equals("")) {
            Toast.makeText(context, "请填写详细地址", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (infoHousePurposeTxt.getText().toString().equals("")) {
            Toast.makeText(context, "请选择房屋用途", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (infoHouseRoomNum.getText().toString().equals("")) {
            Toast.makeText(context, "请填写房间数", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (infoHouseTypeTxt.getText().toString().equals("")) {
            Toast.makeText(context, "请选择使用类型", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (house_img.equals("1")) {
            File file = new File(ToolUtils.IMAGE_PATH + "faceImage1.jpg");
            if (!file.exists()) {
                Toast.makeText(context, "请选择方位照", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (infoHouseFormTxt.getText().toString().equals("")) {
            Toast.makeText(context, "请选择房屋类型", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (infoHouseStructureTxt.getText().toString().equals("")) {
            Toast.makeText(context, "请选择结构类型", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (infoHouseNnerStructure.getText().toString().equals("")) {
            Toast.makeText(context, "请填写内部结构", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(infoHouseMonitorTxt.getText().toString().equals("")){
            Toast.makeText(context, "请选择监控类别", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(infoHouseMonitorEdt.getText().toString().equals("")){
            Toast.makeText(context, "请填写监控个数", Toast.LENGTH_SHORT).show();
            return false;
        }
        // 产权
        if (infoHousePropertyTxt.getText().toString().equals("个人")) {
            if (infoHousePersonName.getText().equals("")) {
                Toast.makeText(context, "请填写产权人姓名", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (infoHousePersonId.getText().toString().equals("")) {
                Toast.makeText(context, "请填写产权人身份证号", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (error_tel == 1) {
                Toast.makeText(context, "请检查手机号", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (error_id == 2) {
                Toast.makeText(context, "请检查身份证号", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (infoHousePersonMobile.getText().toString().equals("")) {
                Toast.makeText(context, "请填写产权人联系方式", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (infoHouseLiveTxt.getText().toString().equals("")) {
                Toast.makeText(context, "请选择产权人居住地", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (infoHouseCensusTxt.getText().toString().equals("")) {
                Toast.makeText(context, "请选择产权人户籍", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (infoHouseServeAdd.getText().toString().equals("")) {
                Toast.makeText(context, "请填写服务处所", Toast.LENGTH_SHORT).show();
                return false;
            }
//            if (infoHousePropertyId.getText().toString().equals("")) {
//                Toast.makeText(context, "请填写产权证号", Toast.LENGTH_SHORT).show();
//                return false;
//            }
        } else {
            // 单位
            if (error_legal_tel == 1) {
                Toast.makeText(context, "请检查法人手机号", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (error_legal_id == 1) {
                Toast.makeText(context, "请检查法人身份证号", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (infoHouseCompanyName.getText().toString().equals("")) {
                Toast.makeText(context, "请填写单位名称", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (infoHouseRealname.getText().toString().equals("")) {
                Toast.makeText(context, "请填写该房屋实际名称", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (infoHouseCompanyAdd.getText().toString().equals("")) {
                Toast.makeText(context, "请填写单位祥址", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (infoHouseCompanyTel.getText().toString().equals("")) {
                Toast.makeText(context, "请填写办公电话", Toast.LENGTH_SHORT).show();
                return false;
            }
//            if (infoHouseCompanyId.getText().toString().equals("")) {
//                Toast.makeText(context, "请填写产权证号", Toast.LENGTH_SHORT).show();
//                return false;
//            }
            if (infoHouseLegalName.getText().toString().equals("")) {
                Toast.makeText(context, "请填写法人代表姓名", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (infoHouseLegalId.getText().toString().equals("")) {
                Toast.makeText(context, "请填写法人代表身份证号", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (infoHouseLegalPost.getText().toString().equals("")) {
                Toast.makeText(context, "请填写法人代表职务", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (infoHouseLegalTel.getText().toString().equals("")) {
                Toast.makeText(context, "请填写法人代表联系方式", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    /**
     * 选择 户籍
     */
    private void showCensus() {
        CityDialog cityDialog = new CityDialog(context, R.style.city_dialog, infoHouseCensusTxt);
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

    /**
     * 选择 居住地
     */
    private void showLive() {
        CityDialog cityDialog = new CityDialog(context, R.style.city_dialog, infoHouseLiveTxt);
        Window dialogWindow = cityDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.8); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);
        cityDialog.show();
    }

    /**
     * 选择 产权信息
     */
    private void showProperty() {
        new ActionSheetDialog(context)
                .builder()
                .setCancelable(false)
                .setTitle("产权信息")
                .setCanceledOnTouchOutside(false)
                .addSheetItem("个人", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_property = "1";
                                infoHousePropertyTxt.setText("个人");
                                infoHousePropertyPerson.setVisibility(View.VISIBLE);
                                infoHousePropertyCompany.setVisibility(View.GONE);
                            }
                        })
                .addSheetItem("单位", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_property = "2";
                                infoHousePropertyTxt.setText("单位");
                                infoHousePropertyPerson.setVisibility(View.GONE);
                                infoHousePropertyCompany.setVisibility(View.VISIBLE);
                            }
                        })
                .show();
    }

    /**
     * 选择 结构类型
     */
    private void showStructure() {
        new ActionSheetDialog(context)
                .builder()
                .setCancelable(false)
                .setTitle("结构类型")
                .setCanceledOnTouchOutside(false)
                .addSheetItem("框架", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_structure = "1";
                                infoHouseStructureTxt.setText("框架");
                            }
                        })
                .addSheetItem("砖混", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_structure = "2";
                                infoHouseStructureTxt.setText("砖混");
                            }
                        })
                .addSheetItem("土墙", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_structure = "3";
                                infoHouseStructureTxt.setText("土墙");
                            }
                        })
                .addSheetItem("立材夹壁", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_structure = "4";
                                infoHouseStructureTxt.setText("立材夹壁");
                            }
                        })
                .addSheetItem("其他", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_structure = "5";
                                infoHouseStructureTxt.setText("其他");
                            }
                        })
                .show();
    }

    /**
     * 选择 房屋类型
     */
    private void showForm() {
        new ActionSheetDialog(context)
                .builder()
                .setCancelable(false)
                .setTitle("房屋类型")
                .setCanceledOnTouchOutside(false)
                .addSheetItem("单元楼", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_form = "1";
                                infoHouseFormTxt.setText("单元楼");
                            }
                        })
                .addSheetItem("筒子楼", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_form = "2";
                                infoHouseFormTxt.setText("筒子楼");
                            }
                        })
                .addSheetItem("别墅", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_form = "3";
                                infoHouseFormTxt.setText("别墅");
                            }
                        })
                .addSheetItem("自建小楼", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_form = "4";
                                infoHouseFormTxt.setText("自建小楼");
                            }
                        })
                .addSheetItem("独立平房", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_form = "5";
                                infoHouseFormTxt.setText("独立平房");
                            }
                        })
                .addSheetItem("四合院平房", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_form = "6";
                                infoHouseFormTxt.setText("四合院平房");
                            }
                        })
                .addSheetItem("商住一体", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_form = "7";
                                infoHouseFormTxt.setText("商住一体");
                            }
                        })
                .addSheetItem("临时工棚", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_form = "8";
                                infoHouseFormTxt.setText("临时工棚");
                            }
                        })
                .addSheetItem("其他", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_form = "9";
                                infoHouseFormTxt.setText("其他");
                            }
                        })
                .show();
    }

    /**
     * 选择 使用类型
     */
    private void showType() {
        new ActionSheetDialog(context)
                .builder()
                .setCancelable(false)
                .setTitle("使用类型")
                .setCanceledOnTouchOutside(false)
                .addSheetItem("自用", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_type = "1";
                                infoHouseLine.setVisibility(View.GONE);
                                infoHouseTimeRel.setVisibility(View.GONE);
                                infoHouseTypeTxt.setText("自用");
                            }
                        })
                .addSheetItem("租借", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_type = "2";
                                infoHouseLine.setVisibility(View.VISIBLE);
                                infoHouseTimeRel.setVisibility(View.VISIBLE);
                                infoHouseTypeTxt.setText("租借");
                            }
                        })
                .addSheetItem("闲置", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_type = "3";
                                infoHouseLine.setVisibility(View.GONE);
                                infoHouseTimeRel.setVisibility(View.VISIBLE);
                                infoHouseTypeTxt.setText("闲置");
                            }
                        })
                .addSheetItem("其他", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_type = "4";
                                infoHouseLine.setVisibility(View.GONE);
                                infoHouseTimeRel.setVisibility(View.VISIBLE);
                                infoHouseTypeTxt.setText("其他");
                            }
                        })
                .show();
    }

    /**
     * 选择 房屋用途
     */
    private void showPurpose() {
        new ActionSheetDialog(context)
                .builder()
                .setCancelable(false)
                .setTitle("房屋用途")
                .setCanceledOnTouchOutside(false)
                .addSheetItem("居住", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_purpose = "1";
                                infoHousePurposeTxt.setText("居住");
                            }
                        })
                .addSheetItem("生产", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_purpose = "2";
                                infoHousePurposeTxt.setText("生产");
                            }
                        })
                .addSheetItem("经营", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_purpose = "3";
                                infoHousePurposeTxt.setText("经营");
                            }
                        })
                .addSheetItem("办公", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_purpose = "4";
                                infoHousePurposeTxt.setText("办公");
                            }
                        })
                .addSheetItem("仓储", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_purpose = "5";
                                infoHousePurposeTxt.setText("仓储");
                            }
                        })
                .addSheetItem("其他", SheetItemColor.Blue,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                house_purpose = "6";
                                infoHousePurposeTxt.setText("其他");
                            }
                        })
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.info_house_legal_id:
                // 法人身份证号
                if (hasFocus) {
                } else {
                    try {
                        if (DemoUtils.IDCardValidate(infoHouseLegalId.getText().toString())) {
                            error_legal_id = 0;
                        } else {
                            error_legal_id = 1;
                            infoHouseLegalId.setError("请检查身份证号码");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.info_house_legal_tel:
                // 法人手机号
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                } else {
                    // 此处为失去焦点时的处理内容
                    if (DemoUtils.checkPhone(infoHouseLegalTel.getText().toString())) {
                        error_legal_tel = 0;
                    } else {
                        error_legal_tel = 1;
                        infoHouseLegalTel.setError("请检查手机号");
                    }
                }
                break;
        }
    }
}
