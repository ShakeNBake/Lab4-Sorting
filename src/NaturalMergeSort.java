/**
 * Natural MergeSort implementation using linked structure.
 */
public class NaturalMergeSort {
    /**
     * Merge two linked lists in ascending order.
     * @param left 1st list to be merged
     * @param right 2nd list to be merged
     * @return A Node pointer to the first element of the merged sorted list.
     */
    private static Node merge(Node left, Node right) {
        Node mergePos = new Node();       // Pointer to the last element in merged list
        Node head = mergePos;           // Head of the merged list

        while (left != null && right != null) {
            if (left.number <= right.number) {
                // Add the elem of the left part into main linked list.
                mergePos.next = left;
                left = left.next;
            } else {
                // Add the elem of the right part into main linked list.
                mergePos.next = right;
                right = right.next;
            }
            mergePos = mergePos.next;
        }
        if (left != null) {
            // Add the remaining part of left part into main linked list.
            mergePos.next = left;
        } else if (right != null) {
            // Add the remaining part of right part into main linked list.
            mergePos.next = right;
        }
        return head.next;
    }

    /**
     * Natural Merge Sort recursive algorithm method.
     * @param list A pointer to the list to be sorted.
     * @return A pointer to the sorted list.
     */
    public static Node mergeSort(Node list) {
        if (list == null || list.next == null) return list; // No need to sort.

        Node prev, curr;
        prev = list;
        curr = list.next;

        while (curr.number >= prev.number) {
            prev = curr;
            curr = curr.next;
            if (curr == null) {            //list is already sorted
                return list;
            }
        }

        prev.next = null;
        curr = mergeSort(curr);
        list = merge(list, curr);
        return list;
    }
}