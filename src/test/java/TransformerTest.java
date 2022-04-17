import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class TransformerTest {
    private static String DOC = "hello";

    @Test
    void testTransform() {
        Operation<?> clientAOp1 = new Operation<>(OperationType.RETAIN, 5);
        Operation<?> clientAOp2 = new Operation<>(OperationType.INSERT, " world");
        Operation<?> clientBOp1 = new Operation<>(OperationType.RETAIN, 5);
        Operation<?> clientBOp2 = new Operation<>(OperationType.INSERT, "!");

        OperationsList opsClientA = new OperationsList();
        opsClientA.performOperation(clientAOp1);
        opsClientA.performOperation(clientAOp2);

        OperationsList opsClientB = new OperationsList();
        opsClientB.performOperation(clientBOp1);
        opsClientB.performOperation(clientBOp2);

        List<OperationsList> result = Transformer.transform(opsClientA, opsClientB);

        OperationsList opsClientAPrime = result.get(0);
        OperationsList opsClientBPrime = result.get(1);

        assert opsClientAPrime.applyToDocument(
                opsClientB.applyToDocument("hello")
        ).equals(
                opsClientBPrime.applyToDocument(
                        opsClientA.applyToDocument("hello")
                )
        );

    }
}
