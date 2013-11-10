package com.dekarrin.cashcounter.data;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * An account in the financial system. Includes doubly-linked tree.
 */
public class Account {
	
	private NumberFormat currencyFormatter;
	
	public static enum Type {
		ASSET,
		LIABILITY,
		INCOME,
		EXPENSE,
		EQUITY,
		OTHER
	}

	private static int instanceCount = 0;

	private List<Account> children = new ArrayList<Account>();
	
	private Account parent = null;
	
	private String name = "";
	
	private String description = "";
	
	//private Currency currency;
	
	//private CommodityFraction fraction;
	
	//private Color color;
	
	private String notes = "";
	
	private boolean placeholder = false;
	
	private Type type = null;
	
	private UUID id = UUID.randomUUID();
	
	private long interfaceId = Account.instanceCount++;
	
	public Account(String name, Type type) {
		this.name = name;
		this.type = type;
		currencyFormatter = NumberFormat.getCurrencyInstance();
	}
	
	public boolean hasChildren() {
		return (children.size() > 0);
	}
	
	public Account get(int position) {
		return children.get(position);
	}
	
	public void copyFrom(Account toCopyFrom) {
		this.name = toCopyFrom.name;
	}
	
	public long getUiId() {
		return interfaceId;
	}
	
	public int getChildrenCount() {
		return children.size();
	}
	
	/**
	 * Formats currency amounts depending on the type of currency this account
	 * uses.
	 * @param amount
	 * @return
	 */
	public String format(int amount) {
		Currency c = Currency.getInstance(Locale.getDefault());
		int frac = c.getDefaultFractionDigits();
		double dAmount = amount / Math.pow(10, frac);
		return currencyFormatter.format(dAmount);
	}
	
	/**
	 * Counts this account and all child accounts under it.
	 * @return
	 */
	public int size() {
		int s = 1;
		for (Account a : children) {
			s += a.size();
		}
		return s;
	}
	
	public String getUuid() {
		return id.toString();
	}
	
	public void setUuid(String uuid) {
		id = UUID.fromString(uuid);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public boolean isPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(boolean placeholder) {
		this.placeholder = placeholder;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public void setParent(Account parent) {
		if (this.parent != null) {
			this.parent.removeChild(this);
		}
		if (parent != null) {
			parent.addChild(this);
		}
		this.parent = parent;
	}
	
	public Account getParent() {
		return parent;
	}
	
	/**
	 * Returns an unmodifiable list of children.
	 * @return
	 */
	public List<Account> getChildren() {
		return Collections.unmodifiableList(children);
	}
	
	private void removeChild(Account child) {
		children.remove(child);
	}
	
	private void addChild(Account child) {
		children.add(child);
	}
	
}
