package lab1;

import java.util.EmptyStackException;

//Christopher Lenk for COS 230
//This class includes a single method, which given a line,
//ensures that {} () and [] come in pairs within the line.  

public class Matcher {

	public MatchRtnCode matcher(String str) {
		
		//Create a new stack to put characters onto
		Stack<Character> arrStk = new ArrayStack<Character>(str.length());
		
		for (int i = 0; i < str.length(); i++) { //Go through the line
			Character c = str.charAt(i); 
			
			try {
				if (c == '{' || c == '(' || c == '[') { //If open symbol found, put it on the stack
					arrStk.push(c);
				} else if (c == '}') { //If close symbol found, take off the top entry on the stack
					if (arrStk.pop() != '{') {
						return MatchRtnCode.UNEXPECTED_SYMBOL;
					}
				} else if (c == ')') {
					if (arrStk.pop() != '(') {
						return MatchRtnCode.UNEXPECTED_SYMBOL;
					}
				} else if (c == ']') {
					if (arrStk.pop() != '[') {
						return MatchRtnCode.UNEXPECTED_SYMBOL;
					}
				}
			
				//If reach end of the line and stack isn't empty, there were too many open symbols
				if (i == str.length()-1) {
					if (!arrStk.isEmpty()) {
						return MatchRtnCode.TOO_MANY_OPEN_SYMBOLS;
					}
				}
			//If pop() throws this error, there were too many close symbols
			} catch (EmptyStackException e) {
				return MatchRtnCode.TOO_MANY_CLOSED_SYMBOLS;
			}
		}
		
		//If no errors were caught, return the "good" message
		return MatchRtnCode.GOOD_STRING;
	}
}
