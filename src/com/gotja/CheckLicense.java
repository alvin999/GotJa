package com.gotja;

import net.emome.hamiapps.sdk.ForwardActivity;

public class CheckLicense extends ForwardActivity
{
	@SuppressWarnings("unchecked")
	@Override
	public Class getTargetActivity() 
	{
		return Welcome.class;
	}
	
	@Override
	public boolean passOnUnavailableDataNetwork()
	{
		return true;
	}
}