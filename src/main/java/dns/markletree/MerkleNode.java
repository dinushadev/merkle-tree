package dns.markletree;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MerkleNode {
    MerkleNode left, right;
    String hash, data;

    MerkleNode(String data) throws NoSuchAlgorithmException {
        this.data = data;
        this.hash = computeHash(data);
    }

    MerkleNode(MerkleNode left, MerkleNode right) throws NoSuchAlgorithmException {
        this.left = left;
        this.right = right;
        this.hash = computeHash(left.hash + right.hash);
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