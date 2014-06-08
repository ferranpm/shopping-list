package com.shopping.list;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;

public class Ajuda extends Builder {

	private static String TEXT_AJUDA = ""
			+ "* A la pestanya \"Llista\" hi trobaràs la llista de la compra que tens pendent de fer. Per esborrar-la, fes click al botó de Menú i borra-la.\n"
			+ "* A la pestanya de \"Categories\" hi ha unes quantes categories, en pots afegir una pulsant la tecla de Menú. També pots afegir un producte a la llista"
			+ "fent click a la seva categoria i després al producte que hi vulguis afegir.\n"
			+ "* Fes un click llarg a qualsevol element d'una llista per eliminar-lo o editar-lo.\n"
			+ "* La llista esta ordenada per categories i en ordre alfabetic dintre de cada una d'elles.\n"
			+ "* Els elements de la llista en vermell són els que encara no s'han comprat, fes click per indicar que ja estan comprats.\n";

	public Ajuda(final Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);

		// set title
		alertDialogBuilder.setTitle("Ajuda");

		// set dialog message
		alertDialogBuilder.setMessage("Vols veure l'ajuda del programa?");
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setPositiveButton("Si",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Text(context);
						dialog.cancel();
					}
				});
		alertDialogBuilder.setNegativeButton("No",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

	}
	
	public static void Text(Context context) {
		AlertDialog.Builder ad = new AlertDialog.Builder(
				context);
		ad.setTitle("Ajuda");
		ad.setMessage(TEXT_AJUDA);
		ad.setNeutralButton("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
						dialog.cancel();
					}
				});
		ad.show();
	}

}
