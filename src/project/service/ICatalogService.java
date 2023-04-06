package project.service;

import project.model.Catalog;

import java.util.List;

public interface ICatalogService extends IGenericService<Catalog> {
    List<Catalog> searchByName(String search);
}
