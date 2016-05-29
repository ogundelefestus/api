package controllers;

import models.*;
import play.mvc.Result;
import redis.clients.jedis.Jedis;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static services.JsonConcat.concat;

public class CustomersController extends BaseController {

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Calendar c = Calendar.getInstance();

    public Result show(long id) {
        String result;
        String uri = request().uri();
        Jedis jedis = jedisPool.getResource();
        try {
            result = jedis.get(uri);
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            }
        }

        if (result != null) {
            return ok(result);
        }
        Customer customer = Customer.find.byId(id);
        if(customer == null) {
            return notFound("Customer not found");
        }
        Identity identity = Identity.find.byId(id);
        return ok(concat(customer, identity));
    }

    public Result debts(Long cid, int i) {

        String result;
        String uri = request().uri();
        Jedis jedis = jedisPool.getResource();
        try {
            result = jedis.get(uri);
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            }
        }
        if (result != null) {
            return ok(result);
        }

        Customer customer = Customer.find.byId(cid);
        if(customer == null) {
            return notFound("Customer not found");
        }
        Identity identity = Identity.find.byId(cid);

        if (i == 0) {
            List<Bill> bills = Bill.find
                    .where()
                    .eq("customer_id", cid)
                    .eq("paid", false)
                    .findList();

            return ok(concat(customer, identity, bills));
        } else {
            List<Bill> getLastBill = Bill
                    .find
                    .where()
                    .eq("customer_id", cid)
                    .orderBy("id desc")
                    .findList();

            Date dateToday = getLastBill.get(0).billed_date;
            Date pastDate = null;
            c.setTime(dateToday);

            if (i == 1) {
                c.add(Calendar.MONTH, -0);
            } else if (i == 3) {
                c.add(Calendar.MONTH, -2);
            } else if (i == 6) {
                c.add(Calendar.MONTH, -5);
            } else if (i == 12) {
                c.add(Calendar.MONTH, -11);
            } else {
                return badRequest(buildJsonResponse("fail", "Invalid month definition"));
            }
            pastDate = c.getTime();
            String today = dateFormat.format(dateToday);
            String past = dateFormat.format(pastDate);


            List<Bill> bills = Bill
                    .find
                    .where()
                    .eq("customer_id", cid)
                    .eq("paid", false)
                    .between("billed_date", past, today)
                    .findList();

            return ok(concat(customer, identity, bills));
        }
    }

    public Result payments(Long cid, int i) {

       String result;
        String uri = request().uri();
        Jedis jedis = jedisPool.getResource();
        try {
            result = jedis.get(uri);
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            }
        }
        if (result != null) {
            return ok(result);
        }

        Customer customer = Customer.find.byId(cid);
        if(customer == null) {
            return notFound("Customer not found");
        }
        Identity identity = Identity.find.byId(cid);

        if (i == 0) {

            List<Bill> bills = Bill.find
                    .where()
                    .eq("customer_id", cid)
                    .eq("paid", true)
                    .findList();

            return ok(concat(customer, identity, bills));
        } else {


            List<Bill> getLastBill = Bill
                    .find
                    .where()
                    .eq("customer_id", cid)
                    .orderBy("id desc")
                    .findList();

            Date dateToday = getLastBill.get(0).billed_date;
            Date pastDate = null;
            c.setTime(dateToday);

            if (i == 1) {
                c.add(Calendar.MONTH, -0);
            } else if (i == 3) {
                c.add(Calendar.MONTH, -2);
            } else if (i == 6) {
                c.add(Calendar.MONTH, -5);
            } else if (i == 12) {
                c.add(Calendar.MONTH, -11);
            } else {
                return badRequest(buildJsonResponse("fail", "Invalid month definition"));
            }
            pastDate = c.getTime();
            String today = dateFormat.format(dateToday);
            String past = dateFormat.format(pastDate);

            List<Bill> bills = Bill
                    .find
                    .where()
                    .eq("customer_id", cid)
                    .eq("paid", true)
                    .between("billed_date", past, today)
                    .findList();

            return ok(concat(customer, identity, bills));
        }
    }
}