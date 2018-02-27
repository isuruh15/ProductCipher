import java.io.*;

/**
 * Created by Isuru Samaranayake on 2/27/2018.
 */
public class IOHandler {
    private String readIn;
    private String writeOut;

    private String path;

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public String readFrom(String filePath) {
        readIn = "";
        this.path = filePath;
        try {
            bufferedReader = new BufferedReader(new FileReader(path));

            while (bufferedReader.ready()) {
                readIn = readIn + bufferedReader.readLine() + " ";

            }

            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not Found!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return readIn;
    }

    public void writeTo(String string, String path) {


        try {
            bufferedWriter = new BufferedWriter(new FileWriter(path));

            bufferedWriter.write(string);

            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
