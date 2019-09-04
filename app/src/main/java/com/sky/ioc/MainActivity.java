package com.sky.ioc;

import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


@ConentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.textView1)
    private TextView textView1;
    @ViewInject(R.id.textView2)
    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        textView1.setText("view1 Inject sucess");
        textView2.setText("view2 Inject sucess");
//        textView2.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                return false;
//            }
//        });
//        textView1 = findViewById(R.id.textView1);


    }

    @OnClick({R.id.textView1, R.id.textView2})
    public boolean viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.textView1:
                Toast.makeText(this, "view1 点击事件被消费", Toast.LENGTH_SHORT).show();
                break;
            case R.id.textView2:
                Toast.makeText(this, "view2 点击事件被消费", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    @OnLongClick({R.id.textView1, R.id.textView2})
    public boolean viewOnLongClick(View view) {
        switch (view.getId()) {
            case R.id.textView1:
                Toast.makeText(this, "view1 长按点击事件被消费", Toast.LENGTH_SHORT).show();
                break;
            case R.id.textView2:
                Toast.makeText(this, "view2 长按点击事件被消费", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

}
