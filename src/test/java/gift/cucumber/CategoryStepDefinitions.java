package gift.cucumber;

import io.cucumber.java.ko.그리고;
import io.cucumber.java.ko.만약;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

public class CategoryStepDefinitions {

    @Autowired
    private SharedState sharedState;

    @만약("카테고리 목록을 조회하면")
    public void 카테고리_목록을_조회하면() {
        sharedState.setResponse(RestAssured.given()
                .when()
                .get("/api/categories"));
    }

    @그리고("카테고리 목록에 {string}이 포함되어 있다")
    public void 카테고리_목록에_이_포함되어_있다(String categoryName) {
        sharedState.getResponse().then().body("name", hasItem(categoryName));
    }

    @그리고("카테고리 목록의 크기는 {int}이다")
    public void 카테고리_목록의_크기는_이다(int size) {
        sharedState.getResponse().then().body("", hasSize(size));
    }
}
