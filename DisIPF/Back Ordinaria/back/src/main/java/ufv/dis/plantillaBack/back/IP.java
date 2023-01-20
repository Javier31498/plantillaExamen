package ufv.dis.plantillaBack.back;

public class IP {

    private int id;
    private long ip_from;
    private long ip_to;
    private String country_code;
    private String country_name;
    private String region_name;
    private String city_name;
    private double latitude;
    private double longitude;
    private String zip_code;
    private String time_zone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getIp_from() {
        return ip_from;
    }

    public void setIp_from(long ip_from) {
        this.ip_from = ip_from;
    }

    public long getIp_to() {
        return ip_to;
    }

    public void setIp_to(long ip_to) {
        this.ip_to = ip_to;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(String time_zone) {
        this.time_zone = time_zone;
    }

    public IP(int id, long ip_from, long ip_to, String country_code, String country_name, String region_name, String city_name, double latitude, double longitude, String zip_code, String time_zone) {
        this.id = id;
        this.ip_from = ip_from;
        this.ip_to = ip_to;
        this.country_code = country_code;
        this.country_name = country_name;
        this.region_name = region_name;
        this.city_name = city_name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zip_code = zip_code;
        this.time_zone = time_zone;
    }

    public IP() {
    }

    @Override
    public String toString() {
        return "IP{" +
                "id=" + id +
                ", ip_from=" + ip_from +
                ", ip_to=" + ip_to +
                ", country_code='" + country_code + '\'' +
                ", country_name='" + country_name + '\'' +
                ", region_name='" + region_name + '\'' +
                ", city_name='" + city_name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", zip_code='" + zip_code + '\'' +
                ", time_zone='" + time_zone + '\'' +
                '}';
    }
}
