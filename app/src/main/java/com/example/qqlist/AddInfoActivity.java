package com.example.qqlist;

import android.content.ContentValues;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qqlist.base.BaseActivity;
import com.example.qqlist.bean.EventBus_Tag;
import com.example.qqlist.bean.QQBean;
import com.example.qqlist.util.DateUtil;
import com.example.qqlist.util.StrUtil;
import com.example.qqlist.util.ToastUtil;
import com.example.qqlist.util.fenleiUtil.I_itemSelectedListener;
import com.example.qqlist.util.fenleiUtil.ItemChooseUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class    AddInfoActivity  extends BaseActivity {

    @BindView(R.id.edt_name)
    EditText edt_name;
    @BindView(R.id.edt_kll)
    TextView edt_kll;
    @BindView(R.id.imgv_pic)
    ImageView imgv_pic;
    @BindView(R.id.tv_login)
    TextView tv_login;
    @BindView(R.id.tv_title)
    TextView tv_title;

    private List<String> list_type;  //类别
    private String[] str_type = new String[]{"关注", "热榜", "科技", "体育", "娱乐"};
    private int pic = R.mipmap.ic_launcher_round;
    int stype = 0;//0添加，1修改
    QQBean bean;

    @Override
    protected void setContent() {
        super.setContent();
        setContentView(R.layout.activity_add_pic);
        ButterKnife.bind(this);
        registerEventBus();
    }

    @Override
    protected void initData() {
        stype = getIntent().getIntExtra("stype", 0);
        bean = (QQBean) getIntent().getSerializableExtra("bean");
        //set view
        if (stype == 0) {
            tv_title.setText("添加家校通信息");
        } else {
            tv_title.setText("修改家校通信息");

            edt_name.setText(bean.getName());
            edt_kll.setText(bean.getContents());
            pic = bean.getmPic();
            imgv_pic.setImageResource(pic);

        }
        //类别
        list_type = new ArrayList();
        list_type = Arrays.asList(str_type);
        edt_kll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemChooseUtil.showItemWheel(AddInfoActivity.this, list_type, "类别", 0, new I_itemSelectedListener() {
                    @Override
                    public void onItemSelected(int currentPosition) {
                        edt_kll.setText(list_type.get(currentPosition));
                    }
                });
            }
        });

        imgv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddInfoActivity.this, ChoicePicActivity.class));
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempPhone = edt_name.getText().toString().trim();
                String tempPwd = edt_kll.getText().toString().trim();
                if (StrUtil.isEmpty(tempPhone) || StrUtil.isEmpty(tempPwd)) {
                    ToastUtil.showToast(myActivity, "名称或类别不能为空");
                    return;
                }
                if (stype == 0) {//add
                    QQBean tempBean = new QQBean();
                    tempBean.setName(tempPhone);
                    tempBean.setContents(tempPwd);
                    tempBean.setTimes(DateUtil.getTodayData_3());
                    tempBean.setmPic(pic);
                    tempBean.save();
                    if (tempBean.isSaved()) {
                        ToastUtil.showToast(myActivity, "添加成功");
                    }
                } else {//updata
                    ContentValues values = new ContentValues();
                    values.put("name", tempPhone);
                    values.put("contents", tempPwd);
                    values.put("mPic", pic);
                    DataSupport.updateAll(QQBean.class, values, "times = ?", bean.getTimes());
                    ToastUtil.showToast(myActivity, "修改成功");
                }
                EventBus.getDefault().post(new EventBus_Tag(1));
                finish();

            }
        });

    }

    @Override
    protected void initListener() {

    }

    public void registerEventBus() {
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBus_Tag event) {
        switch (event.getTag()) {
            case 2:
                imgv_pic.setImageResource(event.getPosition());
                pic = event.getPosition();
                break;
        }
    }
}
