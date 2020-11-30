package com.example.eval.simple.provider;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceRegistration;

import com.example.eval.api.Eval;

import aQute.launchpad.Launchpad;
import aQute.launchpad.LaunchpadBuilder;
import aQute.launchpad.Service;
import aQute.launchpad.junit.LaunchpadRunner;

@RunWith(LaunchpadRunner.class)
public class EvalImplTest
{
	static LaunchpadBuilder builder = new LaunchpadBuilder().bndrun("bnd.bnd");

	interface Foo
	{
	};

	@Service(service = Eval.class)
	EvalImpl eval;

	@Service
	Launchpad launchpad;

	@Test
	public void simple() throws InvalidSyntaxException
	{
		Eval eval = new EvalImpl();
		assertNotNull(eval);
		launchpad.report();
	}

	@Test
	public void bundles() throws Exception
	{
		Bundle b = launchpad.bundle().header("FooBar", "1").install();
		String string = b.getHeaders().get("FooBar");
		assertTrue(string.equals("1"));
	}

	@Test
	public void services() throws Exception
	{
		ServiceRegistration<Foo> register = launchpad.register(Foo.class, new Foo()
		{
		});
		Optional<Foo> service = launchpad.waitForService(Foo.class, 100);
		assertTrue(service.isPresent());
		register.unregister();
		service = launchpad.waitForService(Foo.class, 100);
		assertFalse(service.isPresent());
	}

	@Test
	public void inject() throws Exception
	{
		ServiceRegistration<Foo> register = launchpad.register(Foo.class, new Foo()
		{
		});
		class I
		{
			@Service
			Foo foo;
			@Service
			Bundle bundles[];
			@Service
			BundleContext context;
		}
		I inject = new I();
		launchpad.inject(inject);
		assertTrue(inject.bundles.length != 0);
		assertTrue(inject.foo != null);
		assertTrue(inject.context != null);
		register.unregister();
	}

	@Test
	public void activator() throws Exception
	{
		Bundle start = launchpad.bundle().bundleActivator(Activator.class).start();
	}

//	public static class Activator implements BundleActivator
//	{
//		@Override
//		public void start(BundleContext context) throws Exception
//		{
//			System.out.println("Hello World");
//		}
//
//		@Override
//		public void stop(BundleContext context) throws Exception
//		{
//			System.out.println("Goodbye World");
//		}
//	}
}
