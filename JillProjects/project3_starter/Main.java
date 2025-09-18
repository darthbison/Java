import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
//            Integer[] values = { 90, 70, 50, 20, 40 };
//            CompleteBinaryTree tree = new CompleteBinaryTree(values);
//            tree.preorder();

            //Enter value for Binary Tree
            System.out.print("Enter a binary tree: ");
            Scanner input =  new Scanner(System.in);
            String treeString = input.nextLine();
            CompleteBinaryTree tree = new CompleteBinaryTree(treeString);
            tree.preorder();
            System.out.println("Is a MaxHeap: " + tree.isMaxHeap());
            System.out.println("Is BST: " + tree.isBST());
            tree.inorder();
            ArrayList<Integer> inOrderList = tree.getSortedElements();
            System.out.println("InOrder List: " + inOrderList.toString());
            input.close();

        } catch (InvalidTreeException e) {
            System.out.println(e.getMessage());
        }
    }
}
