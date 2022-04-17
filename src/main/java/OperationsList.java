import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class OperationsList {
    private List<Operation<?>> ops;
    private Iterator<Operation<?>> iterator = null;

    public OperationsList(OperationsList existing) {
        this.ops = new ArrayList<>(existing.asList());
    }

    public OperationsList() {
        this.ops = new ArrayList<>();
    }

    public void performOperation(Operation<?> op) {
        if(op.isInsert()) insert(op);
        else if(op.isRetain()) retain(op);
        else if(op.isDelete()) delete(op);
    }

    public String applyToDocument(String document) {
        List<String> parts = new ArrayList<>();
        int i = 0;
        for(Operation<?> op: ops) {
            if(op.isRetain()) {
                parts.add(document.substring(i, i + op.getIntegerValue()));
                i += op.getIntegerValue();
            }
            else if(op.isInsert()) {
                parts.add(op.getStringValue());
            }
            else {
                i -= op.getIntegerValue();
            }
        }

        return String.join("", parts);
    }

    public boolean insert(Operation<?> op) {

        if(!op.isStringValue()) return false;

        if(
                this.notEmpty() &&
                        this.getLast() != null &&
                        this.getLast().isStringValue()
        ) {
            Operation<String> newValue = new Operation<>(
                    OperationType.INSERT,
                    this.getLast().getStringValue() + op.getStringValue());
            ops.add(ops.size() - 1, newValue);
        }
        else if(
                this.notEmpty() &&
                        this.getLast() != null &&
                        this.getLast().isIntegerValue() &&
                        this.getLast().getIntegerValue() < 0
        ) {

           if(
                   ops.size() > 1 &&
                           ops.get(ops.size() - 2).isStringValue()) {
               ops.add(
                       ops.size() - 2,
                       new Operation<>(
                           OperationType.INSERT,
                           ops.get(ops.size() - 2).getStringValue() + op.getStringValue()
                       )
               );
           }
           else {
               ops.add(this.getLast());
               ops.add(ops.size() - 2, op);
           }
        }
        else ops.add(op);

        return true;
    }

    public boolean retain(Operation<?> op) {
        int count = op.getIntegerValue();

       if(count == 0) return false;
       if(
               this.getLast() != null &&
                       this.getLast().isIntegerValue() &&
                       this.getLast().getIntegerValue() > 0
       ) {
           ops.set(
                   ops.size() - 1,
                   new Operation<>(
                           OperationType.RETAIN,
                           this.getLast().getIntegerValue() + count
           ));
       }
       else ops.add(new Operation<>(OperationType.RETAIN, count));
       return true;
    }

    public boolean delete(Operation<?> op) {
        int count = op.getIntegerValue();
        if(count==0) return true;
        if(count > 0) count = -count;
        if(
                this.notEmpty() &&
                        this.getLast() != null &&
                        this.getLast().getOperationType() == OperationType.DELETE
        ) {
            this.getLast().setOperationValue(this.getLast().getIntegerValue() + count);
        }
        else {
            ops.add(new Operation<>(OperationType.DELETE, count));
        }
        return true;
    }

    public List<Operation<?>> asList() {
        return ops;
    }

    public boolean equals(OperationsList anotherList) {
       return this.ops.equals(anotherList.asList());
    }

    public void addAll(OperationsList listToAdd) {
        ops.addAll(listToAdd.asList());
    }

    public Operation<?> next() {
        if(iterator == null) iterator = ops.iterator();
        if(this.iterator.hasNext()){
            return this.iterator.next();
        }
        else {
            return null;
        }
    }

    public void resetIterator() {
        this.iterator = null;
    }

    private Operation<?> getLast() {
        if(ops == null || ops.size() == 0) return null;
        return ops.get(ops.size() - 1);
    }

    private boolean notEmpty() {
        return ops != null && ops.size() > 0;
    }

}
