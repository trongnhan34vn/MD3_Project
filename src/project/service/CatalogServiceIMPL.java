package project.service;

import project.config.Config;
import project.data.Path;
import project.model.Catalog;

import java.util.List;

public class CatalogServiceIMPL implements ICatalogService{
    List<Catalog> listCatalogs = new Config<Catalog>().readFromFile(Path.CATALOG_PATH);
    @Override
    public List<Catalog> findAll() {
        return listCatalogs;
    }

    @Override
    public void save(Catalog catalog) {
        listCatalogs.add(catalog);
    }

    @Override
    public Catalog findById(int id) {
        for (Catalog catalog: listCatalogs) {
            if (catalog.getCatalogId() == id) {
                return catalog;
            }
        };
        return null;
    }

    @Override
    public void deleteById(int id) {
        for (Catalog catalog: listCatalogs) {
            if (catalog.getCatalogId() == id) {
                listCatalogs.remove(catalog);
            }
        }
    }
}
