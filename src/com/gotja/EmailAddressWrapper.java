package com.gotja;

import android.widget.CheckBox;

public class EmailAddressWrapper {
	public String name;
	public String email;
	public CheckBox ckbox;
	
	public EmailAddressWrapper(){
		super();
	}

	public EmailAddressWrapper(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}
}
