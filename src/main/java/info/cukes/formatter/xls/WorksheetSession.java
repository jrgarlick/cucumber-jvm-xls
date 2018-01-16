package info.cukes.formatter.xls;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class WorksheetSession {

    public WorkbookSession workbookSession;
    public Workbook workbook;
    public Sheet worksheet;
    public CreationHelper creationHelper;
    public Drawing drawing;
    public Set<String> worksheetNames = new HashSet<String>();


    public WorksheetSession(WorkbookSession workbookSession, String worksheetName) {
        this.workbookSession = workbookSession;
        this.workbook = workbookSession.getWorkbook();
        this.worksheet = workbook.createSheet(getUniqueWorksheetName(worksheetName));
        this.creationHelper = workbook.getCreationHelper();
        this.drawing = worksheet.createDrawingPatriarch();
    }

    public Row getRow(int rownum) {
        Row row = worksheet.getRow(rownum);
        if (row == null) {
            row = worksheet.createRow(rownum);
        }
        return row;
    }

    public Row setRow(int rowNum, Object ... cells) {
        return null;
    }

    public Cell getCell(int rownum, int colnum) {
        Row row = getRow(rownum);
        return getCell(row, colnum);
    }

    public Cell getCell(Row row, int colnum) {
        Cell cell = row.getCell(colnum);
        if (cell == null) {
            cell = row.createCell(colnum);
        }
        return cell;
    }

//    public Cell setCell(int rownum, int colnum, Object value) {
//        Cell cell = getCell(rownum, colnum);
//
//        if (value == null) {
//            cell.setCellValue((String) null); //blank out the cell
//        } else {
//            cell.setCellValue(String.valueOf(value));
//        }
//
//        return cell;
//    }

    public Cell setCellEmpty(int rownum, int colnum) {
        Cell cell = getCell(rownum, colnum);
        cell.setCellValue((String) null);
        return cell;
    }


    public Cell setCell(int rownum, int colnum, String value) {
        Cell cell = getCell(rownum, colnum);
        cell.setCellValue(value);
        return cell;
    }

    public Cell setCell(int rownum, int colnum, Date value) {
        Cell cell = getCell(rownum, colnum);
        cell.setCellValue(value);
        return cell;
    }

    public Cell setCell(int rownum, int colnum, Integer value) {
        Cell cell = getCell(rownum, colnum);
        cell.setCellValue(value);
        return cell;
    }

    public Cell setCell(int rownum, int colnum, Double value) {
        Cell cell = getCell(rownum, colnum);
        cell.setCellValue(value);
        return cell;
    }

    public Cell setCell(int rownum, int colnum, RichTextString value) {
        Cell cell = getCell(rownum, colnum);
        cell.setCellValue(value);
        return cell;
    }

    public Cell setCell(int rownum, int colnum, Hyperlink value) {
        Cell cell = getCell(rownum, colnum);
        cell.setHyperlink(value);
        return cell;
    }

    public Comment setCellComment(Cell cell, String commentText, String commentAuthor) {
        HSSFClientAnchor anchor = new HSSFClientAnchor(100, 100, 100, 100, (short)1, 1, (short) 6, 5);
        Comment comment = drawing.createCellComment(anchor);
        comment.setString(creationHelper.createRichTextString(commentText));
        comment.setAuthor(commentAuthor);
        cell.setCellComment(comment);
        return comment;
    }

    public void formatCell(Cell cell, String formatIdentifier) {
        Map<String, CellStyle> styles = workbookSession.getCellStyles();
        CellStyle style = styles.get(formatIdentifier);
        if (style != null) {
            cell.setCellStyle(style);
        }
    }

    public void formatCells(List<Cell> cells, String formatIdentifier) {
        for (Cell cell : cells) {
            formatCell(cell, formatIdentifier);
        }
    }

    private String getUniqueWorksheetName(String worksheetName) {
        int i = 1;
        String currentWorksheetName = worksheetName;
        while (workbook.getSheet(currentWorksheetName) != null) {
            currentWorksheetName = worksheetName + "-" + i++;
        }
        return currentWorksheetName;
    }

}
