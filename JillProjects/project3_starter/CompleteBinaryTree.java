import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class CompleteBinaryTree {

    protected TreeNode root;
    protected Integer level;
    protected Boolean isMaxHeap = true;
    protected Boolean isBinarySearchTree = true;


    /**
     * A static nested class representing a node in the binary tree.
     * Contains an integer value and references to left and right children.
     */
    public static class TreeNode {
        protected Integer value;
        protected TreeNode left;
        protected TreeNode right;


        /**
         * Constructs a TreeNode with a given integer value.
         * 
         * @param value the value to store in the node
         */
        public TreeNode(Integer value) {
            this.value = value;
        }
    }

    /**
     * Constructs a CompleteBinaryTree from an array of Integer values that
     * represent a complete binary tree in level-order.
     * 
     * If the input array is not null and contains elements, it initializes the
     * root of the tree by calling the recursive method `makeNode`, starting from
     * index 0.
     * 
     * @param values an array of Integer values representing the binary tree
     *               in level-order
     * @throws InvalidTreeException if the array contains a null element
     *                              where a node is expected
     */
    public CompleteBinaryTree(Integer[] values) throws InvalidTreeException {
        if (values != null && values.length > 0) {
            root = makeNode(values, 0);
        }
    }

    /**
     * Constructs a CompleteBinaryTree from a whitespace-separated string of
     * integers representing the tree in level-order.
     *
     * The string is parsed into integer tokens and used to recursively build
     the
     * tree starting from index 0 via {@code makeNode}.
     *
     * If the input is null or contains only whitespace, the tree is
     considered
     * empty ({@code root} is null). If any token is not a valid integer, an
     * {@code InvalidTreeException} is thrown.
     *
     * @param levelOrderValues the level-order representation of the tree as a
     * string
     * @throws InvalidTreeException if any token is not a valid integer
     */
    public CompleteBinaryTree(String levelOrderValues) throws
            InvalidTreeException {
        String[] values = levelOrderValues.split(" ");
        ArrayList<Integer> nodeValues = new ArrayList<>();

        try {
            for (String value : values) {

                    int parsedValue = Integer.parseInt(value);
                    nodeValues.add(parsedValue);

                    // Convert ArrayList<Integer> to int[]
                    Integer[] intArray = new Integer[nodeValues.size()];
                    for (int i = 0; i < nodeValues.size(); i++) {
                        intArray[i] = nodeValues.get(i);
                    }

                    root = makeNode(intArray, 0);

            }
        } catch (NumberFormatException e) {
            System.out.println("Node value(s) must be an integer");
            throw new InvalidTreeException("Invalid Tree");
        }

    }

    /**
     * Recursively constructs a complete binary tree from an array.
     * The array is assumed to represent a complete binary tree in level-order
     * traversal.
     * 
     * For each index `i` in the array:
     * - The element at index `i` represents the node.
     * - The left child of the node is at index `2*i + 1`.
     * - The right child of the node is at index `2*i + 2`.
     * 
     * This method constructs the tree in a level-by-level manner.
     * 
     * @param values array of integer values representing the tree in
     *               level-order
     * @param index  current index in the array that corresponds to the
     *               current node
     * @return TreeNode at the current index, with left and right children
     *         recursively constructed
     * @throws InvalidTreeException if a node value is null or invalid
     */
    protected TreeNode makeNode(Integer[] values, int index) throws InvalidTreeException {
        if (index >= values.length) {
            return null;
        }
        if (values[index] == null) {
            throw new InvalidTreeException("Node element must not be null");
        }

        TreeNode node = new TreeNode(values[index]);
        node.left = makeNode(values, 2 * index + 1);
        node.right = makeNode(values, 2 * index + 2);

        return node;
    }

    /**
     * Performs a preorder traversal of the tree.
     */
    public void preorder() {
        System.out.print("Preorder: ");
        level = 0;
        preorder(root, level);
        System.out.println();

    }

    /**
     * Recursive helper method for preorder traversal.
     *
     * @param root the current subtree root
     */
    private void preorder(TreeNode root, int level) {

        if (root == null)
            return;

        if (level == 0)
        {
            System.out.print("\n" + root.value + "\n");
        }
        else {
            StringBuilder spaces = new StringBuilder();
            for (int i = 0; i < level ; i++)
            {
                spaces.append("    ");
            }
            System.out.print(spaces.toString() + root.value + "\n");
        }

        isMaxHeap = performMaxHeapCheck(root, isMaxHeap);
        isBinarySearchTree = performBSTCheck(root, Long.MIN_VALUE, Long.MAX_VALUE);

        level++;

        preorder(root.left, level);
        preorder(root.right, level);

    }

    private boolean performMaxHeapCheck(TreeNode root, boolean maxHeap) {
        if (root.left != null)
        {
            if (root.value < root.left.value)
            {
                maxHeap = false;
            }
        }

        if (root.right != null) {
            if (root.value < root.right.value)
            {
                maxHeap = false;
            }
        }
        return maxHeap;
    }

    public boolean isMaxHeap() {

        return isMaxHeap;
    }

    private boolean performBSTCheck(TreeNode root, long min, long max) {

        if (root == null)
        {
           return true;
        }

        // Check if the current node's value is within the allowed range
        if (root.value <= min || root.value >= max) {
            return false;
        }

        // Recursively check the left and right subtrees
        // For the left subtree, the max allowed value becomes the current node's value
        // For the right subtree, the min allowed value becomes the current node's value
        return  performBSTCheck(root.left, min, root.value) &&
                performBSTCheck(root.right, root.value, max);

    }

    public boolean isBST() {

        return isBinarySearchTree;
    }

    /**
     * Performs a inorder traversal of the tree.
     */
    public void inorder() {
        inorder(root);
    }

    private void inorder(TreeNode root) {
        if (root != null) {
            inorder(root.left);
            inorder(root.right);
        }
    }

    public ArrayList<Integer> getSortedElements() {
        ArrayList<Integer> sortedList = new java.util.ArrayList<>();
        getSortedElementsRec(root, sortedList);
        return sortedList;
    }

    void getSortedElementsRec(TreeNode root, List<Integer> list) {
        if (root != null && isBinarySearchTree && !isMaxHeap) {
            getSortedElementsRec(root.left, list);
            list.add(root.value);
            getSortedElementsRec(root.right, list);
        } else if (root != null && !isBinarySearchTree && isMaxHeap) {
            list.add(root.value);
        }

    }

}
