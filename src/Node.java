/**
 * Integer (primitive) Node. Stores an integer and a pointer to the next integer.
 * @author Yan Vinokur
 */
public class Node {
    protected int number;
    protected Node next;

    public Node () {
        number = 0;
        next = null;
    }

    public Node (int number, Node next) {
        this.number = number;
        this.next = next;
    }
}
