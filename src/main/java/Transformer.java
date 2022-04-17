import java.util.*;

public class Transformer {

    /**
     * Combine two consecutive operations into one that has the same effect
     * when applied to a document.
     * @param opsA
     * @param opsB
     * @return
     */
    public List<OperationsList> compose(OperationsList opsA, OperationsList opsB) {



        return null;
    }
    /**
     * Operational Transformation Algorithm:
     * Transformation Function = F(Received Operation, Local Operation)
     * O1 -> Operation on document 1 by client 1
     * O2 -> Operation on document 1 by client 2
     * If O1 and O2 are performed simultaneously, then,
     * Transformation Function used to apply changes from client O2 on changes from
     * client O1 is O2'=T(O2, O1)
     * and,
     * Transformation Function used to apply changes from client O1 on changes
     * in O2 is O1' = T(O1, O2)
     *
     * such that any OT function satisfies the following property
     * O2'(O1(X)) = O1'(O2(X))
     *
     *
     * @param opsA
     * @param opsB
     * @return
     */
    public static List<OperationsList> transform(OperationsList opsA, OperationsList opsB) {

        OperationsList opsAP = new OperationsList();
        OperationsList opsBP = new OperationsList();

        Operation<?> a = null;
        Operation<?> b = null;

        while(true) {

            if(a == null) a = opsA.next();
            if(b == null) b = opsB.next();

            if(a == null && b==null) break;

            if(a != null && a.getOperationType() == OperationType.INSERT) {
                opsAP.performOperation(a);
                opsBP.performOperation(new Operation<>(OperationType.RETAIN, a.getOperationLength()));
                a = null;
                continue;
            }

            if(b != null && b.getOperationType() == OperationType.INSERT) {
                opsAP.performOperation(new Operation<>(OperationType.RETAIN, b.getOperationLength()));
                opsBP.performOperation(b);
                b = null;
                continue;
            }

            int minLength = Math.min(
                    Objects.requireNonNull(a).getOperationLength(),
                    Objects.requireNonNull(b).getOperationLength()
            );

            if(a.isRetain() && b.isRetain()) {
                opsAP.performOperation(new Operation<>(OperationType.RETAIN, minLength));
                opsBP.performOperation(new Operation<>(OperationType.RETAIN, minLength));
            }
            else if(a.isDelete() && b.isRetain()) opsAP.performOperation(new Operation<>(OperationType.DELETE, minLength));
            else if(a.isRetain() && b.isDelete()) opsBP.performOperation(new Operation<>(OperationType.DELETE, minLength));
            OperationPair pair = new OperationPair(a, b);
            pair.shorten();
            a = pair.getA();
            b = pair.getB();

        }
        return Arrays.asList(opsAP, opsBP);
    }


}