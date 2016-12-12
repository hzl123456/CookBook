package cn.xmrk.cookbook.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.xmrk.cookbook.R;
import cn.xmrk.cookbook.pojo.CookBookInfo;
import cn.xmrk.rkandroid.activity.BackableBaseActivity;
import cn.xmrk.rkandroid.base.BaseFragActView;
import cn.xmrk.rkandroid.utils.RKUtil;

/**
 * 作者：请叫我百米冲刺 on 2016/10/27 14:51
 * 邮箱：mail@hezhilin.cc
 */

public class CookBookDetailActivity extends BackableBaseActivity implements BaseFragActView {


    private static final String OPTION_IMAGE = "image";
    private CookBookInfo ckInfo;

    private ImageView iv_head;
    private TextView tv_des;
    private TextView tv_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ck_detail);

        findViews();
        initViews();
    }

    public static void StartCookBookDetailActivity(Activity activity, View transitionView, CookBookInfo ckInfo) {
        Intent intent = new Intent(activity, CookBookDetailActivity.class);
        intent.putExtra("data", ckInfo);
        // 这里指定了共享的视图元素
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionView, OPTION_IMAGE);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    public void findViews() {
        iv_head = (ImageView) findViewById(R.id.iv_head);
        tv_des = (TextView) findViewById(R.id.tv_des);
        tv_name= (TextView) findViewById(R.id.tv_name);
    }

    @Override
    public void initViews() {
        ckInfo = (CookBookInfo) getIntent().getExtras().get("data");
        // 这里指定了被共享的视图元素
        ViewCompat.setTransitionName(iv_head, OPTION_IMAGE);

        //绑定数据
        RKUtil.displayImage(ckInfo.getImg(), iv_head, R.drawable.bg_caip, this);
        tv_des.setText(ckInfo.getDescription());
        tv_name.setText(ckInfo.getName());
    }

    @Override
    public void setPresenter(Object presenter) {


    }
}
