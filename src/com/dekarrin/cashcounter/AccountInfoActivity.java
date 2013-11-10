package com.dekarrin.cashcounter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dekarrin.cashcounter.data.Account;
import com.dekarrin.cashcounter.data.DataManager;

public class AccountInfoActivity extends Activity {

	private Button buttonAdd;
	private Button buttonCancel;
	private Button buttonDelete;
	private EditText nameField;
	private long accountId;
	private DataManager dm;
	public static final int RESULT_DELETED = Activity.RESULT_FIRST_USER + 1;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_info);
		dm = ((CashCountApplication) getApplicationContext()).getDataManager();
		buttonAdd = (Button)findViewById(R.id.button_add);
		buttonCancel = (Button)findViewById(R.id.button_cancel);
		buttonDelete = (Button)findViewById(R.id.button_delete);
		nameField = (EditText)findViewById(R.id.accountNameField);
		buttonAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AccountInfoActivity.this.add();
			}
		});
		buttonCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AccountInfoActivity.this.cancel();
			}
		});
		int method = getIntent().getIntExtra("com.dekarrin.cashcounter.METHOD", -1);
		if (method == AccountsActivity.EDIT_ACCOUNT) {
			accountId = getIntent().getLongExtra("com.dekarrin.cashcounter.ACCOUNT_ID", -1);
			Account act = dm.getAccountForId(accountId);
			buttonDelete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AccountInfoActivity.this.delete();
				}
			});
			nameField.setText(act.getName());
			buttonAdd.setText(R.string.form_label_button_confirm);
		} else {
			((ViewGroup)buttonDelete.getParent()).removeView(buttonDelete);
		}
	}
	
	private void cancel() {
		setResult(Activity.RESULT_CANCELED);
		finish();
	}
	
	private void delete() {
		Intent in = new Intent();
		in.putExtra("com.dekarrin.cashcounter.ACCOUNT_ID", accountId);
		setResult(RESULT_DELETED, in);
		finish();
	}
	
	private void add() {
		Intent in = new Intent();
		in.putExtra("com.dekarrin.cashcounter.ACCOUNT_ID", accountId);
		in.putExtra("com.dekarrin.cashcounter.ACCOUNT_NAME", nameField.getText().toString());
		setResult(Activity.RESULT_OK, in);
		finish();
	}
	
}
