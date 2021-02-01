package group03.project.page;

import group03.project.services.required.ActivityRepository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class ActivitySignupTest {

    private WebDriver webDriver;

    @Autowired
    ActivityRepository activeRepo;

    // @Test
    public void shouldAddTagAsUserAndDeleteTagAsAdmin() {

        String path = System.getProperty("user.dir");
        System.out.println(path);

        System.setProperty("webdriver.gecko.driver", path + "//src//test//resources//geckodriver.exe");

        webDriver = new FirefoxDriver();


        this.webDriver.get("http://localhost:8080");

        // Log in as a user account.

        WebElement userNameBox = this.webDriver.findElement(By.id("username"));
        userNameBox.clear();
        userNameBox.sendKeys("user");

        WebElement userPasswordBox = this.webDriver.findElement(By.id("password"));
        userPasswordBox.clear();
        userPasswordBox.sendKeys("passw");

        this.webDriver.findElement(By.id("login-button")).click();

        // Check that specific user has logged in successfully by confirming their progress.
        assertThat(webDriver.findElement(By.id("container-box-completed-tags"))
                .getText()).contains("A1 A3");
        assertThat(webDriver.findElement(By.id("participations"))
                .getText()).contains("Participation #19");

        // Navigate to tag creation & create a test tag.

        this.webDriver.findElement(By.id("add-new-tag-btn")).click();

        WebElement tagNameBox = this.webDriver.findElement(By.id("tag-identifier"));
        tagNameBox.click();
        tagNameBox.clear();
        tagNameBox.sendKeys("A new tag");

        WebElement tagDescbox = this.webDriver.findElement(By.id("tag-description"));
        tagDescbox.click();
        tagDescbox.clear();
        tagDescbox.sendKeys("A new tag description");

        this.webDriver.findElement(By.id("submit")).click();

        // Logout as user.
        this.webDriver.findElement(By.id("button-logoff")).click();

        // Check if user has logged out.
        assertThat(webDriver.findElement(By.id("user-name-label")).getText()).contains("User Name");

        // Log in as an admin.

        WebElement adminNameBox = this.webDriver.findElement(By.id("username"));
        adminNameBox.clear();
        adminNameBox.sendKeys("admin");

        WebElement adminPasswordBox = this.webDriver.findElement(By.id("password"));
        adminPasswordBox.clear();
        adminPasswordBox.sendKeys("pass");

        this.webDriver.findElement(By.id("login-button")).click();

        // Confirm that admin has logged in correctly.
        assertThat(webDriver.findElement(By.id("container-box-graph"))
                .getText()).contains("Site Statistics - Users' Completed Tags");

        // Navigate to view of all tags.
        this.webDriver.findElement(By.id("view-official-tags-btn")).click();


        // Find latest tag entry made in table.
        WebElement lastRow = webDriver.findElement(By.xpath("(//table[@id='custom_table']/tbody/tr)[last()]"));

        // Confirm that latest tag is custom one created within test.
        assertThat(lastRow.getText().contains("A new tag"));

        // Delete the tag.
        lastRow.findElement(By.id("delete-row")).click();

        // Check final tag within table again - this should be different now.
        lastRow = webDriver.findElement(By.xpath("(//table[@id='custom_table']/tbody/tr)[last()]"));

        // Confirm that final row is no longer the new tag.
        assertFalse(lastRow.getText().contains("A new tag"));



    }
}
