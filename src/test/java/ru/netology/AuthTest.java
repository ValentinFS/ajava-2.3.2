package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

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
    }

    @Test
    void shouldTestBlockedUser() {
        RegData blockedUser = user.setBlockedUser();
        $("[data-test-id='login'] [class='input__control']").setValue(blockedUser.login);
        $("[data-test-id='password'] [class='input__control']").setValue(blockedUser.password);
        $("[data-test-id='action-login'] [class='button__text']").click();
    }

    @Test
    void shouldTestEmptyUser() {
        RegData emptyUser = user.setEmptyUser();
        $("[data-test-id='login'] [class='input__control']").setValue(emptyUser.login);
        $("[data-test-id='password'] [class='input__control']").setValue(emptyUser.password);
        $("[data-test-id='action-login'] [class='button__text']").click();
        $("[data-test-id='login'].input_invalid [class=input__sub]")
                .shouldBe(Condition.visible).shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestNotValidLogin() {
        RegData notValidLogin = user.setNotValidLogin();
        $("[data-test-id='login'] [class='input__control']").setValue(notValidLogin.login + "1");
        $("[data-test-id='password'] [class='input__control']").setValue(notValidLogin.password);
        $("[data-test-id='action-login'] [class='button__text']").click();
        $("[data-test-id='error-notification'] [class=notification__title]")
                .shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Ошибка"));
        $("[data-test-id='error-notification'] [class=notification__content]")
                .shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));

    }

    @Test
    void shouldTestNotValidPassword() {
        RegData notValidPassword = user.setNotValidPassword();
        $("[data-test-id='login'] [class='input__control']").setValue(notValidPassword.login);
        $("[data-test-id='password'] [class='input__control']").setValue(notValidPassword.password + "1");
        $("[data-test-id='action-login'] [class='button__text']").click();
        $("[data-test-id='error-notification'] [class=notification__title]")
                .shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Ошибка"));
        $("[data-test-id='error-notification'] [class=notification__content]")
                .shouldBe(Condition.visible, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
    }
}
