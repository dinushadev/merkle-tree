//package dns.markletree;
//
//import java.util.Arrays;
//
//public class Main {
//
//    public static void main(String[] args) {
//        MerkleTree1 merkleTree = new MerkleTree1(Arrays.asList("data1", "data2", "data3", "data4"));
//
//        System.out.println("Merkle Root: " + merkleTree.getRootHash());
//
//        // Generate proof for data at index 0
//        System.out.println("Proof for data1: " + merkleTree.generateProof(0));
//
//        // Verify proof
//        String leafHash = merkleTree.hash("data1");
//        boolean isValid = merkleTree.verifyProof(leafHash, merkleTree.generateProof(0), merkleTree.getRootHash());
//        System.out.println("Proof verification for data1: " + isValid);
//
//        // Update a leaf
//        merkleTree.updateLeaf(1, "data2-new");
//        System.out.println("Merkle Root after update: " + merkleTree.getRootHash());
//
//        // Add a new leaf
//        merkleTree.addLeaf("data5");
//        System.out.println("Merkle Root after adding a new leaf: " + merkleTree.getRootHash());
//    }
//}
