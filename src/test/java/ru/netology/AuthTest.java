package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class AuthTest {

    @BeforeEach
    void setUpPage() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldTestActiveUser() {
        DataGenerator.RegData activeUser = DataGenerator.generateUser("active");
        $("[data-test-id='login'] input").sendKeys(activeUser.getLogin());
        $("[data-test-id='password'] input").sendKeys(activeUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2").shouldBe(visible).shouldHave(exactText("Личный кабинет"));
    }

    @Test
    void shouldTestBlockedUser() {
        DataGenerator.RegData blockedUser = DataGenerator.generateUser("blocked");
        $("[data-test-id='login'] [class='input__control']").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] [class='input__control']").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login'] [class='button__text']").click();
        $("h2").shouldBe(visible);
    }

    @Test
    void shouldTestEmptyUser() {
        DataGenerator.RegData emptyUser = DataGenerator.generateEmptyUser("");
        $("[data-test-id='login'] [class='input__control']").setValue(emptyUser.getLogin());
        $("[data-test-id='password'] [class='input__control']").setValue(emptyUser.getPassword());
        $("[data-test-id='action-login'] [class='button__text']").click();
        $("[data-test-id='login'].input_invalid [class=input__sub]")
                .shouldBe(visible).shouldHave(exactText("Поле обязательно для заполнения"));
        $("[data-test-id='password'].input_invalid [class=input__sub]")
                .shouldBe(visible).shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestNotValidLogin() {
        DataGenerator.RegData activeUser = DataGenerator.generateUser("active");
        String randomLogin = DataGenerator.generateRandomLogin();
        $("[data-test-id='login'] input").setValue(randomLogin);
        $("[data-test-id='password'] input").setValue(activeUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] [class=notification__title]")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Ошибка"));
        $("[data-test-id='error-notification'] [class=notification__content]")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldTestNotValidPassword() {
        DataGenerator.RegData activeUser = DataGenerator.generateUser("active");
        String randomPassword = DataGenerator.generateRandomPassword();
        $("[data-test-id='login'] [class='input__control']").setValue(activeUser.getLogin());
        $("[data-test-id='password'] [class='input__control']").setValue(randomPassword);
        $("[data-test-id='action-login'] [class='button__text']").click();
        $("[data-test-id='error-notification'] [class=notification__title]")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Ошибка"));
        $("[data-test-id='error-notification'] [class=notification__content]")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }
}
