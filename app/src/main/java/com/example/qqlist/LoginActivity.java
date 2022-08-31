package com.example.qqlist;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


import com.example.qqlist.base.BaseActivity;
import com.example.qqlist.base.GlobalData;
import com.example.qqlist.base.MyApplication;
import com.example.qqlist.bean.UserInfoBean;
import com.example.qqlist.util.SharedPreUtils;
import com.example.qqlist.util.StrUtil;
import com.example.qqlist.util.ToastUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class     LoginActivity extends BaseActivity {
    @BindView(R.id.tv_regi)
    TextView tv_regi;
    @BindView(R.id.tv_login)
    TextView tv_login;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.iv_right_err)
    ImageView iv_right_err;

    @Override
    protected void setContent() {
        super.setContent();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @Override
    protected void initData() {
        int temp = (int) SharedPreUtils.get(myContext, GlobalData.sp_key_1, 0);
        String temp2 = (String) SharedPreUtils.get(myContext, GlobalData.sp_key_2, "");
        String temp3 = (String) SharedPreUtils.get(myContext, GlobalData.sp_key_3, "");
        if (temp == 0) {
            iv_right_err.setBackgroundResource(R.mipmap.icon_0);
        } else {
            iv_right_err.setBackgroundResource(R.mipmap.icon_1);
            et_phone.setText(temp2);
            et_pwd.setText(temp3);
        }
    }

    @Override
    protected void initListener() {

        iv_right_err.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int) SharedPreUtils.get(myContext, GlobalData.sp_key_1, 0);
                if (temp == 0) {
                    SharedPreUtils.put(myContext, GlobalData.sp_key_1, 1);
                    iv_right_err.setBackgroundResource(R.mipmap.icon_1);
                } else {
                    SharedPreUtils.put(myContext, GlobalData.sp_key_1, 0);
                    iv_right_err.setBackgroundResource(R.mipmap.icon_0);
                }
            }
        });

        tv_regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RegiDialog(myActivity).showDialog();
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempPhone = et_phone.getText().toString().trim();
                String tempPwd = et_pwd.getText().toString().trim();
                if (StrUtil.isEmpty(tempPhone) || StrUtil.isEmpty(tempPwd)) {
                    ToastUtil.showToast(myActivity, "手机号或密码不能为空");
                    return;
                }
                if (tempPhone.length() != 11) {
                    ToastUtil.showToast(myActivity, "手机号错误");
                    return;
                }
                List<UserInfoBean> list = DataSupport.where("name = " + tempPhone + " and " + "pwd = " + tempPwd).find(UserInfoBean.class);//查询表Comment
                if (list.size() == 0) {
                    ToastUtil.showToast(myActivity, "手机号或密码错误");
                    return;
                }
                int temp = (int) SharedPreUtils.get(myContext, GlobalData.sp_key_1, 0);
                if (temp == 0) {
                    SharedPreUtils.put(myContext, GlobalData.sp_key_2, "");
                    SharedPreUtils.put(myContext, GlobalData.sp_key_3, "");
                } else {
                    SharedPreUtils.put(myContext, GlobalData.sp_key_2, tempPhone);
                    SharedPreUtils.put(myContext, GlobalData.sp_key_3, tempPwd);
                }

                ToastUtil.showToast(myActivity, "登录成功");
                MyApplication.curUser = tempPhone;
                startActivity(new Intent(myContext, MainActivity.class));
                finish();
            }
        });
    }


}
