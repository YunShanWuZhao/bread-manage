package org.example.bread.persist.mapper;

import org.apache.ibatis.annotations.Update;
import org.example.bread.model.OrderDetailInfo;
import org.example.bread.model.OrderInfo;

import java.util.List;

public interface OrderMapper {

    List<OrderInfo> selectOrder();

    List<OrderDetailInfo> selectOrderDetail(Long orderId);


    int deliverOrder(String orderNum);
}
