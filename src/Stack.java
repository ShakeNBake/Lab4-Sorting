/**
 * Integer Stack
 * @author Yan Vinokur
 */
public class Stack {
    private final static int MAXSIZE = 40000;
    private int[] numbers;
    private int top;

    /**
     * Default constuctor
     */
    Stack() {
        top = -1;
        numbers = new int[MAXSIZE];
    }

    /**
     * Returns true if stack is empty
     * @return true if stack is empty
     */
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * Push object on top of the stack
     * @param number object to be pushed onto the stack
     */
    public void push(int number) {
        try {
            numbers[++top] = number;
        }
        catch (ArrayIndexOutOfBoundsException aob) {
            top = MAXSIZE - 1;
            System.out.println("Stack is full.");
            System.exit(1);
        }
    }

    /**
     * Pops the stack
     * @return Passenger object
     */
    public int pop() {
        if (this.isEmpty()) {
            System.out.println("Stack is empty.");
            System.exit(1);
        }
        return numbers[top--];
    }

}
