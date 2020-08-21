package json_input;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ParserFactory {
	public ParserFactory() {
	}
	
	public Parser getParser() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		return new Parser(gson);
	}
}
