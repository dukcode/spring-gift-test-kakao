package gift.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberSpringConfiguration {

    @LocalServerPort
    private int port;

    @Value("${test.base-url:}")
    private String baseUrl;

    public int getPort() {
        return port;
    }

    public void setUpRestAssured() {
        if (baseUrl != null && !baseUrl.isEmpty()) {
            RestAssured.baseURI = baseUrl;
            RestAssured.port = RestAssured.DEFAULT_PORT;
        } else {
            RestAssured.baseURI = "http://localhost";
            RestAssured.port = port;
        }
    }
}
