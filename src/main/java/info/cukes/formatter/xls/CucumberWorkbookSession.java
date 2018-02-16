package info.cukes.formatter.xls;

import gherkin.formatter.model.Feature;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

public class CucumberWorkbookSession extends WorkbookSession {

    public static final String STYLE_H1 = "H1";
    public static final String STYLE_H2 = "H2";
    public static final String STYLE_H3 = "H3";
    public static final String STYLE_PASSED = "PASSED";
    public static final String STYLE_FAILED = "FAILED";
    public static final String STYLE_SKIPPED = "SKIPPED";
    public static final String STYLE_H3_CENTERED = "H3_CENTERED";
    public static final String STYLE_CENTERED = "CENTERED";
    public static final String STYLE_ALIGN_LEFT = "ALIGN_LEFT";

    private static CucumberWorkbookSession instance = new CucumberWorkbookSession();
    public static CucumberWorkbookSession getInstance() { return instance; }

    private SummaryWorksheetSession summaryWorksheet;
    private FeatureWorksheetSession activeFeatureWorksheet;
    private Feature currentFeature;

    private Map<Feature, List<ScenarioStat>> featureStats = new LinkedHashMap<Feature, List<ScenarioStat>>();

    private CucumberWorkbookSession() {
        super();
        summaryWorksheet = new SummaryWorksheetSession(this);
        setupStyles();

        summaryWorksheet.setTitleAndHeader();

    }

    public FeatureWorksheetSession beginNewFeature(Feature feature) {
        this.endOfFeature();
        currentFeature = feature;
        activeFeatureWorksheet = new FeatureWorksheetSession(this, feature);
        activeFeatureWorksheet.setTitleAndHeader();
        return activeFeatureWorksheet;
    }

    private void endOfFeature() {
        if (currentFeature != null) {
            featureStats.put(currentFeature, activeFeatureWorksheet.getFeatureStats());
        }
    }

    @Override
    public void save(String outputPath) throws IOException {
        this.endOfFeature();
        summaryWorksheet.addSummaryAndFeatureSections(featureStats);
        super.save(outputPath);
    }

    private void setupStyles() {

        // Arial Bold 10pt
        Font font_arial_bold_10 = workbook.createFont();
        font_arial_bold_10.setFontName(HSSFFont.FONT_ARIAL);
        font_arial_bold_10.setFontHeightInPoints((short) 10);
        font_arial_bold_10.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // Arial Bold 12pt
        Font font_arial_bold_12 = workbook.createFont();
        font_arial_bold_12.setFontName(HSSFFont.FONT_ARIAL);
        font_arial_bold_12.setFontHeightInPoints((short) 12);
        font_arial_bold_12.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // Arial Bold 14pt
        Font font_arial_bold_14 = workbook.createFont();
        font_arial_bold_14.setFontName(HSSFFont.FONT_ARIAL);
        font_arial_bold_14.setFontHeightInPoints((short) 14);
        font_arial_bold_14.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // Add H1
        CellStyle styleH1 = workbook.createCellStyle();
        styleH1.setFont(font_arial_bold_14);
        addCellStyle(STYLE_H1, styleH1);

        // Add H2
        CellStyle styleH2 = workbook.createCellStyle();
        styleH2.setFont(font_arial_bold_12);
        addCellStyle(STYLE_H2, styleH2);

        // Add H3
        CellStyle styleH3 = workbook.createCellStyle();
        styleH3.setFont(font_arial_bold_10);
        addCellStyle(STYLE_H3, styleH3);

        // Add H3, Centered
        CellStyle styleH3Centered = workbook.createCellStyle();
        styleH3Centered.setFont(font_arial_bold_10);
        styleH3Centered.setAlignment(CellStyle.ALIGN_CENTER);
        addCellStyle(STYLE_H3_CENTERED, styleH3Centered);

        CellStyle success = workbook.createCellStyle();
        success.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        success.setFillPattern(CellStyle.SOLID_FOREGROUND);
        success.setAlignment(CellStyle.ALIGN_CENTER);
        addCellStyle(STYLE_PASSED, success);

        CellStyle failed = workbook.createCellStyle();
        failed.setFillForegroundColor(IndexedColors.ROSE.getIndex());
        failed.setFillPattern(CellStyle.SOLID_FOREGROUND);
        failed.setAlignment(CellStyle.ALIGN_CENTER);
        addCellStyle(STYLE_FAILED, failed);

        CellStyle skipped = workbook.createCellStyle();
        skipped.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
        skipped.setFillPattern(CellStyle.SOLID_FOREGROUND);
        success.setAlignment(CellStyle.ALIGN_CENTER);
        addCellStyle(STYLE_SKIPPED, skipped);

        CellStyle centered = workbook.createCellStyle();
        centered.setAlignment(CellStyle.ALIGN_CENTER);
        addCellStyle(STYLE_CENTERED, centered);

        CellStyle alignLeft = workbook.createCellStyle();
        alignLeft.setAlignment(CellStyle.ALIGN_LEFT);
        addCellStyle(STYLE_ALIGN_LEFT, alignLeft);
    }

}
