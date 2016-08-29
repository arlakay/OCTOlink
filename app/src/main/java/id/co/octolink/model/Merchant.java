package id.co.octolink.model;

/**
 * Created by FirdaRinoa on 8/21/16.
 */
public class Merchant {
    public String idmerchant;
    public String merchant_id;
    public String email;
    public String merchant_name;
    public String company;
    public String website;
    public String category;
    public String street;
    public String city;
    public String country;
    public String phone;
    public String discount;

    public Merchant(String idmerchant, String merchant_id, String email, String merchant_name,
                    String company, String website, String category, String street, String city,
                    String country, String phone, String discount) {
        this.idmerchant = idmerchant;
        this.merchant_id = merchant_id;
        this.email = email;
        this.merchant_name = merchant_name;
        this.company = company;
        this.website = website;
        this.category = category;
        this.street = street;
        this.city = city;
        this.country = country;
        this.phone = phone;
        this.discount = discount;
    }

    public String getIdmerchant() {
        return idmerchant;
    }

    public void setIdmerchant(String idmerchant) {
        this.idmerchant = idmerchant;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
