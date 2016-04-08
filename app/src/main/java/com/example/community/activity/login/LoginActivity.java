package com.example.community.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.community.MainActivity;
import com.example.community.R;
import com.example.community.bean.Login;
import com.example.community.bean.Login_Object;
import com.example.community.constant.ConstantString;
import com.example.community.constant.ConstantURL;
import com.example.community.dialog.ECProgressDialog;
import com.example.community.utils.DemoUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Author：Mark
 * Date：2015/11/25 0025
 * Tell：15006330640
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    @Bind(R.id.login_username)
    EditText loginUsername;
    @Bind(R.id.login_iv)
    ImageView loginIv;
    @Bind(R.id.login_password)
    EditText loginPassword;
    @Bind(R.id.login_ll)
    LinearLayout loginLl;
    @Bind(R.id.login_check)
    CheckBox loginCheck;
    @Bind(R.id.login_btn)
    Button loginBtn;
    /**
     * 容联
     */
    private ECProgressDialog mPostingdialog;


    // 对象
    private Context context;
    private ECProgressDialog mPostingDialog;
    private SharedPreferences preferences;
    private String currentUsername;
    private String currentPassword;
    private boolean progressShow;
    private Login login;
    private RequestCall mCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initResourceRefs();
        initConstants();
        loginBtn.setOnClickListener(this);
        // 自动补全登陆账号信息
        Login_Object object = DemoUtils.getLoginAccount(context);
        if (!object.getUsername().equals("") && !object.getPassword().equals("")) {
            loginUsername.setText(object.getUsername());
            loginPassword.setText(object.getPassword());
            loginPassword.requestFocus();
        }
    }

    private void initResourceRefs() {
        loginUsername = (EditText) super.findViewById(R.id.login_username);
        loginPassword = (EditText) super.findViewById(R.id.login_password);
        loginBtn = (Button) super.findViewById(R.id.login_btn);
    }

    private void initConstants() {
        preferences = getSharedPreferences(ConstantString.USER, Activity.MODE_PRIVATE);
        context = LoginActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                if (checkInput()) {
                    mPostingDialog = new ECProgressDialog(this, R.string.login_posting);
                    mPostingDialog.show();
                    login();
                }
                break;
        }
    }

    private boolean checkInput() {
        if (loginUsername.getText().toString().equals("")) {
            Toast.makeText(context, "请输入用户名！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (loginPassword.getText().toString().equals("")) {
            Toast.makeText(context, "请输入密码！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void login() {
        mCall = OkHttpUtils.post()
                .url(ConstantURL.LOGIN)
                .addParams(ConstantString.USERNAME, loginUsername.getText().toString())
                .addParams(ConstantString.PASSWORD, loginPassword.getText().toString())
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
                    login = gson.fromJson(response, Login.class);
                    if (login != null) {
                        System.out.println("登录：" + login.getState() + login.getName() + login.getUid());
                        if (login.getState().equals("1")) {

                        } else if (login.getState().equals("2")) {
                            Toast.makeText(context, getString(R.string.login_error2), Toast.LENGTH_SHORT).show();
                        } else if (login.getState().equals("3")) {
                            Toast.makeText(context, getString(R.string.login_error3), Toast.LENGTH_SHORT).show();
                        } else if (login.getState().equals("4")) {
                            Toast.makeText(context, getString(R.string.login_error4), Toast.LENGTH_SHORT).show();
                        } else if (login.getState().equals("5")) {
                            saveAutoLogin();
                        }
                    } else {
                        Toast.makeText(context, getString(R.string.base_server), Toast.LENGTH_SHORT).show();
                    }
                    DemoUtils.dismissPostingDialog(mPostingDialog);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 保存登陆账号
     */
    private void saveAutoLogin() {
        try {
            dismissPostingDialog();
            String account = loginUsername.getText().toString();
            String password = loginPassword.getText().toString();
            preferences.edit()
                    .putString(ConstantString.USERNAME, account)
                    .putString(ConstantString.PASSWORD, password)
                    .putString(ConstantString.NAME, login.getName())
                    .putString(ConstantString.UID, login.getUid()).commit();
            startActivity(new Intent(context, MainActivity.class));
            LoginActivity.this.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 关闭对话框
     */
    private void dismissPostingDialog() {
        if (mPostingdialog == null || !mPostingdialog.isShowing()) {
            return;
        }
        mPostingdialog.dismiss();
        mPostingdialog = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCall != null) {
            mCall.cancel();
        }
        ButterKnife.unbind(this);
    }
}
