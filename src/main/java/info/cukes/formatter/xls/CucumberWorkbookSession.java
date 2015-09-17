package info.cukes.formatter.xls;

import gherkin.formatter.model.Feature;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

public class CucumberWorkbookSession extends WorkbookSession {

    public static final String STYLE_H1 = "H1";
    public static final String STYLE_H2 = "H2";
    public static final String STYLE_H3 = "H1";
    public static final String STYLE_PASSED = "PASSED";
    public static final String STYLE_FAILED = "FAILED";
    public static final String STYLE_SKIPPED = "SKIPPED";
    public static final String STYLE_H3_CENTERED = "H3_CENTERED";

    private static CucumberWorkbookSession instance = new CucumberWorkbookSession();
    public static CucumberWorkbookSession getInstance() { return instance; }

    private SummaryWorksheetSession summaryWorksheet;
    private FeatureWorksheetSession activeFeatureWorksheet;
    private Feature currentFeature;

    private Map<Feature, List<ScenarioStat>> featureStats = new LinkedHashMap<Feature, List<ScenarioStat>>();

    private CucumberWorkbookSession() {
        super();
        summaryWorksheet = new SummaryWorksheetSession(this);
        summaryWorksheet.setTitleAndHeader();

        setupStyles();
    }

    public FeatureWorksheetSession beginNewFeature(Feature feature) {
        if (currentFeature != null) {
            this.endOfFeature();
        }
        currentFeature = feature;
        activeFeatureWorksheet = new FeatureWorksheetSession(this, feature);
        activeFeatureWorksheet.setTitleAndHeader();
        return activeFeatureWorksheet;
    }

    private void endOfFeature() {
        featureStats.put(currentFeature, activeFeatureWorksheet.getFeatureStats());
    }

    @Override
    public void save(String outputPath) throws IOException {
        if (currentFeature != null) {
            this.endOfFeature();
        }
        summaryWorksheet.addSummaryAndFeatureSections(featureStats);
        super.save(outputPath);
    }

    private void setupStyles() {
        // Add H1
        CellStyle styleH1 = workbook.createCellStyle();
        Font fontH1 = workbook.createFont();
        fontH1.setBoldweight(Font.BOLDWEIGHT_BOLD);
        fontH1.setFontHeightInPoints((short) 16);
        styleH1.setFont(fontH1);
        addCellStyle(STYLE_H1, styleH1);

        // Add H2
        CellStyle styleH2 = workbook.createCellStyle();
        Font fontH2 = workbook.createFont();
        fontH2.setFontHeightInPoints((short) 12);
        fontH2.setBoldweight(Font.BOLDWEIGHT_BOLD);
        styleH2.setFont(fontH2);
        addCellStyle(STYLE_H2, styleH2);

        // Add H3
        CellStyle styleH3 = workbook.createCellStyle();
        Font fontH3 = workbook.createFont();
        fontH3.setBoldweight(Font.BOLDWEIGHT_BOLD);
        styleH3.setFont(fontH3);
        addCellStyle(STYLE_H3, styleH3);

        // Add H3, Centered
        Font fontH3Centered = workbook.createFont();
        fontH3Centered.setBoldweight(Font.BOLDWEIGHT_BOLD);
        CellStyle styleH3Centered = workbook.createCellStyle();
        styleH3Centered.setFont(fontH3Centered);
        addCellStyle(STYLE_H3_CENTERED, styleH3Centered);

        CellStyle success = workbook.createCellStyle();
        success.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        success.setFillPattern(CellStyle.SOLID_FOREGROUND);
        addCellStyle(STYLE_PASSED, success);

        CellStyle failed = workbook.createCellStyle();
        failed.setFillForegroundColor(IndexedColors.PINK.getIndex());
        failed.setFillPattern(CellStyle.SOLID_FOREGROUND);
        addCellStyle(STYLE_FAILED, failed);

        CellStyle skipped = workbook.createCellStyle();
        skipped.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
        skipped.setFillPattern(CellStyle.SOLID_FOREGROUND);
        addCellStyle(STYLE_SKIPPED, skipped);
    }


}
