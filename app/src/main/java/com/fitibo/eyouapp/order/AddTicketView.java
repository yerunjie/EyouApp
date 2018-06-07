package com.fitibo.eyouapp.order;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import com.fitibo.eyouapp.R;
import com.fitibo.eyouapp.base.EyouBaseActivity;
import com.fitibo.eyouapp.base.EyouBaseCallBack;
import com.fitibo.eyouapp.bean.*;
import com.fitibo.eyouapp.main.api.SkusApi;
import com.google.common.collect.Lists;
import com.lemon.support.util.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by yerunjie on 2018/6/7
 *
 * @author yerunjie
 */
public class AddTicketView implements DatePickerDialog.OnDateSetListener, View.OnClickListener {
    private EyouBaseActivity activity;
    private PopupWindow window;
    private Spinner sp_ticket_select;
    private Spinner sp_ticket_time_select;
    private Sku sku;
    private SkuTicket skuTicket;
    private SkuTicketPrice skuTicketPrice;
    private Date date;
    private Button btn_choose_date;
    private Button btn_add;
    private TextView tv_chosen_date;
    EditText et_name;
    EditText et_age;
    EditText et_weight;
    private int skuId;
    private int orderId = 0;

    public AddTicketView(EyouBaseActivity eyouBaseActivity, View view) {
        this.activity = eyouBaseActivity;
        View contentView = LayoutInflater.from(activity).inflate(R.layout.view_add_ticket, (ViewGroup) activity.getWindow().getDecorView(), false);
        window = new PopupWindow(contentView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(false);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        window.setFocusable(true);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
        window.setOnDismissListener((PopupWindow.OnDismissListener) activity);
        window.showAsDropDown(view);
        //window.setOnDismissListener();
    }



    public void init(int skuId) {
        this.skuId = skuId;
        View contentView = window.getContentView();
        sp_ticket_select = contentView.findViewById(R.id.sp_ticket_select);
        btn_choose_date = contentView.findViewById(R.id.btn_choose_date);
        tv_chosen_date = contentView.findViewById(R.id.tv_chosen_date);
        sp_ticket_time_select = contentView.findViewById(R.id.sp_ticket_time_select);
        et_name = contentView.findViewById(R.id.et_name);
        et_age = contentView.findViewById(R.id.et_age);
        et_age.setText("0");
        et_weight = contentView.findViewById(R.id.et_weight);
        et_weight.setText("0");
        btn_add = contentView.findViewById(R.id.btn_add);
        btn_add.setEnabled(false);
        btn_choose_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(activity, AddTicketView.this, year, month, day).show();
            }
        });
        activity.addRequest(activity.getService(SkusApi.class).querySkuById(skuId), new EyouBaseCallBack<Sku>() {
            @Override
            public void onSuccess200(Sku sku) {
                AddTicketView.this.sku = sku;
                final List<SkuTicket> skuTickets = sku.getTickets();
                List<String> strings = new ArrayList<>();
                for (SkuTicket skuTicket : skuTickets) {
                    strings.add(skuTicket.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, strings);
                sp_ticket_select.setAdapter(adapter);
                sp_ticket_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        AddTicketView.this.skuTicket = skuTickets.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
    }

    public void init(Order order) {
        orderId = order.getId();
        init(order.getSkuId());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date = calendar.getTime();
        tv_chosen_date.setText(DateUtil.date2Str(date, DateUtil.FORMAT_YMD));
        getTicketTimes();
    }

    public void getTicketTimes() {
        if (skuTicket == null) {
            activity.makeToast("未选择票种");
        }
        if (date == null) {
            activity.makeToast("未选择日期");
        }
        activity.addRequest(activity.getService(SkusApi.class).querySkuTicketPrice(skuId, skuTicket.getId(),
                DateUtil.date2Str(date, DateUtil.FORMAT_YMD), orderId), new EyouBaseCallBack<List<SkuTicketPrice>>() {
            @Override
            public void onSuccess200(final List<SkuTicketPrice> prices) {
                List<String> strings = new ArrayList<>();
                for (SkuTicketPrice skuTicketPrice : prices) {
                    strings.add(skuTicketPrice.getTime());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, strings);
                sp_ticket_time_select.setAdapter(adapter);
                sp_ticket_time_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        AddTicketView.this.skuTicketPrice = prices.get(position);
                        btn_add.setEnabled(true);
                        et_age.setEnabled(true);
                        et_name.setEnabled(true);
                        et_weight.setEnabled(true);
                        btn_add.setOnClickListener(AddTicketView.this);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (activity instanceof IAddTicketInterface) {
            OrderTicket ticket = new OrderTicket();
            ticket.setPrice(skuTicketPrice.getPrice());
            ticket.setSalePrice(skuTicketPrice.getSalePrice());
            ticket.setSkuTicketId(skuTicket.getId());
            ticket.setTicketDate(skuTicketPrice.getDate());
            ticket.setTicketTime(skuTicketPrice.getTime());
            ticket.setTicketPriceId(skuTicketPrice.getId());
            ticket.setSkuTicket(skuTicket.getName());
            ticket.setSkuTicketId(skuTicket.getId());
            OrderTicketUser user = new OrderTicketUser();
            try {
                user.setName(et_name.getText().toString());
                user.setAge(Integer.parseInt(et_age.getText().toString()));
                user.setWeight(Integer.parseInt(et_weight.getText().toString()));
            } catch (Exception e) {
                activity.makeToast("信息错误");
            }
            ticket.setOrderTicketUsers(Lists.newArrayList(user));
            ((IAddTicketInterface) activity).addTicket(ticket);
            window.dismiss();
        }
    }
}
