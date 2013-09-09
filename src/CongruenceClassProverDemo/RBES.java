package CongruenceClassProverDemo;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Mike
 */
public class RBES implements Comparable {

    private static int static_id = 1;
    private static TreeMap<RBES, RBES> allInstances = new TreeMap<RBES, RBES>();
    private static final String congruenceClassStr = "CC_";
    private int id;
    public Set<CSC_Lst> operandOf;
    public Set<Expression> members;
    //private ArrayList<Expression> members; // elements may be modified w/o removal
    //public static IdentityHashMap<Expression,RBES> allExpressions = new IdentityHashMap<Expression,RBES>();

    public RBES(Expression e) {
        operandOf = new TreeSet<CSC_Lst>();
        members = new TreeSet<Expression>();
        members.add(e);
        id = static_id++;
        addThisToMap();
    }

    private void addThisToMap() {
        allInstances.put(this, this);
    }

    public void addCSCwhereUsed(CSC_Lst where_used) {
        operandOf.add(where_used);
    }

    public void addExpressionSetWithThisRoot(Set<Expression> se) {
        members.addAll(se);
    }

    public int getId() {
        return id;
    }

    public void replaceRootWith(RBES other) {
        // Replace all references to this in the Expressions root with ref to other
        // expressions are not sorted based on their root
        for (Expression e : members) {
            e.setRoot(other);
        }
    }

    public static void removeUnusedRBES(RBES toRemove, RBES replacement) {
        // notify the expressions that they now have a new root
        System.out.print(toRemove.id);
        System.out.println(" ~~> " + replacement.id);
        allInstances.remove(toRemove);
        for (Expression e : toRemove.members) {
            e.setRoot(replacement);
        }
        replacement.addExpressionSetWithThisRoot(toRemove.members);
        allInstances.remove(toRemove);
    }

    public int compareTo(Object t) {
        RBES u = (RBES) t;
        return id - u.id;
    }

    @Override
    public String toString() {
        return congruenceClassStr + Integer.toString(id);
    }

    public static String allInstancesToString() {
        String r = "";
        for (RBES rb : allInstances.values()) {
            r += rb.toString() + "::\n";
            for (Expression e : rb.members) {
                r += "\t" + e.toString() + "\n";
            }
            r += "\n";
        }
        return r;

    }
}
