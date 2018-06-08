package com.fitibo.eyouapp.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.fitibo.eyouapp.R;
import com.fitibo.eyouapp.base.EyouBaseActivity;
import com.fitibo.eyouapp.main.fragments.MessageBoardFragment;
import com.fitibo.eyouapp.main.fragments.OrdersFragment;
import com.fitibo.eyouapp.main.fragments.SkusFragment;

import java.util.ArrayList;

public class MainActivity extends EyouBaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    private static final int FRAGMENT_SIZE = 3;

    private static final int FRAGMENT_POSITION_INDEX = 0;
    private static final int FRAGMENT_POSITION_ORDER = 1;
    private static final int FRAGMENT_POSITION_SKU = 2;


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ArrayList<Fragment> fragments;
    private TextView tv_title, tv_nick_name;
    private Toolbar tb_title_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tb_title_bar = (Toolbar) findViewById(R.id.tb_title_bar);
//       civ_title_bar_head_portrait = (CircleImageView) tb_title_bar.findViewById(R.id.title_bar_head_portrait);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //todo
            }
        });

//        civ_title_bar_head_portrait.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });

        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        bottomNavigationBar.setBackgroundResource(R.color.black);
        //bottomNavigationBar.setBarBackgroundColor(R.color.black);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.home, "首页").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.colorPrimaryDark))
                .addItem(new BottomNavigationItem(R.drawable.form, "订单").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.colorPrimaryDark))
                .addItem(new BottomNavigationItem(R.drawable.sku, "库存").setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.colorPrimaryDark))
                .setFirstSelectedPosition(0)
                .initialise();
        fragments = getFragments();
        bottomNavigationBar.setTabSelectedListener(this);
        onTabSelected(0);
    }

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(MessageBoardFragment.newInstance());
        fragments.add(OrdersFragment.newInstance());
        fragments.add(SkusFragment.newInstance());
        return fragments;
    }

    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = null;
                switch (position) {
                    case FRAGMENT_POSITION_SKU:
                        fragment = SkusFragment.newInstance();
                        break;
                    case FRAGMENT_POSITION_ORDER:
                        fragment = OrdersFragment.newInstance();
                        break;
                    case FRAGMENT_POSITION_INDEX:
                        fragment = MessageBoardFragment.newInstance();
                        break;
                    default:
                        break;
                }
                if (fragment != null) {
                    refreshFragment(position, fragment);
                }
//                fragments.remove(position);
//                fragments.add(position, fragment);
//                if (fragment.isAdded()) {
//                    ft.replace(R.id.layFrame, fragment);
//                } else {
//                    ft.add(R.id.layFrame, fragment);
//                }
//                ft.commitAllowingStateLoss();
            }
        }
    }

    private <T extends Fragment> void refreshFragment(int position, T fragment) {
        if (position < 0 || position >= FRAGMENT_SIZE) return;

        if (fragments.size() == FRAGMENT_SIZE) {
            fragments.remove(position);
        }

        fragments.add(position, fragment);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (fragment.isAdded()) {
            ft.replace(R.id.layFrame, fragment);
        } else {
            ft.add(R.id.layFrame, fragment);
        }
        ft.commitAllowingStateLoss();
    }
}
