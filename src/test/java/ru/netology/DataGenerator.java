package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static Faker faker = new Faker(new Locale("en"));

    @BeforeAll
    static void setUpAll(RegData user) {
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(user) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public RegData setActiveUser() {
        RegData activeUser = new RegData(faker.name().username(), faker.internet().password(), "active");
        setUpAll(activeUser);
        return activeUser;
    }

    public RegData setBlockedUser() {
        RegData blockedUser = new RegData(faker.name().username(), faker.internet().password(), "blocked");
        setUpAll(blockedUser);
        return blockedUser;
    }

    public RegData setEmptyUser() {
        RegData emptyUser = new RegData("", faker.internet().password(), "active");
        setUpAll(emptyUser);
        return emptyUser;
    }


    public RegData setNotValidLogin() {
        RegData notValidLogin = new RegData(faker.name().firstName().toLowerCase(), faker.internet().password(), "active");
        setUpAll(notValidLogin);
        return notValidLogin;
    }

    public RegData setNotValidPassword() {
        RegData notValidPassword = new RegData(faker.name().username(), faker.internet().password().toLowerCase(), "active");
        setUpAll(notValidPassword);
        return notValidPassword;
    }

}
