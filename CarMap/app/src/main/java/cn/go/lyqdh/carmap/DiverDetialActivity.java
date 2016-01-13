package cn.go.lyqdh.carmap;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.go.lyqdh.carmap.adapter.SimpleFragmentPagerAdapter;
import cn.go.lyqdh.carmap.widget.CircleImageView;

public class DiverDetialActivity extends AppCompatActivity {
    private static final String TAG = DiverDetialActivity.class.getSimpleName();
    private Toolbar toolbar;
    private String name;
    private String sex;
    private String phone;
    private String qq;
    private String weixin;

    private int imgId;
    private int age;
    private int zan;
    private int comment;

    private CircleImageView avatar;
    private TextView tvage;
    private TextView nickName;
    private TextView signature;
    private TextView like;

    private SimpleFragmentPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Fragment fragment1;
    private Fragment fragment2;
    private List<Fragment> fragmentList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diverdetial);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        sex = intent.getStringExtra("sex");
        phone = intent.getStringExtra("phone");
        qq = intent.getStringExtra("qq");
        weixin = intent.getStringExtra("weixin");
        imgId = intent.getIntExtra("imgId", R.drawable.avator);
        age = intent.getIntExtra("age", 23);
        zan = intent.getIntExtra("zan", 45);
        comment = intent.getIntExtra("comment", 67);

        pagerAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), this);
        Bundle bundle = new Bundle();
        bundle.putString("phone", phone);
        bundle.putString("qq", qq);
        bundle.putString("weixin", weixin);
        fragment1 = new Page1Fragment();
        fragment2 = new Page2Fragment();
        fragment1.setArguments(bundle);
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        pagerAdapter.setItems(fragmentList);
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tvage = (TextView) findViewById(R.id.tx_age);
        like = (TextView) findViewById(R.id.user_like);
        signature = (TextView) findViewById(R.id.user_signature);
        nickName = (TextView) findViewById(R.id.user_nickname);
        avatar = (CircleImageView) findViewById(R.id.user_avatar);
        avatar.setImageResource(imgId);
        nickName.setText(name);
        like.setText(zan + "");
        tvage.setText(age + "");
        toolbar.setNavigationIcon(R.drawable.btn_back);


        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setTabMode();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
