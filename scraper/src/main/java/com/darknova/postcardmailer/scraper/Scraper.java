package com.darknova.postcardmailer.scraper;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * This scraper extracts data from the Clark County website.
 */
@Service
public class Scraper {

    private static final Logger LOG = LoggerFactory.getLogger(Scraper.class);

    public void test() throws IOException {
        final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
        HtmlPage page = webClient.getPage("https://recorder.co.clark.nv.us/RecorderEcommerce/default.aspx");

        final HtmlSpan doctypeSearchSpan = page.getFirstByXPath("/html/body/form/div[5]/div/div[2]/div[4]/div/div/ul/li[8]/a/span/span/span");
        page = doctypeSearchSpan.click();

        final HtmlTextInput doctypeInput = page.getFirstByXPath("/html/body/form/div[6]/div/div[2]/div[5]/div/div/div/div/div/div[2]/table/tbody/tr/td/input");
        doctypeInput.setText("D");

        final HtmlTextInput startDateInput = page.getFirstByXPath("/html/body/form/div[6]/div/div[2]/div[5]/div/div/div/div/div/div[4]/table/tbody/tr/td/span/input");
        final HtmlTextInput endDateInput = page.getFirstByXPath("/html/body/form/div[6]/div/div[2]/div[5]/div/div/div/div/div/div[5]/table/tbody/tr/td/span/input");

        startDateInput.setText("03/20/2014");
        endDateInput.setText("03/23/2014");

        final HtmlButtonInput searchButton = page.getFirstByXPath("/html/body/form/div[6]/div/div[2]/div[5]/div/div/div/div/div/div[6]/a/input");
        page = searchButton.click();

        final HtmlButtonInput csvExport = page.getFirstByXPath("/html/body/form/div[5]/div/div[2]/div[8]/div[8]/div/table/thead/tr/td/table/tbody/tr/td[2]/input[3]");
        page = csvExport.click();

        System.out.println(page.toString());
    }
}
