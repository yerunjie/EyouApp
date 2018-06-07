package com.fitibo.eyouapp.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fitibo.eyouapp.R;
import com.fitibo.eyouapp.bean.Order;
import com.fitibo.eyouapp.bean.Sku;
import com.fitibo.eyouapp.constants.OrderStatus;

import java.util.List;

/**
 * Created by yerunjie on 2018/6/6
 *
 * @author yerunjie
 */
public class SkusAdapter extends RecyclerView.Adapter<SkusAdapter.ViewHolder> implements View.OnClickListener {

    private List<Sku> skus;
    private OnItemClickListener mOnItemClickListener = null;

    public SkusAdapter(List<Sku> skus) {
        this.skus = skus;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sku_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Sku sku = skus.get(position);
        holder.tv_sku_name.setText(sku.getName());
        holder.tv_city.setText(sku.getCity());
        holder.tv_uuid.setText(sku.getUuid());
        //holder.tv_status.setText(OrderStatus.valueOf(order.getStatus()).getDesc());
        //holder.tv_sku.setText(order.getSku());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return skus.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, skus.get((int) v.getTag()));
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_uuid)
        TextView tv_uuid;
        @BindView(R.id.tv_sku_name)
        TextView tv_sku_name;
        @BindView(R.id.tv_city)
        TextView tv_city;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, Sku sku);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
