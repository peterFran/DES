/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package des;

/**
 *
 * @author petermeckiffe
 */
public class KeyManipulation {
    int[] shiftQ = {1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1};
    int[] reductionTable = {14, 7, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10,
23, 18, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2,
41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48,
44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32};
    
    public String removeParity(String key){
        String result = "";
        for(int i=0;i<64;i+=8){
            result += key.substring(i,i+7);
        }
        return result;
    }
    public String permutateKey(String key, int round){
        String k0 = key.substring(0, 28);
        String k1 = key.substring(28, 56);
        for(int i=0; i < shiftQ[round-1];i++){
            k0 = this.leftShift(k0);
            k1 = this.leftShift(k1);
        }
        return this.reduceKey(k0+k1);
        
    }
    private String leftShift(String key){
        String result = "";
        result += key.substring(1, 28);
        result += key.charAt(0);
        return result;
    }
    private String reduceKey(String key){
        String resultString = "";
        for(int i:reductionTable){
            resultString += key.charAt(i-1);
        }
        return resultString;
    }
}
