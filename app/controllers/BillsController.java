package controllers;

import models.Bill;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;


public class BillsController extends Controller {

    public Result show(long id) {
        Bill bill = Bill
                .find
                .fetch("customer")
                .fetch("customer.identity")
                .where()
                .eq("id", id)
                .findUnique();

        return ok(Json.toJson(bill));
    }
}