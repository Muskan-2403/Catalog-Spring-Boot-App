package com.example.CatalogApp.Model;

import java.util.Date;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity
public class Product {
    //Lombok will handle getter setters and constructor creation
    // @Data, @AllArgsConstructor, @NoArgsConstructor -> Lombok
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String brand;
    private int price;
    private String category;
    // @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date releaseDate;
    private boolean productAvailable;
    private int quantstockQuantity;

    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageDate;
    // @Lob wil handle large object
    // typo- it should be imageData

    //Date format is currently being handeled by frontend
    // else we could have used json format for date formatting
    
}
