import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TypingExcel {
    public static int totalWeeks;
    public static int classSize;
    private FileInputStream inputStream;
    private XSSFWorkbook workbook;
    private File excelFile = new File("Excels/typingExcel.xlsx");

    public TypingExcel() throws IOException {
        this.inputStream = new FileInputStream(this.excelFile);
        this.workbook = new XSSFWorkbook(this.inputStream);
        XSSFSheet sheet = this.workbook.getSheetAt(0);
    }

    public void addToExcel(Server server) throws IOException {
        this.inputStream = new FileInputStream(this.excelFile);
        String[] rawName = server.getName().split(" ");
        String firstName = rawName[0];
        String lastName = rawName[1];

        for(int s = 0; s < totalWeeks; s++) {
            boolean done = false;
            int r;
            int c;
            if (this.workbook.getNumberOfSheets() == s) {
                System.out.println("creating new sheet");
                this.workbook.cloneSheet(s - 1, "Week" + (s + 1));

                for(r = 3; r <= classSize + 3; r++) {
                    for(c = 4; c <= 20; c++) {
                        try {
                            if (this.workbook.getSheetAt(s).getRow(r).getCell(c).getCellType() == CellType.NUMERIC) {
                                this.workbook.getSheetAt(s).getRow(r).getCell(c).setCellValue(0.0D);
                            }
                        } catch (NullPointerException var10) {
                        }
                    }
                }
            }

            for(r = 0; r <= this.workbook.getSheetAt(s).getLastRowNum(); r++) {
                if (this.workbook.getSheetAt(s).getRow(r).getCell(0).toString().equals(firstName) && this.workbook.getSheetAt(s).getRow(r).getCell(1).toString().equals(lastName)) {
                    for(c = 3; c <= this.workbook.getSheetAt(s).getRow(r).getLastCellNum(); c++) {
                        if (this.workbook.getSheetAt(s).getRow(r).getCell(c).getNumericCellValue() == 0.0D) {
                            if (c > 12) {
                                System.out.println("must create new sheet");
                            } else {
                                this.workbook.getSheetAt(s).getRow(r).getCell(c).setCellValue(server.getWpm());
                                this.workbook.getSheetAt(s).getRow(r).getCell(c + 5).setCellValue(server.getAccuracy());
                                done = true;
                            }
                            break;
                        }
                    }
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
        this.excelFile = new File("Excels/typingExcel.xlsx");
    }
}
