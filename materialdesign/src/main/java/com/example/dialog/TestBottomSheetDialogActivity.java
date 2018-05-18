package com.example.dialog;

import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.dialog.bottomsheetdialog.BottomSheetDialogListView;
import com.example.dialog.bottomsheetdialog.SpringBackBottomSheetDialog;
import com.example.statusbar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/5/14.
 */
@Route(path = "/material_design/custom_bottom_sheet")
public class TestBottomSheetDialogActivity extends AppCompatActivity {

    private BottomSheetDialogListView mSheetDialogListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_dialog_layout);
        findViewById(R.id.btn_onclick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomSheet();
            }
        });
    }

    private void showCustomSheet(){
        SpringBackBottomSheetDialog c = new SpringBackBottomSheetDialog(this);
        View v = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_list_view,null,false);
        BottomSheetDialogListView l = v.findViewById(R.id.bottom_sheet_list);
        initListView(l);
        c.setContentView(v);

        l.bindBottomSheetDialog(v);
        c.addSpringBackDisLimit(-1);

        c.show();
    }

    private void initListView(final BottomSheetDialogListView l){
        final List<String> datas = new ArrayList<>();

        for(int i=0;i<40;i++){
            datas.add(String.valueOf(i));
        }

        l.setAdapter(
                new ListAdapter() {
                    @Override
                    public boolean areAllItemsEnabled() {
                        return false;
                    }

                    @Override
                    public boolean isEnabled(int position) {
                        return false;
                    }

                    @Override
                    public void registerDataSetObserver(DataSetObserver observer) {

                    }

                    @Override
                    public void unregisterDataSetObserver(DataSetObserver observer) {

                    }

                    @Override
                    public int getCount() {
                        return datas.size();
                    }

                    @Override
                    public Object getItem(int position) {
                        return datas.get(position);
                    }

                    @Override
                    public long getItemId(int position) {
                        return position;
                    }

                    @Override
                    public boolean hasStableIds() {
                        return false;
                    }

                    @Override
                    public View getView(final int position, View convertView,final ViewGroup parent) {
                        if(convertView == null){
                            convertView = new TextView(parent.getContext());
                            convertView.setLayoutParams(
                                    new AbsListView.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            40*3 // 40dp
                                    )
                            );
                        }
                        TextView t = (TextView) convertView;
                        t.setTextColor(Color.BLACK);
                        t.setGravity(Gravity.CENTER);
                        t.setText(datas.get(position));
                        t.setTextSize(17);
                        t.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(parent.getContext(),""+position,Toast.LENGTH_LONG).show();
                                    }
                                }
                        );
                        t.setOnTouchListener(
                                new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        if(event.getAction() == MotionEvent.ACTION_DOWN){
                                            l.setCoordinatorDisallow();
                                        }
                                        return false;
                                    }
                                }
                        );
                        return t;
                    }

                    @Override
                    public int getItemViewType(int position) {
                        return 0;
                    }

                    @Override
                    public int getViewTypeCount() {
                        return 1;
                    }

                    @Override
                    public boolean isEmpty() {
                        return false;
                    }
                }
        );
    }
}
