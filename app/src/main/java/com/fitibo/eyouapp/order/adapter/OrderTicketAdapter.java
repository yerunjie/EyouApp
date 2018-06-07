package com.fitibo.eyouapp.order.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fitibo.eyouapp.R;
import com.fitibo.eyouapp.bean.OrderTicket;
import com.fitibo.eyouapp.bean.OrderTicketUser;
import com.fitibo.eyouapp.order.IEditable;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by yerunjie on 2018/6/7
 *
 * @author yerunjie
 */
public class OrderTicketAdapter extends RecyclerView.Adapter<OrderTicketAdapter.ViewHolder> implements View.OnClickListener {

    private List<OrderTicket> tickets;

    private IEditable editable;

    private OnButtonClickListener mOnButtonClickListener = null;

    public OrderTicketAdapter(List<OrderTicket> tickets, IEditable editable) {
        this.tickets = tickets;
        this.editable = editable;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_ticket_ticket_name)
        TextView tv_ticket_ticket_name;
        @BindView(R.id.tv_ticket_ticket_date)
        TextView tv_ticket_ticket_date;
        @BindView(R.id.tv_ticket_ticket_time)
        TextView tv_ticket_ticket_time;
        @BindView(R.id.tv_ticket_cost_price)
        TextView tv_ticket_cost_price;
        @BindView(R.id.tv_ticket_sale_price)
        TextView tv_ticket_sale_price;
        @BindView(R.id.et_name)
        EditText et_name;
        @BindView(R.id.et_age)
        EditText et_age;
        @BindView(R.id.et_weight)
        EditText et_weight;
        @BindView(R.id.btn_delete)
        Button btn_delete;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_ticket_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final OrderTicket ticket = tickets.get(position);
        final OrderTicketUser user;
        if (ticket.getOrderTicketUsers() == null || ticket.getOrderTicketUsers().size() == 0) {
            user = new OrderTicketUser();
            ticket.setOrderTicketUsers(Lists.newArrayList(user));
        } else {
            user = ticket.getOrderTicketUsers().get(0);
        }
        holder.tv_ticket_ticket_name.setText(ticket.getSkuTicket());
        holder.tv_ticket_ticket_date.setText(ticket.getTicketDate());
        holder.tv_ticket_ticket_time.setText(ticket.getTicketTime());
        holder.tv_ticket_cost_price.setText(ticket.getPrice().toString());
        holder.tv_ticket_sale_price.setText(ticket.getSalePrice().toString());
        holder.et_name.setText(Strings.nullToEmpty(user.getName()));
        holder.et_age.setText(String.valueOf(user.getAge()));
        holder.et_weight.setText(String.valueOf(user.getWeight()));
        if (editable.isEditing()) {
            holder.et_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    user.setName(s.toString());
                }
            });
            holder.et_age.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        int age = Integer.parseInt(s.toString());
                        user.setAge(age);
                    } catch (Exception e) {

                    }
                }
            });
            holder.et_weight.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        int weight = Integer.parseInt(s.toString());
                        user.setWeight(weight);
                    } catch (Exception e) {

                    }
                }
            });
            holder.btn_delete.setOnClickListener(this);
            holder.btn_delete.setTag(position);
        } else {
            holder.et_name.setEnabled(false);
            holder.et_age.setEnabled(false);
            holder.et_weight.setEnabled(false);
            holder.btn_delete.setVisibility(View.GONE);
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnButtonClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnButtonClickListener.onButtonClick(v, (int) v.getTag());
        }
    }

    public interface OnButtonClickListener {
        void onButtonClick(View view, int position);
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.mOnButtonClickListener = onButtonClickListener;
    }
}
