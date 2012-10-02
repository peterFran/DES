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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String key = "1010101111001101111011110001001000110100010101101010101100010010";
        String block = "1010101111001101000100100011010011011100101110100100001100100001";
        Permutations perm = new Permutations();
        String m0 = perm.initialPermutation(block);
        
        KeyManipulation km = new KeyManipulation();
        String pKey = km.removeParity(key);
        String nKey = km.permutateKey(pKey, 1);
        
        String l0 = m0.substring(0, 32);
        String r0 = m0.substring(32, 64);
        String a1 = perm.expansionPermutation(r0);
        
        String xored = DES.get48XOR(a1, nKey);
        System.out.println(xored);

    }
    public static String get48XOR(String block, String key){
        String result="";
        System.out.println(block);
        System.out.println(key);
        char[] keyItems = block.toCharArray();
        char[] blockItems = key.toCharArray();
        for(int i = 0;i<48;i++){
            result += BitwiseOps.XOR(blockItems[i], keyItems[i]);
        }
        
        return result;
    }
}