import java.util.Scanner;

// Asset class to hold market asset details
class Asset {
    int id;
    String name;
    String type;      // e.g., Crypto, Stock, Commodity, Index
    double price;
    double change;    // Percentage change

    public Asset(int id, String name, String type, double price, double change) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.change = change;
    }

    public void display() {
        System.out.printf("ID: %-4d | Name: %-12s | Type: %-10s | Price: $%-8.2f | Change: %+.2f%%\n",
                id, name, type, price, change);
    }
}

// Node class for Singly Linked List (Watchlist)
class Node {
    Asset asset;
    Node next;

    public Node(Asset asset) {
        this.asset = asset;
        this.next = null;
    }
}

// Watchlist implementation using Singly Linked List
class Watchlist {
    private Node head;

    public void insert(Asset asset) {
        Node newNode = new Node(asset);
        if (head == null) {
            head = newNode;
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
        System.out.println("Asset " + asset.name + " added to Watchlist.");
    }

    public void delete(int id) {
        if (head == null) {
            System.out.println("Watchlist is empty.");
            return;
        }
        if (head.asset.id == id) {
            System.out.println("Removed " + head.asset.name + " from Watchlist.");
            head = head.next;
            return;
        }
        Node temp = head;
        while (temp.next != null && temp.next.asset.id != id) {
            temp = temp.next;
        }
        if (temp.next == null) {
            System.out.println("Asset ID not found in Watchlist.");
        } else {
            System.out.println("Removed " + temp.next.asset.name + " from Watchlist.");
            temp.next = temp.next.next;
        }
    }

    public void search(int id) {
        Node temp = head;
        while (temp != null) {
            if (temp.asset.id == id) {
                System.out.println("Asset found in Watchlist:");
                temp.asset.display();
                return;
            }
            temp = temp.next;
        }
        System.out.println("Asset ID " + id + " not found in Watchlist.");
    }

    public void display() {
        if (head == null) {
            System.out.println("Watchlist is empty.");
            return;
        }
        System.out.println("\n--- Investor Watchlist ---");
        Node temp = head;
        while (temp != null) {
            temp.asset.display();
            temp = temp.next;
        }
    }
}

// Transaction Node for Stack
class StackNode {
    String transactionDetails;
    StackNode next;

    public StackNode(String details) {
        this.transactionDetails = details;
        this.next = null;
    }
}

// Stack implementation for Recent Transactions (LIFO)
class TransactionStack {
    private StackNode top;

    public void push(String details) {
        StackNode newNode = new StackNode(details);
        newNode.next = top;
        top = newNode;
        System.out.println("Transaction recorded: " + details);
    }

    public void pop() {
        if (top == null) {
            System.out.println("Stack Underflow! No recent transactions to undo.");
            return;
        }
        System.out.println("Removed/Undone Transaction: " + top.transactionDetails);
        top = top.next;
    }

    public void peek() {
        if (top == null) {
            System.out.println("Stack is empty.");
            return;
        }
        System.out.println("Most Recent Transaction (Top): " + top.transactionDetails);
    }

    public void display() {
        if (top == null) {
            System.out.println("No recent transactions.");
            return;
        }
        System.out.println("\n--- Recent Transactions (LIFO Order) ---");
        StackNode temp = top;
        while (temp != null) {
            System.out.println("[Recent] " + temp.transactionDetails);
            temp = temp.next;
        }
    }
}

// Order Node for Queue
class QueueNode {
    String orderDetails;
    QueueNode next;

    public QueueNode(String details) {
        this.orderDetails = details;
        this.next = null;
    }
}

// Queue implementation for Trading Orders (FIFO)
class OrderQueue {
    private QueueNode front;
    private QueueNode rear;

    public void enqueue(String details) {
        QueueNode newNode = new QueueNode(details);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        System.out.println("Order placed: " + details);
    }

    public void dequeue() {
        if (front == null) {
            System.out.println("Queue Underflow! No pending orders to process.");
            return;
        }
        System.out.println("Processed Order: " + front.orderDetails);
        front = front.next;
        if (front == null) {
            rear = null;
        }
    }

    public void peek() {
        if (front == null) {
            System.out.println("Queue is empty.");
            return;
        }
        System.out.println("Next Order to Process (Front): " + front.orderDetails);
    }

