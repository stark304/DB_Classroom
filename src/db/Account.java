/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

/**
 * @author kuhail
 */
public class Account {
    private int AdvertisementID;
    private String AdvTitle;
    private String AdvDetails;
    private String AdvDateTime;
    private float Price;
    private String UserID;
    private String ModeratorID;
    private String CategoryID;
    private String StatusID;

    public Account(int AdvertisementID, String AdvTitle, String AdvDetails, String AdvDateTime, float Price, String UserID, String ModeratorID, String CategoryID, String StatusID) {
        this.AdvertisementID = AdvertisementID;
        this.AdvTitle = AdvTitle;
        this.AdvDetails = AdvDetails;
        this.AdvDateTime = AdvDateTime;
        this.Price = Price;
        this.UserID = UserID;
        this.ModeratorID = ModeratorID;
        this.CategoryID = CategoryID;
        this.StatusID = StatusID;
    }

    public int getAdvertisementID() {
        return AdvertisementID;
    }

    public String getAdvTitle() {
        return AdvTitle;
    }

    public String getAdvDetails() {
        return AdvDetails;
    }

    public String getAdvDateTime() {
        return AdvDateTime;
    }

    public float getPrice() {
        return Price;
    }

    public String getUserID() {
        return UserID;
    }

    public String getModeratorID() {
        return ModeratorID;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public String getStatusID() {
        return StatusID;
    }

    Object[] toArray() {
        return new Object[]{AdvTitle, AdvDetails, Price, AdvDateTime};
    }

}
