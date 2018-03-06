package de.peerthing.scenarioeditor.editor.xml;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class XMLRuleScanner extends RuleBasedScanner {
	private static Color TAG_COLOR = new Color(Display.getCurrent(), new RGB(
            200, 0, 0));


	private static Color COMMENT_COLOR = new Color(Display.getCurrent(),
            new RGB(0, 200, 0));

    private static Color BLACK = new Color(Display.getCurrent(), new RGB(0, 0,
            0));

    private static String[] keywords = { "scenario",
    	"connectionCategory", "nodeCategory", "resourceCategory",
    	"action", "condition", "callBehaviour", "delay", "loop",
    	"listen", "size", "uplink", "downlink", "behaviour",
    	"case", "default", "connection", "resource"};

	public XMLRuleScanner() {
		IToken tagToken = new Token(new TextAttribute(TAG_COLOR));
        IToken commentToken = new Token(new TextAttribute(COMMENT_COLOR));
        IToken keywordToken = new Token(
                new TextAttribute(BLACK, null, SWT.BOLD));
        IToken defaultToken = new Token(new TextAttribute(BLACK));

        IRule[] rules = new IRule[4];
        rules[0] = new WordRule(new XMLWordDetector(), defaultToken);

        for (String keyword : keywords) {
            ((WordRule) rules[0]).addWord(keyword, keywordToken);
        }

        rules[1] = new SingleLineRule("\"", "\"", tagToken, '\\');
        rules[2] = new SingleLineRule("'", "'", tagToken, '\\');
        rules[3] = new MultiLineRule("<!--", "-->", commentToken);

        setRules(rules);
        setDefaultReturnToken(defaultToken);
	}
}

class XMLWordDetector implements IWordDetector {
    public boolean isWordStart(char c) {
        return Character.isJavaIdentifierPart(c);
    }

    public boolean isWordPart(char c) {
        return Character.isJavaIdentifierStart(c);
    }
}