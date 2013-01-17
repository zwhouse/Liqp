package liqp;

import liqp.parser.LiquidLexer;
import liqp.parser.LiquidParser;
import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;

/**
 * Used for debugging: prints the AST of some Liquid-input.
 */
public class Examples {

    private static void walk(CommonTree tree, String[] tokenNames, int indent) {

        if(tree == null) {
            return;
        }

        for(int i = 0; i < indent; i++) {
            System.out.print("  ");
        }

        boolean leaf = tree.getChildCount() == 0;

        if(tree.getType() == LiquidLexer.EOF) {
            return;
        }

        System.out.println(tokenNames[tree.getType()] + (leaf ? "='" + tree.getText() + "'" : ""));

        for(int i = 0; i < tree.getChildCount(); i++) {
            walk((CommonTree)tree.getChild(i), tokenNames, indent + 1);
        }
    }

    public static void main(String[] args) throws Exception {
        String test = "{% for item in array limittt:3 offset:2 %}{{ item }}{% endfor %}\n" +
                "{% for i in (1..item.quantity) offset:2 %}{{ i }}{% endfor %}";
        LiquidLexer lexer = new LiquidLexer(new ANTLRStringStream(test));
        LiquidParser parser = new LiquidParser(new CommonTokenStream(lexer));
        CommonTree ast = (CommonTree)parser.parse().getTree();
        walk(ast, parser.getTokenNames(), 0);

        /*
        Template template = Template.parse(
                "{% if user.name == 'tobi' %}\n" +
                        "  Hello tobi\n" +
                        "{% elsif user.name == 'bob' %}\n" +
                        "  Hello bob\n" +
                        "{% else %}\n" +
                        "  Hello ???\n" +
                        "{% endif %}");


        String json = "{\"user\" : {\"name\" : \"tobi\"} }";

        Object output = template.render(json);

        System.out.printf(">>>%s<<<", output);
        */
    }
}