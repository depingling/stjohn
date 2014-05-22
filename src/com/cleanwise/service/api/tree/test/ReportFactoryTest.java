package com.cleanwise.service.api.tree.test;

import java.util.Locale;

import com.cleanwise.service.api.tree.ReportFactory;
import com.cleanwise.service.api.tree.ReportItem;
import com.cleanwise.service.api.tree.transformer.ExtendedReportTransformer;
import com.cleanwise.service.api.tree.transformer.SimpleReportTransformer;
import com.cleanwise.service.api.tree.types.ReportDate;
import com.cleanwise.service.api.tree.types.ReportMessage;

public class ReportFactoryTest {

    private final static byte TEST_BYTE = 1;

    private final static short TEST_SHORT = 2;

    private final static char TEST_CHAR = 'A';

    private final static int TEST_INT = 3;

    private final static long TEST_LONG = 4l;

    private final static float TEST_FLOAT = 5.5f;

    private final static double TEST_DOUBLE = 6.6d;

    private final static String TEST_STRING = "TEST_STRING";

    private final static ReportDate TEST_DATE = new ReportDate("reportDate");

    private final static ReportMessage TEST_MESSAGE = new ReportMessage("key");

    private final static ReportMessage TEST_MESSAGE_COMPLEX = new ReportMessage(
            "root1");

    private final static String REPORT_FACTORY_CLASS = "com.cleanwise.service.api.tree.xml.ReportXmlFactory";
    static {
        ReportMessage rm = new ReportMessage("child1");
        ReportMessage rm2_1 = new ReportMessage("child2.1");
        ReportMessage rm3_1 = new ReportMessage("child3.1");
        TEST_MESSAGE_COMPLEX.addParam(rm);
        rm.addParam(rm2_1);
        rm2_1.addParam(rm3_1);
    }

    public void testSimpleReport() {
        String str = getReportFactory(REPORT_FACTORY_CLASS).transform(
                getRoot(), new SimpleReportTransformer());
        System.err.println(str);
    }

    private final static Locale[] LOCALES = new Locale[] { new Locale(""),
            Locale.UK, Locale.US, new Locale("ru", "RU") };

    public void testExtendReport() {
        for (int i = 0; i < LOCALES.length; i++) {
            System.err.println();
            System.err.println("============================================");
            System.err.println();
            System.err.println("CHECK LOCALE:" + LOCALES[i]);
            System.err.println();
            String str = getReportFactory(REPORT_FACTORY_CLASS).transform(
                    getRoot(),
                    new ExtendedReportTransformer(getClass().getName(),
                            LOCALES[i]));
            System.err.println(str);
        }
    }

    private final static ReportItem getRoot() {
        ReportItem item = ReportItem.createValue("ROOT");
        initTestChildren(item);
        initTestAttributes(item);
        return item;
    }

    private final static void initTestChildren(ReportItem item) {
        item.addValue("TESTS");
        item.addChild(ReportItem.createValue("CHILD_BYTE", TEST_BYTE));
        item.addChild(ReportItem.createValue("CHILD_SHORT", TEST_SHORT));
        item.addChild(ReportItem.createValue("TEST_CHAR", TEST_CHAR));
        item.addChild(ReportItem.createValue("TEST_INT", TEST_INT));
        item.addChild(ReportItem.createValue("TEST_LONG", TEST_LONG));
        item.addChild(ReportItem.createValue("TEST_FLOAT", TEST_FLOAT));
        item.addChild(ReportItem.createValue("TEST_DOUBLE", TEST_DOUBLE));
        item.addChild(ReportItem.createValue("TEST_STRING", TEST_STRING));
        item.addChild(ReportItem.createValue("TEST_DATE", TEST_DATE));
        item.addChild(ReportItem.createValue("TEST_MESSAGE", TEST_MESSAGE));
        item.addChild(ReportItem.createValue("TEST_MESSAGE_COMPLEX",
                TEST_MESSAGE_COMPLEX));
    }

    private final static void initTestAttributes(ReportItem item) {
        item.addAttribute("name", "TESTS").addAttribute("ATTR_BYTE", TEST_BYTE)
                .addAttribute("ATTR_SHORT", TEST_SHORT).addAttribute(
                        "ATTR_CHAR", TEST_CHAR).addAttribute("ATTR_INT",
                        TEST_INT).addAttribute("ATTR_LONG", TEST_LONG)
                .addAttribute("ATTR_FLOAT", TEST_FLOAT).addAttribute(
                        "ATTR_DOUBLE", TEST_DOUBLE).addAttribute("ATTR_STRING",
                        TEST_STRING).addAttribute("ATTR_DATE", TEST_DATE)
                .addAttribute("ATTR_MESSAGE", TEST_MESSAGE);
    }

    private ReportFactory getReportFactory(String className) {
        try {
            return (ReportFactory) Class.forName(className).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ReportFactoryTest test = new ReportFactoryTest();
        test.testSimpleReport();
        test.testExtendReport();
    }
}
