package project.service;

import project.model.Catalog;
import project.model.Product;

import java.util.List;

public interface IProductService extends IGenericService<Product>{
    List<Catalog> getAllCatalogs();

    Catalog findCatalogById(int id);
}
