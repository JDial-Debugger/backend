package json_input;

import java.util.Set;

/**
 * Represents something that contains stack frames
 */
public interface Frameable {
	/**
	 * Searches for all functions that appear in a higher stack frame than 
	 * caller function
	 * @param callerFunc - The function to search for that directly or 
	 * indirectly calls the function names to look for
	 * @return - The called function names 
	 */
	Set<String> getCalledFuncs(String callerFunc, int callLine);
}
