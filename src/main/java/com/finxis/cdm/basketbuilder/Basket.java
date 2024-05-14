package com.finxis.cdm.basketbuilder;

import com.finxis.cdm.basketbuilder.CollateralDescription;

public class Basket implements Cloneable {
    private CollateralDescription collateralDescription = null;

    private String purchasePrice = null;
    private String repurchasePrice = null;
    private String repoInterest = null;
    private String repoRate = null;

    private String haircut = null;

    private String startDate = null;

    private String endDate = null;

    private int quantity = 0;
    private double price = 0;


    private boolean canceled = false;
    private boolean isNew = true;
    private String message = null;
    private String ID = null;
    private String originalID = null;
    private static int nextID = 1;

    public Basket() {
        ID = generateID();
    }

    public Basket(String ID) {
        this.ID = ID;
    }

    public Object clone() {
        try {
            Basket basket= (Basket) super.clone();
            basket.setOriginalID(getID());
            basket.setID(basket.generateID());
            return basket;
        } catch (CloneNotSupportedException e) {}
        return null;
    }

    public String generateID() {
        return Long.toString(System.currentTimeMillis() + (nextID++));
    }


    public CollateralDescription getCollateralDescription() {
        return collateralDescription;
    }

    public void setCollateralDescription(CollateralDescription symbol) {
        this.collateralDescription = symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getRepurchasePrice() {
        return repurchasePrice;
    }

    public void setRepurchasePrice(String repurchasePrice) {
        this.repurchasePrice = repurchasePrice;
    }

    public String getRepoRate() {
        return repoRate;
    }

    public void setRepoRate(String repoRate) {
        this.repoRate = repoRate;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public boolean getCanceled() {
        return canceled;
    }

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setOriginalID(String originalID) {
        this.originalID = originalID;
    }

    public String getOriginalID() {
        return originalID;
    }
}
