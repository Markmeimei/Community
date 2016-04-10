package com.example.community.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.community.R;
import com.example.community.adapter.Information_Overseas_ListView;
import com.example.community.bean.Overseas_Dependent;
import com.example.community.utils.DateUtils;
import com.example.community.utils.PictureUtils;
import com.example.community.utils.ToolUtils;
import com.example.community.widget.ListViewForScrollView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 林炜智 on 2016/4/9.
 * 社区警务平台信息采集 ---> 常住境外人员登记表
 */
public class Information_Overseas extends AppCompatActivity {
    public final static String Tag = "Information_Overseas";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.bottom_btn)
    Button bottomBtn;
    @Bind(R.id.bottom_ll)
    LinearLayout bottomLl;
    @Bind(R.id.info_overseas_basic_FirstName)
    EditText infoOverseasBasicFirstName;    //英文名
    @Bind(R.id.info_overseas_basic_LastName)
    EditText infoOverseasBasicLastName;     //英文姓
    @Bind(R.id.info_overseas_basic_Name)
    EditText infoOverseasBasicName;     //中文姓名
    @Bind(R.id.info_overseas_basic_Sex)
    RadioGroup infoOverseasBasicSex;    //性别
    @Bind(R.id.info_overseas_basic_Man)
    RadioButton infoOverseasBasicMan;   //男
    @Bind(R.id.info_overseas_basic_Woman)
    RadioButton infoOverseasBasicWoman; //女
    @Bind(R.id.info_overseas_basic_State)
    TextView infoOverseasBasicState;    //国家/地区
    @Bind(R.id.info_overseas_basic_BirthDate)
    TextView infoOverseasBasicBirthDate;    //出生日期
    @Bind(R.id.info_overseas_basic_Photo)
    ImageView infoOverseasBasicPhoto;   //照片
    @Bind(R.id.info_overseas_certificate_PapersType)
    TextView infoOverseasCertificatePapersType; //证件类型
    @Bind(R.id.info_overseas_certificate_PapersNumber)
    EditText infoOverseasCertificatePapersNumber;   //证件号码
    @Bind(R.id.info_overseas_certificate_PapersDate)
    TextView infoOverseasCertificatePapersDate; //证件有效期
    @Bind(R.id.info_overseas_certificate_IdentityNumber)
    EditText infoOverseasCertificateIdentityNumber; //身份证号码
    @Bind(R.id.info_overseas_certificate_FlowRadioGroup)
    RadioGroup infoOverseasCertificateFlowRadioGroup;   //签证(注)/居留许可种类
    @Bind(R.id.info_overseas_certificate_RadioButton_1)
    RadioButton infoOverseasCertificateRadioButton1;    //访问
    @Bind(R.id.info_overseas_certificate_RadioButton_2)
    RadioButton infoOverseasCertificateRadioButton2;    //旅游
    @Bind(R.id.info_overseas_certificate_RadioButton_3)
    RadioButton infoOverseasCertificateRadioButton3;    //外国人居留许可
    @Bind(R.id.info_overseas_certificate_RadioButton_4)
    RadioButton infoOverseasCertificateRadioButton4;    //其他
    @Bind(R.id.info_overseas_certificate_PermissionNumber)
    EditText infoOverseasCertificatePermissionNumber;   //签证(注)/居留许可种类
    @Bind(R.id.info_overseas_certificate_Validity)
    TextView infoOverseasCertificateValidity;   //有效次数
    @Bind(R.id.info_overseas_certificate_StartDate)
    TextView infoOverseasCertificateStartDate;  //签发开始时间
    @Bind(R.id.info_overseas_certificate_EndDate)
    TextView infoOverseasCertificateEndDate;    //签发结束时间
    @Bind(R.id.info_overseas_certificate_IssueAt)
    TextView infoOverseasCertificateIssueAt;    //签发地
    @Bind(R.id.info_overseas_certificate_EnterDate)
    TextView infoOverseasCertificateEnterDate;  //入境时间
    @Bind(R.id.info_overseas_certificate_EnterPort)
    TextView infoOverseasCertificateEnterPort;  //入境港口
    @Bind(R.id.info_overseas_certificate_LeaveDate)
    TextView infoOverseasCertificateLeaveDate;  //离开时间
    @Bind(R.id.info_overseas_CompanyName)
    EditText infoOverseasCompanyName;   //在华单位
    @Bind(R.id.info_overseas_Occupation)
    EditText infoOverseasOccupation;    //职业
    @Bind(R.id.info_overseas_Address)
    EditText infoOverseasAddress;   //在华地址
    @Bind(R.id.info_overseas_CompanyPhone)
    EditText infoOverseasCompanyPhone;  //单位联系方式
    @Bind(R.id.info_overseas_Receiver)
    EditText infoOverseasReceiver;  //单位外管联络员或接待人
    @Bind(R.id.info_overseas_ReceiverPhone)
    EditText infoOverseasReceiverPhone; //接待人联系电话
    @Bind(R.id.info_overseas_PapersChange)
    EditText infoOverseasPapersChange;  //证件变更情况
    @Bind(R.id.info_overseas_relation_ListView)
    ListViewForScrollView infoOverseasRelationListView; //随行家属情况 ListView
    @Bind(R.id.info_overseas_relation_Annotation)
    EditText infoOverseasRelationAnnotation;    //备注
    @Bind(R.id.activity_information_overseas_Name)
    EditText activityInformationOverseasName;   //房主姓名
    @Bind(R.id.activity_information_overseas_Relation)
    EditText activityInformationOverseasRelation;   //与房主关系
    @Bind(R.id.activity_information_overseas_LiveType)
    RadioGroup activityInformationOverseasLiveType;     //居住类型
    @Bind(R.id.activity_information_overseas_Permanent)
    RadioButton activityInformationOverseasPermanent;   //常住
    @Bind(R.id.activity_information_overseas_Temporary)
    RadioButton activityInformationOverseasTemporary;   //暂住
    @Bind(R.id.activity_information_overseas_HousingProperty)
    RadioGroup activityInformationOverseasHousingProperty;  //住房性质
    @Bind(R.id.activity_information_overseas_RadioButton1)
    RadioButton activityInformationOverseasRadioButton1;    //自购
    @Bind(R.id.activity_information_overseas_RadioButton2)
    RadioButton activityInformationOverseasRadioButton2;    //租凭
    @Bind(R.id.activity_information_overseas_RadioButton3)
    RadioButton activityInformationOverseasRadioButton3;    //借住
    @Bind(R.id.info_overseas_relation_AddItem)
    ImageView infoOverseasRelationAddItem;  //添加 随行家属情况的按钮

    private List<Overseas_Dependent> dependentList;
    private Information_Overseas_ListView listViewAdapter;

    private static final String IMAGE_FILE_NAME = "bear_image.jpg";
    // 请求码
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_overseas);
        ButterKnife.bind(this);
        Init();
    }

    private void Init() {
        dependentList = new ArrayList<>();
        listViewAdapter = new Information_Overseas_ListView(this, dependentList);
        Content();
    }

    private void Content() {
        infoOverseasRelationListView.setAdapter(listViewAdapter);
    }

    @OnClick({R.id.info_overseas_relation_AddItem, R.id.info_overseas_basic_State, R.id.info_overseas_basic_BirthDate, R.id.info_overseas_basic_Photo, R.id.info_overseas_certificate_PapersType, R.id.info_overseas_certificate_PapersDate, R.id.info_overseas_certificate_Validity, R.id.info_overseas_certificate_StartDate, R.id.info_overseas_certificate_EndDate, R.id.info_overseas_certificate_IssueAt, R.id.info_overseas_certificate_EnterDate, R.id.info_overseas_certificate_EnterPort, R.id.info_overseas_certificate_LeaveDate})
    public void onClick(View view) {
        Overseas_Dependent dependent;   //家属信息
        DatePickerDialog datePicker;    //时间Dialog

        switch (view.getId()) {
            /*添加随行家属条目*/
            case R.id.info_overseas_relation_AddItem:
                dependent = new Overseas_Dependent();
                dependentList.add(dependent);
                listViewAdapter.notifyDataSetChanged();
                break;
            /*国家/地区*/
            case R.id.info_overseas_basic_State:
                break;
            /*出生日期*/
            case R.id.info_overseas_basic_BirthDate:
                datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectDate = +year + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                        Log.e("选定时间：", selectDate);
                        infoOverseasBasicBirthDate.setText(selectDate);
                    }
                }, DateUtils.getDateYear(), DateUtils.getDateMonth() - 1, DateUtils.getDateDay());//获取当前时间
                datePicker.show();
                break;
            /*拍照（照片）*/
            case R.id.info_overseas_basic_Photo:
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

                break;
            /*证件种类*/
            case R.id.info_overseas_certificate_PapersType:
                break;
            /*证件有效期*/
            case R.id.info_overseas_certificate_PapersDate:
                datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectDate = +year + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                        Log.e("选定时间：", selectDate);
                        infoOverseasCertificatePapersDate.setText(selectDate);
                    }
                }, DateUtils.getDateYear(), DateUtils.getDateMonth() - 1, DateUtils.getDateDay());//获取当前时间
                datePicker.show();
                break;
            /*有效次数*/
            case R.id.info_overseas_certificate_Validity:
                break;
            /*签发开始时间*/
            case R.id.info_overseas_certificate_StartDate:
                datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectDate = +year + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                        Log.e("选定时间：", selectDate);
                        infoOverseasCertificateStartDate.setText(selectDate);
                    }
                }, DateUtils.getDateYear(), DateUtils.getDateMonth() - 1, DateUtils.getDateDay());//获取当前时间
                datePicker.show();
                break;
            /*签发结束时间*/
            case R.id.info_overseas_certificate_EndDate:
                datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectDate = +year + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                        Log.e("选定时间：", selectDate);
                        infoOverseasCertificateEndDate.setText(selectDate);
                    }
                }, DateUtils.getDateYear(), DateUtils.getDateMonth() - 1, DateUtils.getDateDay());//获取当前时间
                datePicker.show();
                break;
            /*签发地*/
            case R.id.info_overseas_certificate_IssueAt:
                break;
            /*入境时间*/
            case R.id.info_overseas_certificate_EnterDate:
                datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectDate = +year + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                        Log.e("选定时间：", selectDate);
                        infoOverseasCertificateEnterDate.setText(selectDate);
                    }
                }, DateUtils.getDateYear(), DateUtils.getDateMonth() - 1, DateUtils.getDateDay());//获取当前时间
                datePicker.show();
                break;
            /*入境口岸*/
            case R.id.info_overseas_certificate_EnterPort:
                break;
            /*离开时间*/
            case R.id.info_overseas_certificate_LeaveDate:
                datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectDate = +year + "-" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                        Log.e("选定时间：", selectDate);
                        infoOverseasCertificateLeaveDate.setText(selectDate);
                    }
                }, DateUtils.getDateYear(), DateUtils.getDateMonth() - 1, DateUtils.getDateDay());//获取当前时间
                datePicker.show();
                break;
        }
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

                        infoOverseasBasicPhoto.setImageBitmap(bitmap);
                    } else {
                        Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
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
}
