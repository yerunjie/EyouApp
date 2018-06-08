package com.fitibo.eyouapp.main.api;

import com.fitibo.eyouapp.bean.MessageBoard;
import com.lemon.support.request.SimpleCall;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

/**
 * Created by yerunjie on 2018/6/6
 *
 * @author yerunjie
 */
public interface MessageBoardApi {
    @GET("message")
    SimpleCall<List<MessageBoard>> queryMessages(
            @Query("tag") int tag,
            @Query("pagesize") int pageSize,
            @Query("pagenumber") int pageNumber);

}
