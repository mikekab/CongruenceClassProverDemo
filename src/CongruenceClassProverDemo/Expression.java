package CongruenceClassProverDemo;


import java.util.TreeMap;

/**
 * @author mike
 */
public class Expression implements Comparable {
    //public static IdentityHashMap<Expression,RBES> allExpressions = new IdentityHashMap<Expression,RBES>();

    public static TreeMap<Expression, Expression> allInstances = new TreeMap<Expression, Expression>();
    //public static TreeMap<SimpleImmutableEntry<Op_Name_Type, CSC_Lst>, Expression> allInstances = new TreeMap<SimpleImmutableEntry<Op_Name_Type, CSC_Lst>, Expression>();
    private Op_Name_Type operator;
    private CSC_Lst arguments;
    public RBES root;

    private Expression(Op_Name_Type op, CSC_Lst args, RBES rb) {
        //SimpleImmutableEntry<Op_Name_Type, CSC_Lst> k = new SimpleImmutableEntry<Op_Name_Type, CSC_Lst>(op, args);
        operator = op;
        arguments = args;
        root = rb;

    }

    public static Expression create(Op_Name_Type op, CSC_Lst args) {
        //SimpleImmutableEntry<Op_Name_Type, CSC_Lst> k = new SimpleImmutableEntry<Op_Name_Type, CSC_Lst>(op, args);
        Expression testExp = new Expression(op, args, null);
        Expression inMap = allInstances.get(testExp);
        if (inMap != null) {
            return inMap;
        }
        // maybe let rbes know via contructor what expr it has
        testExp.root = new RBES(testExp);
        allInstances.put(testExp, testExp);
        return testExp;
    }

    public void setRoot(RBES r) {
        root = r;
    }

    public void pullFromAllInstances() {
        //SimpleImmutableEntry<Op_Name_Type, CSC_Lst> k = new SimpleImmutableEntry<Op_Name_Type, CSC_Lst>(operator, arguments);
        allInstances.remove(this);
    }

    public void addToAllInstances() {
        //SimpleImmutableEntry<Op_Name_Type, CSC_Lst> k = new SimpleImmutableEntry<Op_Name_Type, CSC_Lst>(operator, arguments);
        allInstances.put(this, this);
    }

    public void replaceCSC_Lst(CSC_Lst newArgs) {
        arguments = newArgs;
    }

    @Override
    public String toString() {
        if (arguments != null) {
            return operator + arguments.toString() + " :: " + root.toString();
        }
        return operator + " :: " + root.toString();
    }

    public static String allExpressionsToString() {
        String r = "";
        for (Expression e : allInstances.values()) {
            r += e.toString() + "\n";
        }
        return r;
    }

    public int compareTo(Object o) {
        if (o == null) {
            return 1;
        }
        Expression t = (Expression) o;
        int eval;
        eval = operator.compareTo(t.operator);
        if (eval != 0) {
            return eval;
        }
        // same operator
        if (arguments == null) {
            if (t.arguments == null) {
                return 0;
            } else {
                return -1;
            }
        }
        return arguments.compareTo(t.arguments);
    }
}
