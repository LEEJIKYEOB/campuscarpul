package com.larvafly.adapter;


import com.larvafly.campuscarpul.Meching_Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("DefaultLocale")
public class MainViewPagerAdapter extends FragmentPagerAdapter{

	
	private Activity activity;
	
	private Meching_Fragment daeyeon;
	private Meching_Fragment gyeongseong;
	private Meching_Fragment dongmyeong;
	
	
	public MainViewPagerAdapter(Activity activity,FragmentManager mFragmentManager){
		super(mFragmentManager);
		this.activity = activity;
		
		daeyeon = new Meching_Fragment(activity);
		gyeongseong = new Meching_Fragment(activity);
		dongmyeong = new Meching_Fragment(activity);
		
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public Fragment getItem(int index) {
		// TODO Auto-generated method stub
		
		if(index == 0){
			return daeyeon;
		}else if(index == 1){
			return gyeongseong;
		}else if(index == 2){
			return dongmyeong;
		}
		
		
		return null;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
//		super.destroyItem(container, position, object);
	}

    @Override
    public CharSequence getPageTitle(int position) {

        if(position == 0){
            return "대연";
        }else if(position == 1){
            return "경성";
        }else if(position == 2){
            return "학교";
        }

        return super.getPageTitle(position);
    }
}
