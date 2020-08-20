package sketch.output;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class LineNumberReaderFactory {

	public LineNumberReaderFactory() {
	}

	public LineNumberReader getLineNumberReader(InputStream stream) {
		return new LineNumberReader(new InputStreamReader(stream));
	}

}
