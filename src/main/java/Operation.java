public class Operation <T>{

    private OperationType operationType;
    private T operationValue;

    public Operation(OperationType type, T operationValue) {
        this.operationType = type;
        this.operationValue = operationValue;

    }

    public OperationType getOperationType() {
        return this.operationType;
    }

    public T getOperationValue() {
        if(!(operationValue instanceof Integer || operationValue instanceof String)) {
            System.out.println("Error: Operation has value other than int or str");
            return null;
        }
        return this.operationValue;
    }

    @SuppressWarnings("unchecked")
    public void setOperationValue(String value) {
        this.operationValue = (T) value;
    }

    @SuppressWarnings("unchecked")
    public void setOperationValue(Integer value) {
        this.operationValue = (T) value;
    }

    public String getStringValue() {
        if(!(operationValue instanceof String)) return null;
        return (String) operationValue;
    }

    public Integer getIntegerValue() {
        if(!(operationValue instanceof Integer)) return null;
        return (Integer) operationValue;
    }

    public boolean isStringValue() {
        return operationValue instanceof String;
    }

    public boolean isRetain() {
        return getOperationType() == OperationType.RETAIN;
    }

    public boolean isInsert() {
        return getOperationType() == OperationType.INSERT;
    }

    public boolean isDelete() {
        return getOperationType() == OperationType.DELETE;
    }

    public int getOperationLength() {
        if(isStringValue()) return getStringValue() == null ? 0 : getStringValue().length();
        return Math.abs(getIntegerValue() == null ? 0 : getIntegerValue());
    }

    public boolean isIntegerValue() {
        return operationValue instanceof Integer;
    }
}
