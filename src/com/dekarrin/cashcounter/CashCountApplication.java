package com.dekarrin.cashcounter;

import com.dekarrin.cashcounter.data.Account;
import com.dekarrin.cashcounter.data.DataManager;

import android.app.Application;

public class CashCountApplication extends Application {

	private DataManager dm;

	public CashCountApplication() {
		super();
		dm = new DataManager();
		Account assets = new Account("Assets", Account.Type.ASSET);
		Account liabs = new Account("Liabilities", Account.Type.LIABILITY);
		Account eq = new Account("Equity", Account.Type.EQUITY);
		dm.addAccount(assets);
		dm.addAccount(liabs);
		dm.addAccount(eq);
	}
	
	public DataManager getDataManager() {
		return dm;
	}
	
}
