package com.shopping.list;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Manager extends SQLiteOpenHelper {

	private Context context;

	public Manager(Context context) {
		super(context, "ShoppingList", null, 1);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		new Ajuda(context);
		String CREATE_LLISTA_TABLE = "CREATE TABLE llista ("
				+ "producte VARCHAR(30), categoria VARCHAR(30), tatxat BOOLEAN)";
		String CREATE_CATEGORIES_TABLE = "CREATE TABLE categories (categoria VARCHAR(30))";
		String CREATE_PRODUCTES_TABLE = "CREATE TABLE productes ("
				+ "producte VARCHAR(30)," + "categoria VARCHAR(30),"
				+ "FOREIGN KEY (categoria) REFERENCES categories(categoria),"
				+ "PRIMARY KEY (producte))";
		String CREATE_ALARM_TABLE = "CREATE TABLE alarma (time VARCHAR(30))";
		db.execSQL(CREATE_LLISTA_TABLE);
		db.execSQL(CREATE_CATEGORIES_TABLE);
		db.execSQL(CREATE_PRODUCTES_TABLE);
		db.execSQL(CREATE_ALARM_TABLE);

		db.execSQL("INSERT INTO categories (categoria) VALUES ('Fruita')");
		db.execSQL("INSERT INTO productes (categoria, producte) VALUES ('Fruita','Meló')");
		db.execSQL("INSERT INTO productes (categoria, producte) VALUES ('Fruita','Kiwi')");
		db.execSQL("INSERT INTO productes (categoria, producte) VALUES ('Fruita','Peres')");
		db.execSQL("INSERT INTO productes (categoria, producte) VALUES ('Fruita','Síndria')");
		
		db.execSQL("INSERT INTO categories (categoria) VALUES ('Carn i Peix')");
		db.execSQL("INSERT INTO productes (categoria, producte) VALUES ('Carn i Peix','Llom')");
		db.execSQL("INSERT INTO productes (categoria, producte) VALUES ('Carn i Peix','Pernil salat')");
		db.execSQL("INSERT INTO productes (categoria, producte) VALUES ('Carn i Peix','Xoriç')");
		db.execSQL("INSERT INTO productes (categoria, producte) VALUES ('Carn i Peix','Pollastre')");
		db.execSQL("INSERT INTO productes (categoria, producte) VALUES ('Carn i Peix','Pernil dolç')");
		
		db.execSQL("INSERT INTO categories (categoria) VALUES ('Làctics')");
		db.execSQL("INSERT INTO productes (categoria, producte) VALUES ('Làctics','Llet')");
		db.execSQL("INSERT INTO productes (categoria, producte) VALUES ('Làctics','Yogur')");
		db.execSQL("INSERT INTO productes (categoria, producte) VALUES ('Làctics','Formatge')");
		db.execSQL("INSERT INTO productes (categoria, producte) VALUES ('Làctics','Batut')");
		db.execSQL("INSERT INTO productes (categoria, producte) VALUES ('Làctics','Natilles')");
		
		db.execSQL("INSERT INTO categories (categoria) VALUES ('Verdura')");
		db.execSQL("INSERT INTO productes (categoria, producte) VALUES ('Verdura','Mongetes')");
		db.execSQL("INSERT INTO productes (categoria, producte) VALUES ('Verdura','Escarola')");
		db.execSQL("INSERT INTO productes (categoria, producte) VALUES ('Verdura','Pastanaga')");
		db.execSQL("INSERT INTO productes (categoria, producte) VALUES ('Verdura','Pebrot')");
		db.execSQL("INSERT INTO productes (categoria, producte) VALUES ('Verdura','Bròquil')");
		
		db.execSQL("INSERT INTO categories (categoria) VALUES ('Neteja')");
		db.execSQL("INSERT INTO productes (categoria, producte) VALUES ('Neteja','Lleixiu')");
		db.execSQL("INSERT INTO productes (categoria, producte) VALUES ('Neteja','Suavitzant')");
		db.execSQL("INSERT INTO productes (categoria, producte) VALUES ('Neteja','Sabó de mans')");
		db.execSQL("INSERT INTO productes (categoria, producte) VALUES ('Neteja','Xampú')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS productes");
		db.execSQL("DROP TABLE IF EXISTS categories");
		db.execSQL("DROP TABLE IF EXISTS llista");
		onCreate(db);
	}

	public void addItem(String producte) {
		SQLiteDatabase db = null;
		
		db = this.getReadableDatabase();
		String query = "SELECT categoria FROM productes WHERE producte='" + producte + "'";
		String categoria = null;
		Cursor cursor = db.rawQuery(query, null);
		if (cursor != null) {
			if (cursor.moveToFirst())
				categoria = cursor.getString(0);
		}
		db.close();
		
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("producte", producte);
		values.put("categoria", categoria);
		values.put("tatxat", false);
		db.insert("llista", null, values);
		db.close();
	}

	public void addCategoria(String categoria) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("categoria", categoria);
		db.insert("categories", null, values);
		db.close();
	}

	public void addProducte(String categoria, String producte) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("producte", producte);
		values.put("categoria", categoria);
		db.insert("productes", null, values);
		db.close();
	}

	public ArrayList<String> getLlista() {
		ArrayList<String> list = new ArrayList<String>();
		String query = "SELECT producte FROM llista ORDER BY categoria, producte";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			do {
				String item = cursor.getString(0);
				list.add(item);
			} while (cursor.moveToNext());
		}
		db.close();
		return list;
	}

	public ArrayList<Boolean> getStriked() {
		ArrayList<Boolean> list = new ArrayList<Boolean>();
		String selectQuery = "SELECT tatxat FROM llista ORDER BY categoria, producte";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Boolean item = cursor.getInt(0) > 0;
				list.add((Boolean) item);
			} while (cursor.moveToNext());
		}
		db.close();
		return list;
	}

	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		String selectQuery = "SELECT categoria FROM categories ORDER BY categoria";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				String item = cursor.getString(0);
				list.add(item);
			} while (cursor.moveToNext());
		}
		db.close();
		return list;
	}

	public ArrayList<String> getProductes(String categoria) {
		ArrayList<String> list = new ArrayList<String>();
		String selectQuery = "SELECT producte FROM productes WHERE categoria='"
				+ categoria + "' ORDER BY producte";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				String item = cursor.getString(0);
				list.add(item);
			} while (cursor.moveToNext());
		}
		db.close();
		return list;
	}

	public boolean llistaHas(String item) {
		ArrayList<String> list = getLlista();
		for (int i = 0; i < list.size(); i++)
			if (list.get(i).equals(item))
				return true;
		return false;
	}

	public boolean hasCategoria(String categoria) {
		String selectQuery = "SELECT * " + "FROM categories "
				+ "WHERE UPPER(categoria)=UPPER('" + categoria + "')";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		boolean ret = false;
		if (cursor != null)
			if (cursor.moveToFirst())
				ret = true;
		db.close();
		return ret;
	}

	public boolean hasProducte(String categoria, String producte) {
		String selectQuery = "SELECT * " + "FROM productes "
				+ "WHERE UPPER(producte)=UPPER('" + producte + "')";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		boolean ret = false;
		if (cursor != null)
			if (cursor.moveToFirst())
				ret = true;
		db.close();
		return ret;
	}

	public void deleteList() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("llista", null, null);
		db.close();
	}

	public void deleteFromList(String producte) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("llista", "producte='" + producte + "'", null);
		db.close();
	}

	public void tatxar(String producte) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("tatxat", true);
		db.update("llista", values, "producte='" + producte + "'", null);
		db.close();
	}

	public void destatxar(String producte) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("tatxat", false);
		db.update("llista", values, "producte='" + producte + "'", null);
		db.close();
	}

	public boolean tatxat(String producte) {
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT tatxat FROM llista WHERE producte='" + producte + "'";
		Cursor cursor = db.rawQuery(query, null);
		boolean ret = false;
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				ret = cursor.getInt(0) > 0;
			}
		}
		db.close();
		return ret;
	}

	public void deleteFromProductes(String producte) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("productes", "producte='" + producte + "'", null);
		db.close();
	}

	public void deleteCategoria(String categoria) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("productes", "categoria='" + categoria+ "'", null);
		db.delete("categories", "categoria='" + categoria+ "'", null);
		db.close();
	}

	public void setAlarm(String time) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("alarma", null, null);
		ContentValues values = new ContentValues();
		values.put("time", time);
		db.insert("alarma", null, values);
		db.close();
	}

	public long getAlarma() {
		SQLiteDatabase db = this.getReadableDatabase();
		String query = "SELECT time FROM alarma";
		Cursor cursor = db.rawQuery(query, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				String time = cursor.getString(0);
				return Long.parseLong(time);
			}
		}
		return -1;
	}	

}
