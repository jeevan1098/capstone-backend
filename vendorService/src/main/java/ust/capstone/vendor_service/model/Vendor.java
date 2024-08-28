package ust.capstone.vendor_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Document(collection = "vendors")
public class Vendor {
    @Id
    private String id;
    private String name;
    private String description;
    private String contactMail;
    private String contactPhone;
    private String website;
    private String city;

    public Vendor() {
    }

    public Vendor(String id, String name, String description, String contactMail, String contactPhone, String website, String city) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.contactMail = contactMail;
        this.contactPhone = contactPhone;
        this.website = website;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactMail() {
        return contactMail;
    }

    public void setContactMail(String contactMail) {
        this.contactMail = contactMail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
