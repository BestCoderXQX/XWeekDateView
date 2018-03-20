package acffo.xqx.weekdateview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author xqx
 * @email djlxqx@163.com
 * blog:http://www.cnblogs.com/xqxacm/
 * createAt $date$
 * description: 周日历  横向滑动控件
 */
public class WeekDateView extends LinearLayout{

    private static final int RIGHT = 1;  // 标记：日历滑动方向
    private static final int LEFT = 2;   // 标记：日历滑动方向

    private RecyclerView weekListView ;   // 日历，用列表显示
    private WeekDateAdapter weekDateAdapter;  // 列表适配器
    private ArrayList<ArrayList<ArrayList<String>>> weekCalendarDatas;  // 数据源
    private String selectedDate ; //  选中的日期  yyyy-MM-dd
    private ArrayList<String> weekCalendar = new ArrayList<String>(); // 星期
    private GestureDetector gestureDetector;  // 手势判断
    private int ADD_TYPE = 0;  // 0 默认第一次 ，1 ：往前滑数据不够，新添加10个  2：往后滑数据不够，新添加10个

    private int selectImg = R.mipmap.icon_week_selecte ; // 日期选中的背景效果
    private int unSelectImg = 0; // 日期未选中的背景效果

    private WeekInterface weekInterface;  // 接口回调，获取当前选择的日期

    public WeekDateView(Context context) {
        this(context,null);
    }

    public WeekDateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WeekDateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context) {
        weekCalendarDatas = new ArrayList<>();
        gestureDetector = new GestureDetector(context,onGestureListener);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.week_view,null,false);

        initView(view , context);
        addView(view);
    }
    public void setChange(WeekInterface weekInterface){
        this.weekInterface = weekInterface ;
        weekInterface.getSelectDate(selectedDate);
    }
    /**
     * 初始化周日历，预加载10个周的数据，参数为最后一个周的最后一天，为选中状态
     * 如果参数为空字符串，则取当前时间为最后一天
     * @param date
     */
    public void initSelectDate(String date){
        Calendar calendar = Calendar.getInstance();
        if (date.equals("")){

        }else {
            calendar.set(Calendar.YEAR, Integer.parseInt(date.split("-")[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(date.split("-")[1]) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.split("-")[2]));
        }
        selectedDate = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
        int weekOfDay = calendar.get(Calendar.DAY_OF_WEEK);// 当前是在一周的第几天
        if (weekOfDay == 2) {
            //周一
            weekCalendar.add("二");
            weekCalendar.add("三");
            weekCalendar.add("四");
            weekCalendar.add("五");
            weekCalendar.add("六");
            weekCalendar.add("日");
            weekCalendar.add("一");
        } else if (weekOfDay == 3) {
            weekCalendar.add("三");
            weekCalendar.add("四");
            weekCalendar.add("五");
            weekCalendar.add("六");
            weekCalendar.add("日");
            weekCalendar.add("一");
            weekCalendar.add("二");
        } else if (weekOfDay == 4) {
            weekCalendar.add("四");
            weekCalendar.add("五");
            weekCalendar.add("六");
            weekCalendar.add("日");
            weekCalendar.add("一");
            weekCalendar.add("二");
            weekCalendar.add("三");
        } else if (weekOfDay == 5) {
            weekCalendar.add("五");
            weekCalendar.add("六");
            weekCalendar.add("日");
            weekCalendar.add("一");
            weekCalendar.add("二");
            weekCalendar.add("三");
            weekCalendar.add("四");
        } else if (weekOfDay == 6) {
            weekCalendar.add("六");
            weekCalendar.add("日");
            weekCalendar.add("一");
            weekCalendar.add("二");
            weekCalendar.add("三");
            weekCalendar.add("四");
            weekCalendar.add("五");
        } else if (weekOfDay == 0) {
            weekCalendar.add("日");
            weekCalendar.add("一");
            weekCalendar.add("二");
            weekCalendar.add("三");
            weekCalendar.add("四");
            weekCalendar.add("五");
            weekCalendar.add("六");
        } else if (weekOfDay == 1) {
            weekCalendar.add("一");
            weekCalendar.add("二");
            weekCalendar.add("三");
            weekCalendar.add("四");
            weekCalendar.add("五");
            weekCalendar.add("六");
            weekCalendar.add("日");
        }

        // 当天先减六天
        calendar.add(Calendar.DATE, -6);

        /*可以往前滑动 10个星期*/
        for (int j = 0; j < 10; j++) {
            ArrayList<String> dayOfMonth = new ArrayList<String>();
            for (int i = 0; i < 7; i++) {
                dayOfMonth.add(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
                calendar.add(Calendar.DATE, 1);// 加一天
            }
            calendar.add(Calendar.DATE, -14);

            ArrayList<ArrayList<String>> weekData = new ArrayList<ArrayList<String>>();
            weekData.add(weekCalendar);
            weekData.add(dayOfMonth);
            weekCalendarDatas.add(0, weekData);
        }
        if (ADD_TYPE!=1) {
            weekDateAdapter.notifyDataSetChanged();
            weekListView.scrollToPosition(weekCalendarDatas.size() - 1);
            weekDateAdapter.setSelectDay(weekCalendarDatas.size() - 1, 7);  //默认当前已选中
            selectedDate = weekCalendarDatas.get(weekCalendarDatas.size() - 1).get(1).get(6); // 设置当前选择的日期
        }
    }


    private void initView(View view ,Context context) {
        weekListView = (RecyclerView) view.findViewById(R.id.weekListView);

        weekListView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        weekDateAdapter = new WeekDateAdapter(weekCalendarDatas);
        weekDateAdapter.setSelectImg(selectImg);
        weekDateAdapter.setUnSelectImg(unSelectImg);

        weekDateAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View itemView, int position) {

                if (itemView.getId() == R.id.txtWeek1 || itemView.getId() == R.id.txtDay1) {
                    weekDateAdapter.setSelectDay(position, 1);
                    selectedDate = weekCalendarDatas.get(position).get(1).get(0); // 设置当前选择的日期
                } else if (itemView.getId() == R.id.txtWeek2 || itemView.getId() == R.id.txtDay2) {
                    weekDateAdapter.setSelectDay(position, 2);
                    selectedDate = weekCalendarDatas.get(position).get(1).get(1); // 设置当前选择的日期
                } else if (itemView.getId() == R.id.txtWeek3 || itemView.getId() == R.id.txtDay3) {
                    weekDateAdapter.setSelectDay(position, 3);
                    selectedDate = weekCalendarDatas.get(position).get(1).get(2); // 设置当前选择的日期
                } else if (itemView.getId() == R.id.txtWeek4 || itemView.getId() == R.id.txtDay4) {
                    weekDateAdapter.setSelectDay(position, 4);
                    selectedDate = weekCalendarDatas.get(position).get(1).get(3); // 设置当前选择的日期
                } else if (itemView.getId() == R.id.txtWeek5 || itemView.getId() == R.id.txtDay5) {
                    weekDateAdapter.setSelectDay(position, 5);
                    selectedDate = weekCalendarDatas.get(position).get(1).get(4); // 设置当前选择的日期
                } else if (itemView.getId() == R.id.txtWeek6 || itemView.getId() == R.id.txtDay6) {
                    weekDateAdapter.setSelectDay(position, 6);
                    selectedDate = weekCalendarDatas.get(position).get(1).get(5); // 设置当前选择的日期
                } else if (itemView.getId() == R.id.txtWeek7 || itemView.getId() == R.id.txtDay7) {
                    weekDateAdapter.setSelectDay(position, 7);
                    selectedDate = weekCalendarDatas.get(position).get(1).get(6); // 设置当前选择的日期
                } else {

                }
                weekInterface.getSelectDate(selectedDate);
                weekListView.scrollToPosition(position);
            }
        });
        weekListView.setAdapter(weekDateAdapter);

