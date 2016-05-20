package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.mvc.*;
import play.libs.Json;

public class BaseController extends Controller {


    public BaseController () {}

    protected static ObjectNode buildJsonResponse(String type, String message) {
        ObjectNode wrapper = Json.newObject();
        ObjectNode msg = Json.newObject();
        msg.put("message", message);
        wrapper.put(type, msg);
        return wrapper;
    }
}