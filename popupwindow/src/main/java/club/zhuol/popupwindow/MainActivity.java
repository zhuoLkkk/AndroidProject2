package club.zhuol.popupwindow;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * 必须使用view调用findViewById()方法来绑定PopupWindow里面的组件 否则会报错
 * View view = LayoutInflater.from(MainActivity.this).
 * inflate(R.layout.layout_popup_window, null);
 * button1 = view.findViewById(R.id.button1);
 * button2 = view.findViewById(R.id.button2);
 */
public class MainActivity extends AppCompatActivity {
    Button button1, button2;
    ImageView iv_test;


//    public void showAsDropDown(final PopupWindow pw, final View anchor, final int xoff, final int yoff) {
//        if (Build.VERSION.SDK_INT >= 24) {
//            Rect visibleFrame = new Rect();
//            anchor.getGlobalVisibleRect(visibleFrame);
//            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
//            pw.setHeight(height);
//            pw.showAsDropDown(anchor, xoff, yoff);
//        } else {
//            pw.showAsDropDown(anchor, xoff, yoff);
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_test = findViewById(R.id.iv_test);

        iv_test.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                initPopupWindow(v);
                return false;
            }
        });

    }

    private void initPopupWindow(View v) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_popup_window, null);
            button1 = view.findViewById(R.id.button1);
            button2 = view.findViewById(R.id.button2);
            //构造一个PopupWindow 参数依次是加载的View 宽高
            final PopupWindow popupWindow = new PopupWindow(view,
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            //设置加载动画
            //popupWindow.setAnimationStyle(R.anim.anim);
            /**
             * 以下代码解决问题为:
             * 点击非PopupWindow区域 PopupWindow会消失
             * 如果无以下代码 当PopupWindow显示出来时 无论按多少次回退键
             * PopupWindow并不会关闭 且退不出程序
             */
            popupWindow.setTouchable(true);
            popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                @Override
                /**
                 * 如果返回true touch事件将被拦截
                 * 拦截后 PopupWindow的onTouchEvent不被调用
                 * 这样点击外部区域无法dismiss
                 */
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
            //要为PopupWindow设置一个背景才有效
            popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
            //设置PopupWindow显示的位置 参数依次是参照View x轴的偏移量 y轴的偏移量
            popupWindow.showAsDropDown(v, 50, 0);
            //设置PopupWindow里的按钮事件
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "点击了格式",
                            Toast.LENGTH_SHORT).show();
                    popupWindow.dismiss();
                }
            });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "点击了属性",
                        Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
    }
}
