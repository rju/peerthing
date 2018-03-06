package de.peerthing.visualization.queryeditor;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.NumberRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * A rule scanner used for the TextViewer
 * in the Query editor. It highlights SQL
 * elements and Variables specific for 
 * visualization queries.
 * 
 * @author Michael Gottschalk
 *
 */
public class SQLRuleScanner extends RuleBasedScanner {
    private static Color TAG_COLOR = new Color(Display.getCurrent(), new RGB(
            200, 0, 0));

    private static Color COMMENT_COLOR = new Color(Display.getCurrent(),
            new RGB(0, 200, 0));

    private static Color BLACK = new Color(Display.getCurrent(), new RGB(0, 0,
            0));

    private static Color RED = new Color(Display.getCurrent(), new RGB(255, 0,
            0));

    private static Color BLUE = new Color(Display.getCurrent(), new RGB(0, 0, 255));

    private static String[] keywords = { "select", "update", "values",
            "insert", "into", "where", "or", "and", "from",
            "union", "order", "by", "group", "having", "create", "table"};

    public SQLRuleScanner() {
        IToken tagToken = new Token(new TextAttribute(TAG_COLOR));
        IToken commentToken = new Token(new TextAttribute(COMMENT_COLOR));
        IToken keywordToken = new Token(
                new TextAttribute(BLACK, null, SWT.BOLD));
        IToken numberToken = new Token(new TextAttribute(RED));
        IToken defaultToken = new Token(new TextAttribute(BLACK));
        IToken blueBoldToken = new Token(new TextAttribute(BLUE, null, SWT.BOLD));

        IRule[] rules = new IRule[5];
        rules[0] = new WordRule(new SQLWordDetector(), defaultToken);

        for (String keyword : keywords) {
            ((WordRule) rules[0]).addWord(keyword, keywordToken);
            ((WordRule) rules[0]).addWord(keyword.toUpperCase(), keywordToken);
        }
        ((WordRule) rules[0]).addWord("$RUN$", blueBoldToken);
        ((WordRule) rules[0]).addWord("$STARTTIME$", blueBoldToken);
        ((WordRule) rules[0]).addWord("$ENDTIME$", blueBoldToken);

        rules[1] = new NumberRule(numberToken);
        rules[2] = new EndOfLineRule("--", commentToken);
        rules[3] = new SingleLineRule("\"", "\"", tagToken, '\\');
        rules[4] = new SingleLineRule("'", "'", tagToken, '\\');

        setRules(rules);
        setDefaultReturnToken(defaultToken);
    }
}

/**
 * Used by SQLRuleScanner to detect the boundaries
 * of words.
 * 
 * @author Michael Gottschalk
 *
 */
class SQLWordDetector implements IWordDetector {
    public boolean isWordStart(char c) {
        return Character.isJavaIdentifierPart(c);
    }

    public boolean isWordPart(char c) {
        return Character.isJavaIdentifierStart(c);
    }
}
