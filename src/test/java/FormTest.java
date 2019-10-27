import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class FormTest {

    @Test
    void happyPathTest(){
        open("http://localhost:9999");
        List <SelenideElement> fields = $$("[class='input__control']");
        fields.get(0).setValue("Иван Петров-Водкин");
        fields.get(1).setValue("+71234569999");
        $("[class='checkbox__box']").click();
        $("[class='button button_view_extra button_size_m button_theme_alfa-on-white']").submit();
        $("[class='paragraph paragraph_theme_alfa-on-white']").shouldHave(Condition.exactText("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }


    @Test
    void nameFieldValidation() {
        open("http://localhost:9999");
        List <SelenideElement> fields = $$("[class='input__control']");
        fields.get(0).setValue("Ivan Petrov");
        $("[class='button button_view_extra button_size_m button_theme_alfa-on-white']").submit();
        $("[class='input__sub']").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        field0Clear(fields);

        fields.get(0).setValue("56467845312");
        $("[class='button button_view_extra button_size_m button_theme_alfa-on-white']").submit();
        $("[class='input__sub']").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        field0Clear(fields);

        fields.get(0).setValue("Иван_Петров");
        $("[class='button button_view_extra button_size_m button_theme_alfa-on-white']").submit();
        $("[class='input__sub']").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        field0Clear(fields);

        fields.get(0).setValue("Иван ;етров");
        $("[class='button button_view_extra button_size_m button_theme_alfa-on-white']").submit();
        $("[class='input__sub']").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void phoneFieldValidation() {
        open("http://localhost:9999");
        List <SelenideElement> fields = $$("[class='input__control']");
        List <SelenideElement> subFields = $$("[class='input__sub']");
        fields.get(0).setValue("Иван Петров-Водкин");
        fields.get(1).setValue("71111591234");
        $("[class='button button_view_extra button_size_m button_theme_alfa-on-white']").submit();
        subFields.get(1).shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
        field1Clear(fields);

        fields.get(1).setValue("711115912345");
        $("[class='button button_view_extra button_size_m button_theme_alfa-on-white']").submit();
        subFields.get(1).shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
        field1Clear(fields);

        fields.get(1).setValue("-11115912345");
        $("[class='button button_view_extra button_size_m button_theme_alfa-on-white']").submit();
        subFields.get(1).shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
        field1Clear(fields);

        fields.get(1).setValue("wqeerrerteryt");
        $("[class='button button_view_extra button_size_m button_theme_alfa-on-white']").submit();
        subFields.get(1).shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    private void field0Clear(List<SelenideElement> fields) {
        fields.get(0).sendKeys(Keys.CONTROL + "a");
        fields.get(0).sendKeys(Keys.DELETE);
    }

    private void field1Clear(List<SelenideElement> fields) {
        fields.get(1).sendKeys(Keys.CONTROL + "a");
        fields.get(1).sendKeys(Keys.DELETE);
    }
}
