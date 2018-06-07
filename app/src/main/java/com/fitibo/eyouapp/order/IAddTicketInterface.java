package com.fitibo.eyouapp.order;

import android.widget.PopupWindow;
import com.fitibo.eyouapp.bean.OrderTicket;

/**
 * Created by yerunjie on 2018/6/7
 *
 * @author yerunjie
 */
public interface IAddTicketInterface extends PopupWindow.OnDismissListener {
    void addTicket(OrderTicket ticket);
}
