package com.dekarrin.cashcounter.data;

import java.util.List;

import android.util.LongSparseArray;

/**
 * Holds all data for the program.
 */
public class DataManager {

	private Account root;
	
	private LongSparseArray<Account> idMap;
	
	public DataManager() {
		root = new Account("", Account.Type.OTHER);
		idMap = new LongSparseArray<Account>();
	}
	
	public void removeAccount(long id) {
		Account act = idMap.get(id);
		act.setParent(null);
		idMap.delete(id);
	}
	
	public Account getAccountForId(long id) {
		return idMap.get(id);
	}
	
	public void addAccount(Account act) {
		act.setParent(root);
		idMap.put(act.getInterfaceId(), act);
	}
	
	public List<Account> getTopLevelAccounts() {
		return root.getChildren();
	}
	
	/**
	 * Returns the number of accounts, not including the root account.
	 * 
	 * @return The number of accounts that are direct or indirect descendants
	 * of the root account.
	 */
	public int getAccountCount() {
		return root.size() - 1;
	}
	
}
