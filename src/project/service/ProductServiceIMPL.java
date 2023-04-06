package project.service;

import project.config.Config;
import project.data.Path;
import project.model.Catalog;
import project.model.Product;

import java.util.List;

public class ProductServiceIMPL implements IProductService {
    List<Product> listProducts = new Config<Product>().readFromFile(Path.PRODUCT_PATH);

    List<Catalog> listCatalogs = new Config<Catalog>().readFromFile(Path.CATALOG_PATH);

    @Override
    public List<Product> findAll() {
        return listProducts;
    }

    @Override
    public void save(Product product) {
        if (findById(product.getProductId()) == null) {
            listProducts.add(product);

        } else {
            listProducts.set(listProducts.indexOf(product), product);
        }
        new Config<Product>().writeToFile(listProducts, Path.PRODUCT_PATH);
    }

    @Override
    public Product findById(int id) {
        for (Product product : listProducts) {
            if (product.getProductId() == id) {
                return product;
            }
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        listProducts.removeIf(product -> product.getProductId() == id);
        new Config<Product>().writeToFile(listProducts, Path.PRODUCT_PATH);
    }

    @Override
    public List<Catalog> getAllCatalogs() {
        return listCatalogs;
    }

    @Override
    public Catalog findCatalogById(int id) {
        for (Catalog catalog: listCatalogs) {
            if (catalog.getCatalogId() == id) {
                return catalog;
            }
        }
        return null;
    }
}
