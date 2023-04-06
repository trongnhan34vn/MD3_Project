package project.controller;

import project.model.Catalog;
import project.service.CatalogServiceIMPL;
import project.service.ICatalogService;

import java.util.List;

public class CatalogController {
    ICatalogService catalogService = new CatalogServiceIMPL();

    public List<Catalog> getAllCatalogs() {
        return catalogService.findAll();
    }

    public void createCatalog(Catalog catalog) {
        catalogService.save(catalog);
    }

    public Catalog findById(int id) {
        return catalogService.findById(id);
    }

    public void updateCatalog(Catalog catalog) {
        catalogService.save(catalog);
    }

    public void deleteCatalog(int id) {
        catalogService.deleteById(id);
    }

    public List<Catalog> searchByName(String search) {
        return searchByName(search);
    }
}
