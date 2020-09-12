package repair;

import java.util.ArrayList;

import ast.ParserWrapperFactory;

public class CorrectionExampleListFactory {
	public CorrectionExampleListFactory() {

	}

	public CorrectionExampleList getCorrectionExampleList() {
		return new CorrectionExampleList(
			new ArrayList<CorrectionExample>(),
			new ParserWrapperFactory()
		);
	}
}
