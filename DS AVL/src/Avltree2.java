import java.util.Scanner;

public class Avltree2 {

    //--------------------------------------------------------LinkedList
    private Node root;
    private boolean h = false;

    private static class Node {

        private Node left;
        private Node right;
        private double value;
        private double height;


        private Node(double value1) {

            value = value1;
            height = 1;
            right = null;
            left = null;

        }
    }

//--------------------------------------------------------DefaultEntry

    private void add(double value) {

        root = addfunction(root, value);

    }

    private void find(double value) {

        Node searchednode = Search(root, value);

        if (searchednode == null) {

            inorder(root, value);
            if (!h) {

                System.out.println("not found");
            }
            h = false;
        } else {
            System.out.println(searchednode.value);
        }
    }

    private void remove(double value) {

        root = doremove(root, value);

    }

//--------------------------------------------------------Main

    public static void main(String[] args) {
        Avltree2 node = new Avltree2();
        Scanner scanner1 = new Scanner(System.in);
        int a = scanner1.nextInt();
        for (int i = 0; i < a; i++) {
            String a1 = scanner1.next();
            double a2 = scanner1.nextDouble();
            if (a1.equals("add")) {
                node.add(a2);
            }
            if (a1.equals("find")) {
                node.find(a2);
            }
            if (a1.equals("remove")) {
                node.remove(a2);
            }
        }
        scanner1.close();

    }
//-------------------------------------------------------PrivateFunctions

    private Node Search(Node node, double value) {

        if (node == null) {
            return null;
        }

        if (node.value == value)
            return node;
        if (value < node.value)
            return Search(node.left, value);
        else
            return Search(node.right, value);
    }


    private Node addfunction(Node node, double value) {

        if (node == null) {
            System.out.println("added");
            node = new Node(value);
            return node;
        }

        if (value > node.value) {
            node.right = addfunction(node.right, value);
        } else if (value < node.value) {
            node.left = addfunction(node.left, value);
        } else {
            System.out.println("already in there");
            return node;
        }


        if (GetNodeHeight(node.left) > GetNodeHeight(node.right)) {
            node.height = GetNodeHeight(node.left) + 1;
        } else {
            node.height = GetNodeHeight(node.right) + 1;
        }

        double BL;

        BL = GetNodeHeight(node.left) - GetNodeHeight(node.right);

        node = balancing(node, BL, value);

        return node;
    }


    private Node doremove(Node node, Double value) {

        if (node == null) {
            System.out.println("does not exist");
            return null;
        }

        if (value > node.value)
            node.right = doremove(node.right, value);
        else if (value < node.value)
            node.left = doremove(node.left, value);
        else {
            Node testnode1 = node;
            Node testnode2 = node;
            Node testnode3 = node;
            int a = 0;
            if (testnode1.left == null && testnode1.right == null) {
                a = 1;
            } else if (testnode2.right == null) {
                testnode2 = testnode2.left;
                a = 2;
            } else if (testnode3.left == null) {
                testnode3 = testnode3.right;
                a = 3;

            } else {
                Node temp = succesor(node.right);
                node.value = temp.value;
                node.right = doremove(node.right, temp.value);
            }
            if (a == 1) {
                node = null;
                System.out.println("removed");
            } else if (a == 2) {
                node = testnode2;
                System.out.println("removed");
            } else if (a == 3) {
                node = testnode3;
                System.out.println("removed");
            }
        }

        double BL;

        if (node == null) {
            return null;
        }

        if (GetNodeHeight(node.left) > GetNodeHeight(node.right)) {
            node.height = GetNodeHeight(node.left) + 1;
        } else {
            node.height = GetNodeHeight(node.right) + 1;
        }

        BL = GetNodeHeight(node.left) - GetNodeHeight(node.right);

        node = balancing(node, BL, value);

        return node;
    }

    private double GetNodeHeight(Node node) {

        double thisheight;

        if (node == null) {

            thisheight = 0;
        } else {

            thisheight = node.height;
        }
        return thisheight;
    }

    private void inorder(Node node, double value) {

        if (node != null) {
            inorder(node.left, value);
            if (node.value > value && !h) {
                System.out.println(node.value);
                h = true;
            }
            inorder(node.right, value);

        }

    }