//        ...设置手势给recyclerview
        weekListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }
    /**
     * 手势监听是否是左右滑动，这里认为滑动距离超过100就算左右滑动
     */
    private GestureDetector.OnGestureListener onGestureListener =
            new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                       float velocityY) {
                    if (e1==null ||e2==null){
                        weekListView.scrollToPosition(weekDateAdapter.getWeekPosition());
                        return true;
                    }
                    float x = e2.getX() - e1.getX();
                    if (x > 100) {
                        doResult(RIGHT);
                    } else if (x < -100) {
                        doResult(LEFT);
                    }else{
                        weekListView.scrollToPosition(weekDateAdapter.getWeekPosition());
                    }

                    return true;
                }
            };
    public void doResult(int action) {

        switch (action) {
            case RIGHT:
                if (weekDateAdapter.getWeekPosition()==0){
                    initSelectDate(DateUtil.getNextDayMD(weekDateAdapter.getData().get(0).get(1).get(0),"-1"));
                    weekDateAdapter.setSelectDay(9,7);
                }else {
                    weekDateAdapter.setSelectDay(weekDateAdapter.getWeekPosition() - 1, 7);
                }
                weekListView.scrollToPosition(weekDateAdapter.getWeekPosition());
                selectedDate = weekDateAdapter.getSelectDay();
                weekInterface.getSelectDate(selectedDate);
                break;
            case LEFT:
                if (weekDateAdapter.getWeekPosition()<weekDateAdapter.getData().size()-1) {
                    weekDateAdapter.setSelectDay(weekDateAdapter.getWeekPosition() + 1, 7);
                }
                weekListView.scrollToPosition(weekDateAdapter.getWeekPosition());
                selectedDate = weekDateAdapter.getSelectDay();
                weekInterface.getSelectDate(selectedDate);
                break;
        }
    }

    /**
     * 设置未选中日期的背景效果
     * @param unSelectImg
     */
    public void setUnSelectImg(int unSelectImg) {
        this.unSelectImg = unSelectImg;
    }

    /**
     * 设置选中日期的背景效果
     * @param selectImg
     */
    public void setSelectImg(int selectImg) {
        this.selectImg = selectImg;
    }
}
