package com.east.jaminioc;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.east.baselibrary.ioc.JaminIOC;
import com.east.baselibrary.ioc.annotation.BindClick;
import com.east.baselibrary.ioc.annotation.BindView;
import com.east.baselibrary.ioc.annotation.CheckNet;
import com.east.baselibrary.ioc.annotation.ThrottleClick;


/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 * @description: 测试IOC框架
 * @author: jamin
 * @date: 2020/4/28
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class MainActivity extends AppCompatActivity {


    @BindView(R.id.test_tv)
    private TextView mTestTv;
    @BindView(R.id.test_iv)
    private ImageView mTestIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JaminIOC.INSTANCE.bind(this); //一定要加上
    }


    @CheckNet
    @ThrottleClick
    @BindClick({R.id.test_tv, R.id.test_iv})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.test_tv:

                break;
            case R.id.test_iv:

                break;
            default:
                break;
        }
    }
}
