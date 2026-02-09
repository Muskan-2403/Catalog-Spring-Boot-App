package com.example.CatalogApp.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.CatalogApp.Model.Product;
import com.example.CatalogApp.Service.ProductService;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    // @RequestMapping("/")
    // public String greet(){
    //     return "Hello World!";
    // }

    // @RequestMapping("/products")
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){
        // Response Entity - helps send custom status codes too
        // custom status code helps customise frontend
        return new ResponseEntity<>(productService.getProducts(),
            HttpStatus.OK);
    }


    // controller layer just responds to server req
    // service layer implements business logic

    // @RequestMapping("/products/{id}")
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> geProductById(@PathVariable int id){
        // Response Entity- if prod not found then return not found
        Product p = productService.getProductById(id);
        if(p==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(p,HttpStatus.OK);
    }

    // @RequestMapping("/products")
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product p, @RequestPart MultipartFile imgFile){
        // ? - we might return data or just status values
        // productService.addProduct(p);

        try {
            Product curr = productService.addProduct(p,imgFile);
            return new ResponseEntity<>(curr,HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // @PutMapping("/products")
    // public void updateProduct(@RequestBody Product p){
    //     productService.updateProduct(p);
    // }

    // @DeleteMapping("/products/{id}")
    // public void deleteProduct(@PathVariable int id){
    //     productService.deleteProduct(id);
    // }

    @GetMapping("/product/{id}/image")
    private ResponseEntity<byte[]> getImageByProductId(@PathVariable int id){
        Product curr = productService.getProductById(id);
        if(curr==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        byte[] imageFile = curr.getImageDate();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(curr.getImageType()))
                .body(imageFile);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product,
        @RequestPart MultipartFile imageFile){
            Product curr;
            try {
                curr = productService.updateProduct(id, product, imageFile);
            } catch (IOException e) {
                return new ResponseEntity<>("Failed to Update",HttpStatus.BAD_REQUEST);
            }

            if(curr!=null){
                return  new ResponseEntity<>("Updated", HttpStatus.OK);                
            }

            return new ResponseEntity<>("Failed to Update",HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product p = productService.getProductById(id);
        if(p!=null){
            productService.deleteProduct(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }

        return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword){
        System.out.println("Searching with: "+keyword);
        List<Product> products = productService.search(keyword);
        // if(products == null){

        // }
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
}

