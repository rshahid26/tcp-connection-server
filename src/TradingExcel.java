import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TradingExcel {
    public static int totalWeeks;
    public static int classSize;
    private FileInputStream inputStream;
    private XSSFWorkbook workbook;
    private File excelFile = new File("Excels/tradingExcel.xlsx");

    public TradingExcel() throws IOException {
        this.inputStream = new FileInputStream(this.excelFile);
        this.workbook = new XSSFWorkbook(this.inputStream);
        XSSFSheet sheet = this.workbook.getSheetAt(0);
    }

    public void addToExcel(Server server) throws IOException {
        String[] rawName = server.getName().split(" ");

        String lastName;
        for (int i = 0; i < rawName.length; i++) {
            String var10000 = rawName[i].substring(0, 1).toUpperCase();
            lastName = var10000 + rawName[i].substring(1);
            rawName[i] = lastName;
        }

        String firstName = rawName[0];
        lastName = rawName[1];

        for (int s = 0; s < totalWeeks; s++) {
            boolean done = false;
            int r;
            if (this.workbook.getNumberOfSheets() == s) {
                System.out.println("creating new sheet");
                this.workbook.cloneSheet(s - 1, "Week" + (s + 1));

                for (r = 3; r <= classSize + 3; r++) {
                    for (int c = 4; c <= 20; c++) {
                        try {
                            if (this.workbook.getSheetAt(s).getRow(r).getCell(c).getCellType() == CellType.NUMERIC) {
                                this.workbook.getSheetAt(s).getRow(r).removeCell(this.workbook.getSheetAt(s).getRow(r).getCell(c));
                            }
                        } catch (NullPointerException var12) {
                        }
                    }
                }
            }

            for (r = 0; r <= this.workbook.getSheetAt(s).getLastRowNum(); r++) {
                if (this.workbook.getSheetAt(s).getRow(r).getCell(0).toString().equals(firstName) && this.workbook.getSheetAt(s).getRow(r).getCell(1).toString().equals(lastName)) {
                    int lastCellNum = this.workbook.getSheetAt(s).getRow(r).getLastCellNum();
                    if (lastCellNum >= 18) {
                        System.out.println("must create new sheet!");
                        break;
                    }

                    XSSFCell NPL = this.workbook.getSheetAt(s).getRow(r).createCell(lastCellNum);
                    NPL.setCellValue((double) server.getNpl());
                    XSSFCell LPL = this.workbook.getSheetAt(s).getRow(r).createCell(lastCellNum + 1);
                    LPL.setCellValue((double) server.getLpl());
                    XSSFCell Shares = this.workbook.getSheetAt(s).getRow(r).createCell(lastCellNum + 2);
                    Shares.setCellValue(server.getShares());
                    System.out.println(lastCellNum + 2);
                    done = true;
                }
            }

            if (done) {
                break;
            }
        }

        FileOutputStream outputStream = new FileOutputStream(this.excelFile);
        this.workbook.write(outputStream);
        this.workbook.close();
        outputStream.close();
        this.inputStream.close();
        this.excelFile = new File("Excels/tradingExcel.xlsx");
    }
}
