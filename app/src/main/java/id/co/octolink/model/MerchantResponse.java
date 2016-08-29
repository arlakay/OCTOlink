package id.co.octolink.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ILM on 8/29/2016.
 */
public class MerchantResponse {
    @SerializedName("merchant")
    private List<Merchant> merchant;

    public MerchantResponse(List<Merchant> merchant) {
        this.merchant = merchant;
    }

    public List<Merchant> getMerchant() {
        return merchant;
    }

    public void setMerchant(List<Merchant> merchant) {
        this.merchant = merchant;
    }
}
