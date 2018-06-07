package com.fitibo.eyouapp.constants;

import com.google.common.collect.ArrayListMultimap;

import java.util.List;

/**
 * Created by qianhao.zhou on 8/25/16.
 */
public final class Transition {

    public Transition(int to, String action, String actionEn) {
        this(to, action, actionEn, true);
    }

    public Transition(int to, String action, String actionEn, boolean sendEmail) {
        this.to = to;
        this.action = action;
        this.actionEn = actionEn;
        this.sendEmail = sendEmail;
    }

    public int getTo() {
        return to;
    }

    public String getAction() {
        return action;
    }

    public String getActionEn() {
        return actionEn;
    }

    public boolean isSendEmail() {
        return sendEmail;
    }

    private final int to;
    private final String action;
    private final String actionEn;
    private final boolean sendEmail;
    private static ArrayListMultimap<OrderStatus, Transition> transitionMultimap;

    static {
        transitionMultimap = ArrayListMultimap.create();
        transitionMultimap.put(
                OrderStatus.CART, new Transition(OrderStatus.NEW.getValue(), "预定", "order"));
        transitionMultimap.put(
                OrderStatus.CART, new Transition(OrderStatus.DELETE.getValue(), "删除", "Delete"));

        transitionMultimap.put(
                OrderStatus.NEW, new Transition(OrderStatus.PENDING.getValue(), "审核通过", "Approve"));
        transitionMultimap.put(
                OrderStatus.NEW,
                new Transition(OrderStatus.PENDING.getValue(), "审核通过不发邮件", "Approve Without Email", false));
        transitionMultimap.put(
                OrderStatus.NEW, new Transition(OrderStatus.CLOSED.getValue(), "关闭订单", "Close"));
        transitionMultimap.put(
                OrderStatus.NEW, new Transition(OrderStatus.CANCELLED.getValue(), "取消订单", "Cancel"));

        transitionMultimap.put(
                OrderStatus.PENDING, new Transition(OrderStatus.FULL.getValue(), "库存已满", "Full"));
        transitionMultimap.put(
                OrderStatus.PENDING,
                new Transition(OrderStatus.FULL.getValue(), "库存已满不发邮件", "Full Without Email", false));
        transitionMultimap.put(
                OrderStatus.PENDING, new Transition(OrderStatus.CONFIRMED.getValue(), "预订成功", "Confirm"));

        transitionMultimap.put(
                OrderStatus.PENDING,
                new Transition(OrderStatus.CONFIRMED_NEED_OPERATE.getValue(), "改定成功", "Transfer"));

        transitionMultimap.put(
                OrderStatus.FULL, new Transition(OrderStatus.CLOSED.getValue(), "关闭订单", "Close"));
        transitionMultimap.put(
                OrderStatus.FULL, new Transition(OrderStatus.RESUBMIT.getValue(), "再提交", "Resubmit"));

        transitionMultimap.put(
                OrderStatus.CONFIRMED,
                new Transition(OrderStatus.MODIFYING.getValue(), "确认后修改订单", "Modify"));
        transitionMultimap.put(
                OrderStatus.CONFIRMED, new Transition(OrderStatus.CANCELLED.getValue(), "取消订单", "Cancel"));
        transitionMultimap.put(
                OrderStatus.CONFIRMED,
                new Transition(OrderStatus.AFTER_SALE.getValue(), "进入售后", "After Sale"));

        transitionMultimap.put(
                OrderStatus.MODIFYING, new Transition(OrderStatus.CONFIRMED.getValue(), "预订成功", "Confirm"));
        transitionMultimap.put(
                OrderStatus.MODIFYING, new Transition(OrderStatus.CLOSED.getValue(), "关闭订单", "Close"));
        transitionMultimap.put(
                OrderStatus.MODIFYING, new Transition(OrderStatus.CANCELLED.getValue(), "取消订单", "Cancel"));

        // transitionMultimap.put(OrderStatus.CANCELLED.getValue(), );

        // transitionMultimap.put(OrderStatus.CLOSED.getValue(), );

        transitionMultimap.put(
                OrderStatus.RESUBMIT,
                new Transition(OrderStatus.RECONFIRMING.getValue(), "审核通过", "Approve"));
        transitionMultimap.put(
                OrderStatus.RESUBMIT,
                new Transition(
                        OrderStatus.RECONFIRMING.getValue(), "审核通过不发邮件", "Approve Without Email", false));
        transitionMultimap.put(
                OrderStatus.RESUBMIT, new Transition(OrderStatus.CLOSED.getValue(), "关闭订单", "Close"));

        transitionMultimap.put(
                OrderStatus.RECONFIRMING, new Transition(OrderStatus.FULL.getValue(), "库存已满", "Full"));
        transitionMultimap.put(
                OrderStatus.RECONFIRMING,
                new Transition(OrderStatus.FULL.getValue(), "库存已满不发邮件", "Full Without Email", false));
        transitionMultimap.put(
                OrderStatus.RECONFIRMING,
                new Transition(OrderStatus.CONFIRMED.getValue(), "预订成功", "Confirm"));

        transitionMultimap.put(
                OrderStatus.AFTER_SALE,
                new Transition(OrderStatus.PROCESSED.getValue(), "处理完成", "Processed"));

        transitionMultimap.put(
                OrderStatus.PROCESSED,
                new Transition(OrderStatus.AFTER_SALE.getValue(), "再次售后", "After Sale"));

        transitionMultimap.put(OrderStatus.CONFIRMED_NEED_OPERATE,
                new Transition(OrderStatus.CONFIRMED.getValue(), "处理完成", "Operate Success", false));

        transitionMultimap.put(OrderStatus.API_FAILED,
                new Transition(OrderStatus.NEW.getValue(), "再次自动下单", "Auto Operate Again", false));

        transitionMultimap.put(OrderStatus.API_FAILED,
                new Transition(OrderStatus.PENDING.getValue(), "手动确认", "Manual Operate"));
    }

    public static List<Transition> getAvailableTransitions(int status) {
        return getAvailableTransitions(OrderStatus.valueOf(status));
    }

    public static List<Transition> getAvailableTransitions(OrderStatus status) {
        return transitionMultimap.get(status);
    }
}
