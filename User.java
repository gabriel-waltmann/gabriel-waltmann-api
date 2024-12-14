import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
class Geo {
    private String lat;
    private String lng;

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Address {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private Geo geo;

     public String getStreet() {
         return street;
     }

     public String getSuite() {
         return suite;
     }

     public String getCity() {
         return city;
     }

     public String getZipcode() {
         return zipcode;
     }

     public Geo getGeo() {
         return geo;
     }

     public void setStreet(String street) {
         this.street = street;
     }

     public void setSuite(String suite) {
         this.suite = suite;
     }

     public void setCity(String city) {
         this.city = city;
     }

     public void setZipcode(String zipcode) {
         this.zipcode = zipcode;
     }

     public void setGeo(Geo geo) {
         this.geo = geo;
     }
 }

@JsonIgnoreProperties(ignoreUnknown = true)
class Company {
    private String name;
    private String catchPhrase;
    private String bs;
}

public class User {
    @JsonProperty("id")
    int id;

    @JsonProperty("name")
    String name;

    @JsonProperty("username")
    String username;

    @JsonProperty("email")
    String email;

    @JsonProperty("phone")
    String phone;

    @JsonProperty("website")
    String website;

    @JsonProperty("address")
    Address address;

    @JsonProperty("company")
    Company company;
}
