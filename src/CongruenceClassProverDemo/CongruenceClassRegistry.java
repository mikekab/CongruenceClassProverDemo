package CongruenceClassProverDemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: mike
 * Date: 9/9/13
 * Time: 11:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class CongruenceClassRegistry {
    public static void changeRBESvalue(RBES oldRBES, RBES newRBES) {
        Set<CSC_Lst> argLists;
        Set<Expression> expressions = new TreeSet<Expression>();

        // get references to objects to be modified
        // arg lists with oldRbes
        argLists = oldRBES.operandOf;
        for (CSC_Lst a : argLists) {
            expressions.addAll(a.argListOf);
        }


        // pull objects to be modified
        for (CSC_Lst a : argLists) {
            a.pullFromAllInstances();
        }
        for (Expression e : expressions) {
            e.pullFromAllInstances();
        }

        // modify the objects

        // oldRBES just gets removed
        for (CSC_Lst a : argLists) {
            a.replaceRBES(oldRBES, newRBES);
            for (Expression e : a.argListOf) {
                e.replaceCSC_Lst(a);
            }
        }

        // add to their allInstances collection
        for (CSC_Lst a : argLists) {
            a.addToAllInstances();
        }
        for (Expression e : expressions) {
            e.addToAllInstances();
        }
        oldRBES.replaceRootWith(newRBES);
        RBES.removeUnusedRBES(oldRBES, newRBES);
    }

    // get rbes for single vars or make one
    public static RBES opToRBES(Op_Name_Type opThatIsVarName) {
        Expression ex = Expression.create(opThatIsVarName, null);
        return ex.root;
    }

    private static Expression addRecursive(List<Op_Name_Type> opList) {
        if (opList.size() == 1) {
            return Expression.create(opList.get(0), null);
        }
        ArrayList<RBES> args = new ArrayList<RBES>();
        // slice off front up to next op
        Op_Name_Type firstOp = opList.remove(0);
        int argsNum = firstOp.getArity();
        for (int i = 0; i < argsNum; i++) {
            if (opList.get(0).getArity() == 0) {
                args.add(opToRBES(opList.remove(0)));
            } else {
                args.add(addRecursive(opList).root);
            }
        }
        if (opList.isEmpty()) {
            return Expression.create(firstOp, CSC_Lst.create(args));
        }
        return Expression.create(firstOp, CSC_Lst.create(args));
    }

    public static Expression addAnExpression(String expr) {
        String[] strArr = expr.split("\\.");
        ArrayList<Op_Name_Type> opList = new ArrayList<Op_Name_Type>();

        for (int i = 0; i < strArr.length; ++i) {
            opList.add(Op_Name_Type.getRefFor(strArr[i]));
        }
        return addRecursive(opList);
    }

    public static void addConjunct(String exp1, String exp2) {
        Expression e1, e2;
        e1 = addAnExpression(exp1);
        e2 = addAnExpression(exp2);
        changeRBESvalue(e1.root, e2.root);
    }

    public static void addConjunct(Expression exp1, String exp2) {
        Expression expr2 = addAnExpression(exp2);
        changeRBESvalue(exp1.root, expr2.root);
    }

}
