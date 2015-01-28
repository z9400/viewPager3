package com.z9400.viewpager3;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.z9400.viewpager3.adapter.PagerAdapter;
import com.z9400.viewpager3.fragment.FragmentPage1;
import com.z9400.viewpager3.fragment.FragmentPage2;
import com.z9400.viewpager3.fragment.FragmentPage3;
import com.z9400.viewpager3.fragment.FragmentPage4;
import com.z9400.viewpager3.fragment.FragmentPage5;
import com.z9400.viewpager3.utils.BaseTools;
import com.z9400.viewpager3.widget.ColumnHorizontalScrollView;


public class MainActivity extends FragmentActivity {

    /** ��Ļ��� */
    private int mScreenWidth = 0;
    /** Item��� */
    private int mItemWidth = 0;
    /** head ͷ�� �����˵� ��ť */
    private ArrayList<String> userChannelList;
    /** ��ǰѡ�е���Ŀ */
    private int columnSelectIndex = 0;
    private ArrayList<Fragment> fragments;
    private Fragment newfragment;
    private PagerAdapter mAdapetr;
    
    private double back_pressed ;
    
    protected ViewPager mViewPager;
    protected LinearLayout mRadioGroup_content;
    protected ColumnHorizontalScrollView mColumnHorizontalScrollView;
    protected RelativeLayout rl_column;
    protected ImageView shade_left;
    protected ImageView shade_right;

    /** ��ǰѡ�е���Ŀ */

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		shade_left = (ImageView)findViewById(R.id.shade_left) ;
		shade_right = (ImageView)findViewById(R.id.shade_right) ;
		mViewPager = (ViewPager)findViewById(R.id.mViewPager) ;
		rl_column = (RelativeLayout)findViewById(R.id.rl_column) ;
		mRadioGroup_content = (LinearLayout)findViewById(R.id.mRadioGroup_content) ;
		mColumnHorizontalScrollView = (ColumnHorizontalScrollView)findViewById(R.id.mColumnHorizontalScrollView) ;
		
		
		
        mScreenWidth = BaseTools.getWindowsWidth(this);
        mItemWidth = mScreenWidth / 7;// һ��Item���Ϊ��Ļ��1/7
        userChannelList = new ArrayList<String>();
        fragments = new ArrayList<Fragment>();
        
        initData() ;
        
	    initViewPager();
	    initTabColumn();
	}

    private void initData(){
    	 userChannelList.add("��ҳ") ;
         userChannelList.add("����") ;
         userChannelList.add("����") ;
         userChannelList.add("�Ƽ�") ;
         userChannelList.add("����") ;
         userChannelList.add("��ҳ") ;
         userChannelList.add("����") ;
         userChannelList.add("����") ;
         userChannelList.add("�Ƽ�") ;
         userChannelList.add("����") ;
         
         fragments.add(new FragmentPage1()) ;
         fragments.add(new FragmentPage2()) ;
         fragments.add(new FragmentPage3()) ;
         fragments.add(new FragmentPage4()) ;
         fragments.add(new FragmentPage5()) ;
         fragments.add(new FragmentPage1()) ;
         fragments.add(new FragmentPage2()) ;
         fragments.add(new FragmentPage3()) ;
         fragments.add(new FragmentPage4()) ;
         fragments.add(new FragmentPage5()) ;
    }


    private void initViewPager() {
        mAdapetr = new PagerAdapter(
                getSupportFragmentManager(),fragments);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(mAdapetr);
        mViewPager.setOnPageChangeListener(pageListener);
    }






    /**
     * ��ʼ��Column��Ŀ��
     */
    private void initTabColumn() {
//        mRadioGroup_content.removeAllViews();
    	
        mColumnHorizontalScrollView.setParam(this, mScreenWidth, mRadioGroup_content, shade_left,
                shade_right, rl_column);
        
        
        for (int i = 0,count = userChannelList.size(); i < count; i++) {
        	
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mItemWidth,
                    LayoutParams.WRAP_CONTENT);
            
            params.leftMargin = 5;
            params.rightMargin = 5;
            
            TextView columnTextView = new TextView(this);
            columnTextView.setTextAppearance(this, R.style.top_category_scroll_view_item_text);
            columnTextView.setBackgroundResource(R.drawable.radio_buttong_bg);
            columnTextView.setGravity(Gravity.CENTER);
            columnTextView.setPadding(5, 5, 5, 5);
            columnTextView.setId(i);
            columnTextView.setText(userChannelList.get(i));
            columnTextView.setTextColor(getResources().getColorStateList(R.color.top_category_scroll_text_color_day));
            if (columnSelectIndex == i) {
                columnTextView.setSelected(true);
            }
            columnTextView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
                        View localView = mRadioGroup_content.getChildAt(i);
                        if (localView != v)
                            localView.setSelected(false);
                        else {
                            localView.setSelected(true);
                            mViewPager.setCurrentItem(i);
                        }
                    }
                }
            });
            mRadioGroup_content.addView(columnTextView, i, params);
        }
    }



    /**
     * ViewPager�л���������
     */
    public OnPageChangeListener pageListener = new OnPageChangeListener() {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            mViewPager.setCurrentItem(position);
            selectTab(position);
        }
    };

    /**
     * ѡ���Column�����Tab
     */
    private void selectTab(int tab_postion) {
        columnSelectIndex = tab_postion;
        View checkView = mRadioGroup_content.getChildAt(tab_postion);
        int k = checkView.getMeasuredWidth();
        int l = checkView.getLeft();
        int i2 = l + k / 2 - mScreenWidth / 2;
        mColumnHorizontalScrollView.smoothScrollTo(i2, 0);
        // �ж��Ƿ�ѡ��
        for (int j = 0; j < mRadioGroup_content.getChildCount(); j++) {
            boolean ischeck;
            if (j == tab_postion) {
                ischeck = true;
            } else {
                ischeck = false;
            }
            mRadioGroup_content.getChildAt(j).setSelected(ischeck);
        }
    }
    
    
    /**
     * ������η����˳�ϵͳ
     * 
     * @param view
     */
    @Override
    public void onBackPressed() {
            if (back_pressed + 3000 > System.currentTimeMillis()) {
                finish();
                super.onBackPressed();
            }
            else{
            	Toast.makeText(this, "�ٰ�����˳���",Toast.LENGTH_SHORT).show();
            }

            back_pressed = System.currentTimeMillis();
    }



}
