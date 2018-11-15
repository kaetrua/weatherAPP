package bean;

public class FtrWeather {
    private String date;

    public String getFengxiang() {
        return fengxiang;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    private String fengxiang;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String high;

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    private String low;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFengli() {
        return fengli;
    }

    @Override
    public String toString() {
        return "FtrWeather{" +
                "date='" + date + '\'' +
                ", fengxiang='" + fengxiang + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", type='" + type + '\'' +
                ", fengli='" + fengli + '\'' +
                '}';
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    private String fengli;
//    private String city;
//    private String updatetime;
//    private String wendu;
//    private String shidu;
//    private String pm25;
//    private String quality;
//    private String fengxiang;
}
