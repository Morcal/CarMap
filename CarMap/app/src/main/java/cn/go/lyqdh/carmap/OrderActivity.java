package cn.go.lyqdh.carmap;

import android.content.Intent;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.utils.DistanceUtil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.go.lyqdh.carmap.modle.User;

public class OrderActivity extends AppCompatActivity {
    private static final String TAG = OrderActivity.class.getSimpleName();
    public double qiLat, qiLog, zhunLat, zhunLog;
    public static final double qibu = 12.5;
    private GeoCoder mSearch1;
    private GeoCoder mSearch2;
    public LatLng qiLatLng;
    public LatLng zhunLatLng;
    private TextView tvQibu;
    private TextView tvPrice;
    private TextView tvLIchen;
    private TextView tvYuantu;

    private TextView submitOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dingdan);
        initData();
        initView();
        initListener();
    }

    private void initData() {

        Intent intent = getIntent();
        String qi = intent.getStringExtra("QI");
        String zhun = intent.getStringExtra("ZHUN");

        // 地理编码
        mSearch1 = GeoCoder.newInstance();
        mSearch1.setOnGetGeoCodeResultListener(listener1);
        mSearch1.geocode(new GeoCodeOption()
                .city("福州")
                .address(qi));

        mSearch2 = GeoCoder.newInstance();
        mSearch2.setOnGetGeoCodeResultListener(listener2);
        mSearch2.geocode(new GeoCodeOption()
                .city("福州")
                .address(zhun));

    }

    OnGetGeoCoderResultListener listener1 = new OnGetGeoCoderResultListener() {
        public void onGetGeoCodeResult(GeoCodeResult result) {
            Log.i(TAG, "result" + result);
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有检索到结果
                Log.i(TAG, "没找到地儿~");
            } else {
                //获取地理编码结果
                Log.i(TAG, "起点");
                qiLatLng = result.getLocation();
                qiLat = result.getLocation().latitude;
                qiLog = result.getLocation().longitude;
                Log.i(TAG, "lat：" + qiLat + "  " + "log: " + qiLog);

            }

        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有找到检索结果
            }
            //获取反向地理编码结果
        }
    };
    OnGetGeoCoderResultListener listener2 = new OnGetGeoCoderResultListener() {
        public void onGetGeoCodeResult(GeoCodeResult result) {
            Log.i(TAG, "result" + result);
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有检索到结果
                Log.i(TAG, "没找到地儿~");
            } else {
                Log.i(TAG, "终点");
                zhunLatLng = result.getLocation();
                zhunLat = result.getLocation().latitude;
                zhunLog = result.getLocation().longitude;
                Log.i(TAG, "lat：" + zhunLat + "  " + "log: " + zhunLog);

                Log.i(TAG, "起点： " + qiLatLng);
                double dis = DistanceUtil.getDistance(qiLatLng, zhunLatLng);
                int price = (int) (dis * 0.003);// 费用
                tvLIchen.setText(price + "");
                int yuan = 0;
                if (dis > 10000) {
                    yuan = (int) (dis * 0.001); //远途费
                    tvYuantu.setText(+yuan + "");
                }
                tvPrice.setText((price + qibu + yuan) + "");

                Log.i(TAG, "dis--->" + dis);
            }

        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有找到检索结果
            }
            //获取反向地理编码结果
        }
    };

    private void initListener() {
        submitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
                String date = sDateFormat.format(new java.util.Date());
                // 提交数据到Bmob
                Toast.makeText(OrderActivity.this, "提交数据到Bmob", Toast.LENGTH_SHORT).show();
                User user = User.getCurrentUser(OrderActivity.this, User.class);
                String order = "时间 " + date + "   价格 " + tvPrice.getText();
                Log.i("OrderActivity", "order: " + order);
                if (user.getOrder1() == null) {
                    user.setOrder1(order);
                } else {
                    if (user.getOrder2() == null) {
                        user.setOrder2(order);
                    } else {
                        if (user.getOrder3() == null) {
                            user.setOrder3(order);
                        } else {
                            if (user.getOrder4() == null) {
                                user.setOrder4(order);
                            } else {
                                user.setOrder5(order);
                            }
                        }
                    }
                }

                user.update(OrderActivity.this, user.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(OrderActivity.this, "保存订单成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(OrderActivity.this, "保存订单失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    private void initView() {
        tvQibu = (TextView) findViewById(R.id.tv_qibu);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvLIchen = (TextView) findViewById(R.id.tv_licheng);
        tvYuantu = (TextView) findViewById(R.id.tv_yuantu);
        submitOrder = (TextView) findViewById(R.id.but_submitorder);
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
    protected void onDestroy() {
        super.onDestroy();
    }
}
