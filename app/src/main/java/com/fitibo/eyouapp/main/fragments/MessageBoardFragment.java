package com.fitibo.eyouapp.main.fragments;


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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.fitibo.eyouapp.R;
import com.fitibo.eyouapp.base.EyouBaseCallBack;
import com.fitibo.eyouapp.base.EyouBaseFragment;
import com.fitibo.eyouapp.bean.MessageBoard;
import com.fitibo.eyouapp.constants.MessageBoardTag;
import com.fitibo.eyouapp.main.adapter.MessageBoardsAdapter;
import com.fitibo.eyouapp.main.api.MessageBoardApi;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageBoardFragment extends EyouBaseFragment {
    private static final int offset = 30;
    private static final int pageSize = 10;

    private Unbinder unbinder;

    @BindView(R.id.rv_messages)
    RecyclerView rv_messages;
    @BindView(R.id.srl_messages)
    SwipeRefreshLayout srl_messages;
    @BindView(R.id.spn_message_tag)
    Spinner spn_message_tag;

    MessageBoardsAdapter adapter;
    private List<MessageBoard> messageBoards = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private int pageNumber = 0;
    private int lastVisibleItem = 10;

    private int tag = 0;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initData() {
        addRequest(getService(MessageBoardApi.class).queryMessages(tag, pageSize, pageNumber), new EyouBaseCallBack<List<MessageBoard>>() {
            @Override
            public void onSuccess200(List<MessageBoard> list) {
                messageBoards.addAll(list);
                adapter.notifyDataSetChanged();
                srl_messages.setRefreshing(false);
                pageNumber++;
            }
        });
    }

    private void initView() {
        setTitleString("首页");
        srl_messages.setColorSchemeResources(R.color.colorPrimary);
        srl_messages.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNumber = 0;
                messageBoards.clear();
                initData();
            }
        });
        linearLayoutManager = new LinearLayoutManager(this.getActivity());
        rv_messages.setLayoutManager(linearLayoutManager);
        rv_messages.setItemAnimator(new DefaultItemAnimator());
        adapter = new MessageBoardsAdapter(messageBoards);
        rv_messages.setAdapter(adapter);
        rv_messages.addItemDecoration(new RecyclerView.ItemDecoration() {
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
        rv_messages.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
        List<String> strings = new ArrayList<>();
        for (MessageBoardTag messageBoardTag : MessageBoardTag.values()) {
            strings.add(messageBoardTag.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, strings);
        spn_message_tag.setAdapter(adapter);
        spn_message_tag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tag = position;
                pageNumber = 0;
                messageBoards.clear();
                initData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public MessageBoardFragment() {
        // Required empty public constructor
    }

    public static MessageBoardFragment newInstance() {
        return new MessageBoardFragment();
    }
}
