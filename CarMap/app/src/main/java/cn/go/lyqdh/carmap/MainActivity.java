package cn.go.lyqdh.carmap;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.Point;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.platform.comapi.map.A;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import cn.go.lyqdh.carmap.adapter.LeftMenuAdapter;
import cn.go.lyqdh.carmap.modle.AddressInfo;
import cn.go.lyqdh.carmap.modle.LeftMenu;
import cn.go.lyqdh.carmap.modle.User;
import cn.go.lyqdh.carmap.widget.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private List<LeftMenu> list;
    private ListView drawList;
    private LeftMenuAdapter adapter;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private MapView mapView;
    private BaiduMap baiduMap;
    private BaiduReceiver mRecevier;
    private LocationClient mLocClient;
    private static BDLocation lastLocation = new BDLocation();
    private GeoCoder mSearch = null;
    BitmapDescriptor bdgeo = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_geo);
    // 覆盖物相关
    private BitmapDescriptor mMarker;

    private User user;

    private TextView tvQidian;
    private TextView tvZhund;
    private ImageView submit;

    public String qi;
    public String zhun;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        drawList.setAdapter(adapter);
        initListener();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void initData() {
        user = getIntent().getParcelableExtra("USER");
        initList();
        drawList = new ListView(this);
        adapter = new LeftMenuAdapter(this);
        adapter.setItems(list);
        mMarker = BitmapDescriptorFactory.fromResource(R.drawable.maker);

    }

    private void initList() {
        list = new ArrayList<>();
        LeftMenu payment = new LeftMenu("付款", R.drawable.ub__icon_menu_payment_normal);
        LeftMenu history = new LeftMenu("历史记录", R.drawable.ub__icon_menu_trip_history_normal);
        LeftMenu promo = new LeftMenu("登录注册", R.drawable.ub__icon_menu_promo_normal);
        LeftMenu setting = new LeftMenu("设置", R.drawable.ub__icon_menu_settings_normal);
        LeftMenu about = new LeftMenu("关于", R.drawable.ub__icon_menu_share_normal);
        list.add(payment);
        list.add(history);
        list.add(promo);
        list.add(setting);
        list.add(about);

    }

    private void initListener() {
        drawList.setOnItemClickListener(new DrawItemClickListener());
        // 点击覆盖物显示
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle extraInfo = marker.getExtraInfo();
                AddressInfo info = extraInfo.getParcelable("addinfo");
                // String name, int age, String sex, String phone, String QQ, String weiXin, int zan, int comment, int avator
                int imgId = info.getDriver().getAvator();
                int age = info.getDriver().getAge();
                int zan = info.getDriver().getZan();
                int comment = info.getDriver().getComment();
                String name = info.getDriver().getName();
                String sex = info.getDriver().getSex();
                String phone = info.getDriver().getPhone();
                String qq = info.getDriver().getQQ();
                String weixin = info.getDriver().getWeiXin();

                View view = LayoutInflater.from(MainActivity.this).inflate(
                        R.layout.view_drivermarker, null);
                CircleImageView driverMark = (CircleImageView) view.findViewById(R.id.civ_drivermark);
                driverMark.setImageResource(imgId);
                driverMarkClick(driverMark, imgId, age, zan, comment, name, sex, phone, qq, weixin);
                final LatLng latLng = marker.getPosition();
                // 将经纬度转化为屏幕上点
                android.graphics.Point point = baiduMap.getProjection()
                        .toScreenLocation(latLng);
                point.y -= 47;// 设置一个偏移量
                LatLng ll = baiduMap.getProjection().fromScreenLocation(point);

                // 初始化infoWindow
                InfoWindow infoWindow = new InfoWindow(driverMark, ll, -47);// 5为y轴偏移量

                // 显示infoWindow
                baiduMap.showInfoWindow(infoWindow);
                return true;
            }

            private int OnInfoWindowClickListener() {
                baiduMap.hideInfoWindow();
                return 0;
            }
        });

        tvQidian.setOnClickListener(this);
        tvZhund.setOnClickListener(this);
        submit.setOnClickListener(this);

    }

    private void driverMarkClick(View view, final int imgId, final int age, final int zan, final int comment, final String name, final String sex, final String phone, final String qq, final String weixin) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到司机详情页
                Intent intent = new Intent(MainActivity.this, DiverDetialActivity.class);
                // String name, int age, String sex, String phone, String QQ, String weiXin, int zan, int comment, int avator
                intent.putExtra("imgId", imgId);
                intent.putExtra("age", age);
                intent.putExtra("zan", zan);
                intent.putExtra("comment", comment);
                intent.putExtra("name", name);
                intent.putExtra("sex", sex);
                intent.putExtra("phone", phone);
                intent.putExtra("weixin", weixin);
                intent.putExtra("qq", qq);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_qidian:
                Log.i(TAG, "起点");
                Intent intent1 = new Intent(MainActivity.this, EditQiActivity.class);
                startActivityForResult(intent1, 1);
                break;
            case R.id.tv_zhundian:
                Log.i(TAG, "准点");
                Intent intent2 = new Intent(MainActivity.this, EditZhActivity.class);
                startActivityForResult(intent2, 2);
                break;
            case R.id.submit_select:
                Log.i(TAG, "提交");
                Intent intent3 = new Intent(MainActivity.this, OrderActivity.class);
                intent3.putExtra("QI", qi);
                intent3.putExtra("ZHUN", zhun);
                startActivity(intent3);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                qi = data.getStringExtra("QIDIAN");
                tvQidian.setText(qi);
                break;
            case 2:
                zhun = data.getStringExtra("ZHUNDIAN");
                tvZhund.setText(zhun);
                break;

        }

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://cn.go.lyqdh.carmap/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://cn.go.lyqdh.carmap/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private class DrawItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
            Log.i(TAG, "position:" + position);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void selectItem(int position) {
        drawList.setItemChecked(position, true);
        drawerLayout.closeDrawer(drawList);
        switch (position) {
            case 0:
                Uri uri = Uri.parse("https://auth.alipay.com/login/index.htm");
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent1);
                break;
            case 1:
                // 历史纪录
                Intent history = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(history);
                break;
            case 2:
                Intent intent3 = new Intent(MainActivity.this, LoginRegistActivity.class);
                startActivity(intent3);
                break;
            case 3:
                // 设置
                Intent intent5 = new Intent(this, SetActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("USER", user);
                intent5.putExtras(bundle);
                startActivity(intent5);
                break;
            case 4:
                Intent intent6 = new Intent(this, AboutActivity.class);
                startActivity(intent6);
                break;
            default:
                break;

        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawList = (ListView) findViewById(R.id.left_menu);
        tvQidian = (TextView) findViewById(R.id.tv_qidian);
        tvZhund = (TextView) findViewById(R.id.tv_zhundian);
        submit = (ImageView) findViewById(R.id.submit_select);
        initBaiduMap();
        drawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerToggle.syncState();
        drawerLayout.setDrawerListener(drawerToggle);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initBaiduMap() {
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.showZoomControls(false);
        baiduMap = mapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomBy(15.0f);
        baiduMap.setMapStatus(msu);
        baiduMap.setMaxAndMinZoomLevel(18, 13);

        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mRecevier = new BaiduReceiver();
        registerReceiver(mRecevier, iFilter);

        initLocClient();

        addOverlays(AddressInfo.infos);
    }

    private void initLocClient() {
        baiduMap.setMyLocationEnabled(true);
        baiduMap
                .setMyLocationConfigeration(new MyLocationConfiguration(
                        MyLocationConfiguration.LocationMode.NORMAL,
                        true, null));
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(new MyLocationListenner());
        LocationClientOption option = new LocationClientOption();
        option.setProdName("bmobim");
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        option.setIgnoreKillProcess(true);
        mLocClient.setLocOption(option);
        mLocClient.start();

        if (mLocClient != null && mLocClient.isStarted())
            mLocClient.requestLocation();

        if (lastLocation != null) {
            LatLng ll = new LatLng(lastLocation.getLatitude(),
                    lastLocation.getLongitude());

            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(u);
        }
    }

    private void addOverlays(List<AddressInfo> infos) {
        baiduMap.clear();
        LatLng latLng = null;
        Marker marker = null;
        OverlayOptions options;
        for (AddressInfo info : infos) {
            // 经纬度
            latLng = new LatLng(info.getLatitude(), info.getLongitude());
            // 图标
            options = new MarkerOptions().position(latLng).icon(mMarker)
                    .zIndex(5);
            marker = (Marker) baiduMap.addOverlay(options);
            Bundle arg0 = new Bundle();
            // 通过序列化后的id
            arg0.putParcelable("addinfo", info);
            marker.setExtraInfo(arg0);
        }

        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        baiduMap.setMapStatus(msu);

    }

    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // 获得当前地址的省市
            String address1 = lastLocation.getAddrStr();
            String sheng = lastLocation.getProvince();
            String city = lastLocation.getCity();
            String quxian = lastLocation.getDistrict();
            System.out.println("获得当前地址的省市" + address1 + " " + sheng + " "
                    + city + " " + quxian);

            // map view 销毁后不在处理新接收的位置
            if (location == null || mapView == null)
                return;

            if (lastLocation != null) {
                if (lastLocation.getLatitude() == location.getLatitude()
                        && lastLocation.getLongitude() == location
                        .getLongitude()) {
                    Log.i("MainActivity", "获取坐标相同");// 若两次请求获取到的地理位置坐标是相同的，则不再定位
                    mLocClient.stop();
                    return;
                }
            }
            lastLocation = location;

            Log.i("MainActivity", "lontitude = " + location.getLongitude()
                    + ",latitude = " + location.getLatitude() + ",地址 = "
                    + lastLocation.getAddrStr());

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            baiduMap.setMyLocationData(locData);
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            String address = location.getAddrStr();
            if (address != null && !address.equals("")) {
                lastLocation.setAddrStr(address);
            } else {
                // 反Geo搜索
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ll));
            }
            // 显示在地图上
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(u);

        }
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

    /**
     * 构造广播监听类，监听 SDK key 验证以及网络异常广播
     */
    public class BaiduReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String s = intent.getAction();
            if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
                Log.i("BaiduReceiver",
                        "key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置");
            } else if (s
                    .equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
                Log.i("BaiduReceiver", "网络出错");
            }
        }
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mLocClient != null && mLocClient.isStarted()) {
            // 退出时销毁定位
            mLocClient.stop();
        }
        // 关闭定位图层
        baiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;
        // 取消监听 SDK 广播
        unregisterReceiver(mRecevier);
        super.onDestroy();
        // 回收 bitmap 资源
        bdgeo.recycle();
    }
}
