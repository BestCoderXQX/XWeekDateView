package acffo.xqx.xweekdateview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import acffo.xqx.weekdateview.WeekDateView;
import acffo.xqx.weekdateview.WeekInterface;

public class MainActivity extends AppCompatActivity implements WeekInterface {
    private WeekDateView weekdateView;
    private TextView txtSeletDate; // 显示选中的日期

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weekdateView = (WeekDateView) findViewById(R.id.weekdateView);
        txtSeletDate = (TextView)findViewById(R.id.txtSeletDate);
        weekdateView.initSelectDate("");
        weekdateView.setChange(this);  // 监听  获取选中的日期
    }

    @Override
    public void getSelectDate(String content) {
        txtSeletDate.setText(content);
    }
}
