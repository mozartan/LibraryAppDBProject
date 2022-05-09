package com.library.step_definitions;

import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class BorrowedBooksStepDefs {
    LoginPage loginPage=new LoginPage();
    String expectedNumber;


    @Given("I am in the homepage of library app")
    public void i_am_in_the_homepage_of_library_app() {

        loginPage.login("librarian");
    }
    @When("I take borrowed books number")
    public void i_take_borrowed_books_number() {
        BrowserUtil.waitFor(3);
         expectedNumber = loginPage.borrowedBooksNumber.getText();
        System.out.println("expectedNumber = " + expectedNumber);

    }
    @Then("borrowed books number information must match with DB")
    public void borrowed_books_number_information_must_match_with_db() {
        String query="select count(*) from book_borrow where is_returned=0";
        DB_Util.runQuery(query);
        String actualNumber = DB_Util.getFirstRowFirstColumn();
        System.out.println("DB_Util.getCellValue(1, 1) = " + DB_Util.getCellValue(1, 1));


        Assert.assertEquals(expectedNumber, actualNumber);

    }
}
