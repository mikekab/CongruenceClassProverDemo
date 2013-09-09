package CongruenceClassProverDemo;

/**
 * @author Mike
 */
public class Demo {

    public void print(Object p) {
        System.out.println(p);
    }

    private String[] opNames = {"a", "b", "c", "x", "y", "z", "+#2"};
    private Op_Name_Type ops = new Op_Name_Type(opNames);
    private CongruenceClassRegistry C;
    private Expression E;

    public void run1() {
        print("C.addConjunct(\"x\", \"a\");");
        C.addConjunct("x", "a");
        print(E.allExpressionsToString());

        print("C.addConjunct(\"y\", \"b\");");
        C.addConjunct("y", "b");
        print(E.allExpressionsToString());

        print("C.addConjunct(C.addAnExpression(\"+.a.b\"), \"c\");");
        C.addConjunct(C.addAnExpression("+.a.b"), "c");
        print(E.allExpressionsToString());

        print("C.addConjunct(C.addAnExpression(\"+.+.x.y.c\"), \"z\");");
        C.addConjunct(C.addAnExpression("+.+.x.y.c"), "z");
        print(E.allExpressionsToString());

        print("print(C.addAnExpression(\"+.z.c\").root\n" +
                "                == C.addAnExpression(\"+.z.+.x.y.+.x.y\").root);");
        print(C.addAnExpression("+.z.c").root
                == C.addAnExpression("+.z.+.x.y.+.x.y").root);
        print(E.allExpressionsToString());
        print(RBES.allInstancesToString());
    }
}

