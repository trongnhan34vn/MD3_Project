package project.controller;

import project.dto.request.ProductDTO;
import project.dto.response.ResponseMessage;
import project.model.Catalog;
import project.model.Product;
import project.service.IProductService;
import project.service.ProductServiceIMPL;

import java.util.List;

public class ProductController {
    IProductService productService = new ProductServiceIMPL();
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    public Product findById(int id) {
        return productService.findById(id);
    }

    public List<Catalog> getAllCatalogs() {
        return productService.getAllCatalogs();
    }

    public Catalog findCatalogById (int id) {
        return productService.findCatalogById(id);
    }

    public ResponseMessage createProduct(ProductDTO productDTO) {
        Product newProduct = new Product(productDTO.getProductId(), productDTO.getProductName(), productDTO.getCatalog(), productDTO.getQuantity(), productDTO.getPrice());
        productService.save(newProduct);
        return new ResponseMessage("Success!");
    }

    public void updateProduct(Product product) {
        productService.save(product);
    }
}
