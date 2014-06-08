package com.shopping.list;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class AlarmResponse extends Activity {

	public AlarmResponse() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("A comprar!");
		builder.setMessage("Cal anar a comprar");
		builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				AlarmResponse.this.finish();
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
