package com.fitibo.eyouapp.main.api;

import com.fitibo.eyouapp.bean.Sku;
import com.lemon.support.request.SimpleCall;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * Created by yerunjie on 2018/6/6
 *
 * @author yerunjie
 */
public interface SkusApi {
    @GET("skus")
    SimpleCall<List<Sku>> querySkus(
            @Query("keyword") String keyword,
            @Query("pagesize") int pageSize,
            @Query("pagenumber") int pageNumber);

    @GET("skus/{id}")
    SimpleCall<Sku> querySkuById(@Path("id") int id);

    /*@PUT("skus/{id}/status/{toStatus}")
    SimpleCall<ResultVo> updateOrderStatus(@Path("id") int id,
                                           @Path("toStatus") int toStatus,
                                           @Query("sendEmail") boolean sendEmail,
                                           @Body Order order);*/
}
