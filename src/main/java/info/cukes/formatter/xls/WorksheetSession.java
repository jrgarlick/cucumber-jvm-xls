package info.cukes.formatter.xls;

import java.util.Date;
import java.util.Map;

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

    protected WorkbookSession workbookSession;
    protected Workbook workbook;
    protected Sheet worksheet;
    protected CreationHelper creationHelper;
    protected Drawing drawing;


    public WorksheetSession(WorkbookSession workbookSession, String worksheetName) {
        this.workbookSession = workbookSession;
        this.workbook = workbookSession.getWorkbook();
        this.worksheet = workbook.createSheet(worksheetName);
        this.creationHelper = workbook.getCreationHelper();
        this.drawing = worksheet.createDrawingPatriarch();
    }

    protected Row getRow(int rownum) {
        Row row = worksheet.getRow(rownum);
        if (row == null) {
            row = worksheet.createRow(rownum);
        }
        return row;
    }

    protected Row setRow(int rowNum, Object ... cells) {
        return null;
    }

    protected Cell getCell(Row row, int colnum) {
        Cell cell = row.getCell(colnum);
        if (cell == null) {
            cell = row.createCell(colnum);
        }
        return cell;
    }

    protected Cell setCell(int rownum, int colnum, Object value) {
        Row row = getRow(rownum);
        Cell cell = getCell(row, colnum);

        if (value == null) {
            cell.setCellValue((String) null); //blank out the cell
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof RichTextString) {
            cell.setCellValue((RichTextString) value);
        } else if (value instanceof Hyperlink) {
            cell.setHyperlink((Hyperlink) value);
        } else {
            cell.setCellValue(String.valueOf(value));
        }

        return cell;
    }

    protected Comment setCellComment(Cell cell, String commentText, String commentAuthor) {
        HSSFClientAnchor anchor = new HSSFClientAnchor(100, 100, 100, 100, (short)1, 1, (short) 6, 5);
        Comment comment = drawing.createCellComment(anchor);
        comment.setString(creationHelper.createRichTextString(commentText));
        comment.setAuthor(commentAuthor);
        cell.setCellComment(comment);
        return comment;
    }

    protected void formatCell(Cell cell, String formatIdentifier) {
        Map<String, CellStyle> styles = workbookSession.getCellStyles();
        CellStyle style = styles.get(formatIdentifier);
        if (style != null) {
            cell.setCellStyle(style);
        }
    }


}
