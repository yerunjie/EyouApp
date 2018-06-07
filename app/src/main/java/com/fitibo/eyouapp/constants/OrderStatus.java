package com.fitibo.eyouapp.constants;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by qianhao.zhou on 8/18/16.
 */
public enum OrderStatus {
  CART(1, "预提交", "Cart"),
  DELETE(2, "删除", "Deleted"),
  NEW(10, "新建", "New"),
  API_FAILED(-20, "自动处理失败", "Auto Order Failed"),
  PENDING(20, "待确认", "Pending"),
  FULL(30, "已满", "Full"),
  RESUBMIT(11, "再提交", "Resubmit"),
  RECONFIRMING(21, "再确认", "Reconfirming"),
  CONFIRMED(40, "已确认", "Confirmed"),
  CONFIRMED_NEED_OPERATE(-40, "待补单", "Confirmed Need Operate"),
  MODIFYING(50, "修改中", "Modified"),
  CANCELLED(60, "取消", "Cancelled"),
  CLOSED(90, "已关闭", "Closed"),
  AFTER_SALE(70, "售后", "After Sale"),
  PROCESSED(80, "已处理", "Processed");


  OrderStatus(int value, String desc, String descEn) {
    this.value = value;
    this.desc = desc;
    this.descEn = descEn;
  }

  public int getValue() {
    return value;
  }

  public String getDesc() {
    return desc;
  }

  public String getDescEn() {
    return descEn;
  }

  public static OrderStatus valueOf(int value) {
    for (OrderStatus orderStatus : OrderStatus.values()) {
      if (orderStatus.value == value) {
        return orderStatus;
      }
    }
    throw new IllegalArgumentException("invalid status value:" + value);
  }

  private final int value;
  private final String desc;
  private final String descEn;

  public static boolean isFinalStatus(OrderStatus orderStatus) {
    return orderStatus == OrderStatus.CONFIRMED ||
        orderStatus == OrderStatus.CLOSED ||
        orderStatus == OrderStatus.CANCELLED ||
        orderStatus == OrderStatus.PROCESSED;
  }

  public static boolean isFinalStatus(int orderStatus) {
    return isFinalStatus(OrderStatus.valueOf(orderStatus));
  }

  public boolean isFinalStatus() {
    return isFinalStatus(this);
  }

  public static List<OrderStatus> getVisibleStatus() {
    List<OrderStatus> list = Lists.newArrayList(values());
    list.remove(CART);
    list.remove(DELETE);
    return list;
  }

}
