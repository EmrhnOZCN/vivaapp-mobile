    package com.example.vivaapp_mobile.model.repository;

    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.util.Log;
    import android.widget.Toast;

    import androidx.annotation.Nullable;

    import com.example.vivaapp_mobile.model.Product;
    import com.example.vivaapp_mobile.model.User;

    public class DatabaseHelper extends SQLiteOpenHelper {

        private Context context;
        private static final String DATABASE_NAME = "viva.db";
        private static final int DATABASE_VERSION = 2;

        // Kullanıcı Tablosu
         static final String TABLE_USERS = "users";
         static final String COLUMN_USER_ID = "id";
         static final String COLUMN_AD = "ad";
         static final String COLUMN_SOYAD = "soyad";
         static final String COLUMN_DOGUM_TARIHI = "dogum_tarihi";
         static final String COLUMN_TELEFON_NO = "telefon_no";
         static final String COLUMN_EPOSTA = "eposta";
         static final String COLUMN_SIFRE = "sifre";

        // Ürün Tablosu
         static final String TABLE_PRODUCTS = "product";
         static final String COLUMN_PRODUCT_ID = "id";
         static final String COLUMN_IMAGE_RESOURCE = "imageResource";
         static final String COLUMN_NAME = "name";
         static final String COLUMN_PRICE = "price";
         static final String COLUMN_CATEGORY_NAME = "categoryName";

        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("DatabaseHelper", "onCreate() called");
            // Kullanıcı tablosu oluşturma sorgusu
            String createUserTableQuery = "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_AD + " TEXT, " +
                    COLUMN_SOYAD + " TEXT, " +
                    COLUMN_DOGUM_TARIHI + " TEXT, " +
                    COLUMN_TELEFON_NO + " TEXT, " +
                    COLUMN_EPOSTA + " TEXT, " +
                    COLUMN_SIFRE + " TEXT);";
            db.execSQL(createUserTableQuery);

            // Ürün tablosu oluşturma sorgusu
            String createProductTableQuery = "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                    COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_IMAGE_RESOURCE + " TEXT, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_PRICE + " TEXT NOT NULL, " +
                    COLUMN_CATEGORY_NAME + " TEXT);";
            db.execSQL(createProductTableQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Veritabanı güncelleme işlemi

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
            onCreate(db);
        }

        // Kullanıcı ekleme işlemi
        public long addUser(User user) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_AD, user.getAd());
            cv.put(COLUMN_SOYAD, user.getSoyad());
            cv.put(COLUMN_DOGUM_TARIHI, user.getDogumTarihi());
            cv.put(COLUMN_TELEFON_NO, user.getTelefonNo());
            cv.put(COLUMN_EPOSTA, user.getEposta());
            cv.put(COLUMN_SIFRE, user.getSifre());
            long result = db.insert(TABLE_USERS, null, cv);
            if (result == -1) {
                Toast.makeText(context, "Başarısız oldu", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Başarıyla eklendi", Toast.LENGTH_SHORT).show();
            }
            db.close();
            return result;
        }

        // Kullanıcı bilgilerini almak için metod
        public User getUserByEmail(String email) {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EPOSTA + "=?";
            Cursor cursor = db.rawQuery(query, new String[]{email});

            if (cursor != null && cursor.moveToFirst()) {
                int adIndex = cursor.getColumnIndex(COLUMN_AD);
                int soyadIndex = cursor.getColumnIndex(COLUMN_SOYAD);
                int epostaIndex = cursor.getColumnIndex(COLUMN_EPOSTA);

                // Hata kontrolü ekleyelim
                if (adIndex != -1 && soyadIndex != -1 && epostaIndex != -1) {
                    String ad = cursor.getString(adIndex);
                    String soyad = cursor.getString(soyadIndex);
                    String eposta = cursor.getString(epostaIndex);
                    cursor.close();
                    return new User(ad, soyad, eposta);
                } else {
                    cursor.close();
                    Log.e("DatabaseHelper", "Sütun adları bulunamadı.");
                    return null;
                }
            }

            if (cursor != null) {
                cursor.close();
            }
            return null;
        }

        // Ürün ekleme işlemi
        public long addProduct(Product product) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_IMAGE_RESOURCE,  product.getImageResource());
            cv.put(COLUMN_NAME, product.getName());
            cv.put(COLUMN_PRICE, product.getPrice());
            cv.put(COLUMN_CATEGORY_NAME, product.getCategoryName());
            long result = db.insert(TABLE_PRODUCTS, null, cv);
            if (result == -1) {
                Toast.makeText(context, "Ürün ekleme başarısız oldu", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Ürün başarıyla eklendi", Toast.LENGTH_SHORT).show();
            }
            db.close();
            return result;
        }

        // Tüm ürünleri okuma işlemi
        public Cursor readAllProducts() {
            String query = "SELECT * FROM " + TABLE_PRODUCTS;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = null;
            if (db != null) {
                cursor = db.rawQuery(query, null);
            }
            return cursor;
        }

        // Kullanıcı doğrulama işlemi
        public boolean checkUser(String eposta, String sifre) {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EPOSTA + "=? AND " + COLUMN_SIFRE + "=?";
            Cursor cursor = db.rawQuery(query, new String[]{eposta, sifre});
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
            return false;
        }
        public Cursor readAllProductsByCategory(String category) {
            SQLiteDatabase db = this.getReadableDatabase();
            Log.d("DatabaseHelper", "Requested category: " + category); // Kategori bilgisini logla
            String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_CATEGORY_NAME + "=?";
            Cursor cursor = db.rawQuery(query, new String[]{category});

            // Eğer cursor boş ise ve veri bulunamadıysa null döndür.
            if (cursor == null || cursor.getCount() == 0) {
                return null;
            }

            return cursor;
        }



    }
