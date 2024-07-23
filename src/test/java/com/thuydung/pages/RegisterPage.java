package com.thuydung.pages;

import com.thuydung.helpers.PropertiesHelper;
import com.thuydung.helpers.SystemHelper;
import com.thuydung.keywords.WebUI;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterPage extends CommonPage{
    public static By imgCanvasCode = By.xpath("//canvas[@id='captcha']");
    public static By buttonRefreshCaptcha = By.xpath("//a[@class='btn btn-icon']");
    public void getTextFromCanvas2() throws TesseractException, IOException {
        WebUI.openURL(PropertiesHelper.getValue("URL"));
        WebUI.scrollToElementToBottom(imgCanvasCode);
        WebUI.sleep(2);
//        WebUI.getCaptchaCanvas(imgCanvasCode);
        getTextFromCaptcha();
    }
    public void getTextFromCaptcha() {
        File f = WebUI.getWebElement(imgCanvasCode).getScreenshotAs(OutputType.FILE);
        String filePath = SystemHelper.getCurrentDir() + "src\\test\\resources\\CaptchaImages\\Captcha" + ".png";

        File theDir = new File("src\\test\\resources\\CaptchaImages");
        if (!theDir.exists()) {
            theDir.mkdirs();
        }

        try {
            FileUtils.copyFile(f, new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File image = new File(filePath);
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
        tesseract.setLanguage("eng");
        tesseract.setPageSegMode(1);
        tesseract.setOcrEngineMode(1);
        String result;
        try {
            result = tesseract.doOCR(image);
            result = result.replace(" ", "");
            System.out.println("TEXT EXTRACT: " + result);
        } catch (TesseractException e) {
            throw new RuntimeException(e);
        }

        if (result.isEmpty() || result.isBlank()) {
            for (int i = 1; i <= 5; i++) {
                try {
                    WebUI.clickElement(buttonRefreshCaptcha);
                    WebUI.waitForPageLoaded();
                    WebUI.sleep(1);
                    File f2 = WebUI.getWebElement(imgCanvasCode).getScreenshotAs(OutputType.FILE);

                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
                    String filePath2 = SystemHelper.getCurrentDir() + "src\\test\\resources\\CaptchaImages\\Captcha" + ".png";
                    FileUtils.copyFile(f2, new File(filePath2));

                    result = tesseract.doOCR(image);
                    result = result.replace(" ", "");
                    System.out.println("TEXT EXTRACT: " + result);
                    if (!result.isEmpty() && !result.isBlank()) {
                        break;
                    }
                } catch (TesseractException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


}
