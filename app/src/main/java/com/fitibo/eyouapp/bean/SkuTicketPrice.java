package com.fitibo.eyouapp.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by qianhao.zhou on 8/11/16.
 */
@Data
public class SkuTicketPrice {

    private int id;
    private int skuId;
    private int skuTicketId;
    private String date;
    private String time;
    private BigDecimal salePrice;
    private BigDecimal costPrice;
    private BigDecimal realPrice;
    private BigDecimal price;
    private String description;
    private int currentCount;
    private int totalCount;
}
