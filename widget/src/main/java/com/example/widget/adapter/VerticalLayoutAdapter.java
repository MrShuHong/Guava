package com.example.widget.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.widget.R;
import com.example.widget.bean.VerticalLayoutBean;

import java.util.List;

/**
 * Created by admin on 2018/8/31.
 */

public class VerticalLayoutAdapter  extends BaseQuickAdapter<VerticalLayoutBean,BaseViewHolder>{
    public VerticalLayoutAdapter(int layoutResId, @Nullable List<VerticalLayoutBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VerticalLayoutBean item) {

        Log.d("dsh",item.getContent());
        helper.setText(R.id.txt_content,item.getContent());
    }
}
