import java.util.Random;

class Main {
    public static void main(String[] args) {
        MyHashTable<MyTestingClass, Student> table = new MyHashTable<>();
        Random random = new Random();

        // Adding random 10000 elements
        for (int i = 0; i < 10000; i++) {
            table.put(new MyTestingClass(i), new Student("Student" + random.nextInt(10000)));
        }

        // Accessing and printing the chain array
        MyHashTable<MyTestingClass, Student>.HashNode<MyTestingClass, Student>[] chainArray = table.getChainArray();
        for (int i = 0; i < chainArray.length; i++) {
            int count = 0;
            MyHashTable<MyTestingClass, Student>.HashNode<MyTestingClass, Student> node = chainArray[i];
            while (node != null) {
                count++;
                node = node.next;
            }
            System.out.println("Bucket " + i + " has " + count + " elements.");
        }
    }
}

class MyTestingClass {
    private int id;

    public MyTestingClass(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        return prime * id ^ (id >>> 16);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MyTestingClass that = (MyTestingClass) obj;
        return id == that.id;
    }
}

class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}