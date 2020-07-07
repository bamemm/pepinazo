package todoist;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static junit.framework.Assert.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TodoistStepDefs {
    public WebDriver driver;
    public WebDriverWait wait;
    @Before
    public void setUpTest() {
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("--disable-notifications");
        String driverPath = System.getProperty("C:\\libs\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver",driverPath);
        driver = new ChromeDriver(opt);
        wait = new WebDriverWait(driver, 30);
        driver.manage().window().setSize(new Dimension(900, 550));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    @After
    public void tearDownTest() {
        driver.quit();
    }
    @Given("I navigate to todoist")
    public void iNavigateToTodoist() {
        driver.get("https://todoist.com");
        String title = driver.getTitle();
        //asegurar que el titulo sea igual a "Todoist: The to do list to organize work & life";
        assertEquals("Todoist: The to do list to organize work & life", title);
        System.out.println("Titulo es correcto");
    }
    @When("I enter my credentials")
    public void iEnterMyCredentials() {
        //click login button
        driver.findElement(By.cssSelector("[href='/users/showlogin']")).click();
        WebElement userNameTxt = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        //asegurarse que el userNameTxt este visible
        assertTrue(userNameTxt.isDisplayed());
        System.out.println("userNameTxt es visible");
        //enter username
        userNameTxt.sendKeys("jomarnavarro@gmail.com");
        //enter password
        driver.findElement(By.id("password")).sendKeys("Test@1234");

        //click login button
        WebElement loginButton = driver.findElement(By.cssSelector(".submit_btn"));
        ///asegurarse que el boton este habilitado
        assertTrue(loginButton.isEnabled());
        System.out.println("loginButton esta habilitado");
        //click al boton
        loginButton.click();
    }
    @Then("I can see the Project page")
    public void iCanSeeTheProjectPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text() = 'Inbox']")));
        //asegurar que el titulo de la pagina sea "Today: Todoist"
        String title = driver.getTitle();
        assertEquals("Today: Todoist", title);
        System.out.println("Titulo after login es correcto");
    }
    @When("I log in using facebook")
    public void iLogInUsingFacebook() {
        //click login button
        driver.findElement(By.cssSelector("[href='/users/showlogin']")).click();
        WebElement userNameTxt = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        //darle click a login with facebook
        driver.findElement(By.cssSelector(".ist_button_facebook")).click();
        //autorizar la aplicacion
        driver.findElement(By.id("email")).sendKeys("jomarnavarro@gmail.com");
        driver.findElement(By.id("pass")).sendKeys("Test123456");
        driver.findElement(By.id("loginbutton")).click();
    }

    @Given("I am in todist website")
    public void iAmInTodistWebsite() {
        iNavigateToTodoist();
        iEnterMyCredentials();
        iCanSeeTheProjectPage();
    }

    @When("I add a new project form the {string}")
    public void agregoUnProyectoNuevoDesde(String manera) {
        if(manera.contains("+")) {
            agregarProyectoDesdeIcono();
        } else if (manera.contains("New Project")) {
            agregarProyectoLigaNewProject();
        } else if(manera.contains("above")) {
            agregarProyectoArriba();
        } else if(manera.contains("below")) {
            agregarProyectoAbajo();
        } else if(manera.contains("task")) {
            agregarProyectoTask();
        }
    }

    private void agregarProyectoTask() {
    }

    private void agregarProyectoAbajo() {
    }

    private void agregarProyectoArriba() {
    }

    private void agregarProyectoLigaNewProject() {

    }
    private void agregarProyectoDesdeIcono() {
        //css="button[data-track='navigation|projects_panel'] + div > button"
        WebElement addProjectbtn = driver.findElement(By.cssSelector("button[data-track='navigation|projects_panel']"));

        //luego poner el cursor encima del elemento projects
        Actions ac = new Actions(driver);
        ac.moveToElement(addProjectbtn).perform();


        //wait hasta que este visible el icono de mas
        //css="button[data-track='navigation|projects_panel'] + div > button"
        //darle click al elemento

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-track='navigation|projects_panel'] + div > button"))).click();

        //wait hasta que este presente el elemento css="div[role='dialog'] form"
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[role='dialog'] form")));

        //buscar el elemento de projectname id="edit_project_modal_field_name"
        WebElement projectModal = driver.findElement(By.id("edit_project_modal_field_name"));
        projectModal.sendKeys("Proyecto X");

        //buscar el elemento que dice css="button[aria-labelledby="edit_project_modal_field_color_label"]"
        WebElement colorBtn = driver.findElement(By.cssSelector("button[aria-labelledby='edit_project_modal_field_color_label']"));
        // /darle click
        colorBtn.click();
        // esperar hasta que haya muchos elementos en la lista css="li[id*='dropdown-select']"

        // click en el elemento cuyo linkText="<color deseado>"
        List<WebElement> colores = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("li[id*='dropdown-select']")));
        WebElement color = colores.get(2);
        color.click();

        //buscar el elemento de 'favorito', y darle click solo en el caso de que sea fovorito name="is_favorite"
        //WebElement favoriteBtn = driver.findElement(By.cssSelector("[name='is_favorite']"));
        //favoriteBtn.isSelected();
        WebElement favoriteBtn = driver.findElement(By.cssSelector(".reactist_switch"));
        favoriteBtn.click ();

        //buscar por linkText="Add" y darle click
        WebElement addBtn = driver.findElement(By.cssSelector("[type='submit']"));
        addBtn.click();
    }

    @Then("The new project is listed in the end")
    public void theNewProjectIsListedInTheEnd() {
        System.out.println("Entramos al Then");
    }

   }
