public class RedBlackTree {
    private Node root;

    // insert product into Red-Black Tree
    public boolean insert(String productId, String productName, String category, double price) {
        Node existingNode = searchNode(root, productId);
        if (existingNode != null) {
            // return false indicating insertion failure
            return false;
        }

        // Proceed with regular insertion logic if the product does not exist
        root = insertRec(root, productId, productName, category, price);
        return true;
    }

    // update product details if product with ID exists
    public void updateProduct(String productId, String productName, String category, double price) {
        Node existingNode = searchNode(root, productId);
        if (existingNode != null) {
            // Update product details in existing node
            existingNode.productName = productName;
            existingNode.category = category;
            existingNode.price = price;
            System.out.println("Product updated: [ID: " + productId + ", Name: " + productName + ", Category: " + category + ", Price: " + price + "]");
        } else {
            System.out.println("Error: Product with ID " + productId + " not found.");
        }
    }

    //search for product node by productId
    private Node searchNode(Node root, String productId) {
        if (root == null || root.productId.equals(productId)) {
            return root;
        }

        if (productId.compareTo(root.productId) < 0) {
            return searchNode(root.left, productId);
        } else {
            return searchNode(root.right, productId);
        }
    }

    // insert product node into tree
    private Node insertRec(Node root, String productId, String productName, String category, double price) {
        if (root == null) {
            root = new Node(productId, productName, category, price);
            return root;
        }

        if (productId.compareTo(root.productId) < 0) {
            root.left = insertRec(root.left, productId, productName, category, price);
        } else {
            root.right = insertRec(root.right, productId, productName, category, price);
        }

        return root;
    }

    //search for product by product-Id
    public void searchById(String productId) {
        Node node = searchNode(root, productId);
        if (node != null) {
            System.out.println("Product found: [ID: " + node.productId + ", Name: " + node.productName + ", Category: " + node.category + ", Price: " + node.price + "]");
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }
    }

    //Node for Red-Black Tree
    private static class Node {
        String productId;
        String productName;
        String category;
        double price;
        Node left, right;
        //Red-Black Tree properties color, parent, etc.

        Node(String productId, String productName, String category, double price) {
            this.productId = productId;
            this.productName = productName;
            this.category = category;
            this.price = price;
            this.left = this.right = null;
        }
    }
}
