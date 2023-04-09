package project.model.product;

import java.io.Serializable;

public class Product implements Serializable {
    private int productId;
    private String productName;
    private String description;
    private Catalog catalog;
    private int quantity;
    private float price;
    private boolean productStatus;

    public Product(int productId, String productName, String description, Catalog catalog, int quantity, float price, boolean productStatus) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.catalog = catalog;
        this.quantity = quantity;
        this.price = price;
        this.productStatus = true;
    }

    public Product(int productId, String productName, Catalog catalog, int quantity, float price) {
        this.productId = productId;
        this.productName = productName;
        this.catalog = catalog;
        this.quantity = quantity;
        this.price = price;
        this.productStatus = true;
    }

    public Product() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", catalog=" + catalog +
                ", quantity=" + quantity +
                ", price=" + price +
                ", productStatus=" + productStatus +
                '}';
    }
}
