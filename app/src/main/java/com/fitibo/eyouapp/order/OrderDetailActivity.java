package com.fitibo.eyouapp.order;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fitibo.eyouapp.R;
import com.fitibo.eyouapp.base.EyouBaseActivity;
import com.fitibo.eyouapp.base.EyouBaseCallBack;
import com.fitibo.eyouapp.bean.Order;
import com.fitibo.eyouapp.bean.ResultVo;
import com.fitibo.eyouapp.constants.OrderStatus;
import com.fitibo.eyouapp.constants.Transition;
import com.fitibo.eyouapp.main.api.OrdersApi;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailActivity extends EyouBaseActivity {
    @BindView(R.id.right_labels)
    FloatingActionsMenu menu;

    @BindView(R.id.et_remark)
    EditText et_remark;

    @BindView(R.id.et_sku)
    EditText et_sku;
    @BindView(R.id.et_status)
    EditText et_status;
    @BindView(R.id.et_price)
    EditText et_price;
    @BindView(R.id.et_uuid)
    EditText et_uuid;
    @BindView(R.id.et_reference_number)
    EditText et_reference_number;
    @BindView(R.id.et_vendor_phone)
    EditText et_vendor_phone;
    @BindView(R.id.et_agent_name)
    EditText et_agent_name;
    @BindView(R.id.et_agent_order_id)
    EditText et_agent_order_id;
    @BindView(R.id.et_ticket_date)
    EditText et_ticket_date;
    @BindView(R.id.et_gathering_place)
    EditText et_gathering_place;
    @BindView(R.id.et_gathering_time)
    EditText et_gathering_time;
    @BindView(R.id.et_ticket_time)
    EditText et_ticket_time;
    @BindView(R.id.et_first_name)
    EditText et_first_name;
    @BindView(R.id.et_last_name)
    EditText et_last_name;
    @BindView(R.id.et_primary_contact_email)
    EditText et_primary_contact_email;
    @BindView(R.id.et_primary_contact_phone)
    EditText et_primary_contact_phone;

    private int orderId;
    private Order order;
    private boolean editing = false;

    private List<FloatingActionButton> buttons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        orderId = getIntent().getIntExtra("orderId", 0);
        initView();
        initData();
    }

    private void initView() {
        ButterKnife.bind(this);
        showBackIcon();
        setTitleString("订单详情");

    }

    private void initData() {
        addRequest(getService(OrdersApi.class).queryOrderById(orderId), new EyouBaseCallBack<Order>() {
            @Override
            public void onSuccess200(Order data) {
                order = data;
                initOrder(order);
            }
        });
    }

    private void initOrder(Order order) {
        initOrder(editing);
        resetStatusButton(order.getStatus());
    }

    private void resetOrder() {
        order.setRemark(et_remark.getText().toString());
        order.setModifiedPrice(new BigDecimal(et_price.getText().toString()));
        order.setReferenceNumber(et_reference_number.getText().toString());
        order.setAgentOrderId(et_agent_order_id.getText().toString());
        order.setGatheringPlace(et_gathering_place.getText().toString());
        order.setGatheringTime(et_gathering_time.getText().toString());
        order.setTicketTime(et_ticket_time.getText().toString());
        order.setFirstName(et_first_name.getText().toString());
        order.setLastName(et_last_name.getText().toString());
        order.setPrimaryContactEmail(et_primary_contact_email.getText().toString());
        order.setPrimaryContactPhone(et_primary_contact_phone.getText().toString());

    }

    private void initOrder(boolean enabled) {
        et_status.setText(OrderStatus.valueOf(order.getStatus()).getDesc());
        et_status.setEnabled(false);
        et_remark.setText(order.getRemark());
        et_remark.setEnabled(enabled);
        et_sku.setText(order.getSku());
        et_sku.setEnabled(false);
        et_price.setText(order.getModifiedPrice().toString());
        et_price.setEnabled(enabled);
        et_uuid.setText(order.getUuid());
        et_uuid.setEnabled(false);
        et_reference_number.setText(order.getReferenceNumber());
        et_reference_number.setEnabled(enabled);
        et_vendor_phone.setText(order.getVendorPhone());
        et_vendor_phone.setEnabled(false);
        et_agent_name.setText(order.getAgentName());
        et_agent_name.setEnabled(false);
        et_agent_order_id.setText(order.getAgentOrderId());
        et_agent_order_id.setEnabled(enabled);
        et_ticket_date.setText(order.getTicketDate());
        et_ticket_date.setEnabled(false);
        et_gathering_place.setText(order.getGatheringPlace());
        et_gathering_place.setEnabled(enabled);
        et_gathering_time.setText(order.getGatheringTime());
        et_gathering_time.setEnabled(enabled);
        et_ticket_time.setText(order.getTicketTime());
        et_ticket_time.setEnabled(enabled);
        et_first_name.setText(order.getFirstName());
        et_first_name.setEnabled(enabled);
        et_last_name.setText(order.getLastName());
        et_last_name.setEnabled(enabled);
        et_primary_contact_email.setText(order.getPrimaryContactEmail());
        et_primary_contact_email.setEnabled(enabled);
        et_primary_contact_phone.setText(order.getPrimaryContactPhone());
        et_primary_contact_phone.setEnabled(enabled);

    }

    private void resetStatusButton(int status) {
        for (FloatingActionButton button : buttons) {
            menu.removeButton(button);
        }
        buttons.clear();
        List<Transition> transitions = Transition.getAvailableTransitions(status);
        for (final Transition transition : transitions) {
            FloatingActionButton button = new FloatingActionButton(getApplicationContext());
            button.setSize(FloatingActionButton.SIZE_MINI);
            button.setTitle(transition.getAction());
            menu.addButton(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //makeToast(transition.getAction());
                    Order order1 = new Order();
                    order1.setReferenceNumber(order.getReferenceNumber());
                    addRequest(getService(OrdersApi.class).updateOrderStatus(orderId, transition.getTo(), transition.isSendEmail(), order1), new EyouBaseCallBack<ResultVo>() {
                        @Override
                        public void onSuccess200(ResultVo data) {
                            if (data.isSuccess()) {
                                makeToast("success");
                            } else {
                                makeToast(data.getMsg());
                            }
                        }
                    });
                }
            });
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (editing) {
            getMenuInflater().inflate(R.menu.order_detail_finish_menu, menu);
        } else {
            getMenuInflater().inflate(R.menu.order_detail_edit_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_edit:
                editing = true;
                initOrder(editing);
                invalidateOptionsMenu();
                return true;
            case R.id.option_finish:
                editing = false;
                initOrder(editing);
                invalidateOptionsMenu();
                return true;
            default:
                return false;
        }
    }
}
