
public class OperationPair {
    private Operation<?> a;
    private Operation<?> b;

    public OperationPair(Operation<?> a, Operation<?> b) {
        this.a = a;
        this.b = b;
    }

    public void shorten() {
        if(a.getOperationLength() == b.getOperationLength()) {
            a = null;
            b = null;
        }
        else if (a.getOperationLength() > b.getOperationLength()) {
            if(a.isStringValue()) {
                a.setOperationValue(shorten(a.getStringValue(), b.getIntegerValue()));
                b = null;
            }
            else if(a.isIntegerValue()) {
                a.setOperationValue(shorten(a.getIntegerValue(), b.getIntegerValue()));
                b = null;
            }
        }
        else {
            if(b.isStringValue()) {
                b.setOperationValue(shorten(b.getStringValue(), a.getIntegerValue()));
                a = null;
            }
            else if(b.isIntegerValue()) {
                b.setOperationValue(shorten(b.getIntegerValue(), a.getIntegerValue()));
                a = null;
            }
        }
    }

    public Operation<?> getA() {
        return a;
    }

    public Operation<?> getB() {
        return b;
    }

    private String shorten(String a, int b) {
        return a.substring(b);
    }

    private int shorten(int a, int b) {
       if(a < 0) return a + b;
       return a - b;
    }

}
