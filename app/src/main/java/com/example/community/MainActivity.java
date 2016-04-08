package com.example.community;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.community.activity.Information_House;
import com.example.community.activity.login.LoginActivity;
import com.example.community.bean.Version_Object;
import com.example.community.constant.ConstantURL;
import com.example.community.dialog.AlertDialog;
import com.example.community.utils.ToastUtil;
import com.example.community.utils.ToolUtils;
import com.example.community.utils.checkupdate.DownloadService;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MainActivity extends AppCompatActivity
        implements AMapLocationListener, AMap.OnCameraChangeListener, View.OnClickListener, LocationSource, GeocodeSearch.OnGeocodeSearchListener, NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.map)
    MapView mapView;
    @Bind(R.id.location)
    TextView tvCurLocation;
    @Bind(R.id.destination)
    TextView tvDestination;
    @Bind(R.id.myLocation)
    ImageView locationImg;
    @Bind(R.id.container)
    FrameLayout containerLayout;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.re_project)
    RelativeLayout reProject;

    //
    private AMap aMap;
    private BitmapDescriptor movingDescriptor, chooseDescripter, successDescripter;
    private GeocodeSearch geocodeSearch;
    private ImageView ivCircle;
    private LocationManagerProxy mLocationManagerProxy;
    private Handler handler = new Handler();
    private OnLocationChangedListener listener;
    private LatLng myLocation = null;
    private Marker centerMarker;
    private boolean isMovingMarker = false;
    private ValueAnimator animator = null;
    private Context context;
    private boolean isBinded;
    private DownloadService.DownloadBinder binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = MainActivity.this;
        setSupportActionBar(toolbar);

        reProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Information_House.class));
            }
        } );
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, AmapActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                startActivity(intent);
//                overridePendingTransition(0, 0);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startActivity(new Intent(MainActivity.this, Information_House.class));
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 地图
        mapView.onCreate(savedInstanceState);

        initUI();
        initAmap();
        setUpLocationStyle();
        /**
         *  检测更新
         */
        checkUpdate();
    }

    private void initUI() {
        locationImg.setOnClickListener(this);
        tvDestination.setVisibility(View.GONE);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("现在用车");
//        toolbar.setTitleTextColor(Color.WHITE);
//        setSupportActionBar(toolbar);
        introAnimPrepare();
    }

    private void initAmap() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        aMap.setLocationSource(this);// 设置定位监听
        aMap.setMyLocationEnabled(true);
        aMap.getUiSettings().setZoomControlsEnabled(false);

        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(15);
        aMap.moveCamera(cameraUpdate);

        movingDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.icon_loaction_choose_moving);
        chooseDescripter = BitmapDescriptorFactory.fromResource(R.mipmap.icon_loaction_choose);
        successDescripter = BitmapDescriptorFactory.fromResource(R.mipmap.icon_usecarnow_position_succeed);

        geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
    }

    private void setUpLocationStyle() {
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.mipmap.img_location_now));
        myLocationStyle.strokeWidth(0);
        myLocationStyle.radiusFillColor(Color.TRANSPARENT);
        aMap.setMyLocationStyle(myLocationStyle);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camara) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        }
        if (id == R.id.nav_share) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
