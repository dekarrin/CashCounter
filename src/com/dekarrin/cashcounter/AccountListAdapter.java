package com.dekarrin.cashcounter;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.dekarrin.cashcounter.data.Account;
import com.dekarrin.cashcounter.data.DataManager;

public class AccountListAdapter implements ExpandableListAdapter {
	
	private Set<DataSetObserver> dataListeners;
	private Account parentAccount;
	
	public AccountListAdapter(Account parentAccount) {
		dataListeners = new HashSet<DataSetObserver>();
		this.parentAccount = parentAccount;
	}
	
	public void notifyDataChange() {
		for (DataSetObserver dso : dataListeners) {
			dso.onChanged();
		}
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
	public void unregisterDataSetObserver(DataSetObserver dso) {
		dataListeners.remove(dso);
	}
	
	@Override
	public Account getChild(int groupPosition, int childPosition) {
		return parentAccount.get(groupPosition).get(childPosition);
	}
	
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return getChild(groupPosition, childPosition).getUiId();
	}
	
	@Override
	public int getChildrenCount(int groupPosition) {
		return getGroup(groupPosition).getChildrenCount();
	}
	
	@Override
	public long getCombinedChildId(long groupId, long childId) {
		return childId;
	}
	
	@Override
	public long getCombinedGroupId(long groupId) {
		return groupId;
	}
	
	@Override
	public Account getGroup(int groupPosition) {
		return parentAccount.get(groupPosition);
	}
	
	@Override
	public int getGroupCount() {
		return parentAccount.getChildrenCount();
	}
	
	@Override
	public long getGroupId(int position) {
		return getGroup(position).getUiId();
	}
	
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		if (groupPosition >= getGroupCount()) {
			throw new ArrayIndexOutOfBoundsException(groupPosition);
		} else if (childPosition >= getChildrenCount(groupPosition)) {
			throw new ArrayIndexOutOfBoundsException(childPosition);
		} else {
			return true;
		}
	}
	
	@Override
	public void onGroupExpanded(int groupPosition) {
		
	}
	
	@Override
	public void onGroupCollapsed(int groupPosition) {
		
	}
	
	@Override
	public View getGroupView(int position, boolean isExpanded, View convertView,
			ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater inf = LayoutInflater.from(parent.getContext());
			v = inf.inflate(R.layout.list_element, null);
		}
		Account a = parentAccount.get(position);
		if (a != null) {
			TextView accountName = (TextView) v.findViewById(R.id.account_name);
			TextView accountTotal = (TextView) v.findViewById(R.id.account_total);
			TextView accountArrow = (TextView) v.findViewById(R.id.account_arrow);
			accountName.setText(a.getName());
			accountTotal.setText(text);
		}
		return v;
	}
	
	@Override
	public boolean hasStableIds() {
		return true;
	}
	
	@Override
	public boolean isEmpty() {
		return (getGroupCount() == 0);
	}
}
