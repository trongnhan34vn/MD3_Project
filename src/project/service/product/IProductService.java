package project.service.product;

import project.model.product.Catalog;
import project.model.product.Product;
import project.service.IGenericService;

import java.util.List;

public interface IProductService extends IGenericService<Product> {
    List<Product> searchProductByName(String search);
    List<Catalog> getAllCatalogs();
    Catalog findCatalogById(int id);
    List<Product> searchProductByCatalog(String catalogName);
}
