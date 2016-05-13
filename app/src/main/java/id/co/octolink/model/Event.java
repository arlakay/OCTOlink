package id.co.octolink.model;

/**
 * Created by E.R.D on 4/2/2016.
 */
public class Event {
    private String title;
    private String thumbnailUrl;
    private String date;
    private String desc;
    private String letter;
    private String diskon;
    private String storeName;

    public Event() {
    }

    public Event(String name, String thumbnailUrl, String year, String rating) {
        this.title = name;
        this.thumbnailUrl = thumbnailUrl;
        this.date = date;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getDiskon() {
        return diskon;
    }

    public void setDiskon(String diskon) {
        this.diskon = diskon;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

}
