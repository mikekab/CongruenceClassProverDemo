package CongruenceClassProverDemo;

import java.util.HashMap;

/**
 * All operators used must be in the ArrayOfOpNames passed to the constructor.
 *
 * @author Mike
 */
public class Op_Name_Type implements Comparable {

    private static HashMap<String, Op_Name_Type> OpMap;
    private String name;
    private String type;
    private int arity;

    public Op_Name_Type(String[] ArrayOfOpNames) {
        OpMap = new HashMap<String, Op_Name_Type>((int) (ArrayOfOpNames.length / .75));
        for (String op : ArrayOfOpNames) {
            String[] splt = op.split("\\#");
            int ar = 0;
            if (splt.length > 1) {
                ar = Integer.decode(splt[1]);
                op = splt[0];
            }
            OpMap.put(op, new Op_Name_Type(op, ar));
        }
    }

    Op_Name_Type(String name, int arity) {
        this.name = name;
        this.arity = arity;
    }

    public static Op_Name_Type getRefFor(String name) {
        return OpMap.get(name);
    }

    public int getArity() {
        return arity;
    }


    public String toString() {
        return name;
    }

    public int compareTo(Object t) {
        Op_Name_Type u = (Op_Name_Type) t;
        return name.compareTo(u.name);
    }
}
