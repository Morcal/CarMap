package cn.go.lyqdh.carmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.go.lyqdh.carmap.modle.User;
import cn.go.lyqdh.carmap.widget.SexDialog;

public class SetActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout layoutHeader;
    private LinearLayout layoutName;
    private LinearLayout layoutSex;
    private LinearLayout layoutPhone;
    private LinearLayout layouteMail;
    private LinearLayout layoutSignature;

    private TextView tvSignature;
    private TextView tvNickName;

    private TextView tvSex;
    private TextView tvPhone;
    private TextView tvEmail;

    private Toolbar toolbar;
    private FloatingActionButton fab;

    private User user;
    private String nickName;
    private String sex;
    private String phone;
    private String eMail;
    private String qianming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initData();
        initView();
        initListener();
        initEvent();
    }

    private void initData() {
        User user = User.getCurrentUser(SetActivity.this, User.class);
        BmobQuery<User> userQuery = new BmobQuery<User>();
        userQuery.getObject(SetActivity.this, user.getObjectId(), new GetListener<User>() {
            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(SetActivity.this, "查询数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(User user) {
                nickName = user.getUsername();
                sex = user.getSex();
                phone = user.getMobilePhoneNumber();
                eMail = user.getEmail();
                tvNickName.setText(nickName);
                tvSex.setText(sex);
                tvPhone.setText(phone);
                tvEmail.setText(eMail);
                Toast.makeText(SetActivity.this, "查询数据成功", Toast.LENGTH_SHORT).show();
                Log.i("SetActivity", "nickName: " + nickName + "  " + "sex: " + sex);
            }
        });

    }

    private void initListener() {
        layoutHeader.setOnClickListener(this);
        layoutName.setOnClickListener(this);
        layoutSex.setOnClickListener(this);
        layoutPhone.setOnClickListener(this);
        layouteMail.setOnClickListener(this);
        layoutSignature.setOnClickListener(this);
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        layoutHeader = (LinearLayout) findViewById(R.id.item_head);
        layoutName = (LinearLayout) findViewById(R.id.item_nickname);
        layoutPhone = (LinearLayout) findViewById(R.id.item_phone);
        layoutSex = (LinearLayout) findViewById(R.id.item_sex);
        layouteMail = (LinearLayout) findViewById(R.id.item_email);
        layoutSignature = (LinearLayout) findViewById(R.id.item_signature);
        tvSex = (TextView) findViewById(R.id.tv_sex);
        tvPhone = (TextView) findViewById(R.id.tv_phone);
        tvEmail = (TextView) findViewById(R.id.tv_email);
        tvSignature = (TextView) findViewById(R.id.tv_signature);
        tvNickName = (TextView) findViewById(R.id.tv_nickname);
        toolbar.setNavigationIcon(R.drawable.btn_back);
    }

    private void initEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 将个人信息保存到bmob

                User user = User.getCurrentUser(SetActivity.this, User.class);
                user.setUsername(tvNickName.getText().toString());
                user.setSex(tvSex.getText().toString());
                user.setMobilePhoneNumber(tvPhone.getText().toString());
                user.setEmail(tvEmail.getText().toString());
                user.update(SetActivity.this, new UpdateListener() {

                    @Override
                    public void onSuccess() {
                        Toast.makeText(SetActivity.this, "保存数据成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(SetActivity.this, "保存数据失败", Toast.LENGTH_SHORT).show();
                    }
                });
                finish();
            }
        });
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_head:
                break;
            case R.id.item_nickname:
                Intent intent = new Intent(this, NickNameActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.item_sex:
                SexDialog.show(this).setOnDialogItemClickListener(new SexDialog.DialogItemClickListener() {

                    @Override
                    public void onClick(int type) {
                        tvSex.setText(SexDialog.MAN == type ? "男" : "女");
                    }
                });

                break;
            case R.id.item_phone:
                Intent phone = new Intent(this, PhoneActivity.class);
                startActivityForResult(phone, 4);
                break;
            case R.id.item_email:
                Intent email = new Intent(this, EmailActivity.class);
                startActivityForResult(email, 5);
                break;
            case R.id.item_signature:
                Intent signature = new Intent(this, SignatureActivity.class);
                startActivityForResult(signature, 6);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2:
                String nickName = data.getStringExtra("NICENAME");
                tvNickName.setText(nickName);
                break;
            case 4:
                String phone = data.getStringExtra("PHONE");
                tvPhone.setText(phone);
                break;
            case 5:
                String email = data.getStringExtra("EMAIL");
                tvEmail.setText(email);
                break;
            case 6:
                String signature = data.getStringExtra("SIGNATURE");
                tvSignature.setText(signature);
                break;

        }

    }
}
