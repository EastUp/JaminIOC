package com.east.jaminiocapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.east.jaminioc.ioc.JaminIOC;
import com.east.jaminioc.ioc.annotation.BindClick;
import com.east.jaminioc.ioc.annotation.BindView;
import com.east.jaminioc.ioc.annotation.CheckNet;
import com.east.jaminioc.ioc.annotation.ThrottleClick;


/**
 * |---------------------------------------------------------------------------------------------------------------|
 *
 *  @description: 测试IOC框架
 *  @author: jamin
 *  @date: 2020/4/28
 * |---------------------------------------------------------------------------------------------------------------|
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.test_tv)
    private TextView mTv;

    @BindView(R.id.test_iv)
    private ImageView mIv;

    int index = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //开始进行解析
        JaminIOC.INSTANCE.bind(this);
        mTv.setText("这是Ioc获取到View后赋予的值");
    }


    @ThrottleClick(5000L)
    @CheckNet/*(errorMsg = "好像没网诶")*/
    @BindClick({R.id.test_tv, R.id.test_iv})
    private void testClick(View v) {
//        Toast.makeText(MainActivity.this, "点击完成了", Toast.LENGTH_SHORT).show();
        int id = v.getId();
        if (id == R.id.test_tv) {
            Toast.makeText(MainActivity.this, "点击了文本框", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.test_iv) {
            Toast.makeText(MainActivity.this, "点击了图片", Toast.LENGTH_SHORT).show();
        }
    }
}
