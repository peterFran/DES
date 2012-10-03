/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package des;

/**
 *
 * @author petermeckiffe
 */
public class DES {

    Permutations perm;
    SBoxes sboxes;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String key = "1010101111001101111011110001001000110100010101101010101100010010";
        String block = "1010101111001101000100100011010011011100101110100100001100100001";
        DES des = new DES();
        System.out.println(BitwiseOps.getHex(des.doDEA(key, block)));
    }

    public DES() {
        this.perm = new Permutations();
        this.sboxes = new SBoxes();
    }

    public String doDEA(String key, String block) {
        // Get the key table
        String[] keys = this.getKeyTable(key);
        // Perform IP on entire block
        block = this.perm.initialPermutation(block);
        
        // Divide block in two
        String left = block.substring(0, 32);
        String right = block.substring(32);
        
        // Process 16 rounds
        for (int i = 0; i < 16; i++) {
            String leftTemp = left;
            left = BitwiseOps.getXOR(this.f(keys[i], right), left);
            right = leftTemp;
            System.out.println(BitwiseOps.getHex(left) + " " + BitwiseOps.getHex(right));
        }
        // Switch sides and do final permutation
        String encryption = this.perm.antiInitialPermutation(right+left);
        System.out.println(encryption);
        return encryption;
    }

    public String f(String key, String right) {
        
        // Expand Ri to 48 bits
        String eRight = this.perm.expansionPermutation(right);
        // XOR expanded bits with the key Ki
        String B = BitwiseOps.getXOR(eRight, key);
        // Send the result from the XOR through the s-boxes 
        // In order to bring it back down to 32 bits
        String C = this.sboxes.s(B);
        // Perform the final permutation and return the result
        return this.perm.finalPermutation(C);
    }

    

    public String[] getKeyTable(String key) {
        // Permutate key
        key = perm.keyPermutationA(key);
        
        // Create array for results and divide key
        String[] resultTable = new String[16];
        String keyC = key.substring(0, 28);
        String keyD = key.substring(28);
        
        // Cycle over keys 16 times
        for (int i = 0; i < 16; i++) {
            // Shift each side left by the required amount each round
            keyC = this.perm.leftShift(keyC, i);
            keyD = this.perm.leftShift(keyD, i);
            // Add the new key to the result table
            resultTable[i] = this.perm.keyPermutationB(keyC + keyD);
            System.out.println(BitwiseOps.getHex(resultTable[i]));
        }
        return resultTable;
    }
}