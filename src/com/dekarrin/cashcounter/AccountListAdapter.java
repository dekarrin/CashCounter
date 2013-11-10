package com.dekarrin.cashcounter;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.dekarrin.cashcounter.data.Account;
import com.dekarrin.cashcounter.data.DataManager;

public class AccountListAdapter implements ListAdapter {
	
	private Set<DataSetObserver> dataListeners;
	private DataManager dm;
	
	public AccountListAdapter(Context context) {
		dataListeners = new HashSet<DataSetObserver>();
		dm = ((CashCountApplication)context.getApplicationContext()).getDataManager();
	}
	
	private void notifyDataChange() {
		for (DataSetObserver dso : dataListeners) {
			dso.onChanged();
		}
	}
	
	public void add(Account item) {
		dm.addAccount(item);
		notifyDataChange();
	}
	
	public void remove(long id) {
		dm.removeAccount(id);
		notifyDataChange();
	}
	
	public void modify(long id, Account toCopyFrom) {
		Account a = dm.getAccountForId(id);
		a.copyFrom(toCopyFrom);
		notifyDataChange();
	}
	
	@Override
	public void registerDataSetObserver(DataSetObserver dso) {
		dataListeners.add(dso);
	}
	
	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}
	
	@Override
	public int getViewTypeCount() {
		return 1;
	}
	
	@Override
	public boolean isEnabled(int position) {
		if (position >= getCount()) {
			throw new ArrayIndexOutOfBoundsException(position);
		} else {
			return true;
		}
	}
	
	@Override
	public void unregisterDataSetObserver(DataSetObserver dso) {
		dataListeners.remove(dso);
	}
	
	@Override
	public int getCount() {
		return dm.getAccountCount();
	}
	
	@Override
	public Account getItem(int position) {
		return dm.getTopLevelAccounts().get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return getItem(position).getInterfaceId();
	}
	
	@Override
	public int getItemViewType(int position) {
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater inf = LayoutInflater.from(parent.getContext());
			v = inf.inflate(R.layout.list_element, null);
		}
		Account a = dm.getTopLevelAccounts().get(position);
		if (a != null) {
			((TextView) v).setText(a.getName());
		}
		return v;
	}
	
	@Override
	public boolean hasStableIds() {
		return true;
	}
	
	@Override
	public boolean isEmpty() {
		return getCount() == 0;
	}
}
