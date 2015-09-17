package info.cukes.formatter.xls;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFOptimiser;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class WorkbookSession {

    protected Workbook workbook;
    private Map<String, CellStyle> cellStyles = new HashMap<String,CellStyle>();

    public WorkbookSession() {
        workbook = new HSSFWorkbook();
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void save(String outputPath) throws IOException {
        HSSFOptimiser.optimiseCellStyles((HSSFWorkbook) workbook);
        HSSFOptimiser.optimiseFonts((HSSFWorkbook) workbook);

        File outputFile = new File(outputPath);
        FileOutputStream out = new FileOutputStream(outputFile);
        workbook.write(out);
        workbook.close();
        out.close();
    }

    public Map<String, CellStyle> getCellStyles() {
        return cellStyles;
    }

    public void addCellStyle(String styleIdentifier, CellStyle style) {
        cellStyles.put(styleIdentifier, style);
    }
}
