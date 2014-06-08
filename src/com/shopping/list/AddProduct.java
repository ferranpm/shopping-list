package com.shopping.list;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddProduct extends Activity {

    private Manager manager;
    private String categoria;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);
        manager = new Manager(this);
        Intent intent = getIntent();
        categoria = intent.getStringExtra("categoria");
    }

    public void addProduct(View v) {
        EditText et = (EditText)findViewById(R.id.edit_producte);
        String producte = et.getText().toString();
        String text = null;
        if (manager.hasProducte(categoria, producte)) {
            text = "Ja existeix aquest producte.";
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        }
        else {
            manager.addProducte(categoria, producte);
            text = "S'ha afegit " + producte + " a la categoria " + categoria + ".";
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void endActivity(View v) {
        finish();
    }

    //    @Override
    //    public boolean onCreateOptionsMenu(Menu menu) {
    //        getMenuInflater().inflate(R.menu.add_product, menu);
    //        return true;
    //    }
}
