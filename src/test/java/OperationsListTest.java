import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

class OperationsListTest {

    private OperationsList operationsList;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testApplyToDocument() {
            try {
                operationsList = new OperationsList();
                operationsList.performOperation(new Operation<>(OperationType.INSERT, "L"));
                operationsList.performOperation(new Operation<>(OperationType.DELETE, 1));
                operationsList.performOperation(new Operation<>(OperationType.RETAIN, 4));
                operationsList.performOperation(new Operation<>(OperationType.INSERT, "ipsum"));
                String result = operationsList.applyToDocument("lorem");

                Assertions.assertEquals("Loremipsum", result);
            }
            catch(Exception ignored){}
    }


}