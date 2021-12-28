package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class AuthTest {

    DataGenerator user;

    @BeforeEach
    void setUpPage() {
        open("http://localhost:9999/");
        user = new DataGenerator();
    }

    @Test
    void shouldTestActiveUser() {
        RegData activeUser = user.setActiveUser();
        $("[data-test-id='login'] [class='input__control']").setValue(activeUser.login);
        $("[data-test-id='password'] [class='input__control']").setValue(activeUser.password);
        $("[data-test-id='action-login'] [class='button__text']").click();
        $("h2").shouldBe(visible);
    }

    @Test
    void shouldTestBlockedUser() {
        RegData blockedUser = user.setBlockedUser();
        $("[data-test-id='login'] [class='input__control']").setValue(blockedUser.login);
        $("[data-test-id='password'] [class='input__control']").setValue(blockedUser.password);
        $("[data-test-id='action-login'] [class='button__text']").click();
        $("h2").shouldBe(visible);
    }

    @Test
    void shouldTestEmptyUser() {
        RegData emptyUser = user.setEmptyUser();
        $("[data-test-id='login'] [class='input__control']").setValue(emptyUser.login);
        $("[data-test-id='password'] [class='input__control']").setValue(emptyUser.password);
        $("[data-test-id='action-login'] [class='button__text']").click();
        $("[data-test-id='login'].input_invalid [class=input__sub]")
                .shouldBe(visible).shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestNotValidLogin() {
        RegData activeUser = user.setActiveUser();
        RegData notValidLogin = user.setNotValidLogin();
        $("[data-test-id='login'] [class='input__control']").setValue(notValidLogin.login);
        $("[data-test-id='password'] [class='input__control']").setValue(activeUser.password);
        $("[data-test-id='action-login'] [class='button__text']").click();
        $("[data-test-id='error-notification'] [class=notification__title]")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Ошибка"));
        $("[data-test-id='error-notification'] [class=notification__content]")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldTestNotValidPassword() {
        RegData activeUser = user.setActiveUser();
        RegData notValidPassword = user.setNotValidPassword();
        $("[data-test-id='login'] [class='input__control']").setValue(activeUser.login);
        $("[data-test-id='password'] [class='input__control']").setValue(notValidPassword.password);
        $("[data-test-id='action-login'] [class='button__text']").click();
        $("[data-test-id='error-notification'] [class=notification__title]")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Ошибка"));
        $("[data-test-id='error-notification'] [class=notification__content]")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));
    }
}
