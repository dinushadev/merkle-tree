package dns.markletree;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MerkleTree {
    private final MerkleNode root;

    public MerkleTree(List<String> data) throws NoSuchAlgorithmException {
        List<MerkleNode> nodes = new ArrayList<>();
        for (String datum : data) {
            nodes.add(new MerkleNode(datum));
        }
        this.root = buildTree(nodes);
    }

    private MerkleNode buildTree(List<MerkleNode> nodes) throws NoSuchAlgorithmException {
        while (nodes.size() > 1) {
            List<MerkleNode> nextLevel = new ArrayList<>();
            for (int i = 0; i < nodes.size(); i += 2) {
                if (i + 1 < nodes.size()) {
                    nextLevel.add(new MerkleNode(nodes.get(i), nodes.get(i + 1)));
                } else {
                    nextLevel.add(nodes.get(i)); // Handle odd number of nodes
                }
            }
            nodes = nextLevel;
        }
        return nodes.get(0);
    }

    public String getRootHash() {
        return this.root.hash;
    }

    public List<String> generateMerkleProof(String data) throws NoSuchAlgorithmException {
        List<String> proof = new ArrayList<>();
        if (findNode(root, data, proof)) {
            return proof;
        }
        return proof;
    }

    private boolean findNode(MerkleNode current, String data, List<String> proof) {
        if (current == null) return false;
        if (current.data != null && current.data.equals(data)) {
            return true;
        }

        boolean foundInLeft = findNode(current.left, data, proof);
        boolean foundInRight = findNode(current.right, data, proof);

        if (foundInLeft && current.right != null) {
            proof.add("R" + current.right.hash);  // Add right sibling's hash with prefix "R"
            return true;
        } else if (foundInRight && current.left != null) {
            proof.add("L" + current.left.hash);  // Add left sibling's hash with prefix "L"
            return true;
        }

        return false;
    }

    public boolean verifyMerkleProof(String data, List<String> proof, String rootHash) throws NoSuchAlgorithmException {
        String currentHash = computeHash(data);
        StringBuilder hashBuilder = new StringBuilder();

        for (String proofHash : proof) {
            hashBuilder.setLength(0);  // Reset StringBuilder for each iteration

            if (proofHash.startsWith("L")) {
                hashBuilder.append(proofHash.substring(1)).append(currentHash);
            } else {
                hashBuilder.append(currentHash).append(proofHash.substring(1));
            }

            currentHash = computeHash(hashBuilder.toString());
        }
        return currentHash.equals(rootHash);
    }

    private String computeHash(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(data.getBytes());
        StringBuilder hexString = new StringBuilder(hashBytes.length * 2);
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}