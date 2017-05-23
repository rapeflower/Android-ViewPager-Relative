package com.j1.healthcare.patient.view.common.slidingstackview;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author rape flower
 */
public class StackPagerAdapter extends PagerAdapter {

    private List<View> data;

    public StackPagerAdapter(List<View> views) {
        this.data = views;
    }

    @Override
    public int getCount() {
        //设置成最大，使用户看不到边界
        return this.data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(data.get(position), 0);
        return data.get(position);

//        //对ViewPager页号求模取出View列表中要显示的项
//        position %= data.size();
//        if (position<0){
//            position = data.size()+position;
//        }
//        View view = data.get(position);
//        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
//        ViewParent vp =view.getParent();
//        if (vp!=null){
//            ViewGroup parent = (ViewGroup)vp;
//            parent.removeView(view);
//        }
//        container.addView(view);
//        //add listeners here if necessary
//        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(data.get(position));
    }

}
