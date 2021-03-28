package redmine_projeto;

import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.locators.RelativeLocator.withTagName;

public class Redmine {

    private WebDriver driver;

    private WebDriverWait wait;

    Date date = new Date(); //criar um número para não repetir
    long complemento = date.getTime();

    //criando um json array chamado título que contém as tarefas de bug
    private JSONArray titulos = new JSONArray()
            .put("bug" + complemento + 1)
            .put("bug" + complemento + 2)
            .put("bug" + complemento + 3)
            .put("bug" + complemento + 4)
            .put("bug" + complemento + 5)
            .put("bug" + complemento + 6)
            .put("bug" + complemento + 7)
            .put("bug" + complemento + 8)
            .put("bug" + complemento + 9)
            .put("bug" + complemento + 10)
            .put("bug" + complemento + 11)
            .put("bug" + complemento + 12)
            .put("bug" + complemento + 13)
            .put("bug" + complemento + 14)
            .put("bug" + complemento + 15)
            .put("bug" + complemento + 16)
            .put("bug" + complemento + 17)
            .put("bug" + complemento + 18)
            .put("bug" + complemento + 19)
            .put("bug" + complemento + 20)
            .put("bug" + complemento + 21)
            .put("bug" + complemento + 22)
            .put("bug" + complemento + 23)
            .put("bug" + complemento + 24)
            .put("bug" + complemento + 25)
            .put("bug" + complemento + 26)
            .put("bug" + complemento + 27)
            .put("bug" + complemento + 28)
            .put("bug" + complemento + 29)
            .put("bug" + complemento + 30);


    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    void realizarCadastro(){
        //acessando a página redmine
        driver.get("http://demo.redmine.org/"); //abrir página redmine demo
        driver.findElement(By.cssSelector("#account > ul > li:nth-child(2) > a")).click(); //clicando em cadastra-se

        //preenchendo os dados de criação de usuário
        driver.findElement(By.cssSelector("#user_login")).sendKeys("test" + complemento); //preenchendo usuário
        driver.findElement(By.cssSelector("#user_password")).sendKeys("abacaxi01"); //preenchendo senha
        driver.findElement(By.cssSelector("#user_password_confirmation")).sendKeys("abacaxi01"); //preenchendo confirmação senha
        driver.findElement(By.cssSelector("#user_firstname")).sendKeys("Teste"); //preenchendo nome
        driver.findElement(By.cssSelector("#user_lastname")).sendKeys("Criando"); //preenchendo sobrenome
        driver.findElement(By.cssSelector("#user_mail")).sendKeys("test" + complemento + "@test.com"); //preenchendo e-mail
        driver.findElement(By.cssSelector("#new_user > input[type=submit]:nth-child(4)")).click(); //clicando no botão cadastrar

        Assertions.assertEquals("Sua conta foi ativada. Você pode acessá-la agora.", driver.findElement(By.cssSelector("#flash_notice")).getText()); //validando se o teste foi para a tela esperada

        //preenchendo os dados de criação de um projeto
        driver.get("http://demo.redmine.org/projects"); //acessando projetos
        driver.findElement(By.cssSelector("#content > div.contextual > a.icon.icon-add")).click(); //clicando em novo projeto
        driver.findElement(By.cssSelector("#project_name")).sendKeys("Projeto" + complemento); //preenchendo nome do projeto
        driver.findElement(By.cssSelector("#project_trackers > label:nth-child(3) > input[type=checkbox]")).click(); //desmarcando feature
        driver.findElement(By.cssSelector("#project_trackers > label:nth-child(4) > input[type=checkbox]")).click(); //desmarcando support
        driver.findElement(By.cssSelector("#new_project > input[type=submit]:nth-child(8)")).click(); //clicando no botão salvar e  continuar

        Assertions.assertEquals("Criado com sucesso.", driver.findElement(By.cssSelector("#flash_notice")).getText()); //validando se o teste foi para a tela esperada

        //acessando o projeto
        driver.get("http://demo.redmine.org/projects"); //acessando projetos
        driver.findElement(By.xpath("//a[contains(text(),'Projeto" + complemento +"')]")).click(); //encontrando o projeto pelo nome
        driver.findElement(By.cssSelector("#main-menu > ul > li:nth-child(4) > a")).click(); //clicando na aba nova tarefa

        //criando as 30 tarefas de bug com base nos titulos criados anteriormente com o json
        for (int i=0; i < 30; i ++) {
            driver.findElement(By.cssSelector("#issue_subject")).sendKeys(titulos.getString(i));
            driver.findElement(By.cssSelector("#issue-form > input[type=submit]:nth-child(5)")).click();
        }

        //paginação do grid e validação da 29ª tarefa
        driver.findElement(By.cssSelector("#main-menu > ul > li:nth-child(3) > a")).click(); //clicar na aba tarefas
        Assertions.assertEquals("Normal", driver.findElement(By.xpath("//a[contains(.,'" + titulos.getString(28) +"')]/../..")).findElement(By.className("priority")).getText());
        Assertions.assertEquals("New", driver.findElement(By.xpath("//a[contains(.,'" + titulos.getString(28) +"')]/../..")).findElement(By.className("status")).getText());
        Assertions.assertEquals("Bug", driver.findElement(By.xpath("//a[contains(.,'" + titulos.getString(28) +"')]/../..")).findElement(By.className("tracker")).getText());

    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

}
