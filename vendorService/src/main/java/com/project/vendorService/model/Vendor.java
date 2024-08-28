package com.project.vendorService.model;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
   
