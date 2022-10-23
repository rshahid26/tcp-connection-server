import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket serverSocket;
    Socket clientSocket;

    private String name;
    private int npl;
    private int lpl;
    private double shares;
    private double wpm;
    private double accuracy;

    public Server() {
        try {
            while(true) {
                this.serverSocket = new ServerSocket(31002);
                this.clientSocket = this.serverSocket.accept();
                DataInputStream dataInputStream = new DataInputStream(this.clientSocket.getInputStream());
                int fileContentLength = dataInputStream.readInt();
                byte[] fileContentBytes = new byte[fileContentLength];
                dataInputStream.readFully(fileContentBytes, 0, fileContentLength);

                try {
                    this.byteToTyping(fileContentBytes);
                    TypingExcel typingExcel = new TypingExcel();
                    typingExcel.addToExcel(this);
                } catch (NumberFormatException var7) {
                    try {
                        this.byteToTrading(fileContentBytes);
                        TradingExcel tradingExcel = new TradingExcel();
                        tradingExcel.addToExcel(this);
                    } catch (NumberFormatException var6) {
                    }
                }

                this.clientSocket.close();
                this.serverSocket.close();
            }
        } catch (IOException var8) {
            new Server();
        }
    }

    public void byteToTyping(byte[] fileContentBytes) {
        int newLineCount = 0;
        byte[] var3 = fileContentBytes;
        int var4 = fileContentBytes.length;

        int var5;
        for(var5 = 0; var5 < var4; var5++) {
            byte letter = var3[var5];
            if (Character.toString((char)letter).equals("\n")) {
                ++newLineCount;
            }

            if (newLineCount == 4) {
                throw new NumberFormatException();
            }
        }

        String str = "";
        newLineCount = 0;
        byte[] var9 = fileContentBytes;
        var5 = fileContentBytes.length;

        for(int var10 = 0; var10 < var5; var10++) {
            byte letter = var9[var10];
            if (Character.toString((char)letter).equals("\n")) {
                System.out.println(str);
                newLineCount++;
                if (newLineCount == 1) {
                    this.name = str;
                }

                if (newLineCount == 2) {
                    this.wpm = Double.parseDouble(str);
                }

                if (newLineCount == 3) {
                    this.accuracy = Double.parseDouble(str);
                }

                str = "";
            } else {
                str = str + Character.toString((char)letter);
            }
        }

        System.out.println("-----");
    }

    public void byteToTrading(byte[] fileContentBytes) {
        String str = "";
        int newLineCount = 0;
        byte[] var4 = fileContentBytes;
        int var5 = fileContentBytes.length;

        for(int var6 = 0; var6 < var5; var6++) {
            byte letter = var4[var6];
            if (Character.toString((char)letter).equals("\n")) {
                System.out.println(str);
                newLineCount++;
                if (newLineCount == 1) {
                    this.name = str;
                }

                if (newLineCount == 2) {
                    this.npl = Integer.parseInt(str);
                }

                if (newLineCount == 3) {
                    this.lpl = Integer.parseInt(str);
                }

                if (newLineCount == 4) {
                    this.shares = Double.parseDouble(str);
                }

                str = "";
            } else {
                str = str + Character.toString((char)letter);
            }
        }

        System.out.println("-----");
    }

    public String getName() {
        return this.name;
    }

    public int getNpl() {
        return this.npl;
    }

    public int getLpl() {
        return this.lpl;
    }

    public double getShares() {
        return this.shares;
    }

    public double getWpm() {
        return this.wpm;
    }

    public double getAccuracy() {
        return this.accuracy;
    }
}
