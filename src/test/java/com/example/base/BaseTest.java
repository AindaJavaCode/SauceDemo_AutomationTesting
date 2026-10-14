package com.example.base;

import com.example.config.Config;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseTest {

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    protected WebDriver driver;

    @BeforeEach
    protected void setUp(TestInfo info) {
        log.info("START: {} on {}", info.getDisplayName(), Config.browser());
        DriverManager.start();
        driver = DriverManager.getDriver();
        driver.get(Config.baseUrl());
    }

    @AfterEach
    protected void tearDown(TestInfo info) {
        DriverManager.quit();
        log.info("END: {}", info.getDisplayName());
    }
}