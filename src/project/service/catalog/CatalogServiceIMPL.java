package project.service.catalog;

import project.config.Config;
import project.data.Path;
import project.model.product.Catalog;

import java.util.ArrayList;
import java.util.List;

public class CatalogServiceIMPL implements ICatalogService {
    List<Catalog> listCatalogs = new Config<Catalog>().readFromFile(Path.CATALOG_PATH);

    @Override
    public List<Catalog> findAll() {
        return listCatalogs;
    }

    @Override
    public void save(Catalog catalog) {
        if (findById(catalog.getCatalogId()) == null) {
            listCatalogs.add(catalog);
        } else {
            listCatalogs.set(listCatalogs.indexOf(findById(catalog.getCatalogId())), catalog);
        }
        new Config<Catalog>().writeToFile(listCatalogs, Path.CATALOG_PATH);
    }

    @Override
    public Catalog findById(int id) {
        for (Catalog catalog : listCatalogs) {
            if (catalog.getCatalogId() == id) {
                return catalog;
            }
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        listCatalogs.removeIf(catalog -> catalog.getCatalogId() == id);
        new Config<Catalog>().writeToFile(listCatalogs,Path.CATALOG_PATH);
    }

    @Override
    public List<Catalog> searchByName(String search) {
        List<Catalog> searchList = new ArrayList<>(listCatalogs);
        searchList.removeIf(catalog -> !catalog.getCatalogName().toLowerCase().trim().contains(search.toLowerCase().trim()));
        return searchList;
    }
}
