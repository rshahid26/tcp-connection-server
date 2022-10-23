import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PointsCalc {
    public static int classSize;
    public int weekNumber;
    public double sharesLimit1;
    public int lplLimit1;
    public double sharesLimit2;
    public int lplLimit2;
    public double sharesLimit3;
    public int lplLimit3;
    public double sharesLimit4;
    public int lplLimit4;
    public double sharesLimit5;
    public int lplLimit5;
    private final FileInputStream inputStream;
    private final XSSFWorkbook workbook;
    private final XSSFCellStyle style;
    private File excelFile;
    private double[][] array;

    public PointsCalc(int weekNumber, double sharesLimit1, int lplLimit1, double sharesLimit2, int lplLimit2, double sharesLimit3, int lplLimit3, double sharesLimit4, int lplLimit4, double sharesLimit5, int lplLimit5) throws IOException {
        this.weekNumber = weekNumber;
        this.sharesLimit1 = sharesLimit1;
        this.lplLimit1 = lplLimit1;
        this.sharesLimit2 = sharesLimit2;
        this.lplLimit2 = lplLimit2;
        this.sharesLimit3 = sharesLimit3;
        this.lplLimit3 = lplLimit3;
        this.sharesLimit4 = sharesLimit4;
        this.lplLimit4 = lplLimit4;
        this.sharesLimit5 = sharesLimit5;
        this.lplLimit5 = lplLimit5;
        this.excelFile =  new File("Excels/tradingExcel.xlsx");
        this.inputStream = new FileInputStream(this.excelFile);
        this.workbook = new XSSFWorkbook(this.inputStream);
        this.style = this.workbook.createCellStyle();
        this.style.setBorderRight(BorderStyle.THIN);
        this.style.setAlignment(HorizontalAlignment.CENTER);
        this.array = new double[classSize][15];

        for(int r = 0; r < classSize; r++) {
            for(int c = 0; c < classSize; c++) {
                try {
                    this.array[r][c] = this.workbook.getSheetAt(weekNumber - 1).getRow(r + 2).getCell(c + 4).getNumericCellValue();
                } catch (NullPointerException var23) {
                }
            }
        }

        double[][] raw1 = new double[classSize][3];
        for(int i = 0; i < classSize; i++) {
            System.arraycopy(this.array[i], 0, raw1[i], 0, 3);
        }

        double[][] raw2 = new double[classSize][3];
        for(int i = 0; i < classSize; i++) {
            System.arraycopy(this.array[i], 3, raw2[i], 0, 3);
        }

        double[][] raw3 = new double[classSize][3];
        for(int i = 0; i < classSize; i++) {
            System.arraycopy(this.array[i], 6, raw3[i], 0, 3);
        }

        double[][] raw4 = new double[classSize][3];
        for(int i = 0; i < classSize; i++) {
            System.arraycopy(this.array[i], 9, raw4[i], 0, 3);
        }

        double[][] raw5 = new double[classSize][3];
        for(int i = 0; i < classSize; i++) {
            System.arraycopy(this.array[i], 12, raw5[i], 0, 3);
        }

        this.enterPoints(this.totalPoints(raw1, raw2, raw3, raw4, raw5));
    }

    public void enterPoints(int[][] points) throws IOException {
        for(int r = 0; r < classSize; r++) {
            this.workbook.getSheetAt(this.weekNumber - 1).getRow(r + 2).createCell(3);
            this.workbook.getSheetAt(this.weekNumber - 1).getRow(r + 2).getCell(3).setCellStyle(this.style);
            this.workbook.getSheetAt(this.weekNumber - 1).getRow(r + 2).getCell(3).setCellValue(points[r][0]);
        }

        FileOutputStream outputStream = new FileOutputStream(this.excelFile);
        this.workbook.write(outputStream);
        this.workbook.close();
        outputStream.close();
        this.inputStream.close();
        this.excelFile = new File("Excels/tradingExcel.xlsx");
    }

    public int[][] totalPoints(double[][] raw1, double[][] raw2, double[][] raw3, double[][] raw4, double[][] raw5) {
        int[][] totalPoints = new int[classSize][1];

        for(int i = 0; i < classSize; i++) {
            totalPoints[i][0] = this.dailyPoints(raw1, this.lplLimit1, this.sharesLimit1)[i][0] + this.dailyPoints(raw2, this.lplLimit2, this.sharesLimit2)[i][0] + this.dailyPoints(raw3, this.lplLimit3, this.sharesLimit3)[i][0] + this.dailyPoints(raw4, this.lplLimit4, this.sharesLimit4)[i][0] + this.dailyPoints(raw5, this.lplLimit5, this.sharesLimit5)[i][0];
        }

        return totalPoints;
    }

    public int[][] dailyPoints(double[][] array, int lplLimit, double sharesLimit) {
        int[][] dailyPoints = new int[classSize][1];
        if (lplLimit != 0 || sharesLimit != 0.0D) {
            for(int i = 0; i < classSize; i++) {
                dailyPoints[i][0] = this.nplPoints(array, lplLimit, sharesLimit)[i][0] + this.sharesPoints(array, sharesLimit)[i][0] + this.lplPoints(array, lplLimit)[i][0] + this.streakPoints(array, lplLimit, sharesLimit)[i][0];
            }
        }

        return dailyPoints;
    }

    public int[][] nplPoints(double[][] array, int lplLimit, double sharesLimit) {
        int[][] nplPoints = new int[classSize][1];

        for(int i = 0; i < classSize; i++) {
            if (array[i][2] <= sharesLimit && array[i][2] >= sharesLimit / 4.0D && array[i][1] >= (double)lplLimit) {
                if (array[i][0] == 0.0D) {
                    nplPoints[i][0] = 0;
                }

                if (array[i][0] < 0.0D) {
                    nplPoints[i][0] = (int)(array[i][0] / 200.0D - 1.0D);

                    //update co2022
                    if(nplPoints[i][0] < -15) nplPoints[i][0] = -15;
                }

                if (array[i][0] > 0.0D) {
                    nplPoints[i][0] = (int)(array[i][0] / 200.0D + 1.0D);
                }
            } else {
                nplPoints[i][0] = 0;
            }
        }

        return nplPoints;
    }

    public int[][] sharesPoints(double[][] array, double sharesLimit) {
        int[][] sharesPoints = new int[classSize][1];

        for(int i = 0; i < classSize; i++) {
            if (array[i][2] <= sharesLimit) {
                sharesPoints[i][0] = 1;
                if (array[i][2] < sharesLimit / 4.0D) {
                    sharesPoints[i][0] = -2;
                }
            }

            if (array[i][2] > sharesLimit) {
                sharesPoints[i][0] = -1;
                if (array[i][2] > 2.0D * sharesLimit) {
                    sharesPoints[i][0] = -2;
                }

                if (array[i][2] > 4.0D * sharesLimit) {
                    sharesPoints[i][0] = -3;
                }
            }
        }

        return sharesPoints;
    }

    public int[][] lplPoints(double[][] array, int lplLimit) {
        int[][] lplPoints = new int[classSize][1];

        for(int i = 0; i < classSize; i++) {
            if (array[i][1] >= (double)lplLimit) {
                lplPoints[i][0] = 1;
            }

            if (array[i][1] < (double)lplLimit) {
                lplPoints[i][0] = -1;
                if (array[i][1] < (double)(2 * lplLimit)) {
                    lplPoints[i][0] = -2;
                }

                if (array[i][1] < (double)(4 * lplLimit)) {
                    lplPoints[i][0] = -3;
                }
            }
        }

        return lplPoints;
    }

    public int[][] streakPoints(double[][] array, int lplLimit, double sharesLimit) {
        int[][] streakPoints = new int[classSize][1];

        for(int i = 0; i < classSize; i++) {
            if (array[i][2] <= sharesLimit && array[i][2] >= sharesLimit / 4.0D && array[i][1] >= (double)lplLimit) {
                if (array[i][0] >= 0.0D) {
                    streakPoints[i][0] = 1;
                } else {
                    streakPoints[i][0] = -1;
                }
            } else {
                streakPoints[i][0] = -1;
            }
        }

        return streakPoints;
    }

    public void print(int[][] array) {
        for(int rows = 0; rows < array.length; rows++) {
            System.out.println();

            for(int cols = 0; cols < array[0].length; ++cols) {
                System.out.print(array[rows][cols] + " ");
            }
        }

    }

    public void print(double[][] array) {
        for(int rows = 0; rows < array.length; rows++) {
            System.out.println();

            for(int cols = 0; cols < array[0].length; ++cols) {
                System.out.print(array[rows][cols] + " ");
            }
        }

    }
}
