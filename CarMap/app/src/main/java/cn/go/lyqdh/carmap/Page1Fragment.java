package cn.go.lyqdh.carmap;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by lyqdh on 2016/1/11.
 */
public class Page1Fragment extends Fragment implements View.OnClickListener {
    public static final String ARG_PAGE = "ARG_PAGE";
    private String phone;
    private String qq;
    private String weixin;
    private LinearLayout layoutPhone;
    private LinearLayout layoutQQ;
    private LinearLayout layoutWeiXin;

    public static Page1Fragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        Page1Fragment pageFragment = new Page1Fragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        Bundle bundle = getArguments();
        phone = bundle.getString("phone");
        qq = bundle.getString("qq");
        weixin = bundle.getString("weixin");

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_diver_tab1, container, false);
        layoutPhone = (LinearLayout) view.findViewById(R.id.layout_phone);
        layoutQQ = (LinearLayout) view.findViewById(R.id.layout_qq);
        layoutWeiXin = (LinearLayout) view.findViewById(R.id.layout_weixin);
        layoutPhone.setOnClickListener(this);
        layoutWeiXin.setOnClickListener(this);
        layoutQQ.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_phone:
                // 拨打电话
                Log.i("Page1Fragment", "电话");
                Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
                        + phone));
                startActivity(intent1);
                break;
            case R.id.layout_qq:
                // 跳转到qq
                Log.i("Page1Fragment", "qq");
                Intent intent2 = new Intent();
                intent2.setClassName("com.tencent.mobileqq",
                        "com.tencent.mobileqq.activity.SplashActivity");
                startActivity(intent2);
                break;
            case R.id.layout_weixin:
                // 跳转到微信
                Log.i("Page1Fragment", "微信");

                Intent intent = new Intent();
                ComponentName cmp = new ComponentName(" com.tencent.mm ", "com.tencent.mm.ui.LauncherUI");
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setComponent(cmp);

                break;
        }
    }
}
