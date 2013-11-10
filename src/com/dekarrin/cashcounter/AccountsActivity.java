package com.dekarrin.cashcounter;

import com.dekarrin.cashcounter.R;
import com.dekarrin.cashcounter.data.Account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class AccountsActivity extends Activity {
	
	static final int CREATE_ACCOUNT = 0;
	
	static final int EDIT_ACCOUNT = 1;
	
	private AccountListAdapter listAdapter;
	
	public void addAccount(MenuItem item) {
		Intent intent = new Intent(this, AccountInfoActivity.class);
		intent.putExtra("com.dekarrin.cashcounter.METHOD", CREATE_ACCOUNT);
		startActivityForResult(intent, CREATE_ACCOUNT);
	}
	
	protected void onActivityResult(int req, int result, Intent data) {
		if (req == CREATE_ACCOUNT) {
			if (result == Activity.RESULT_OK) {
				String name = data.getStringExtra("com.dekarrin.cashcounter.ACCOUNT_NAME");
				Account a = new Account(name, null);
				listAdapter.add(a);
			}
		} else if (req == EDIT_ACCOUNT) {
			if (result == AccountInfoActivity.RESULT_DELETED) {
				listAdapter.remove(data.getLongExtra("com.dekarrin.cashcounter.ACCOUNT_ID", -1));
			} else if (result == Activity.RESULT_OK) {
				String name = data.getStringExtra("com.dekarrin.cashcounter.ACCOUNT_NAME");
				Account a = new Account(name, null);
				long id = data.getLongExtra("com.dekarrin.cashcounter.ACCOUNT_ID", -1);
				listAdapter.modify(id, a);
			}
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accounts);
		listAdapter = new AccountListAdapter(this);
		ListView lv = (ListView) findViewById(R.id.accounts_list); 
		lv.setAdapter(listAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				Intent intent = new Intent(AccountsActivity.this, AccountSummaryActivity.class);
				intent.putExtra("com.dekarrin.cashcounter.ACCOUNT_ID", id);
				startActivity(intent);
			}
		});
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int pos, long id) {
				Intent intent = new Intent(AccountsActivity.this, AccountInfoActivity.class);
				intent.putExtra("com.dekarrin.cashcounter.ACCOUNT_ID", id);
				intent.putExtra("com.dekarrin.cashcounter.METHOD", EDIT_ACCOUNT);
				startActivityForResult(intent, EDIT_ACCOUNT);
				return true;
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accounts, menu);
		return true;
	}
}
