package com.shopping.list;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

import android.widget.TextView;

public class AddItem extends Activity {

    private Manager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        manager = new Manager(this);

        Intent intent = getIntent();
        String item = intent.getStringExtra("item");

        TextView tv = (TextView)findViewById(R.id.addItem_TextView);
        EditText et = (EditText)findViewById(R.id.addItem_EditText);
        Button b = (Button)findViewById(R.id.addItem_Button);
        if (item.equals("Product")) {
            tv.setText("Afegir producte");
            final String categoria = intent.getStringExtra("categoria");
            b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    addProduct(v, categoria);
                }
            });
        } else if (item.equals("Category")) {
            tv.setText("Afegir categoria");
            b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    addCategoria(v);
                }
            });
        }

    }

    public void addProduct(View v, String categoria) {
        EditText et = (EditText)findViewById(R.id.addItem_EditText);
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

    public void addCategoria(View view) {
        EditText et = (EditText)findViewById(R.id.addItem_EditText);
        String categoria = et.getText().toString();
        String text = null;
        if (categoria.length() > 0) {
            if (manager.hasCategoria(categoria)) {
                text = "Ja existeix la categoria " + categoria + ".";
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            }
            else {
                manager.addCategoria(categoria);
                text = "S'ha afegit " + categoria + " a la llista de categories.";
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        else {
            text = "Introdueix el nom de la categoria.";
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        }
    }

    public void endActivity(View v) {
        finish();
    }

}
