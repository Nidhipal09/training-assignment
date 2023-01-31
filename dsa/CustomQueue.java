import java.util.Scanner;

public class CustomQueue {

  public static void main(String[] args) {

    try {
      Scanner sc = new Scanner(System.in);

      Queue queue = new Queue();

      while (true) {
        System.out.print("Your Queue: ");
        queue.display(queue.head);
        System.out.println();

        System.out.println("Enter 1 to add");
        System.out.println("Enter 2 to remove");
        System.out.println("Enter 3 to peek");
        System.out.println("Enter 4 to search");

        int choice = sc.nextInt();

        if (choice == 1) {
          System.out.print("Enter the value: ");
          int val = sc.nextInt();
          queue.add(val);
          System.out.println("-------------------------------------");
        } else if (choice == 2) {
          System.out.println("Removed value: " + queue.remove());
          System.out.println("-------------------------------------");
        } else if (choice == 3) {
          System.out.println("Peeked value: " + queue.peek());
          System.out.println("-------------------------------------");
        } else if (choice == 4) {
          System.out.print("Enter the value to search: ");
          int val = sc.nextInt();
          if(queue.search(val)==-1) System.out.println(val+" Doesn't exists.");
          else System.out.println("Searched value is present at index: " + queue.search(val));
          System.out.println("-------------------------------------");
        } else {
          System.out.println("Program terminated successfully.");
          System.out.println("-------------------------------------");
          return;
        }

      }

    } catch (Exception e) {
      System.out.println(e);
    }

  }

}

class Node {
  int data;
  Node next;
}

class Queue {
  Node head, tail;
  int size;

  void add(int val) {
    Node temp = new Node(); //a temporary node
    temp.data = val;

    if (size == 0) {
      head = tail = temp;
    } else {
      tail.next = temp;
      tail = temp;
    }

    size++;
  }

  int remove() {
    if (size == 0) {
      System.out.println("List is empty");
      return -1;
    } else if (size == 1) {
      int headData = head.data;
      head = tail = null;
      size = 0;
      return headData;
    } else {
      int headData = head.data;
      head = head.next;
      size--;
      return headData;
    }
  }

  int peek() {
    if (size == 0) {
      System.out.println("List is empty");
      return -1;
    } else {
      return head.data;
    }
  }

  int search(int val) {
    Node temp = head;

    int idx = 0;  //index
    while (temp != null) {
      if (temp.data == val)
        return idx;
      temp = temp.next;
      idx++;
    }

    return -1;
  }

  void display(Node node) {
    if (node == null)
      return;

    System.out.print(node.data + " ");
    display(node.next);
  }

  int size() {
    return size;
  }

}
