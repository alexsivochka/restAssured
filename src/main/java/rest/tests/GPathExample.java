package rest.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

public class GPathExample {

    JsonPath jsonPath;

    @BeforeMethod
    public void getTestJson(){
        jsonPath = JsonPath.from(new File("src/main/resources/jsonTestData.json"));
    }

    @Test(description = "Получаем корневой элемент")
    public void getRootElement() {
        String $ = jsonPath.getString("$");
        System.out.println($);
    }

    @Test(description = "Получаем список объектов")
    public void getClassElements() {
        String $ = jsonPath.getString("class");
        System.out.println($);
    }

    @Test(description = "Получаем первый элемент")
    public void getFirstClassElements() {
        String $ = jsonPath.getString("class[0]");
        System.out.println($);
    }

    @Test(description = "Получаем последний элемент")
    public void getLastClassElements() {
        String $ = jsonPath.getString("class[-1]");
        System.out.println($);
    }

    @Test(description = "Получаем элементы по индексам")
    public void getClassElementsFromTo() {
        String $ = jsonPath.getString("class[0..1]");
        System.out.println($);
    }

    @Test(description = "Получаем поле объекта")
    public void getClassElementsField() {
        String $ = jsonPath.getString("class[0].age");
        System.out.println($);
    }

    @Test(description = "Пример экранирования")
    public void getElementsWithEscapeChar() {
        String $ = jsonPath.getString("class[-1].'some.value'");
        System.out.println($);
    }

    @Test(description = "Условие find")
    public void getFind() {
        String $ = jsonPath.getString("class.find{it.age == '14'}");
        System.out.println($);
    }

    @Test(description = "Условие findAll")
    public void getFindAll() {
        String $ = jsonPath.getString("class.findAll{it.age == '14'}");
        System.out.println($);
    }

    @Test(description = "Условие findAll")
    public void getFindAll1() {
        String $ = jsonPath.getString("class.findAll{it.address.house > 10}");
        System.out.println($);
    }

    @Test(description = "Условие hasFiled")
    public void getHasField() {
        String $ = jsonPath.getString("class.findAll {it.name}");
        System.out.println($);
    }

    @Test(description = "Условие hasFiled")
    public void getHasField1() {
        String $ = jsonPath.getString("class.findAll {it.'some.value'}");
        System.out.println($);
    }

    @Test(description = "Функция min()")
    public void getMin() {
        String $ = jsonPath.getString("class[0].marks.min()");
        System.out.println($);
    }

    @Test(description = "Функция max()")
    public void getMax() {
        String $ = jsonPath.getString("class.address.house.max()");
        System.out.println($);
    }


    @Test(description = "Функция max()")
    public void getMax1() {
        String $ = jsonPath.getString("class.max {it.address.house}");
        System.out.println($);
    }

    @Test(description = "Функция sum()")
    public void getSum() {
        String $ = jsonPath.getString("class[-1].marks.sum() ");
        System.out.println($);
    }

    @Test(description = "Функция sum()")
    public void getSum1() {
        String $ = jsonPath.getString("class.marks.flatten().sum()");
        System.out.println($);
    }

    @Test(description = "Функция size()")
    public void getSize() {
        String $ = jsonPath.getString("class[0].address.size()");
        System.out.println($);
    }

    @Test(description = "Функция unique()")
    public void getUnique() {
        String $ = jsonPath.getString("class.name.unique()");
        System.out.println($);
    }

    @Test(description = "Функция endWith()")
    public void getEndWith() {
        String $ = jsonPath.getString("class.findAll {it.surname.endsWith('rov')}");
        System.out.println($);
    }

    @Test(description = "Функция startWith()")
    public void getStartWith() {
        String $ = jsonPath.getString("class.findAll {it.name.startsWith('I')}");
        System.out.println($);
    }

    @Test(description = "Функция contains()")
    public void getContains() {
        String $ = jsonPath.getString("class.findAll {it.name.contains('e')}");
        System.out.println($);
    }

    @Test(description = "Функция contains()")
    public void getContains1() {
        String $ = jsonPath.getString("class.findAll {it.marks.contains(2)}");
        System.out.println($);
    }

    @Test(description = "Функция notContains()")
    public void getNotContains() {
        String $ = jsonPath.getString("class.findAll {!it.marks.contains(2)}");
        System.out.println($);
    }

    @Test(description = "Функция collect()")
    public void getCollect() {
        String $ = jsonPath.getString("class.collect {it.age.toInteger()}.max()");
        System.out.println($);
    }

    @Test(description = "Функция collect()")
    public void getCollect1() {
        String $ = jsonPath.getString("class.collect {it.name + ' ' + it.surname}");
        System.out.println($);
    }

    @Test(description = "Функция firstLast()")
    public void getFirstAndLast() {
        String $ = jsonPath.getString("class.first().marks.last()");
        System.out.println($);
    }

    @Test(description = "Функция sort()")
    public void getSort() {
        String $ = jsonPath.getString("class.sort {it.surname}");
        System.out.println($);
    }

    @Test(description = "Функция reverse()")
    public void getReverse() {
        String $ = jsonPath.getString("class.sort {it.marks.sum()/it.marks.size()}.reverse()");
        System.out.println($);
    }

    @Test(description = "Функция every()")
    public void getEvery() {
        String $ = jsonPath.getString("class.every {it.marks.contains(5)}");
        System.out.println($);
    }

    @Test(description = "Функция any()")
    public void getEny() {
        String $ = jsonPath.getString("class.any {it.marks.contains(5)}");
        System.out.println($);
    }

    @Test(description = "changeData")
    public void changeData() {
        String $ = jsonPath.getString("class.each {it.marks = it.marks.sum()/it.marks.size()}");
        System.out.println($);
    }

    @Test(description = "addNewField")
    public void addNewField() {
        String $ = jsonPath.getString("class.each {it.average = it.marks.sum()/it.marks.size()}");
        System.out.println($);
    }
}
