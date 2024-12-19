package org.example;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.List;

public class PriceComparison {
    public static void main(String[] args) {
        WebDriverManager.firefoxdriver().setup();
        WebDriver driver = new FirefoxDriver();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");


        List<String> urls = List.of(
                "https://www.hepsiburada.com/sony-playstation-5-slim-digital-edition-pm-HBC00005FNTP8",
                "https://www.vatanbilgisayar.com/sony-playstation-5-slim-digital-surum-oyun-konsolu.html",
                "https://www.trendyol.com/sony/playstation-5-dijital-slim-turkce-menu-1-tb-p-781022412?boutiqueId=61&merchantId=981546"
        );

        List<Double> prices = new ArrayList<>();
        String[] priceSelectors = {
                "//*[@id=\"container\"]/div/div/main/div/div/div[2]/section[1]/div[2]/div[3]/div[1]/div/span[1]",
                "/html/body/main/div[1]/div[5]/div/div/div/div/div[2]/div[3]/div/div[1]/div[2]/div[1]/div/div/span[1]",
                "//*[@id=\"product-detail-app\"]/div/div[2]/div/div[2]/div[2]/div/div[1]/div[2]/div/div/div[4]/div/div/span[1]"
        };
        try {
            for (int i = 0; i < urls.size(); i++) {
                driver.get(urls.get(i));
                WebElement priceElement = driver.findElement(By.xpath(priceSelectors[i]));
                String priceText = priceElement.getText();

                priceText = priceText.replaceAll("\\.", "");
                priceText = priceText.replaceAll("[^\\d.,]", "");

                priceText = priceText.replace(",", ".");

                // String'i double'a dönüştür
                double price = Double.parseDouble(priceText);
                prices.add(price);
            }
        } finally {
            driver.quit();
        }

        double cheapest = prices.stream().min(Double::compareTo).orElse(0.0);
        double mostExpensive = prices.stream().max(Double::compareTo).orElse(0.0);
        double average = prices.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        System.out.printf("Cheapest: %.2f₺, Most Expensive: %.2f₺, Average: %.2f₺%n", cheapest, mostExpensive, average);    }
}