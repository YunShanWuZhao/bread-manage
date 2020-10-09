package org.example.bread.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.apache.ibatis.session.SqlSession;
import org.example.bread.model.OrderData;
import org.example.bread.model.OrderDetailInfo;
import org.example.bread.model.OrderInfo;
import org.example.bread.persist.SqlConnection;
import org.example.bread.persist.mapper.OrderMapper;
import org.example.bread.util.Sound;

import java.io.File;
import java.net.URL;
import java.util.*;

public class OrderController implements Initializable {

    @FXML
    public TableView orderTable;

    private static Long lastOrderId = 0L;

    private Sound sound;


    public void initialize(URL location, ResourceBundle resources) {
        TableColumn orderNumCol = new TableColumn("订单编号");
        TableColumn productNameCol = new TableColumn("商品名称");
        TableColumn specCol = new TableColumn("商品规格");
        TableColumn addrCol = new TableColumn("收货地址");
        TableColumn statusCol = new TableColumn("订单状态");

        orderNumCol.setCellValueFactory(new PropertyValueFactory<>("orderNum"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        specCol.setCellValueFactory(new PropertyValueFactory<>("spec"));
        addrCol.setCellValueFactory(new PropertyValueFactory<>("addr"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        orderTable.getColumns().addAll(orderNumCol, productNameCol, specCol, addrCol, statusCol);
        orderTable.setPlaceholder(new Text("没有数据"));
        //orderTable.setColumnResizePolicy((param) -> true );
        queryData();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("开始查询待发货订单");
                queryData();
            }
        }, 15000,15000);
    }

    @FXML
    public void deliver(){
        OrderData orderData = (OrderData)orderTable.getSelectionModel().getSelectedItem();
        System.out.println("deliver orderNum:"+orderData.getOrderNum());
        try (SqlSession session = SqlConnection.sessionFactory.openSession()) {
            OrderMapper orderMapper = session.getMapper(OrderMapper.class);
            orderMapper.deliverOrder(orderData.getOrderNum());
            session.commit();
        }
        queryData();
    }

    public ObservableList<OrderData> queryData(){
        ObservableList<OrderData> orderDataList;
        try (SqlSession session = SqlConnection.sessionFactory.openSession()) {
            OrderMapper orderMapper = session.getMapper(OrderMapper.class);
            List<OrderInfo> orderInfos = orderMapper.selectOrder();
            List<OrderData> orderInfoList = new ArrayList<>();
            for(OrderInfo orderInfo:orderInfos){
                if(orderInfo.getId() > lastOrderId){
                    System.out.println("存在新的订单，发送通知");
                    sound = new Sound(new File("alert.mp3"), false);
                    sound.play();
                    System.out.println("播放完毕");
                    //todo
                }
                OrderData orderData = new OrderData();
                orderData.setOrderNum(orderInfo.getOrder_num());
                orderData.setAddr(orderInfo.getDistrict()+"--"+orderInfo.getDetail());
                orderData.setStatus("待发货");
                List<OrderDetailInfo> orderDetailInfos = orderMapper.selectOrderDetail(orderInfo.getId());
                StringBuilder names = new StringBuilder("");
                StringBuilder specs = new StringBuilder("");
                for(OrderDetailInfo orderDetailInfo:orderDetailInfos){
                    names.append(orderDetailInfo.getProduct_name()).append("\n");
                    specs.append(orderDetailInfo.getProduct_spec()).append("\n");
                }
                orderData.setProductName(names.toString());
                orderData.setSpec(specs.toString());
                orderInfoList.add(orderData);
            }
            if(orderInfos.size() > 0){
                lastOrderId = orderInfos.get(0).getId();
            }
            orderDataList = FXCollections.observableList(orderInfoList);
        }
        orderTable.setItems(orderDataList);
        //Platform.runLater(() -> orderTable.setItems(orderDataList));

        return orderDataList;
    }
}
