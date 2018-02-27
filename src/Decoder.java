import static java.lang.Math.pow;

/**
 * Created by Isuru Samaranayake on 2/27/2018.
 */
public class Decoder {
    private  final String keyFunc;
    private final int [] permArray;
    private String encText;

    public Decoder(String encText, String keyFunc, int[] permArray) {
        this.encText = encText;
        this.keyFunc = keyFunc;
        this.permArray = permArray;
    }

    private String detranspositionString(String transposedString, int [] permutKey){
        StringBuffer outStringBuff = new StringBuffer();

        String [] tempArray = new String[8];
        String inArray[] = transposedString.split("");

        for (int i =0; i<transposedString.length() - 7;i+=8){
            for (int j=0; j<8; j++){
                tempArray[j]= inArray[i+j];
            }

            for (int k = 0; k<8;k++){
                inArray[i+permutKey[k]-1] = tempArray[k];
            }
        }
        for (int a=0; a<inArray.length;a++){
            outStringBuff.append(inArray[a]);
        }
        //System.out.println("Depermutated "+outStringBuff.toString());
        return outStringBuff.toString();

    }

    private String decryptText(String encrptdText, String encryptFunct){
        int encryptedAscii = 0;
        StringBuffer outStringBuff = new StringBuffer();
        String functArray[] = encryptFunct.split("");

        for (int i=0; i<encrptdText.length();i++){
            encryptedAscii = 0;
            char s = encrptdText.charAt(i);
            for (int j=0; j<encryptFunct.length();j++){
                encryptedAscii+= Integer.parseInt(functArray[j])* pow(i+1,j);
            }
            encryptedAscii = encryptedAscii%95;
            encryptedAscii = (int) s - encryptedAscii;

            if (encryptedAscii<32){
                encryptedAscii+=95;
            }

            outStringBuff.append((char) encryptedAscii);

        }


        //System.out.println("decrypted "+outStringBuff.toString());
        System.out.println("Decrypted");
        return outStringBuff.toString() ;
    }

    public String getDecoded(){

        return this.decryptText(detranspositionString(encText,permArray),keyFunc);

    }


}
