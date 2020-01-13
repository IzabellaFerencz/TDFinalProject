import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {
	public static String returnValue;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Consumer<Object> stringPrinterLambda=(Object s) ->
		// System.out.println(s);
		// Consumer<Object> stringPrinterLambda2=(Object s) ->
		// System.out.println(s);
		// Consumer<Object> stringPrinterLambda3=(Object s) -> {
		// returnValue = String.valueOf(s);
		// System.out.println(s);
		//
		// };
		//
		//
		// Map<String, Consumer<Object> > hm = new HashMap<
		// String,Consumer<Object>>();
		//
		// hm.put("create", stringPrinterLambda);
		// hm.put("update", stringPrinterLambda2);
		// hm.put("void", stringPrinterLambda3);
		//
		//
		//
		//
		// hm.get("create").accept("test create");
		// hm.get("void").accept("qqqqq");
		// hm.get("update").accept(new String("test update"));
		// System.out.println("result is:"+returnValue);

		Function<Object, Object> returnBool = (Object s) -> {
			return true;
		};
		Function<Object, Object> returnString = (Object s) -> {
			return "dsadasda";
		};

		Map<String, Function<Object, Object>> hm = new HashMap<String, Function<Object, Object>>();

		hm.put("create", returnBool);
		hm.put("read", returnString);

		System.out.println(hm.get("create").apply("TEST2"));
		System.out.println(hm.get("read").apply("TEST"));
	}

}
