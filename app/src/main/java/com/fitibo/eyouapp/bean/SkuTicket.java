package com.fitibo.eyouapp.bean;

import lombok.Data;

import java.util.List;

/**
 * Created by qianhao.zhou on 8/3/16.
 */
@Data
public class SkuTicket {

    private int id;
    private String name;
    private int skuId;
    private int count;
    private int minAge;
    private int maxAge;
    private int minWeight;
    private int maxWeight;
    private String description;
    private List<SkuTicketPrice> ticketPrices;

}
