package com.example.eval.api;

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface Eval
{
	public double eval(String expression) throws Exception;
}
