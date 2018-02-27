import static java.lang.Math.pow;

/**
 * Created by Isuru Samaranayake on 2/27/2018.
 */
public class Encoder {

    private  final String keyFunc;
    private final int [] permArray;
    private String oriText;

    public Encoder(String oriText, String keyFunc, int[] permArray) {
        this.oriText = oriText;
        this.keyFunc = keyFunc;
        this.permArray = permArray;


    }


    //substitute
    private String encryptText(String inText, String encryptFunct){
        int encryptedAscii = 0;
        StringBuffer outStringBuff = new StringBuffer();
        String functArray[] = encryptFunct.split("");

        int remainder = 0;

        if(inText.length()%8 !=0){
            remainder=inText.length()%8;
            for (int i =0; i<8-remainder;i++){
                inText+=" ";
            }
        }

        for (int i=0; i<inText.length();i++){
            encryptedAscii = 0;
            char s = inText.charAt(i);
            for (int j=0; j<encryptFunct.length();j++){
                encryptedAscii+= Integer.parseInt(functArray[j])* pow(i+1,j);
            }
            encryptedAscii = encryptedAscii%95;
            encryptedAscii = encryptedAscii+(int) s;
            encryptedAscii = encryptedAscii-32;        //convert to value in range
            encryptedAscii = encryptedAscii%95;
            encryptedAscii = encryptedAscii+32;

            outStringBuff.append((char) encryptedAscii);

        }


        //System.out.println("Encrypted " + outStringBuff.toString());
        System.out.println("Encrypted");
        return outStringBuff.toString() ;
    }

    //permutation
    private String transpositionString(String inString, int [] permutKey){


//        int remainder = 0;
//
//        if(inString.length()%8 !=0){
//            remainder=inString.length()%8;
//            for (int i =0; i<remainder;i++){
//                inString+=" ";
//            }
//        }
        StringBuffer outStringBuff = new StringBuffer();

        String [] tempArray = new String[8];
        String inArray[] = inString.split("");

        for (int i =0; i<inArray.length - 7;i+=8){
            for (int j=0; j<8; j++){
                tempArray[j]= inArray[i+j];
            }

            for (int k = 0; k<8;k++){
                inArray[i+k] = tempArray[permutKey[k]-1];
            }
        }
        for (int a=0; a<inArray.length;a++){
            outStringBuff.append(inArray[a]);
        }
        //System.out.println("Permutated "+outStringBuff.toString());
        return outStringBuff.toString();
    }

    public String getEncodedText(){


        return this.transpositionString(this.encryptText(this.oriText,keyFunc),this.permArray);
    }
}
