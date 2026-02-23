package gift.cucumber;

import io.cucumber.java.Before;
import io.cucumber.java.ko.그러면;
import io.cucumber.java.ko.그리고;
import io.cucumber.java.ko.만약;
import io.cucumber.java.ko.조건;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

public class CategoryStepDefinitions {

    @Autowired
    private CucumberSpringConfiguration configuration;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Response response;

    @Before
    public void setUp() {
        configuration.setUpRestAssured();
    }

    @조건("{string} 카테고리가 등록되어 있다")
    public void 카테고리가_등록되어_있다(String categoryName) {
        jdbcTemplate.update("INSERT INTO category (name) VALUES (?)", categoryName);
    }

    @만약("카테고리 목록을 조회하면")
    public void 카테고리_목록을_조회하면() {
        response = RestAssured.given()
                .when()
                .get("/api/categories");
    }

    @그러면("응답 상태 코드는 {int}이다")
    public void 응답_상태_코드는_이다(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @그리고("카테고리 목록에 {string}이 포함되어 있다")
    public void 카테고리_목록에_이_포함되어_있다(String categoryName) {
        response.then().body("name", hasItem(categoryName));
    }

    @그리고("카테고리 목록의 크기는 {int}이다")
    public void 카테고리_목록의_크기는_이다(int size) {
        response.then().body("", hasSize(size));
    }
}
