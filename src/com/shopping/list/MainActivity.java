package com.shopping.list;

import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class MainActivity extends Activity {

    private TabHost th;
    private Manager manager;
    private ListView llista_view;
    private LlistaAdapter llista_adapter;
    private ListView categories_view;
    private ArrayAdapter<String> categories_adapter;
    final int PRODUCTES_LIST_CODE = 1;
    final int ADD_CATEGORY_CODE = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new Manager(this);

        th = (TabHost) findViewById(R.id.tabhost);
        th.setup();

        TabSpec llista_spec = th.newTabSpec("Llista");
        llista_spec.setIndicator("Llista");
        llista_spec.setContent(R.id.llista_tab);
        llista_adapter = new LlistaAdapter(this);
        llista_view = (ListView) findViewById(R.id.llista_view);
        registerForContextMenu(llista_view);
        llista_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String producte = llista_adapter.getItem(position);
                LinearLayout ll = (LinearLayout)view;
                if (manager.tatxat(producte)) {
                    manager.destatxar(producte);
                    ll.setBackgroundColor(Color.RED);
                }
                else {
                    manager.tatxar(producte);
                    ll.setBackgroundColor(Color.GREEN);
                }
            }
        });
        th.addTab(llista_spec);

        TabSpec categories_spec = th.newTabSpec("Productes");
        categories_spec.setIndicator("Categories");
        categories_spec.setContent(R.id.productes_tab);
        categories_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);
        categories_view = (ListView) findViewById(R.id.categories_view);
        setupCategories();
        registerForContextMenu(categories_view);
        categories_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
                TextView tv = (TextView) view;
                String categoria = tv.getText().toString();
                Intent intent = new Intent(getApplicationContext(), ProductesList.class);
                intent.putExtra("categoria", categoria);
                startActivityForResult(intent, PRODUCTES_LIST_CODE);
            }
        });
        th.addTab(categories_spec);

        th.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                if (tabId.equals("Llista")) {
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        setupLlista();
    }

    private void setupLlista() {
        llista_adapter.update();
        llista_view.setAdapter(llista_adapter);
    }

    private void setupCategories() {
        categories_adapter.clear();
        ArrayList<String> array = manager.getCategories();
        for (int i = 0; i < array.size(); i++)
            categories_adapter.add(array.get(i));
        categories_view.setAdapter(categories_adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //		if (v.getId() == categories_view.getId())
        //			menu.add(0, v.getId(), 0, "sdklj");
        menu.add(0, v.getId(), 0, "Eliminar");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
        if (item.getItemId() == categories_view.getId()) {
            String categoria = categories_adapter.getItem(info.position);
            manager.deleteCategoria(categoria);
            setupCategories();
        }
        else {
            String producte = llista_adapter.getItem(info.position);
            manager.deleteFromList(producte);
            setupLlista();
        }
        return false;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        menu.clear();
        switch (th.getCurrentTab()) {
            case 0:
                inflater.inflate(R.menu.llista, menu);
                break;
            case 1:
                inflater.inflate(R.menu.productes, menu);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_list:
                manager.deleteList();
                setupLlista();
                break;
            case R.id.add_category:
                Intent intent = new Intent(this, AddItem.class);
                intent.putExtra("item", "Category");
                startActivityForResult(intent, ADD_CATEGORY_CODE);
                break;
            case R.id.menu_help:
                Ajuda.Text(this);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ADD_CATEGORY_CODE:
                setupCategories();
                break;
            case PRODUCTES_LIST_CODE:
                setupLlista();
                break;
        }
    }
}
