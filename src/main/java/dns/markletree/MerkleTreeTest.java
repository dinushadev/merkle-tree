package dns.markletree;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class MerkleTreeTest {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        List<String> data = List.of("a", "b", "c", "d","e");
        MerkleTree tree = new MerkleTree(data);
        System.out.println("Root Hash: " + tree.getRootHash());

        String dataToProve = "c";
        List<String> proof = tree.generateMerkleProof(dataToProve);
        System.out.println("Proof of '" + dataToProve + "' Proof hash: " + proof);

        boolean isValid = tree.verifyMerkleProof(dataToProve, proof, tree.getRootHash());
        System.out.println("Valid: " + isValid);
    }
}