package com.thuydung.testcases;

import com.thuydung.common.BaseTest;
import net.sourceforge.tess4j.TesseractException;
import org.testng.annotations.Test;
import java.io.IOException;

public class getTextFromCanvas extends BaseTest {
    @Test
    public void getTextFromCanvas2() throws TesseractException, IOException {
        getRegisterPage().getTextFromCanvas2();
    }
}