//        else if (id == R.id.nav_send) {
//
//        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        containerLayout.removeView(ivCircle);

        if (isBinded) {
            unbindService(conn);
        }
        if (binder != null && binder.isCanceled()) {
            Intent it = new Intent(this, DownloadService.class);
            stopService(it);
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getAMapException().getErrorCode() == 0) {
            if (listener != null) {
                listener.onLocationChanged(aMapLocation);// 显示系统小蓝点
            }
            Log.e("等位坐标", aMapLocation.getLatitude() + "----" + aMapLocation.getLongitude());
            ToolUtils.LATITUDE = String.valueOf(aMapLocation.getLatitude());
            ToolUtils.LONGITUDE = String.valueOf(aMapLocation.getLongitude());
            ToolUtils.ADDRESS = aMapLocation.getRoad() + aMapLocation.getStreet() + aMapLocation.getPoiName();
            myLocation = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            tvCurLocation.setText(aMapLocation.getRoad() + aMapLocation.getStreet() + aMapLocation.getPoiName());
            addChooseMarker();
        }
    }

    private void addChooseMarker() {
        MarkerOptions centerMarkerOption = new MarkerOptions().position(myLocation).icon(chooseDescripter);
        centerMarker = aMap.addMarker(centerMarkerOption);
        centerMarker.setPositionByPixels(mapView.getWidth() / 2, mapView.getHeight() / 2);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CameraUpdate update = CameraUpdateFactory.zoomTo(17f);
                aMap.animateCamera(update, 1000, new AMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        aMap.setOnCameraChangeListener(MainActivity.this);
                    }

                    @Override
                    public void onCancel() {
                    }
                });
            }
        }, 1000);
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        listener = onLocationChangedListener;
        mLocationManagerProxy = LocationManagerProxy.getInstance(this);
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork, -1, 100, this);
    }

    public void deactivate() {
        if (mLocationManagerProxy != null) {
            mLocationManagerProxy.removeUpdates(this);
            mLocationManagerProxy.destroy();
        }
        mLocationManagerProxy = null;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        Log.e("正在移动", "-------------");
//        if (centerMarker != null) {
//            setMovingMarker();
//        }
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        LatLonPoint point = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
        RegeocodeQuery query = new RegeocodeQuery(point, 50, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(query);
        if (centerMarker != null) {
            animMarker();
        }
        showLocationView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.myLocation:
                CameraUpdate update = CameraUpdateFactory.changeLatLng(myLocation);
                aMap.animateCamera(update);
                break;
            default:
                break;
        }
    }

    private void setMovingMarker() {
        if (isMovingMarker)
            return;

        isMovingMarker = true;
        centerMarker.setIcon(movingDescriptor);
        hideLocationView();
    }

    private void animMarker() {
        isMovingMarker = false;
        if (animator != null) {
            animator.start();
            return;
        }
        animator = ValueAnimator.ofFloat(mapView.getHeight() / 2, mapView.getHeight() / 2 - 30);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(150);
        animator.setRepeatCount(1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                centerMarker.setPositionByPixels(mapView.getWidth() / 2, Math.round(value));
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                centerMarker.setIcon(chooseDescripter);
            }
        });
        animator.start();
    }

    private void endAnim() {
        if (animator != null && animator.isRunning())
            animator.end();
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i == 0) {
            if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null) {
                endAnim();
                centerMarker.setIcon(successDescripter);
                RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
                String formatAddress = regeocodeResult.getRegeocodeAddress().getFormatAddress();
                String shortAdd = formatAddress.replace(regeocodeAddress.getProvince(), "").replace(regeocodeAddress.getCity(), "").replace(regeocodeAddress.getDistrict(), "");
                Log.e("地址", shortAdd);
//                tvCurLocation.setText(shortAdd);
            } else {
                ToastUtil.show(MainActivity.this, R.string.no_result);
            }
        } else {
            ToastUtil.show(MainActivity.this, R.string.error_network);
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int rCode) {
    }

    private void introAnimPrepare() {
        toolbar.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                toolbar.getViewTreeObserver().removeOnPreDrawListener(this);
                toolbar.setTranslationY(-toolbar.getHeight());
                return false;
            }
        });
        ivCircle = new ImageView(this);
        ivCircle.setImageResource(R.mipmap.tunahome_imageview_bottom);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        containerLayout.addView(ivCircle, params);
        ivCircle.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                ivCircle.getViewTreeObserver().removeOnPreDrawListener(this);
                ivCircle.setTranslationY(containerLayout.getHeight() / 2 - ivCircle.getHeight());
                ivCircle.setScaleX(2f);
                ivCircle.setScaleY(2f);
                return false;
            }
        });
        containerLayout.post(new Runnable() {
            @Override
            public void run() {
                animIntroduce();
            }
        });
    }

    private void animIntroduce() {
        ObjectAnimator animToolbar = ObjectAnimator.ofFloat(toolbar, "TranslationY", 0f);
        animToolbar.setDuration(300);
        ObjectAnimator animCircle = ObjectAnimator.ofFloat(ivCircle, "TranslationY", 0);
        animCircle.setDuration(400);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(ivCircle, "ScaleX", 1f);
        scaleX.setDuration(400);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(ivCircle, "ScaleY", 1f);
        scaleY.setDuration(400);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animToolbar, animCircle, scaleX, scaleY);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                containerLayout.removeView(ivCircle);
                mapView.setVisibility(View.VISIBLE);
                tvCurLocation.setVisibility(View.VISIBLE);
                tvDestination.setVisibility(View.GONE);
            }
        });
        animatorSet.start();
    }

    private void hideLocationView() {
        ObjectAnimator animLocation = ObjectAnimator.ofFloat(tvCurLocation, "TranslationY", -tvCurLocation.getHeight() * 2);
        ObjectAnimator animDestinatiion = ObjectAnimator.ofFloat(tvDestination, "TranslationY", tvDestination.getHeight() * 2);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animDestinatiion, animLocation);
        set.setDuration(200);
        set.start();
    }

    private void showLocationView() {
        ObjectAnimator animLocation = ObjectAnimator.ofFloat(tvCurLocation, "TranslationY", 0);
        ObjectAnimator animDestinatiion = ObjectAnimator.ofFloat(tvDestination, "TranslationY", 0);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animDestinatiion, animLocation);
        set.setDuration(200);
        set.start();
    }

    /**
     * 检测更新
     */
    private void checkUpdate() {
        OkHttpUtils.get()
                .url(ConstantURL.UPDATE)
                .build().execute(new StringCallback() {
                                     @Override
                                     public void onError(Call call, Exception e) {
                                         Toast.makeText(MainActivity.this, getString(R.string.base_server), Toast.LENGTH_SHORT).show();
                                     }

                                     @Override
                                     public void onResponse(String response) {
                                         try {
                                             Gson gson = new Gson();
                                             Version_Object object = gson.fromJson(response, Version_Object.class);
                                             if (object != null) {
                                                 int version = Integer.parseInt(object.getVersionValue().getVersionOld());
                                                 ToolUtils.apkUrl = object.getVersionValue().getApkUrl();
                                                 ToolUtils.versionCode = ToolUtils.getVersionCode(context);
                                                 System.out.println("本地版本：" + ToolUtils.versionName + "服务器版本:" + version);
                                                 if (ToolUtils.versionCode == version) {
                                                     Log.i("更新", "版本相同,无需升级, 进入主界面");
                                                 } else {
                                                     Log.i("更新", "版本不同,需要升级");
                                                     showUpdateDialog();
                                                 }
                                             } else {
                                                 Toast.makeText(context, context.getString(R.string.base_server), Toast.LENGTH_SHORT).show();
                                             }
                                         } catch (Exception e) {
                                             e.printStackTrace();
                                         }

                                     }
                                 }

        );
    }

    /**
     * 检测更新
     */
    private void showUpdateDialog() {
        new AlertDialog(context)
                .builder()
                .setTitle("检测到新版本")
                .setMsg("更新内容：" + "")
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setPositiveButton("下载", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Comm_Application.setIsDownload(true);
                        if (Comm_Application.isDownload()) {
                            Intent it = new Intent(context, DownloadService.class);
                            context.startService(it);
                            context.bindService(it, conn, Context.BIND_AUTO_CREATE);
                        }
                    }
                })
                .show();
    }

    ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
            isBinded = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            binder = (DownloadService.DownloadBinder) service;
            System.out.println("服务启动!!!");
            // 开始下载
            isBinded = true;
            binder.addCallback(callback);
            binder.start();
        }
    };

    public interface ICallbackResult {
        public void OnBackResult(Object result);
    }

    private ICallbackResult callback = new ICallbackResult() {

        @Override
        public void OnBackResult(Object result) {
            // TODO Auto-generated method stub
            if ("finish".equals(result)) {
                finish();
                return;
            }
        }

    };
}
