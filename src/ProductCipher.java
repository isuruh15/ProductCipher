import com.sun.deploy.util.ArrayUtil;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.pow;

/**
 * Created by Isuru Samaranayake on 2/20/2018.
 */
public class ProductCipher {

    private String inputString;
    private String pathString;
    private String choise;

    private String inText;
    private String outText;

    private String keyFunc;
    private int [] permArray;


    public ProductCipher(){

//        System.out.println("Encrypt of Decrypt: \n1.Encrypt \n2.Decrypt");
//        choise = new Scanner(System.in).nextLine();
//
//        System.out.println("Enter the plain text to be encrypted:");
//        pathString = new Scanner(System.in).nextLine();
//
//        System.out.println("Enter password:");
//        inputString = new Scanner(System.in).nextLine();
//
//
//        keyFunc = this.substituteString(inputString);
//        permArray = permutArray(keyFunc);


    }

    public static void main(String args[]){
        ProductCipher pc1 = new ProductCipher();
        IOHandler ioh1 = new IOHandler();

        System.out.println("Encrypt or Decrypt: \n1.Encrypt \n2.Decrypt");
        pc1.choise = new Scanner(System.in).nextLine();
        System.out.println(pc1.choise);

        if(pc1.choise.equals("1")){
            System.out.println("Enter the File Path to Encrypt:");
            pc1.pathString = new Scanner(System.in).nextLine();

            System.out.println("Enter password:");
            pc1.inputString = new Scanner(System.in).nextLine();
            pc1.keyFunc = pc1.substituteString(pc1.inputString);
            pc1.permArray = pc1.permutArray(pc1.keyFunc);
            pc1.inText =ioh1.readFrom(pc1.pathString);

            Encoder encoder = new Encoder(pc1.inText,pc1.keyFunc,pc1.permArray);
            ioh1.writeTo(encoder.getEncodedText(),"Encoded.txt");

        }else if (pc1.choise.equals("2")){
            System.out.println("Enter the File Path to Decrypt:");
            pc1.pathString = new Scanner(System.in).nextLine();

            System.out.println("Enter password:");
            pc1.inputString = new Scanner(System.in).nextLine();
            pc1.keyFunc = pc1.substituteString(pc1.inputString);
            pc1.permArray = pc1.permutArray(pc1.keyFunc);
            pc1.inText =ioh1.readFrom(pc1.pathString);

            Decoder decoder = new Decoder(pc1.inText,pc1.keyFunc,pc1.permArray);

            ioh1.writeTo(decoder.getDecoded(),"decoded.txt");


        }

    }

    //Generate substitution key
    private String substituteString(String inString){
        double innerKey = 0;
        String functCoef = "";

        for(int i=0; i<inString.length();i++){      //substitution key generation
            char c = inString.charAt(i);
            innerKey=innerKey +  pow(c,i);
        }
        innerKey = innerKey%100000000;           //innerKey to determine function with order 8
        functCoef = Integer.toString((int) innerKey);


        //System.out.println("key "+functCoef);
        return functCoef;
    }


    //substitute
/*
    private String encryptText(String inText, String encryptFunct){
        int encryptedAscii = 0;
        StringBuffer outStringBuff = new StringBuffer();
        String functArray[] = encryptFunct.split("");

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
        System.out.println("Encrypted "+outStringBuff.toString());
        return outStringBuff.toString() ;
    }


    //decrypt - substitute
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


        System.out.println("decrypted "+outStringBuff.toString());
        return outStringBuff.toString() ;
    }

*/
    //generate permutation array
    private int[] permutArray(String funcCoef){
        int[] permutationArray = new int[8];
        int count = 0;

        //int intFuncCoef = Integer.parseInt(funcCoef);


            while (count<funcCoef.length()){
                if (checkArray(permutationArray,Integer.parseInt(Character.toString(funcCoef.charAt(count))))){
                    permutationArray[count]=getSmallestPossible(permutationArray);
                }else{
                    permutationArray[count]=Integer.parseInt(Character.toString(funcCoef.charAt(count)));
                }
                count+=1;
            }
        while(count<8){
            permutationArray[count]= getSmallestPossible(permutationArray);
            count+=1;
        }
        //System.out.println(Arrays.toString(permutationArray));
        return permutationArray;
    }

    //check for availability
    private boolean checkArray(int [] array, int key){
        for(int i=0;i<array.length;i++){
            if(array[i]==key){
                return true;
            }
        }
        return false;
    }

    //take next value to append
    private int getSmallestPossible(int [] array){
        for(int i=1;i<array.length+1;i++){
            if(checkArray(array,i)==false){
                return i;
            }
        }
        return 100;
    }
/*
    //permutation
    private String transpositionString(String inString, int [] permutKey){

        int remainder = 0;

        if(inString.length()%8 !=0){
            remainder=inString.length()%8;
            for (int i =0; i<remainder;i++){
                inString+=" ";
            }
        }
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
        System.out.println("Permutated "+outStringBuff.toString());
        return outStringBuff.toString();
    }

    //permutation inverse
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
        System.out.println("Depermutated "+outStringBuff.toString());
        return outStringBuff.toString();

    }
*/
}
