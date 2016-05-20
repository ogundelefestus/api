package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.User;
import play.data.Form;
import play.data.validation.Constraints;
import play.libs.Json;
import play.mvc.*;


public class AuthController extends BaseController {

    public Result signup() {
        Form<User> userForm = Form.form(User.class);

        if (userForm.hasErrors()) {
            return badRequest(userForm.errorsAsJson());
        }

        User newUser = userForm.bindFromRequest().get();
        newUser.createdAt =  new java.sql.Timestamp ( new java.util.Date().getTime());
        newUser.updatedAt = new java.sql.Timestamp ( new java.util.Date().getTime());;
        newUser.save();
        session("email", newUser.email);

        return ok(buildJsonResponse("success", "Users created successfully"));

    }

    public Result login() {
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(loginForm.errorsAsJson());
        }
        Login loggingInUser = loginForm.get();
        User user = User.findByEmailAndPassword(loggingInUser.email, loggingInUser.password);
        if(user == null) {
            return badRequest(buildJsonResponse("error", "Incorrect email or password"));
        } else {
            session().clear();
            session("email", loggingInUser.email);

            ObjectNode wrapper = Json.newObject();
            ObjectNode msg = Json.newObject();
            msg.put("message", "Logged in successfully");
            msg.put("user", loggingInUser.email);
            wrapper.put("success", msg);
            return ok(wrapper);
        }
    }

    public Result logout() {
        session().clear();
        return ok(buildJsonResponse("success", "Logged out successfully"));
    }

    public Result isAuthenticated() {
        if(session().get("email") == null) {
            return unauthorized();
        } else {
            return ok(buildJsonResponse("Users is logged in already", session().get("email")));
        }
    }

    public static class Login {
        @Constraints.Required
        public String password;

        @Constraints.Required
        @Constraints.Email
        public String email;
    }
}