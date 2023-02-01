import java.util.Scanner;

public class CustomHashMap {

  public static void main(String[] args) {

    try {
      Scanner sc = new Scanner(System.in);
      System.out.println("Enter the size of map: ");
      int mapSize = sc.nextInt();

      HashMap map = new HashMap(mapSize);

      while (true) {
        System.out.println("Your HashMap: ");
        map.display();
        System.out.println();

        System.out.println("Enter 1 to add");
        System.out.println("Enter 2 to remove");
        System.out.println("Enter 3 to get");
        System.out.println("Enter 4 to check whether a value exists \n");

        System.out.println("Note- key is of 'int' data type and value is of 'String' data type.");

        int choice = sc.nextInt();
        int key;
        String val;

        switch (choice) {
          case 1:
            System.out.print("Enter the Key: ");
            key = sc.nextInt();
            System.out.print("Enter the value: ");
            val = sc.next(); // value
            map.put(key, val);
            System.out.println("-------------------------------------");
            break;

          case 2:
            System.out.print("Enter the Key: ");
            key = sc.nextInt();
            map.remove(key);
            System.out.println("-------------------------------------");
            break;

          case 3:
            System.out.print("Enter the Key: ");
            key = sc.nextInt();
            HashMap.HMNode node = map.get(key);
            if (node == null)
              System.out.println("Doesn't exists");
            else
              System.out.println(node.value);
            break;

          case 4:
            System.out.print("Enter the Key: ");
            key = sc.nextInt();
            System.out.println(map.containsKey(key));
            System.out.println("-------------------------------------");
            break;

          default:
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

class HashMap {

  class HMNode { // Hashmap node, a key value pair node
    int key;
    String value;
    HMNode next;

    HMNode() {

    }

    HMNode(int key, String value) {
      this.key = key;
      this.value = value;
    }
  }

  class LinkedList {
    HMNode head, tail;
    int size;

    void add(HMNode node) {

      if (size == 0) {
        head = tail = node;
      } else {
        tail.next = node;
        tail = node;
      }
      size++;
    }

    void removeFirst() {
      if (size == 0) {
        System.out.println("List is empty");
      } else if (size == 1) {
        head = tail = null;
        size = 0;
      } else {
        head = head.next;
        size--;
      }
    }

    void removeLast() {
      if (size == 0) {
        System.out.println("List is empty");
      } else if (size == 1) {
        head = tail = null;
        size = 0;
      } else {
        HMNode temp = head;
        while (temp.next != tail) {
          temp = temp.next;
        }
        temp.next = null;
        tail = temp;
        size--;
      }
    }

    void remove(int idx) {
      if (idx < 0 || idx >= size) {
        System.out.println("Invalid arguments");
      } else if (idx == 0) {
        removeFirst();
      } else if (idx == size - 1) {
        removeLast();
      } else {

        HMNode temp = head;
        for (int i = 1; i < idx; i++) {
          temp = temp.next;
        }
        HMNode nnode = temp.next.next;
        temp.next = nnode;
        size--;
      }
    }

    HMNode get(int idx) {
      if (size == 0) {
        System.out.println("List is empty");
        return new HMNode();
      } else if (idx < 0 || idx >= size) {
        System.out.println("Invalid arguments");
        return new HMNode();
      }

      HMNode temp = head;
      for (int i = 1; i <= idx; i++) {
        temp = temp.next;
      }
      return temp;
    }

  }

  public int size;
  public LinkedList[] buckets; // array of linkedlists

  public HashMap(int n) {
    initbuckets(n);
  }

  public void initbuckets(int n) {
    buckets = new LinkedList[n];
    for (int bi = 0; bi < buckets.length; bi++) {
      buckets[bi] = new LinkedList();
    }
  }

  public void put(int key, String value) {
    int bi = hash(key);
    int di = getIndexWithinBucket(key, bi);
    if (di == -1) {
      buckets[bi].add(new HMNode(key, value));
      size++;
    } else {
      buckets[bi].get(di).value = value;

    }

  }

  public int hash(int key) {
    if (key < 0)
      key += buckets.length;
    return key % buckets.length;
  }

  public int getIndexWithinBucket(int key, int bi) {
    int di = 0;
    for (int i = 0; i < buckets[bi].size; i++) {
      if (buckets[bi].get(i).key == key)
        return di;
      di++;
    }

    return -1;
  }

  public HMNode get(int key) {
    int bi = hash(key);
    int di = getIndexWithinBucket(key, bi);
    if (di == -1) {
      return null;
    } else {
      return buckets[bi].get(di);
    }
  }

  public boolean containsKey(int key) {
    int bi = hash(key);
    int di = getIndexWithinBucket(key, bi);

    if (di == -1) {
      return false;
    } else {
      return true;
    }
  }

  public void remove(int key) {
    int bi = hash(key);
    int di = getIndexWithinBucket(key, bi);
    if (di == -1) {
      System.out.println(key + " doesn't exists");
      return;
    } else {
      buckets[bi].remove(di);
      size--;
    }
  }

  public int size() {
    return size;
  }

  public void display() {
    for (int i = 0; i < buckets.length; i++) {
      for (int j = 0; j < buckets[i].size; j++) {
        System.out.println("Key: " + buckets[i].get(j).key + " " + "Value: " + buckets[i].get(j).value);
      }
    }
  }

}
