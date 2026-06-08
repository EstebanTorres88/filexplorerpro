


/**
 *
 * @author Esteban Torres jimenez
 * @author Dilan Rojas Vargas
 */
public class OperationNode {
    private Operation operation;
    private OperationNode next;

    public OperationNode(Operation operation) {
        this.operation = operation;
        this.next = null;
    }

    public Operation getOperation() {
        return operation;
    }

    public OperationNode getNext() {
        return next;
    }

    public void setNext(OperationNode next) {
        this.next = next;
    }
}
