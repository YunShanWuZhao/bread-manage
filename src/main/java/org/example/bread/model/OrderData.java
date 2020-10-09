package org.example.bread.model;

import javafx.beans.property.SimpleStringProperty;

public class OrderData {
    private SimpleStringProperty orderNum = new SimpleStringProperty();

    private SimpleStringProperty productName = new SimpleStringProperty();

    private SimpleStringProperty spec = new SimpleStringProperty();

    private SimpleStringProperty addr = new SimpleStringProperty();

    private SimpleStringProperty status = new SimpleStringProperty();

    public String getOrderNum() {
        return orderNum.get();
    }

    public SimpleStringProperty orderNumProperty() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum.set(orderNum);
    }

    public String getProductName() {
        return productName.get();
    }

    public SimpleStringProperty productNameProperty() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public String getSpec() {
        return spec.get();
    }

    public SimpleStringProperty specProperty() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec.set(spec);
    }

    public String getAddr() {
        return addr.get();
    }

    public SimpleStringProperty addrProperty() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr.set(addr);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
