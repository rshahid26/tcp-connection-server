import com.formdev.flatlaf.intellijthemes.FlatGrayIJTheme;
import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AdminUI extends JFrame implements ActionListener {
    private File Config = new File("Excels/Config");
    JButton tradingButton;
    JButton configureButton;
    JLabel enterClassSize;
    JTextField classSize;
    JLabel enterWeeks;
    JTextField totalWeeks;
    JButton submitButton;
    JButton backButton;
    JLabel mockTrading;
    JLabel shareLimit;
    JLabel lplLimit;
    JLabel days;
    JLabel helpHome;
    JLabel helpTrading;
    JLabel helpClassSize;
    JLabel error;
    JLabel configError;
    JLabel week;
    JTextField weekNumber;
    JTextField shareLimit1;
    JTextField shareLimit2;
    JTextField shareLimit3;
    JTextField shareLimit4;
    JTextField shareLimit5;
    JTextField lplLimit1;
    JTextField lplLimit2;
    JTextField lplLimit3;
    JTextField lplLimit4;
    JTextField lplLimit5;
    JButton doneButton;
    JLabel errorMissing;
    JLabel errorClassSize;
    JLabel doneMessage;
    JLabel submitMessage;

    public AdminUI() {
        this.homePage();
    }

    public void homePage() {
        this.resetPage();
        this.setTitle("InternData");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JLabel welcome = new JLabel("Welcome.");
        welcome.setFont(new Font(welcome.getFont().getFontName(), welcome.getFont().getStyle(), 40));
        welcome.setBounds(60, 86, 200, 35);
        this.add(welcome);
        this.helpHome = new JLabel("Server is running. You may minimize this window now.");
        this.helpHome.setForeground(new Color(0, 140, 20));
        this.helpHome.setBounds(30, 300, 460, 40);
        this.add(this.helpHome);
        this.configError = new JLabel("Can't find Configuration. Please reconfigure the program!");
        this.configError.setBounds(30, 288, 460, 40);
        this.configError.setForeground(Color.RED);
        this.configureButton = new JButton("Config");
        this.configureButton.setBounds(60, 130, 80, 40);
        this.configureButton.addActionListener(this);
        this.add(this.configureButton);
        this.tradingButton = new JButton("Trading");
        this.tradingButton.setBounds(145, 130, 80, 40);
        this.tradingButton.addActionListener(this);
        this.add(this.tradingButton);
        readConfigFile();
        this.setVisible(true);
    }

    public void configurePage() {
        this.resetPage();
        this.enterClassSize = new JLabel("Class Size");
        this.enterClassSize.setBounds(65, 37, 100, 10);
        this.add(this.enterClassSize);
        this.classSize = new JTextField();
        this.classSize.setBounds(64, 52, 180, 35);
        this.add(this.classSize);
        this.enterWeeks = new JLabel("Total Weeks");
        this.enterWeeks.setBounds(65, 103, 100, 10);
        this.add(this.enterWeeks);
        this.totalWeeks = new JTextField();
        this.totalWeeks.setBounds(64, 117, 180, 35);
        this.add(this.totalWeeks);
        this.helpClassSize = new JLabel("Only count weeks where data will be collected.");
        this.helpClassSize.setBounds(30, 300, 460, 40);
        this.add(this.helpClassSize);
        this.backButton = new JButton("<");
        this.backButton.setBounds(15, 27, 26, 26);
        this.backButton.setFont(new Font(this.enterClassSize.getFont().getFontName(), this.enterClassSize.getFont().getStyle(), 20));
        this.backButton.setBackground(null);
        this.backButton.setBorder(null);
        this.backButton.addActionListener(this);
        this.add(this.backButton);
        this.submitButton = new JButton("Submit");
        this.submitButton.setBounds(480, 300, 80, 40);
        this.submitButton.addActionListener(this);
        this.add(this.submitButton);
        this.errorClassSize = new JLabel("Please enter data for all fields.");
        this.errorClassSize.setForeground(Color.red);
        this.errorClassSize.setBounds(30, 288, 460, 40);
        this.submitMessage = new JLabel("Done! You can enter the limits now or let interns submit data.");
        this.submitMessage.setBounds(30, 288, 460, 40);
        this.submitMessage.setForeground(new Color(0, 140, 20));
    }

    public void tradingPage() {
        this.resetPage();
        this.mockTrading = new JLabel("Mock Trading");
        this.mockTrading.setFont(new Font(this.mockTrading.getFont().getFontName(), this.mockTrading.getFont().getStyle(), 30));
        this.mockTrading.setBounds(200, 20, 200, 40);
        this.add(this.mockTrading);
        Font font = new Font(this.mockTrading.getFont().getFontName(), this.mockTrading.getFont().getStyle(), 20);
        this.week = new JLabel("Week #");
        this.week.setBounds(250, 50, 80, 30);
        this.week.setFont(font);
        this.add(this.week);
        this.weekNumber = new JTextField();
        this.weekNumber.setBounds(315, 53, 25, 25);
        this.weekNumber.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (AdminUI.this.weekNumber.getText().length() == 1) {
                    e.consume();
                }

            }
        });
        this.weekNumber.setFont(font);
        this.weekNumber.setBackground(null);
        this.weekNumber.setBorder(null);
        this.add(this.weekNumber);
        this.days = new JLabel("M");
        this.days.setBounds(140, 100, 40, 40);
        this.days.setFont(font);
        this.add(this.days);
        this.days = new JLabel("T");
        this.days.setBounds(220, 100, 40, 40);
        this.days.setFont(font);
        this.add(this.days);
        this.days = new JLabel("W");
        this.days.setBounds(300, 100, 40, 40);
        this.days.setFont(font);
        this.add(this.days);
        this.days = new JLabel("T");
        this.days.setBounds(380, 100, 40, 40);
        this.days.setFont(font);
        this.add(this.days);
        this.days = new JLabel("F");
        this.days.setBounds(460, 100, 40, 40);
        this.days.setFont(font);
        this.add(this.days);
        this.shareLimit = new JLabel("Share Limits");
        this.shareLimit.setBounds(30, 160, 100, 40);
        this.add(this.shareLimit);
        this.lplLimit = new JLabel("Lpl Limits");
        this.lplLimit.setBounds(30, 220, 100, 40);
        this.add(this.lplLimit);
        this.shareLimit1 = new JTextField("Ex: 10.0");
        this.shareLimit1.setBounds(120, 160, 60, 30);
        this.add(this.shareLimit1);
        this.shareLimit2 = new JTextField("");
        this.shareLimit2.setBounds(200, 160, 60, 30);
        this.add(this.shareLimit2);
        this.shareLimit3 = new JTextField("");
        this.shareLimit3.setBounds(280, 160, 60, 30);
        this.add(this.shareLimit3);
        this.shareLimit4 = new JTextField("");
        this.shareLimit4.setBounds(360, 160, 60, 30);
        this.add(this.shareLimit4);
        this.shareLimit5 = new JTextField("");
        this.shareLimit5.setBounds(440, 160, 60, 30);
        this.add(this.shareLimit5);
        this.lplLimit1 = new JTextField("Ex: -500");
        this.lplLimit1.setBounds(120, 220, 60, 30);
        this.add(this.lplLimit1);
        this.lplLimit2 = new JTextField("");
        this.lplLimit2.setBounds(200, 220, 60, 30);
        this.add(this.lplLimit2);
        this.lplLimit3 = new JTextField("");
        this.lplLimit3.setBounds(280, 220, 60, 30);
        this.add(this.lplLimit3);
        this.lplLimit4 = new JTextField("");
        this.lplLimit4.setBounds(360, 220, 60, 30);
        this.add(this.lplLimit4);
        this.lplLimit5 = new JTextField("");
        this.lplLimit5.setBounds(440, 220, 60, 30);
        this.add(this.lplLimit5);
        this.helpTrading = new JLabel("If there was no mock trading, input zeros for that day's limits.");
        this.helpTrading.setBounds(30, 300, 460, 40);
        this.add(this.helpTrading);
        this.doneButton = new JButton("Submit");
        this.doneButton.setBounds(480, 300, 80, 40);
        this.doneButton.addActionListener(this);
        this.add(this.doneButton);
        this.backButton = new JButton("<");
        this.backButton.setBounds(15, 27, 26, 26);
        this.backButton.setFont(new Font(this.mockTrading.getFont().getFontName(), this.mockTrading.getFont().getStyle(), 20));
        this.backButton.setBackground(null);
        this.backButton.setBorder(null);
        this.backButton.addActionListener(this);
        this.add(this.backButton);
        this.error = new JLabel("Error");
        this.error.setBounds(30, 300, 50, 40);
        this.error.setForeground(Color.RED);
        this.errorMissing = new JLabel("Please enter data for all fields. Lpl limits should be integers.");
        this.errorMissing.setForeground(Color.red);
        this.errorMissing.setBounds(30, 288, 460, 40);
        this.doneMessage = new JLabel("Done! tradingExcel.xslx updated.");
        this.doneMessage.setForeground(new Color(0, 140, 20));
        this.doneMessage.setBounds(30, 288, 460, 40);
    }

    public void resetPage() {
        this.getContentPane().removeAll();
        this.setResizable(false);
        this.setLayout((LayoutManager)null);
        this.revalidate();
        this.repaint();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.tradingButton) {
            this.tradingPage();
        }

        if (e.getSource() == this.backButton) {
            this.homePage();
        }

        if (e.getSource() == this.configureButton) {
            this.configurePage();
        }

        if (e.getSource() == this.submitButton) {
            try {
                if (!this.checkClassSize()) {
                    this.remove(this.errorClassSize);
                    this.revalidate();
                    this.repaint();

                    try {

                        this.Config = new File("Excels/Config");
                        System.out.println("config created!");

                        if (this.Config.isFile()) {
                            this.Config.delete();
                        }

                        this.Config.createNewFile();
                        BufferedWriter output = new BufferedWriter(new FileWriter(this.Config.getAbsolutePath()));
                        int var10001 = Integer.parseInt(this.classSize.getText());
                        output.write(var10001 + "\n" + Integer.parseInt(this.totalWeeks.getText()));
                        output.close();
                    } catch (IOException var5) {
                        this.add(this.errorClassSize);
                        this.revalidate();
                        this.repaint();
                    }

                    this.add(this.submitMessage);
                    this.revalidate();
                    this.repaint();
                } else {
                    this.add(this.errorClassSize);
                    this.revalidate();
                    this.repaint();
                }
            } catch (NullPointerException var6) {
                System.out.println("null");
            }
        }

        if (e.getSource() == this.doneButton) {
            try {
                if (!this.checkBlanks()) {
                    this.remove(this.errorMissing);
                    this.revalidate();
                    this.repaint();
                    int currentWeek = Integer.parseInt(this.weekNumber.getText());
                    new PointsCalc(currentWeek, Double.parseDouble(this.shareLimit1.getText()), Integer.parseInt(this.lplLimit1.getText()), Double.parseDouble(this.shareLimit2.getText()), Integer.parseInt(this.lplLimit2.getText()), Double.parseDouble(this.shareLimit3.getText()), Integer.parseInt(this.lplLimit3.getText()), Double.parseDouble(this.shareLimit4.getText()), Integer.parseInt(this.lplLimit4.getText()), Double.parseDouble(this.shareLimit5.getText()), Integer.parseInt(this.lplLimit5.getText()));
                    this.add(this.doneMessage);
                    this.revalidate();
                    this.repaint();
                } else {
                    this.add(this.errorMissing);
                    this.revalidate();
                    this.repaint();
                }
            } catch (IOException var4) {
                System.out.println("Submit error");
                this.add(this.error);
                this.revalidate();
                this.repaint();
            }
        }
    }

    public void readConfigFile() {
        try {
            Scanner scanner = new Scanner(this.Config);
            int classSizeHolder = scanner.nextInt();
            int totalWeeksHolder = scanner.nextInt();
            scanner.close();

            PointsCalc.classSize = classSizeHolder;
            TradingExcel.classSize = classSizeHolder;
            TradingExcel.totalWeeks = totalWeeksHolder;
            TypingExcel.classSize = classSizeHolder;
            TypingExcel.totalWeeks = totalWeeksHolder;

        } catch (NumberFormatException | NullPointerException | FileNotFoundException | NoSuchElementException var4) {
            var4.printStackTrace();
            this.add(this.configError);
            this.revalidate();
            this.repaint();
        }

    }

    public boolean checkBlanks() {
        return this.shareLimit1.getText().equals("") || this.lplLimit1.getText().equals("") || this.shareLimit2.getText().equals("") || this.lplLimit2.getText().equals("") || this.shareLimit3.getText().equals("") || this.lplLimit3.getText().equals("") || this.shareLimit4.getText().equals("") || this.lplLimit4.getText().equals("") || this.shareLimit5.getText().equals("") || this.lplLimit5.getText().equals("") || this.weekNumber.getText().equals("");
    }

    public boolean checkClassSize() {
        return this.classSize.getText().equals("") || this.totalWeeks.getText().equals("");
    }

    public static void main(String[] args) {
        try {
            FlatGrayIJTheme.setup();
        } catch (Exception var3) {
            System.err.println("Failed to initialize. Send an email to rshahid@trlm.com instead");
        }

        new AdminUI();
        new Server();
    }
}
