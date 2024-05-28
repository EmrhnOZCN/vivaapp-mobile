package com.example.vivaapp_mobile.model.repository;

import android.database.Cursor;
import android.util.Log;

import com.example.vivaapp_mobile.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private DatabaseHelper databaseHelper;

    public ProductRepository(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    // Tüm ürünleri al
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        Cursor cursor = databaseHelper.readAllProducts();
        if (cursor != null) {
            int columnIndexId = cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCT_ID);
            int columnIndexImage = cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_RESOURCE);
            int columnIndexName = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
            int columnIndexPrice = cursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE);
            int columnIndexCategory = cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_NAME);

            if (cursor.moveToFirst()) {
                do {
                    // Sütun indekslerini kontrol et
                    if (columnIndexId != -1 && columnIndexImage != -1 && columnIndexName != -1
                            && columnIndexPrice != -1 && columnIndexCategory != -1) {
                        // Veritabanından gelen her satırı kullanarak ürün oluştur
                        int id = cursor.getInt(columnIndexId);
                        int imageResource = cursor.getInt(columnIndexImage);
                        String name = cursor.getString(columnIndexName);
                        double price = cursor.getDouble(columnIndexPrice);
                        String categoryName = cursor.getString(columnIndexCategory);
                        Product product = new Product(imageResource, name, price, categoryName);
                        productList.add(product);
                    } else {
                        // Bir veya daha fazla sütun indeksi -1 ise, hata durumunu işle
                        Log.e("getColumnIndexError", "One or more column indices are -1");
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return productList;
    }

    // Belirli bir kategoriye göre ürünleri al
    public List<Product> getProductsByCategory(String category) {
        List<Product> productList = new ArrayList<>();
        Cursor cursor = databaseHelper.readAllProductsByCategory(category); // Kategoriye göre ürünleri oku
        if (cursor != null) {
            int columnIndexId = cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCT_ID);
            int columnIndexImage = cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_RESOURCE);
            int columnIndexName = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
            int columnIndexPrice = cursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE);
            int columnIndexCategory = cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_NAME);

            if (cursor.moveToFirst()) {
                do {
                    // Sütun indekslerini kontrol et
                    if (columnIndexId != -1 && columnIndexImage != -1 && columnIndexName != -1
                            && columnIndexPrice != -1 && columnIndexCategory != -1) {
                        // Veritabanından gelen her satırı kullanarak ürün oluştur
                        int id = cursor.getInt(columnIndexId);
                        int imageResource = cursor.getInt(columnIndexImage);
                        String name = cursor.getString(columnIndexName);
                        double price = cursor.getDouble(columnIndexPrice);
                        String categoryName = cursor.getString(columnIndexCategory);
                        Product product = new Product(imageResource, name, price, categoryName);
                        productList.add(product);
                    } else {
                        // Bir veya daha fazla sütun indeksi -1 ise, hata durumunu işle
                        Log.e("getColumnIndexError", "One or more column indices are -1");
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return productList;
    }

    // Method to get products by name
    public List<Product> getProductsByName(String name) {
        List<Product> productList = new ArrayList<>();
        Cursor cursor = databaseHelper.readProductsByName(name);
        if (cursor != null) {
            int columnIndexId = cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCT_ID);
            int columnIndexImage = cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_RESOURCE);
            int columnIndexName = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
            int columnIndexPrice = cursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE);
            int columnIndexCategory = cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY_NAME);

            if (cursor.moveToFirst()) {
                do {
                    if (columnIndexId != -1 && columnIndexImage != -1 && columnIndexName != -1
                            && columnIndexPrice != -1 && columnIndexCategory != -1) {
                        int id = cursor.getInt(columnIndexId);
                        int imageResource = cursor.getInt(columnIndexImage);
                        String productName = cursor.getString(columnIndexName);
                        double price = cursor.getDouble(columnIndexPrice);
                        String categoryName = cursor.getString(columnIndexCategory);
                        Product product = new Product(imageResource, productName, price, categoryName);
                        productList.add(product);
                    } else {
                        Log.e("getColumnIndexError", "One or more column indices are -1");
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return productList;
    }




}
