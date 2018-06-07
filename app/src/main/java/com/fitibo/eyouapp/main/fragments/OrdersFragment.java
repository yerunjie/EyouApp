package com.fitibo.eyouapp.main.fragments;


import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.fitibo.eyouapp.R;
import com.fitibo.eyouapp.base.EyouBaseCallBack;
import com.fitibo.eyouapp.base.EyouBaseFragment;
import com.fitibo.eyouapp.bean.Order;
import com.fitibo.eyouapp.main.adapter.OrdersAdapter;
import com.fitibo.eyouapp.main.api.OrdersApi;
import com.fitibo.eyouapp.order.OrderDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends EyouBaseFragment {
    private static final int offset = 30;
    private Unbinder unbinder;

    @BindView(R.id.rv_orders)
    RecyclerView rv_orders;

    private OrdersAdapter adapter;
    private List<Order> orders = new ArrayList<>();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        addRequest(getService(OrdersApi.class).queryOrders(), new EyouBaseCallBack<List<Order>>() {
            @Override
            public void onSuccess200(List<Order> list) {
                orders.clear();
                orders.addAll(list);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        setTitleString("订单");
        rv_orders.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rv_orders.setItemAnimator(new DefaultItemAnimator());
        adapter = new OrdersAdapter(orders);
        rv_orders.setAdapter(adapter);
        rv_orders.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = offset;
                outRect.right = offset;
                outRect.bottom = offset;
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.top = offset;
                }
            }
        });
        adapter.setOnItemClickListener(new OrdersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Order order) {
                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                intent.putExtra("orderId", order.getId());
                startActivity(intent);
            }
        });
    }

    public OrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static OrdersFragment newInstance() {
        return new OrdersFragment();
    }

}
