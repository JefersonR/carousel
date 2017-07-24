package com.example.jeferson.carouseltest.widgets;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.jeferson.carouseltest.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jeferson on 30/03/2017.
 */

public class PineCarouselPanel extends RelativeLayout {

    //Ui Elements
    @BindView(R.id.pager_title)
    ViewPager mViewPager;

    @BindView(R.id.pager_indicator_up)
    ImageView pagerIndicatorUp;

    @BindView(R.id.pager_indicator_down)
    ImageView pagerIndicatorDown;

    private List<CarouselItemFragment> listFragments;
    private PagerAdapter pageAdapter;
    private List<String> types;

    private int currentPosition = 0;
    private String currentTitle;
    private  Context context;
    private FragmentManager fragmentManager;
    private onPageSelected onPageSelected;
    private colorTitle color;
    private boolean exists = false;
    private final int viewPagerID = 123;

    public PineCarouselPanel(Context context) {
        super(context);
        this.context = context;
    }
    public PineCarouselPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }
    public PineCarouselPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }
    private void initView(Context context, FragmentManager fragmentManager, List<String> titles, colorTitle  color, int id) {
        View view = LayoutInflater.from(context).inflate(R.layout.carousel_panel, this);
        ButterKnife.bind(this, view);
        this.fragmentManager = fragmentManager;
        listFragments = new ArrayList<>();
        this.color = color;
        exists = true;

        initListners();
        createTypes(titles, id);

    }
    public void setData( FragmentManager fragmentManager, List<String> titles){
        setData(fragmentManager,titles, colorTitle.brown, viewPagerID);
    }
    public void setData( FragmentManager fragmentManager, List<String> titles, colorTitle color){
        setData(fragmentManager,titles, color, viewPagerID);
    }
    public void setData( FragmentManager fragmentManager, List<String> titles, colorTitle color, int pagerID){
        initView(context, fragmentManager, titles, color, pagerID);
    }
    public void setOnPageSelected(onPageSelected onPageSelected) {
        this.onPageSelected = onPageSelected;
    }

    public boolean isExists() {
        return exists;
    }

    public void setIndicatorColor(colorIndicator color){
        try {

            switch (color){
                case gray:
                    pagerIndicatorUp.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.triangle_shape_gray));
                    pagerIndicatorDown.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.triangle_shape_gray));
                    break;
                case brown:
                    pagerIndicatorUp.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.triangle_shape));
                    pagerIndicatorDown.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.triangle_shape));
                    break;
                case purple:
                    pagerIndicatorUp.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.triangle_shape_purple));
                    pagerIndicatorDown.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.triangle_shape_purple));
                    break;
            }

        }catch(Exception e){
            e.getMessage();
        }
    }
    private void initListners() {

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                try {
                    currentPosition = position;
                    currentTitle = types.get(position);

                    if(onPageSelected != null){
                        onPageSelected.onPageTitleSelected(currentTitle,currentPosition);
                    }
                    listFragments.get(position).updateTitleNew(color);
                    for(int i = 0; i < listFragments.size(); i++){
                        if(i != position){
                            listFragments.get(i).updateTitleOld();
                        }
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.e("PAGEVIEW", "CHANGED" + state);

            }
        });

    }


    public enum colorIndicator {
        gray,
        purple,
        brown;
    }

    private void createTypes(List<String> titles, int id) {
        try {
            types = titles;
            pageAdapter = new PagerAdapter(fragmentManager, types);
            mViewPager.setAdapter(pageAdapter);
            mViewPager.setId(id);
            int padding = (int) (getResources().getDimension(R.dimen.padding_for_carousel) / getResources().getDisplayMetrics().density);
            mViewPager.setPadding(padding, 0, padding, 0);
            mViewPager.setClipToPadding(false);
            mViewPager.setPageMargin(1);

        } catch (Exception e) {
            e.getMessage();
        }

    }

    public PagerAdapter getPageAdapter() {
        return pageAdapter;
    }

    /**
     * Defining a FragmentPagerAdapter class for controlling the fragments to be shown when user swipes on the screen.
     */
    public class PagerAdapter extends FragmentPagerAdapter {
        final private List<String> data;
        private final String TITLE = "TITLE";
        private final String POS = "POS";
        private final String COLOR = "COLOR";

        public PagerAdapter(FragmentManager fm, List<String> types) {
            super(fm);
            this.data = types;
        }

        @Override
        public Fragment getItem(final int position) {
            /** Show a Fragment based on the position of the current screen */
            try {

                for (int i = 0; i < data.size(); i++) {
                    if (position == i) {
                        CarouselItemFragment filter = new CarouselItemFragment();
                        final Bundle bundle = new Bundle();
                        bundle.putString(TITLE, data.get(i));
                        bundle.putInt(POS, i);
                        switch (color){
                            case gray:
                                bundle.putInt(COLOR, 0);
                                break;
                            case brown:
                                bundle.putInt(COLOR, 1);
                                break;
                            case purple:
                                bundle.putInt(COLOR, 2);
                                break;
                        }
                        filter.setArguments(bundle);
                        listFragments.add(filter);
                        return filter;
                    }
                }
            } catch (Exception e) {
                e.getMessage();
                Log.e("ERROR", e.getMessage() +" ");
            }
            return null;

        }

        @Override
        public int getCount() {

            return data.size();
        }
    }

    public interface onPageSelected {
        public void onPageTitleSelected(String currentTitle, int position);
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public String getCurrentTitle() {
        return currentTitle;
    }

    public enum colorTitle{
        gray,
        purple,
        brown;
    }


}