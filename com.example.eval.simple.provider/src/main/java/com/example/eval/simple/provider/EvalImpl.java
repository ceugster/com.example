package com.example.eval.simple.provider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.felix.service.command.Descriptor;
import org.apache.felix.service.command.annotations.GogoCommand;
import org.osgi.service.component.annotations.Component;

import com.example.eval.api.Eval;

@GogoCommand(scope = "simple", function = { "eval" })
@Component
public class EvalImpl implements Eval
{
	Pattern EXPR = Pattern.compile("\\s*(?<left>\\d+)\\s*(?<op>\\+|-)\\s*(?<right>\\d+)\\s*");

	@Descriptor("Add/Substract Evaluation command")
	@Override
	public double eval(@Descriptor("Formula to evaluate") String expression) throws Exception
	{
		Matcher m = EXPR.matcher(expression);
		if (!m.matches())
			return Double.NaN;
		double left = Double.valueOf(m.group("left"));
		double right = Double.valueOf(m.group("right"));
		switch (m.group("op"))
		{
			case "+":
				return left + right;
			case "-":
				return left - right;
		}
		return Double.NaN;
	}
}
