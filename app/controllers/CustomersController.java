package controllers;

import models.*;
import play.libs.Json;
import play.mvc.Result;

import java.util.List;

public class CustomersController extends BaseController {


    public Result show(long id) {

        /*String user = "user:" + id;
        String result = jedisPool.getResource().get(user);
        if (result != null) {
            return ok(result);
        }*/
        Customer customer = Customer
                .find
                .fetch("identity")
                .where()
                .eq("id", id)
                .findUnique();
        return ok(Json.toJson(customer));

    }

    public Result all(Long start, Long end) {

        List<Customer> customer = Customer
                .find
                .fetch("identity")
                .fetch("bills.customer")
                .where()
                .between("id", start, end)
                .findList();

        return ok(Json.toJson(customer));
    }

    public Result bills(Long id) {

        Customer bills = Customer
                .find
                .fetch("bills")
                .fetch("identity")
                .where()
                .eq("id", id)
                .findUnique();

        return ok(Json.toJson(bills));
    }

}