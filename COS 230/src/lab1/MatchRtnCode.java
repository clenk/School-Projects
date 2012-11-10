package lab1;

/*
 * MatchRtnCode
 * Author: John M. Hunt
 * An enumerated type used by the Matcher class to inform
 * its calling with a string has balanced braces, parens, brackets
 */
public enum MatchRtnCode {
	GOOD_STRING {public String toString(){return"Good String";}},
	UNEXPECTED_SYMBOL {public String toString(){return"Unexpected Symbol";}},
	TOO_MANY_OPEN_SYMBOLS {public String toString(){return"Too Many Open Symbols";}},
	TOO_MANY_CLOSED_SYMBOLS {public String toString(){return"Too Many Closed Symbols";}}
}