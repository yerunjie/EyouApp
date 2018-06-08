package com.fitibo.eyouapp.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.fitibo.eyouapp.R;
import com.fitibo.eyouapp.bean.MessageBoard;

import java.util.List;

/**
 * Created by yerunjie on 2018/6/6
 *
 * @author yerunjie
 */
public class MessageBoardsAdapter extends RecyclerView.Adapter<MessageBoardsAdapter.ViewHolder> implements View.OnClickListener {

    private List<MessageBoard> messageBoards;
    private OnItemClickListener mOnItemClickListener = null;

    public MessageBoardsAdapter(List<MessageBoard> messageBoards) {
        this.messageBoards = messageBoards;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MessageBoard messageBoard = messageBoards.get(position);
        holder.tv_name.setText(messageBoard.getAdminName());
        holder.tv_content.setText(messageBoard.getContent());
        holder.tv_date.setText(messageBoard.getCreateTime());
        holder.tv_tag.setText(messageBoard.getTag());
        //holder.tv_status.setText(OrderStatus.valueOf(order.getStatus()).getDesc());
        //holder.tv_sku.setText(order.getSku());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return messageBoards.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, messageBoards.get((int) v.getTag()));
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_tag)
        TextView tv_tag;
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.tv_content)
        TextView tv_content;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, MessageBoard messageBoard);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
