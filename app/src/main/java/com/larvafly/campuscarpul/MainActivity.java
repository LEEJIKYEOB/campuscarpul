package com.larvafly.campuscarpul;


import com.gc.materialdesign.views.ButtonFloat;
import com.larvafly.adapter.MainViewPagerAdapter;
import com.larvafly.http.HTTP_Handler;
import com.larvafly.http.JOIN_ROOM_HTTP;
import com.larvafly.lib.Static_date;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBarDrawerToggle;

import android.view.Menu;
import android.view.MenuItem;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import me.drakeet.materialdialog.MaterialDialog;


@SuppressLint("DefaultLocale")
public class MainActivity extends ActionBarActivity implements MaterialTabListener {
    public static Activity MainActivity = null;
    MaterialTabHost tabHost;
    ViewPager pager;
    MainViewPagerAdapter adapter;
    ActionBarDrawerToggle dtToggle;
    DrawerLayout dlDrawer;
    ButtonFloat buttonFloat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        MainActivity = this;

        buttonFloat = (ButtonFloat) findViewById(R.id.main_room_add);
        buttonFloat.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Static_date.myProfile.getState() == 2) {
                    Intent intent = new Intent(MainActivity.this, Add_room.class);
                    startActivity(intent);
                } else {
                    carorder_popup(Static_date.myProfile.getState());
                }
            }
        });

        Toolbar toolbar = (android.support.v7.widget.Toolbar) this.findViewById(R.id.toolbar);
        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.setSupportActionBar(toolbar);

        dtToggle = new ActionBarDrawerToggle(this, dlDrawer, R.string.app_name, R.string.app_name);
        dlDrawer.setDrawerListener(dtToggle);

        tabHost = (MaterialTabHost) this.findViewById(R.id.tabHost);
        pager = (ViewPager) this.findViewById(R.id.viewpager);

        // init view pager
        adapter = new MainViewPagerAdapter(this, getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);

            }
        });

        // insert all tabs from pagerAdapter data
        for (int i = 0; i < adapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(adapter.getPageTitle(i))
                            .setTabListener(this)
            );

        }
        if (Static_date.carpopup) carorder_popup(0);

    }


    @Override
    public void onBackPressed() {
        MainActivity = null;

        Log.d("test", "MainActivity onBackPressed");

        super.onBackPressed();
    }

    @Override
    public void finish() {
        MainActivity = null;

        Log.d("test", "MainActivity finish");
        super.finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        chat_fragment_refresh();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        dtToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dtToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (dtToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(MaterialTab tab) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }

    public void chat_fragment_refresh() {

        for (int i = 0; i < adapter.getCount(); i++) {
            Meching_Fragment meching_fragment = (Meching_Fragment) adapter.getItem(i);
            meching_fragment.refresh();
        }
    }

    public void carorder_popup(final int state) {

        // 0 = 카풀등록 버튼 누를때 넘어온것
        // 1 = 심사 대기중

        String message = "";
        switch (state) {
            case 0:
                message = "운전자로 등록을 하면 카풀을 만들수 있어요. 지금 등록 하시겠습니까?";
                break;
            case 1:
                message = "현재 심사가 진행중입니다. 소요시간은 1~2일 걸릴수 있습니다.";
                break;
        }

        final MaterialDialog mMaterialDialog = new MaterialDialog(this);
        mMaterialDialog.setTitle("운전자등록");
        mMaterialDialog.setCanceledOnTouchOutside(true);
        mMaterialDialog.setMessage(message);
        mMaterialDialog.setPositiveButton("확인", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (state != 1) {
                    Intent intent = new Intent(MainActivity.this, Join02Activity.class);
                    startActivity(intent);
                }

                mMaterialDialog.dismiss();

            }
        });
        if (state != 1) {
            mMaterialDialog.setNegativeButton("취소", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMaterialDialog.dismiss();
                }
            });
        }
        mMaterialDialog.show();


    }

}
