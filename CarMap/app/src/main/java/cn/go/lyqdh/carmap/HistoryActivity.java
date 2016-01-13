package cn.go.lyqdh.carmap;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import cn.go.lyqdh.carmap.modle.User;

public class HistoryActivity extends AppCompatActivity {
    private static final String TAG = HistoryActivity.class.getSimpleName();
    private Toolbar toolbar;
    private TextView tvOrder1;
    private TextView tvOrder2;
    private TextView tvOrder3;
    private TextView tvOrder4;
    private TextView tvOrder5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        BmobQuery<User> historyQuery = new BmobQuery<User>();
        User user = User.getCurrentUser(HistoryActivity.this, User.class);
        historyQuery.getObject(HistoryActivity.this, user.getObjectId(), new GetListener<User>() {
            @Override
            public void onSuccess(User user) {
                Toast.makeText(HistoryActivity.this, "获取历史纪录成功", Toast.LENGTH_SHORT).show();
                if (user.getOrder1() != null) {
                    tvOrder1.setVisibility(View.VISIBLE);
                    tvOrder1.setText("订单1:" + user.getOrder1());
                    if (user.getOrder2() != null) {
                        tvOrder2.setVisibility(View.VISIBLE);
                        tvOrder2.setText("订单2:" + user.getOrder2());
                        if (user.getOrder3() != null) {
                            tvOrder3.setVisibility(View.VISIBLE);
                            tvOrder3.setText("订单3:" + user.getOrder3());
                            if (user.getOrder4() != null) {
                                tvOrder4.setVisibility(View.VISIBLE);
                                tvOrder4.setText("订单4:" + user.getOrder4());
                                if (user.getOrder5() != null) {
                                    tvOrder5.setVisibility(View.VISIBLE);
                                    tvOrder5.setText("订单5:" + user.getOrder5());
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(HistoryActivity.this, "获取历史纪录失败", Toast.LENGTH_SHORT).show();
            }
        });
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
        tvOrder1 = (TextView) findViewById(R.id.tv_order1);
        tvOrder2 = (TextView) findViewById(R.id.tv_order2);
        tvOrder3 = (TextView) findViewById(R.id.tv_order3);
        tvOrder4 = (TextView) findViewById(R.id.tv_order4);
        tvOrder5 = (TextView) findViewById(R.id.tv_order5);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.btn_back);
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
