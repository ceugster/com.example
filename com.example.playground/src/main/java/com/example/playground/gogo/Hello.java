package com.example.playground.gogo;

import org.apache.felix.service.command.Descriptor;
import org.apache.felix.service.command.Parameter;
import org.apache.felix.service.command.annotations.GogoCommand;
import org.osgi.service.component.annotations.Component;

@GogoCommand(scope = "hello", function = { "hello" })
@Component(service = Hello.class)
public class Hello
{
	@Descriptor("Friendly welcome command")
	public String hello(@Parameter(absentValue = "World", names = { "-n", "--name" }) @Descriptor("Name to welcome") String name)
	{
		return "Hello " + name;
	}
}
