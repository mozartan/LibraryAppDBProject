package com.library.step_definitions;

import com.library.pages.BookPage;
import com.library.pages.CommonAreaPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class BookStepDefs {
    String actualCategory;

    @When("I execute query to find most popular book genre")
    public void i_execute_query_to_find_most_popular_book_genre() {
        String query = "select bc.name,count(*) from book_borrow bb inner join books b on bb.book_id = b.id\n" +
                "inner join book_categories bc on b.book_category_id = bc.id\n" +
                "group by bc.name\n" +
                "order by 2 desc ";

        DB_Util.runQuery(query);
        actualCategory = DB_Util.getFirstRowFirstColumn();
        System.out.println("actualCategory = " + actualCategory);

    }

    @Then("verify {string} is the most popular book genre.")
    public void verify_is_the_most_popular_book_genre(String expectedCategory) {

        Assert.assertEquals(expectedCategory, actualCategory);
    }

    String actualUser;

    @When("I execute query to find most popular user")
    public void i_execute_query_to_find_most_popular_user() {
        String query = "select full_name,count(*) from users u inner join book_borrow bb on u.id = bb.user_id\n" +
                "group by full_name\n" +
                "order by 2 desc ";
        DB_Util.runQuery(query);
        actualUser = DB_Util.getFirstRowFirstColumn();
        System.out.println("actualUser = " + actualUser);
    }

    @Then("verify {string} is the user who reads the most")
    public void verify_is_the_user_who_reads_the_most(String expectedUser) {
        Assert.assertEquals(expectedUser, actualUser);
    }


    @When("I navigate to {string} page")
    public void i_navigate_to_page(String moduleName) {
        new CommonAreaPage().navigateModule(moduleName);
    }

    BookPage bookPage = new BookPage();

    String bookName;

    @When("I open a book called {string}")
    public void i_open_a_book_called(String book) {
        bookName = book;
        bookPage.search.sendKeys(bookName);
        BrowserUtil.waitFor(3);


    }

    @When("I execute query to get the book information from books table")
    public void i_execute_query_to_get_the_book_information_from_books_table() {
        String query = "select name, author, year from books where name='" + bookName + "'";
        DB_Util.runQuery(query);
    }


    @Then("verify book DB and UI information must match")
    public void verify_book_db_and_ui_information_must_match() {

        // DB Data
        Map<String, String> bookMap = DB_Util.getRowMap(1);
        String actualBookName = bookMap.get("name");
        String actualAuthor = bookMap.get("author");
        String actualYear = bookMap.get("year");


        // UI Data

        String expectedBookName = bookPage.bookName.getText();
        String expectedAuthor = bookPage.authorName.getText();
        String expectedYear = bookPage.year.getText();

        // Verify

        Assert.assertEquals(expectedBookName, actualBookName);
        Assert.assertEquals(expectedAuthor, actualAuthor);
        Assert.assertEquals(expectedYear, actualYear);


    }

    List<String> expectedCategoryList;

    @When("I take all book categories in webpage")
    public void i_take_all_book_categories_in_webpage() {
        expectedCategoryList = BrowserUtil.getAllSelectOptions(bookPage.mainCategoryElement);
        expectedCategoryList.remove(0);
        System.out.println("expectedCategoryList = " + expectedCategoryList);
    }

    List<String> actualCategoryList;

    @When("I execute query to get book categories")
    public void i_execute_query_to_get_book_categories() {
        String query = "select name from book_categories";
        DB_Util.runQuery(query);
        actualCategoryList = DB_Util.getColumnDataAsList(1);
    }

    @Then("verify book categories must match book_categories table from db")
    public void verify_book_categories_must_match_book_categories_table_from_db() {
        Assert.assertEquals(expectedCategoryList, actualCategoryList);
    }
}
