/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

/**
 *
 * @author Computer
 */
public class OrderDetail {
    private int orderId;
    private int watchId;
    private int unitPrice;
    private int quantity;

    /**
     * @return the orderId
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the watchId
     */
    public int getWatchId() {
        return watchId;
    }

    /**
     * @param watchId the watchId to set
     */
    public void setWatchId(int watchId) {
        this.watchId = watchId;
    }

    /**
     * @return the unitPrice
     */
    public int getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
    private String watchTitle;

    /**
     * @return the watchTitle
     */
    public String getWatchTitle() {
        return watchTitle;
    }
    
    public void setWatchTitle(String s) {
        watchTitle = s;
    }
    
    
}
