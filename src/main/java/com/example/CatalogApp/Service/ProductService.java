package com.example.CatalogApp.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.CatalogApp.Model.Product;
import com.example.CatalogApp.Repository.ProductRepo;


@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo; 

    // List<Product> products = new ArrayList<>(Arrays.asList(
    //     new Product(1,"prod 1",100),
    //     new Product(2,"prod 2",200),
    //     new Product(3,"prod 3",300)
    // ));
    public List<Product> getProducts(){
        // return products;
        return productRepo.findAll();
    }

    public Product getProductById(int id) {
        //stream 
        // return products.stream()
        // .filter(p -> p.getPid()==id)
        // .findFirst()
        // .orElse(new Product(0,"Prod not found",0));

        return productRepo.findById(id).orElse(null);
    }

    public void addProduct(Product p){
        // products.add(p);

        productRepo.save(p);
    }

    public void updateProduct(Product p) {
        // int idx=0;
        // for(int i=0;i<products.size();i++){
        //     if(products.get(i).getPid()==p.getPid()){idx=i;break;}
        // }
        // products.set(idx, p);

        productRepo.save(p);
        //if data is there, update else save(add)
    }

    public void deleteProduct(int id) {
        // int idx=0;
        // for(int i=0;i<products.size();i++){
        //     if(products.get(i).getPid()==id){idx=i;break;}
        // }
        // products.remove(idx);

        productRepo.deleteById(id);
    }

    public Product addProduct(Product p, MultipartFile imgFile) throws IOException {
        p.setImageName(imgFile.getOriginalFilename());
        p.setImageType(imgFile.getContentType());
        p.setImageDate(imgFile.getBytes());

        return productRepo.save(p);
    }

    public Product updateProduct(int id, Product p, MultipartFile imgFile) throws IOException {
        p.setImageName(imgFile.getOriginalFilename());
        p.setImageType(imgFile.getContentType());
        p.setImageDate(imgFile.getBytes());
        return productRepo.save(p);

    }

    public List<Product> search(String keyword) {
        return productRepo.searchProducts(keyword);
    }
}
