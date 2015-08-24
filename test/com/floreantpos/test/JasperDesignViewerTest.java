package com.floreantpos.test;

import java.io.InputStream;
import java.util.HashMap;

import org.junit.Test;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperDesignViewer;
import net.sf.jasperreports.view.JasperViewer;

public class JasperDesignViewerTest {

	private String jrxmlPath = "/home/destiny/Code/floreant-micropoplar/test/com/floreantpos/test/template/test.jrxml";
	private String jasperPath = "/home/destiny/Code/floreant-micropoplar/test/com/floreantpos/test/template/test.jasper";
	
	@Test
	public void testPreview() throws JRException {
		JasperDesignViewer.viewReportDesign(
				jrxmlPath, true);
		System.out.println("Test");
	}

	@Test
	public void testCompile() throws JRException {
		JasperCompileManager.compileReportToFile(jrxmlPath);
	}

	@Test
	public void testPreviewByCompiled() throws JRException {
		JasperDesignViewer.viewReportDesign(
				"/home/destiny/Code/floreant-micropoplar/test/com/floreantpos/test/template/FirstReport.jasper", false);
		System.out.println("Test");
	}

	@Test
	public void testGenerateJrPrint() throws JRException {
		JasperFillManager.fillReportToFile(
				jasperPath,
				new HashMap(), new JREmptyDataSource());
	}

	@Test
	public void testViewJrPrint() throws JRException {
		JasperViewer.viewReport(
				"/home/destiny/Code/floreant-micropoplar/test/com/floreantpos/test/template/FirstReport.jrprint", false);
		System.out.println("Test");
	}
	
	@Test
	public void testFullflow() throws JRException {
//		InputStream is = JasperDesignViewerTest.class.getResourceAsStream("/home/destiny/Code/floreant-micropoplar/test/com/floreantpos/test/template/test.jrxml");
        String designFile = JasperCompileManager.compileReportToFile("/home/destiny/Code/floreant-micropoplar/test/com/floreantpos/test/template/test.jrxml");
        JasperPrint printFile = JasperFillManager.fillReport(designFile, new HashMap(),
                                                            new JREmptyDataSource());
        JasperViewer.viewReport(printFile);
	}
	
	@Test
	public void testReadProperty() throws JRException {
		JasperReport jr = (JasperReport) JRLoader.loadObject(jasperPath);
		System.out.println(jr.getProperty("testProperty"));
	}

}
