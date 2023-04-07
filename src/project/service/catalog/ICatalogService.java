package project.service.catalog;

import project.model.Catalog;
import project.service.IGenericService;

import java.util.List;

public interface ICatalogService extends IGenericService<Catalog> {
    List<Catalog> searchByName(String search);
}
