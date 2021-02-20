package com.automationpractice;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class FirstTestRefactored {
    WebDriver driver;

    @BeforeMethod
    public void beforeMethod() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();

        //driver.manage().timeouts().implicitlyWait(40000, TimeUnit.MILLISECONDS);

        driver.manage().window().maximize();
    }

    @Test
    public void test2() {
        driver.get("https://google.com.tr/");
    }

//    @Test
//    public void test3(){
//        System.out.println("test3");
//    }

    @Test
    public void test() {


        driver.get("http://automationpractice.com/");

        //WebElement btnSignIn2 = driver.findElement(By.id("search_query_top"));
        //WebElement btnSignIn3 = driver.findElement(By.xpath("//DIV[@id='search_block_top']//FORM//INPUT[@placeholder='search']"));


        WebElement btnSignIn = driver.findElement(By.className("login"));
        btnSignIn.click();

        WebElement txtEmail = driver.findElement(By.id("email"));
        txtEmail.clear();
        txtEmail.sendKeys("recep.duman@yahoo.com");

        WebElement txtPassword = driver.findElement(By.id("passwd"));
        txtPassword.clear();
        txtPassword.sendKeys("Avis3101");

        WebElement btnSubmit = driver.findElement(By.id("SubmitLogin"));
        btnSubmit.click();

        WebElement lblLoggedInUserName = driver.findElement(By.className("account"));
        System.out.println(lblLoggedInUserName.getText());

        //Assert.assertEquals(lblLoggedInUserName.getText(), "Recep Mehmet");
        //Assert.assertEquals(lblLoggedInUserName.getText(), "Recep Dumann", "Kullanıcı Adları Eşleşmiyor.");

        WebElement btnMyPersonalInformation = driver.findElement(By.xpath("//A[@title='Information']"));
        btnMyPersonalInformation.click();


        WebElement txtLastName = driver.findElement(By.id("lastname"));
        txtLastName.clear();
        txtLastName.sendKeys("Mehmet");

        WebElement txtCurrentPassword = driver.findElement(By.id("old_passwd"));
        txtCurrentPassword.sendKeys("Avis3101");

        WebElement btnSave = driver.findElement(By.name("submitIdentity"));
        btnSave.click();


        //WebDriverWait shortWait = new WebDriverWait(driver,5);
        //shortWait.until(ExpectedConditions.elementToBeClickable(btnSave));

        WebElement lblSuccessResultText = driver.findElement(By.xpath("//P[contains(text(),\"Your personal information has been successfully updated.\")]"));

        //display-none yapınca ekranda görünmediği için patlıyor.
        Assert.assertTrue(lblSuccessResultText.isDisplayed(), "Başarı Mesajı Ekranda Görüntülenemedi.");

        menuSec("Women");

        WebElement productGrid = driver.findElement(By.xpath("//UL[@class='product_list grid row']/li"));
        Actions actions = new Actions(driver);
        actions.moveToElement(productGrid).build().perform();

        //moveTo event'i hover gibi çalışıyor
        WebElement firstProductAddButton = driver.findElement(By.xpath("(//A[@title='Add to cart'])[1]"));
        firstProductAddButton.click();

        WebElement cartResultMessage = driver.findElement(By.xpath("//DIV[@id='layer_cart']//H2[contains(., 'Product successfully added to your shopping cart')]"));

        WebDriverWait shortWait = new WebDriverWait(driver, 5);
        shortWait.until(ExpectedConditions.visibilityOf(cartResultMessage));

        Assert.assertTrue(cartResultMessage.isDisplayed(), "Sepete Ekleme Başarısız");

        WebElement btnProceedToCheckOut = driver.findElement(By.xpath("//A[@title='Proceed to checkout' and @rel='nofollow']"));
        btnProceedToCheckOut.click();

        //yeni sayfada tekrar arat butonu, özellikleri aynı fakat sayfalar farklı (stale_element hatası almamak için tekrar değişkene alınır)
        btnProceedToCheckOut = driver.findElement(By.xpath("//DIV[@id='center_column']//A[@title='Proceed to checkout']"));
        btnProceedToCheckOut.click();

        WebElement btnProcessAddress = driver.findElement(By.name("processAddress"));
        btnProcessAddress.click();

        WebElement chkTerms = driver.findElement(By.id("cgv"));

        checkElement(chkTerms);


        //System.out.println("test Hello");

        //throw new RuntimeException("process is failed!");

    }

    @Test
    public void dropdownTest(){
        driver.get("https://the-internet.herokuapp.com/dropdown");

        WebElement inpDropdown = driver.findElement(By.id("dropdown"));

        Select selectDropdown = new Select(inpDropdown);
        selectDropdown.selectByVisibleText("Option 2");
        selectDropdown.selectByValue("1");

        List<WebElement> selectOptions = selectDropdown.getOptions();

        for (int i=0; i < selectOptions.size(); i++){
            String optionText = selectOptions.get(i).getText();
            String optionValue = selectOptions.get(i).getAttribute("value");

            System.out.println(optionText);
            System.out.println(optionValue);
        }


    }

    public void checkElement(WebElement element){
        Boolean isTermsChecked = Boolean.parseBoolean(element.getAttribute("checked"));

        System.out.println("İlk durum: " + isTermsChecked);
        if (!isTermsChecked) {
            element.click();
        }

        isTermsChecked = Boolean.parseBoolean(element.getAttribute("checked"));

        System.out.println("İkinci durum: " + isTermsChecked);
    }

    public void menuSec(String menuAdi) {
        WebElement btnMenu = driver.findElement(By.xpath("//DIV[@id='block_top_menu']//A[@title='" + menuAdi + "']"));
        btnMenu.click();
    }

    @AfterClass
    public void afterMethod() {
        //driver.quit();
    }
}
