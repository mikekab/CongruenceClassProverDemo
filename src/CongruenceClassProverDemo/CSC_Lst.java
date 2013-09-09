package CongruenceClassProverDemo;


import java.util.*;

/**
 * @author Mike
 */
public class CSC_Lst implements Comparable {

    private List<RBES> members;
    private static TreeMap<CSC_Lst, CSC_Lst> allInstances = new TreeMap<CSC_Lst, CSC_Lst>();
    public Set<Expression> argListOf;

    private CSC_Lst(List<RBES> argList) {
        members = new ArrayList<RBES>(argList);
        argListOf = new TreeSet<Expression>();
    }

    private void updateMap() {
        allInstances.put(this, this);
    }

    public void pullFromAllInstances() {
        allInstances.remove(this);
    }

    public void addToAllInstances() {
        allInstances.put(this, this);
    }

    public static CSC_Lst create(List<RBES> argList) {
        assert (!argList.contains(null));
        // check for identical arglist in allInstances
        CSC_Lst testArgList = new CSC_Lst(argList);
        CSC_Lst inMap = allInstances.get(testArgList);
        if (inMap != null) {
            return inMap;
        }
        allInstances.put(testArgList, testArgList);
        testArgList.notifyRBES();
        return testArgList;
    }

    private void notifyRBES() {
        for (RBES r : members) {
            r.addCSCwhereUsed(this);
        }
    }
    // adding one arg at a time bad/not allowed

    public void replaceRBES(RBES orig, RBES replacement) {
        // Assumption: length of args > 2 almost never
        while (members.contains(orig)) { // may be many uses of orig
            members.set(members.indexOf(orig), replacement);
        }
    }

    private void updateTheExpressions() {

    }

    public void addUser(Expression usedIn) {
        argListOf.add(usedIn);
    }

    public int compareTo(Object s) {
        if (s == null) return 1;
        CSC_Lst t = (CSC_Lst) s;
        int eval = members.size() - t.members.size();
        if (eval != 0) {
            return eval;
        }
        for (int i = 0; i < members.size(); ++i) {
            eval = members.get(i).compareTo(t.members.get(i));
            if (eval != 0) {
                return eval;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        String r = "";
        for (RBES rb : members) {
            r += "." + rb.toString();
        }
        return r;
    }
}