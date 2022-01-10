package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static Faker faker = new Faker(new Locale("en"));

    private static void sendRequest(RegData user) {
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(user) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static String generateRandomLogin() {
        return faker.name().firstName().toLowerCase();
    }

    public static String generateRandomPassword() {
        return faker.internet().password().toLowerCase();
    }

    public static String generateEmptyLogin() {
        String login = "";
        return login;
    }

    public static String generateEmptyPassword() {
        String password = "";
        return password;
    }


    public static RegData generateEmptyUser(String status) {
        RegData user = new RegData(generateEmptyLogin(), generateEmptyPassword(), status);
        return user;
    }

    public static RegData generateUser(String status) {
        RegData user = new RegData(generateRandomLogin(), generateRandomPassword(), status);
        sendRequest(user);
        return user;
    }

//    public static RegData setBlockedUser() {
//        RegData blockedUser = new RegData(faker.name().username(), faker.internet().password(), "blocked");
//        setUpAll(blockedUser);
//        return blockedUser;
//    }
//
//    public static RegData setEmptyUser() {
//        RegData emptyUser = new RegData("", faker.internet().password(), "active");
//        setUpAll(emptyUser);
//        return emptyUser;
//    }


//    public RegData setNotValidLogin() {
//        RegData notValidLogin = new RegData(faker.name().firstName().toLowerCase(), faker.internet().password(), "active");
//        setUpAll(notValidLogin);
//        return notValidLogin;
//    }
//
//    public RegData setNotValidPassword() {
//        RegData notValidPassword = new RegData(faker.name().username(), faker.internet().password().toLowerCase(), "active");
//        setUpAll(notValidPassword);
//        return notValidPassword;
//    }

    @Value
    public static class RegData {

        String login;
        String password;
        String status;

    }
}