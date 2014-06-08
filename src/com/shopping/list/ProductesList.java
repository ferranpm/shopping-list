package com.shopping.list;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ListActivity;
import android.content.Intent;

public class ProductesList extends ListActivity {

	private Manager manager;
	private String categoria;
	private ArrayList<String> productes;
	private ArrayAdapter<String> adapter;
	final private int PRODUCTE_ADDED_CODE = 1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productes_list);
        manager = new Manager(this);
        Intent intent = getIntent();
        categoria = intent.getStringExtra("categoria");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        productes = manager.getProductes(categoria);
        for (int i = 0; i < productes.size(); i++) {
        	adapter.add(productes.get(i));
        }
        setListAdapter(adapter);
        registerForContextMenu(getListView());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.productes_list, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	super.onOptionsItemSelected(item);
    	switch (item.getItemId()) {
    	case R.id.add_product:
    		Intent intent = new Intent(this, AddProduct.class);
    		intent.putExtra("categoria", categoria);
    		startActivityForResult(intent, PRODUCTE_ADDED_CODE);
    		break;
    	}
    	return true;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	switch (requestCode) {
    	case PRODUCTE_ADDED_CODE:
    		productes = manager.getProductes(categoria);
    		adapter.clear();
            for (int i = 0; i < productes.size(); i++) {
            	adapter.add(productes.get(i));
            }
            setListAdapter(adapter);
    		break;
    	}
    }
    
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		TextView tv = (TextView)v;
		String producte = tv.getText().toString();
		String text = null;
		if (manager.llistaHas(producte)) {
			text = "Ja hi ha " + producte + " a la llista.";
			Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
		} else {
			text = "S'ha afegit " + producte + " a la llista.";
			Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
			manager.addItem(producte);
			finish();
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.add("Eliminar");
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		String producte = adapter.getItem(info.position);
		manager.deleteFromProductes(producte);
        adapter.clear();
        productes = manager.getProductes(categoria);
        for (int i = 0; i < productes.size(); i++) {
        	adapter.add(productes.get(i));
        }
        setListAdapter(adapter);
		return super.onContextItemSelected(item);
	}
	
}
