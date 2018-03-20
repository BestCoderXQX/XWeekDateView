package acffo.xqx.weekdateview;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

/**
 * @author xqx
 * @email djlxqx@163.com
 * blog:http://www.cnblogs.com/xqxacm/
 * createAt $date$
 * description:
 */
public class WeekDateAdapter extends BaseQuickAdapter<ArrayList<ArrayList<String>>, BaseViewHolder> {

    private int weekPosition ;
    private int dayPosition;  // 该周的第几个位置 从1-7
    private int selectImg ; // 选中的效果
    private int unSelectImg ; // 未选中的效果

    public WeekDateAdapter(ArrayList<ArrayList<ArrayList<String>>> datas) {
        super(R.layout.item_week_calendar, datas);
    }

    public String getSelectDay(){
        return getData().get(weekPosition).get(1).get(dayPosition-1);
    }


    /**
     * 选择的日期
     * @param weekPosition   哪一周
     * @param dayPosition    该周的哪一个位置 从1开始
     */
    public void setSelectDay(int weekPosition , int dayPosition){
        this.weekPosition = weekPosition;
        this.dayPosition = dayPosition;
        notifyDataSetChanged();
    }

    /**
     * 得到选中的周的位置
     * @return
     */
    public int getWeekPosition() {
        return weekPosition;
    }

    /**
     * 设置选中日期的背景效果
     * @param selectImg
     */
    public void setSelectImg(int selectImg) {
        this.selectImg = selectImg;
    }

    /**
     * 设置未选中日期的背景效果
     * @param unSelectImg
     */
    public void setUnSelectImg(int unSelectImg) {
        this.unSelectImg = unSelectImg;
    }


    @Override
    protected void convert(BaseViewHolder helper, ArrayList<ArrayList<String>> item) {
        helper.setText(R.id.txtWeek1 , item.get(0).get(0))
                .setText(R.id.txtWeek2 , item.get(0).get(1))
                .setText(R.id.txtWeek3 , item.get(0).get(2))
                .setText(R.id.txtWeek4 , item.get(0).get(3))
                .setText(R.id.txtWeek5 , item.get(0).get(4))
                .setText(R.id.txtWeek6 , item.get(0).get(5))
                .setText(R.id.txtWeek7 , item.get(0).get(6))
                .setText(R.id.txtDay1 , item.get(1).get(0).split("-")[2])
                .setText(R.id.txtDay2 , item.get(1).get(1).split("-")[2])
                .setText(R.id.txtDay3 , item.get(1).get(2).split("-")[2])
                .setText(R.id.txtDay4 , item.get(1).get(3).split("-")[2])
                .setText(R.id.txtDay5 , item.get(1).get(4).split("-")[2])
                .setText(R.id.txtDay6 , item.get(1).get(5).split("-")[2])
                .setText(R.id.txtDay7 , item.get(1).get(6).split("-")[2])
        ;

        helper.setImageResource(R.id.img1 , unSelectImg);
        helper.setImageResource(R.id.img2 , unSelectImg);
        helper.setImageResource(R.id.img3 , unSelectImg);
        helper.setImageResource(R.id.img4 , unSelectImg);
        helper.setImageResource(R.id.img5 , unSelectImg);
        helper.setImageResource(R.id.img6 , unSelectImg);
        helper.setImageResource(R.id.img7 , unSelectImg);
        helper.setTextColor(R.id.txtDay1 , mContext.getResources().getColor(R.color.standard_black));
        helper.setTextColor(R.id.txtDay2 , mContext.getResources().getColor(R.color.standard_black));
        helper.setTextColor(R.id.txtDay3 , mContext.getResources().getColor(R.color.standard_black));
        helper.setTextColor(R.id.txtDay4 , mContext.getResources().getColor(R.color.standard_black));
        helper.setTextColor(R.id.txtDay5 , mContext.getResources().getColor(R.color.standard_black));
        helper.setTextColor(R.id.txtDay6 , mContext.getResources().getColor(R.color.standard_black));
        helper.setTextColor(R.id.txtDay7 , mContext.getResources().getColor(R.color.standard_black));


        if (weekPosition == helper.getAdapterPosition()){
            // 如果是该周
            if (dayPosition == 1){
                helper.setImageResource(R.id.img1 , selectImg);
                helper.setTextColor(R.id.txtDay1 , mContext.getResources().getColor(R.color.white));
            }else if (dayPosition == 2){
                helper.setImageResource(R.id.img2 , selectImg);
                helper.setTextColor(R.id.txtDay2 , mContext.getResources().getColor(R.color.white));
            }else if (dayPosition == 3){
                helper.setImageResource(R.id.img3 , selectImg);
                helper.setTextColor(R.id.txtDay3 , mContext.getResources().getColor(R.color.white));
            }else if (dayPosition == 4){
                helper.setImageResource(R.id.img4 , selectImg);
                helper.setTextColor(R.id.txtDay4 , mContext.getResources().getColor(R.color.white));
            }else if (dayPosition == 5){
                helper.setImageResource(R.id.img5 , selectImg);
                helper.setTextColor(R.id.txtDay5 , mContext.getResources().getColor(R.color.white));
            }else if (dayPosition == 6){
                helper.setImageResource(R.id.img6 , selectImg);
                helper.setTextColor(R.id.txtDay6 , mContext.getResources().getColor(R.color.white));
            }else if (dayPosition == 7){
                helper.setImageResource(R.id.img7 , selectImg);
                helper.setTextColor(R.id.txtDay7 , mContext.getResources().getColor(R.color.white));
            }
        }

        helper.addOnClickListener(R.id.txtWeek1 )
                .addOnClickListener(R.id.txtWeek2 )
                .addOnClickListener(R.id.txtWeek3 )
                .addOnClickListener(R.id.txtWeek4 )
                .addOnClickListener(R.id.txtWeek5 )
                .addOnClickListener(R.id.txtWeek6 )
                .addOnClickListener(R.id.txtWeek7 )
                .addOnClickListener(R.id.txtDay1 )
                .addOnClickListener(R.id.txtDay2 )
                .addOnClickListener(R.id.txtDay3 )
                .addOnClickListener(R.id.txtDay4 )
                .addOnClickListener(R.id.txtDay5 )
                .addOnClickListener(R.id.txtDay6 )
                .addOnClickListener(R.id.txtDay7 )
        ;
    }
}
