package com.example.vivaapp_mobile.model.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.vivaapp_mobile.model.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    private Context context;
    private DatabaseHelper dbHelper;

    public OrderRepository(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    // Sipariş ekleme işlemi
    public long addOrder(int userId, int productId, int quantity, String orderDate) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_USER_ID_FK, userId);
        cv.put(DatabaseHelper.COLUMN_PRODUCT_ID_FK, productId);
        cv.put(DatabaseHelper.COLUMN_QUANTITY, quantity);
        cv.put(DatabaseHelper.COLUMN_ORDER_DATE, orderDate);
        long result = db.insert(DatabaseHelper.TABLE_ORDERS, null, cv);
        db.close();
        return result;
    }

    // Tüm siparişleri okuma işlemi
    public List<OrderItem> getAllOrders() {
        List<OrderItem> orderList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_ORDERS, null);

        if (cursor != null && cursor.moveToFirst()) {
            int orderIdIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_ID);
            int userIdIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_ID_FK);
            int productIdIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCT_ID_FK);
            int quantityIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_QUANTITY);
            int orderDateIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_DATE);

            do {
                int orderId = cursor.getInt(orderIdIndex);
                int userId = cursor.getInt(userIdIndex);
                int productId = cursor.getInt(productIdIndex);
                int quantity = cursor.getInt(quantityIndex);
                String orderDate = cursor.getString(orderDateIndex);

                OrderItem order = new OrderItem(orderId, userId, productId, quantity, orderDate);
                orderList.add(order);
            } while (cursor.moveToNext());

            cursor.close();
        } else {
            Log.d("DatabaseHelper", "No orders found in the database.");
        }

        db.close();
        return orderList;
    }


    // Diğer sipariş işlemleri buraya eklenecek (güncelleme, silme vs.)
}

