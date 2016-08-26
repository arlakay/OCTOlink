package id.co.octolink.model;

/**
 * Created by FirdaRinoa on 8/21/16.
 */
public class Merchant {
    public String merchant_name;
    public String merchant_address;
    public String merchant_star;
    public String merchant_discount;
    public String merchant_location;
    public int merchant_image;



    public Merchant(String merchant_name, String merchant_address, String merchant_star, String merchant_discount, String merchant_location, int merchant_image){
        this.merchant_name = merchant_name;
        this.merchant_address = merchant_address;
        this.merchant_star = merchant_star;
        this.merchant_discount = merchant_discount;
        this.merchant_location = merchant_location;
        this.merchant_image = merchant_image;
    }
}
