package com.fitibo.eyouapp.main.api;

import com.fitibo.eyouapp.bean.Order;
import com.fitibo.eyouapp.bean.OrderTicket;
import com.fitibo.eyouapp.bean.ResultVo;
import com.lemon.support.request.SimpleCall;
import retrofit2.http.*;

import java.util.List;

/**
 * Created by yerunjie on 2018/6/6
 *
 * @author yerunjie
 */
public interface OrdersApi {
    @GET("orders")
    SimpleCall<List<Order>> queryOrders(@Query("pagesize") int pageSize,
                                        @Query("pagenumber") int pageNumber);

    @GET("orders/{id}")
    SimpleCall<Order> queryOrderById(@Path("id") int id);

    @PUT("orders/{id}/status/{toStatus}")
    SimpleCall<ResultVo> updateOrderStatus(@Path("id") int id,
                                           @Path("toStatus") int toStatus,
                                           @Query("sendEmail") boolean sendEmail,
                                           @Body Order order);

    @PUT("orders/{id}")
    SimpleCall<Order> updateOrder(@Path("id") int id, @Body Order order);

    @DELETE("orders/tickets/{id}")
    SimpleCall<Boolean> deleteTicket(@Path("id") int ticketId);
}