    public void display() {
        if (front == null) {
            System.out.println("No pending trading orders.");
            return;
        }
        System.out.println("\n--- Pending Trading Orders (FIFO Order) ---");
        QueueNode temp = front;
        while (temp != null) {
            System.out.println("[Pending] " + temp.orderDetails);
            temp = temp.next;
        }
    }
}

// Main Application
public class FintechTradingSystem {

    // Updated dataset of 10 financial assets
    private static Asset[] assets = {
        new Asset(201, "Cardano", "Crypto", 0.45, 3.2),
        new Asset(202, "Google", "Stock", 175.50, -1.1),
        new Asset(203, "Silver", "Commodity", 28.40, 1.8),
        new Asset(204, "Polkadot", "Crypto", 6.20, -0.5),
        new Asset(205, "AMD", "Stock", 160.00, 5.4),
        new Asset(206, "S&P 500", "Index", 5450.00, 0.7),
        new Asset(207, "Intel", "Stock", 31.20, -2.8),
        new Asset(208, "Ripple", "Crypto", 0.58, 4.0),
        new Asset(209, "Netflix", "Stock", 680.30, 2.1),
        new Asset(210, "Copper", "Commodity", 4.15, -0.9)
    };

    private static Watchlist watchlist = new Watchlist();
    private static TransactionStack transactionStack = new TransactionStack();
    private static OrderQueue orderQueue = new OrderQueue();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int mainChoice;
        do {
            System.out.println("\n==========================================");
            System.out.println("   FINTECH TRADING MANAGEMENT SYSTEM");
            System.out.println("==========================================");
            System.out.println("1. Manage Market Assets");
            System.out.println("2. Search Asset");
            System.out.println("3. Sort Market Assets");
            System.out.println("4. Manage Watchlist");
            System.out.println("5. Calculate Portfolio Value");
            System.out.println("6. Manage Transactions");
            System.out.println("7. Manage Trading Orders");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            mainChoice = scanner.nextInt();

            switch (mainChoice) {
                case 1:
                    manageMarketAssets();
                    break;
                case 2:
                    searchAssetMenu();
                    break;
                case 3:
                    sortAssetMenu();
                    break;
                case 4:
                    manageWatchlistMenu();
                    break;
                case 5:
                    calculatePortfolioMenu();
                    break;
                case 6:
                    manageTransactionsMenu();
                    break;
                case 7:
                    manageOrdersMenu();
                    break;
                case 0:
                    System.out.println("Exiting System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option! Try again.");
            }
        } while (mainChoice != 0);
    }

