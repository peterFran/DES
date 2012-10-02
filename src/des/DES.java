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
        System.out.println(des.doDEA(key, block));
//        String[] keys = des.getKeyTable(key);
//        block = des.perm.initialPermutation(block);
//        String leftHalf = block.substring(0, 32);
//        String rightHalf = block.substring(32);
        //System.out.println(DES.getXOR(des.f(keys[0], rightHalf), leftHalf));
//        String afterExp = des.perm.expansionPermutation(rightHalf);
//        String afterXOR=DES.getXOR(afterExp, keys[0]);
//        String afterSBox = des.sboxes.doSboxes(afterXOR);
//        String afterFinalPerm = des.perm.finalPermutation(afterSBox);
//        String afterXORLeftHalf = DES.getXOR(leftHalf, afterFinalPerm);
        //System.out.println(des.perm.leftShift(permKey.substring(28), 0));
        //System.out.println(des.getHex(des.doDEARound(keyA, block.substring(32, 64))));
    }

    public DES() {
        this.perm = new Permutations();
        this.sboxes = new SBoxes();
    }

    public static String getXOR(String block, String key) {
        String result = "";
        char[] keyItems = block.toCharArray();
        char[] blockItems = key.toCharArray();
        for (int i = 0; i < keyItems.length; i++) {
            result += BitwiseOps.XOR(blockItems[i], keyItems[i]);
        }
        return result;
    }

    public String doDEA(String key, String block) {

        String[] keys = this.getKeyTable(key);
        block = this.perm.initialPermutation(block);
        String leftHalf = block.substring(0, 32);
        String rightHalf = block.substring(32);
        for (int i = 0; i < 16; i++) {
            String leftHalfTemp = leftHalf;
            leftHalf = DES.getXOR(this.f(keys[i], rightHalf), leftHalf);
            rightHalf = leftHalfTemp;
            System.out.println(this.getHex(leftHalf) + " " + this.getHex(rightHalf));
        }
        //String encryption = this.perm.antiInitialPermutation(rightHalf+leftHalf);
        //System.out.println(encryption);
        return "a";//encryption;
    }

    public String f(String key, String rightHalf) {
        String afterExp = this.perm.expansionPermutation(rightHalf);
        String afterXOR = DES.getXOR(afterExp, key);
        String afterSBox = this.sboxes.doSboxes(afterXOR);
        String afterFinalPerm = this.perm.finalPermutation(afterSBox);
        return afterFinalPerm;
    }

    public void getBinary(String hex) {
        String bin = Long.toBinaryString(Long.parseLong(hex, 16));
        System.out.println(bin);
    }

    public String getHex(String bin) {
        return Long.toHexString(Long.parseLong(bin, 2));
    }

    public String[] getKeyTable(String key) {
        key = perm.keyPermutationA(key);
        String keyC;
        String keyD;
        String[] resultTable = new String[16];
        keyC = key.substring(0, 28);
        keyD = key.substring(28);
        for (int i = 0; i < 16; i++) {
            keyC = this.perm.leftShift(keyC, i);
            keyD = this.perm.leftShift(keyD, i);
            resultTable[i] = this.perm.keyPermutationB(keyC + keyD);
        }
        return resultTable;
    }
}