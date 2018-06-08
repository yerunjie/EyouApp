package com.fitibo.eyouapp.main.fragments;


import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.fitibo.eyouapp.R;
import com.fitibo.eyouapp.base.EyouBaseCallBack;
import com.fitibo.eyouapp.base.EyouBaseFragment;
import com.fitibo.eyouapp.bean.Sku;
import com.fitibo.eyouapp.main.adapter.SkusAdapter;
import com.fitibo.eyouapp.main.api.SkusApi;
import com.fitibo.eyouapp.order.OrderDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SkusFragment extends EyouBaseFragment {
    private static final int offset = 30;
    private static final int pageSize = 10;
    private Unbinder unbinder;

    @BindView(R.id.rv_skus)
    RecyclerView rv_skus;
    @BindView(R.id.srl_skus)
    SwipeRefreshLayout srl_skus;
    @BindView(R.id.sv_sku_search)
    SearchView sv_sku_search;

    SkusAdapter adapter;
    private List<Sku> skus = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private int pageNumber = 0;
    private int lastVisibleItem = 10;
    private String keyword = "";

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();

    }

    private void initData() {
        addRequest(getService(SkusApi.class).querySkus(keyword, pageSize, pageNumber), new EyouBaseCallBack<List<Sku>>() {
            @Override
            public void onSuccess200(List<Sku> list) {
                skus.addAll(list);
                adapter.notifyDataSetChanged();
                srl_skus.setRefreshing(false);
                pageNumber++;
            }
        });
    }

    private void initView() {
        setTitleString("库存");
        srl_skus.setColorSchemeResources(R.color.colorPrimary);
        srl_skus.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNumber = 0;
                skus.clear();
                initData();
            }
        });
        linearLayoutManager = new LinearLayoutManager(this.getActivity());
        rv_skus.setLayoutManager(linearLayoutManager);
        rv_skus.setItemAnimator(new DefaultItemAnimator());
        adapter = new SkusAdapter(skus);
        rv_skus.setAdapter(adapter);
        rv_skus.addItemDecoration(new RecyclerView.ItemDecoration() {
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
        /*adapter.setOnItemClickListener(new SkusAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Sku sku) {
                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                intent.putExtra("orderId", sku.getId());
                startActivity(intent);
            }
        });*/
        rv_skus.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    initData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
        // 设置搜索文本监听
        sv_sku_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                keyword = query;
                pageNumber = 0;
                skus.clear();
                initData();
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                keyword = newText;
                return false;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_skus, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public SkusFragment() {
        // Required empty public constructor
    }

    public static SkusFragment newInstance() {
        return new SkusFragment();
    }
}