    // --- A. ARRAYS ---
    private static void manageMarketAssets() {
        int choice;
        do {
            System.out.println("\n--- MARKET ASSETS ---");
            System.out.println("1. Display All Assets");
            System.out.println("2. Calculate Average Price");
            System.out.println("3. Find Highest-Priced Asset");
            System.out.println("4. Find Lowest-Priced Asset");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n--- All Market Assets ---");
                    for (Asset a : assets) {
                        a.display();
                    }
                    break;
                case 2:
                    double sum = 0;
                    for (Asset a : assets) {
                        sum += a.price;
                    }
                    double avg = sum / assets.length;
                    System.out.printf("Average Price of Assets: $%.2f\n", avg);
                    break;
                case 3:
                    Asset highest = assets[0];
                    for (int i = 1; i < assets.length; i++) {
                        if (assets[i].price > highest.price) {
                            highest = assets[i];
                        }
                    }
                    System.out.println("Highest-Priced Asset:");
                    highest.display();
                    break;
                case 4:
                    Asset lowest = assets[0];
                    for (int i = 1; i < assets.length; i++) {
                        if (assets[i].price < lowest.price) {
                            lowest = assets[i];
                        }
                    }
                    System.out.println("Lowest-Priced Asset:");
                    lowest.display();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    // --- B. SEARCHING ---
    private static void searchAssetMenu() {
        int choice;
        do {
            System.out.println("\n--- SEARCH ASSET ---");
            System.out.println("1. Linear Search (by Asset ID)");
            System.out.println("2. Binary Search (by Asset ID)");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            if (choice == 1) {
                System.out.print("Enter Asset ID to search: ");
                int id = scanner.nextInt();
                linearSearch(id);
            } else if (choice == 2) {
                System.out.print("Enter Asset ID to search: ");
                int id = scanner.nextInt();
                binarySearch(id);
            }
        } while (choice != 0);
    }

    private static void linearSearch(int id) {
        boolean found = false;
        for (int i = 0; i < assets.length; i++) {
            if (assets[i].id == id) {
                System.out.println("Asset found at index " + i + " using Linear Search:");
                assets[i].display();
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Asset with ID " + id + " not found.");
        }
    }

    private static void binarySearch(int id) {
        quickSortById(0, assets.length - 1);

        int low = 0;
        int high = assets.length - 1;
        boolean found = false;

        System.out.println("Sorting assets by ID for Binary Search...");
        while (low <= high) {
            int mid = low + (high - low) / 2;
            System.out.println("[Tracing] Low: " + low + " | Mid: " + mid + " | High: " + high + " | Checking ID: " + assets[mid].id);

            if (assets[mid].id == id) {
                System.out.println("Asset found at index " + mid + " using Binary Search:");
                assets[mid].display();
                found = true;
                break;
            }

            if (assets[mid].id < id) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        if (!found) {
            System.out.println("Asset with ID " + id + " not found.");
        }
    }

    // --- C. SORTING ALGORITHMS ---
    private static void sortAssetMenu() {
        int choice;
        do {
            System.out.println("\n--- SORT MARKET ASSETS ---");
            System.out.println("1. Bubble Sort (by Price)");
            System.out.println("2. Selection Sort (by Percentage Change)");
            System.out.println("3. Insertion Sort (by Asset ID)");
            System.out.println("4. Merge Sort (by Price)");
            System.out.println("5. Quick Sort (by Asset ID)");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    bubbleSort();
                    System.out.println("Assets sorted by Price (Bubble Sort):");
                    displayAll();
                    break;
                case 2:
                    selectionSort();
                    System.out.println("Assets sorted by Percentage Change (Selection Sort):");
                    displayAll();
                    break;
                case 3:
                    insertionSort();
                    System.out.println("Assets sorted by Asset ID (Insertion Sort):");
                    displayAll();
                    break;
                case 4:
                    mergeSort(0, assets.length - 1);
                    System.out.println("Assets sorted by Price (Merge Sort):");
                    displayAll();
                    break;
                case 5:
                    quickSortById(0, assets.length - 1);
                    System.out.println("Assets sorted by Asset ID (Quick Sort):");
                    displayAll();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    private static void bubbleSort() {
        int n = assets.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (assets[j].price > assets[j + 1].price) {
                    Asset temp = assets[j];
                    assets[j] = assets[j + 1];
                    assets[j + 1] = temp;
                }
            }
        }
    }

    private static void selectionSort() {
        int n = assets.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (assets[j].change < assets[minIdx].change) {
                    minIdx = j;
                }
            }
            Asset temp = assets[minIdx];
            assets[minIdx] = assets[i];
            assets[i] = temp;
        }
    }

    private static void insertionSort() {
        int n = assets.length;
        for (int i = 1; i < n; i++) {
            Asset key = assets[i];
            int j = i - 1;
            while (j >= 0 && assets[j].id > key.id) {
                assets[j + 1] = assets[j];
                j = j - 1;
            }
            assets[j + 1] = key;
        }
    }

    private static void mergeSort(int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(l, m);
            mergeSort(m + 1, r);
            merge(l, m, r);
        }
    }

    private static void merge(int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        Asset[] L = new Asset[n1];
        Asset[] R = new Asset[n2];

        for (int i = 0; i < n1; ++i) L[i] = assets[l + i];
        for (int j = 0; j < n2; ++j) R[j] = assets[m + 1 + j];

        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) {
            if (L[i].price <= R[j].price) {
                assets[k] = L[i];
                i++;
            } else {
                assets[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            assets[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            assets[k] = R[j];
            j++;
            k++;
        }
    }

    private static void quickSortById(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
            quickSortById(low, pi - 1);
            quickSortById(pi + 1, high);
        }
    }

    private static int partition(int low, int high) {
        int pivot = assets[high].id;
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (assets[j].id < pivot) {
                i++;
                Asset temp = assets[i];
                assets[i] = assets[j];
                assets[j] = temp;
            }
        }
        Asset temp = assets[i + 1];
        assets[i + 1] = assets[high];
        assets[high] = temp;
        return i + 1;
    }

    // --- D. WATCHLIST (LINKED LIST) ---
    private static void manageWatchlistMenu() {
        int choice;
        do {
            System.out.println("\n--- MANAGE WATCHLIST ---");
            System.out.println("1. Add Asset to Watchlist");
            System.out.println("2. Delete Asset from Watchlist");
            System.out.println("3. Search Asset in Watchlist");
            System.out.println("4. Display Watchlist");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Asset ID to add: ");
                    int idAdd = scanner.nextInt();
                    Asset foundAdd = findAssetById(idAdd);
                    if (foundAdd != null) watchlist.insert(foundAdd);
                    else System.out.println("Asset ID not found in market!");
                    break;
                case 2:
                    System.out.print("Enter Asset ID to delete: ");
                    int idDel = scanner.nextInt();
                    watchlist.delete(idDel);
                    break;
                case 3:
                    System.out.print("Enter Asset ID to search: ");
                    int idSearch = scanner.nextInt();
                    watchlist.search(idSearch);
                    break;
                case 4:
                    watchlist.display();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    // --- E. RECURSION - PORTFOLIO ---
    private static void calculatePortfolioMenu() {
        int[] quantities = {1000, 20, 100, 500, 15, 2, 50, 2000, 10, 300};

        System.out.println("\n--- PORTFOLIO DETAILS (RECURSIVE DISPLAY) ---");
        displayPortfolioRecursive(assets, quantities, 0);

        double totalValue = calculatePortfolioValueRecursive(assets, quantities, 0);
        System.out.printf("\nTotal Calculated Portfolio Value (Recursive): $%.2f\n", totalValue);
    }

    private static double calculatePortfolioValueRecursive(Asset[] assets, int[] quantities, int index) {
        if (index == assets.length) {
            return 0;
        }
        return (assets[index].price * quantities[index]) + calculatePortfolioValueRecursive(assets, quantities, index + 1);
    }

    private static void displayPortfolioRecursive(Asset[] assets, int[] quantities, int index) {
        if (index == assets.length) {
            return;
        }
        System.out.printf("Holding: %-12s | Price: $%-8.2f | Qty: %-4d | Subtotal: $%.2f\n",
                assets[index].name, assets[index].price, quantities[index], (assets[index].price * quantities[index]));
        displayPortfolioRecursive(assets, quantities, index + 1);
    }

    // --- F. STACK - RECENT TRANSACTIONS ---
    private static void manageTransactionsMenu() {
        int choice;
        do {
            System.out.println("\n--- MANAGE TRANSACTIONS (STACK - LIFO) ---");
            System.out.println("1. Record New Transaction (Push)");
            System.out.println("2. Undo/Remove Last Transaction (Pop)");
            System.out.println("3. View Most Recent Transaction (Peek)");
            System.out.println("4. Display All Recent Transactions");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter transaction details (e.g., 'BUY 50 GOOGL'): ");
                    String details = scanner.nextLine();
                    transactionStack.push(details);
                    break;
                case 2:
                    transactionStack.pop();
                    break;
                case 3:
                    transactionStack.peek();
                    break;
                case 4:
                    transactionStack.display();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    // --- G. QUEUE - TRADING ORDERS ---
    private static void manageOrdersMenu() {
        int choice;
        do {
            System.out.println("\n--- MANAGE TRADING ORDERS (QUEUE - FIFO) ---");
            System.out.println("1. Place New Order (Enqueue)");
            System.out.println("2. Process Next Order (Dequeue)");
            System.out.println("3. View Next Order in Line (Peek)");
            System.out.println("4. Display All Pending Orders");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter trading order details (e.g., 'SELL 100 AMD'): ");
                    String details = scanner.nextLine();
                    orderQueue.enqueue(details);
                    break;
                case 2:
                    orderQueue.dequeue();
                    break;
                case 3:
                    orderQueue.peek();
                    break;
                case 4:
                    orderQueue.display();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    private static Asset findAssetById(int id) {
        for (Asset a : assets) {
            if (a.id == id) return a;
        }
        return null;
    }

    private static void displayAll() {
        for (Asset a : assets) {
            a.display();
        }
    }
}