    private Node balancing(Node node, double BL, double value) {

        if (BL > 1 && value < node.left.value) {

            System.out.println("balancing " + node.value);

            Node currnode2 = node.left;
            Node currnode3 = currnode2.right;
            currnode2.right = node;
            node.left = currnode3;

            if (GetNodeHeight(node.left) > GetNodeHeight(node.right)) {
                node.height = GetNodeHeight(node.left);
            } else {
                node.height = GetNodeHeight(node.right);
            }
            node.height++;

            if (GetNodeHeight(currnode2.left) > GetNodeHeight(currnode2.right)) {
                currnode2.height = GetNodeHeight(currnode2.left);
            } else {
                currnode2.height = GetNodeHeight(currnode2.right);
            }
            currnode2.height++;

            return currnode2;
        }

        if (BL < -1 && value > node.right.value) {

            System.out.println("balancing " + node.value);

            Node currnode2 = node.right;
            Node currnode3 = currnode2.left;
            currnode2.left = node;
            node.right = currnode3;

            if (GetNodeHeight(node.left) > GetNodeHeight(node.right)) {
                node.height = GetNodeHeight(node.left);
            } else {
                node.height = GetNodeHeight(node.right);
            }
            node.height++;

            if (GetNodeHeight(currnode2.left) > GetNodeHeight(currnode2.right)) {
                currnode2.height = GetNodeHeight(currnode2.left);
            } else {
                currnode2.height = GetNodeHeight(currnode2.right);
            }
            currnode2.height++;

            return currnode2;
        }

        if (BL > 1 && value > node.left.value) {

            Node currnode1 = node.left;
            Node currnode2 = currnode1.right;
            Node currnode3 = currnode2.left;
            currnode2.left = currnode1;
            currnode1.right = currnode3;

            if (GetNodeHeight(currnode1.left) > GetNodeHeight(currnode1.right)) {
                currnode1.height = GetNodeHeight(currnode1.left);
            } else {
                currnode1.height = GetNodeHeight(currnode1.right);
            }
            currnode1.height++;

            if (GetNodeHeight(currnode2.left) > GetNodeHeight(currnode2.right)) {
                currnode2.height = GetNodeHeight(currnode2.left);
            } else {
                currnode2.height = GetNodeHeight(currnode2.right);
            }
            currnode2.height++;

            node.left = currnode2;


            System.out.println("balancing " + node.value);

            Node currnode22 = node.left;
            Node currnode32 = currnode22.right;
            currnode22.right = node;
            node.left = currnode32;

            if (GetNodeHeight(node.left) > GetNodeHeight(node.right)) {
                node.height = GetNodeHeight(node.left);
            } else {
                node.height = GetNodeHeight(node.right);
            }
            node.height++;

            if (GetNodeHeight(currnode22.left) > GetNodeHeight(currnode22.right)) {
                currnode22.height = GetNodeHeight(currnode22.left);
            } else {
                currnode22.height = GetNodeHeight(currnode22.right);
            }
            currnode22.height++;

            return currnode22;
        }

        if (BL < -1 && value < node.right.value) {

            Node currnode1 = node.right;
            Node currnode2 = currnode1.left;
            Node currnode3 = currnode2.right;
            currnode2.right = currnode1;
            currnode1.left = currnode3;

            if (GetNodeHeight(currnode1.left) > GetNodeHeight(currnode1.right)) {
                currnode1.height = GetNodeHeight(currnode1.left);
            } else {
                currnode1.height = GetNodeHeight(currnode1.right);
            }
            currnode1.height++;

            if (GetNodeHeight(currnode2.left) > GetNodeHeight(currnode2.right)) {
                currnode2.height = GetNodeHeight(currnode2.left);
            } else {
                currnode2.height = GetNodeHeight(currnode2.right);
            }
            currnode2.height++;

            node.right = currnode2;

            //==================

            System.out.println("balancing " + node.value);

            Node currnode22 = node.right;
            Node currnode32 = currnode22.left;
            currnode22.left = node;
            node.right = currnode32;

            if (GetNodeHeight(node.left) > GetNodeHeight(node.right)) {
                node.height = GetNodeHeight(node.left);
            } else {
                node.height = GetNodeHeight(node.right);
            }
            node.height++;

            if (GetNodeHeight(currnode22.left) > GetNodeHeight(currnode22.right)) {
                currnode22.height = GetNodeHeight(currnode22.left);
            } else {
                currnode22.height = GetNodeHeight(currnode22.right);
            }
            currnode22.height++;

            return currnode22;


        }
        return node;
    }


    private Node succesor(Node node) {

        Node currnote = node;

        while (currnote.left != null) {
            currnote = currnote.left;
        }

        return currnote;
    }

}

