package com.shopping.list;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCategory extends Activity {

    private Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_category);
        manager = new Manager(this);
    }

    public void addCategoria(View view) {
        EditText et = (EditText)findViewById(R.id.edit_categoria);
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

    public void endActivity(View view) {
        finish();
    }
}
