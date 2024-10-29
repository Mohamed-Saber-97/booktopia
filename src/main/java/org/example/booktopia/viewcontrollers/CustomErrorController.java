package org.example.booktopia.viewcontrollers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus httpStatus = HttpStatus.valueOf(Integer.parseInt(status.toString()));
        String errorMessage = switch (httpStatus) {
            case NOT_FOUND -> "The page you are looking for could not be found";
            case BAD_GATEWAY -> "Bad Gateway";
            case SERVICE_UNAVAILABLE -> "Service Unavailable";
            case GATEWAY_TIMEOUT -> "Gateway Timeout";
            case HTTP_VERSION_NOT_SUPPORTED -> "HTTP Version Not Supported";
            case UNAUTHORIZED -> "You do not have the necessary credentials to access this page";
            case FORBIDDEN -> "Access Denied. You do not have the permission to access this page on this server";
            case INTERNAL_SERVER_ERROR -> "Something went wrong on our end. Please try again later";
            default -> "An unexpected error occurred. Please try again later";
        };
        String errorReason = switch (httpStatus) {
            case FORBIDDEN ->
                    "execute access forbidden, read access forbidden, write access forbidden, ssl required, ssl 128 " +
                            "required, ip address rejected, client certificate required, site access denied, too many" +
                            " users, invalid configuration, password change, mapper denied access, client certificate" +
                            " revoked, directory listing denied, client access licenses exceeded, client certificate " +
                            "is untrusted or invalid, client certificate has expired or is not yet valid, passport " +
                            "logon failed, source access denied, infinite depth is denied, too many requests from the" +
                            " same client ip";
            case UNAUTHORIZED -> "authentication required, invalid credentials, session expired";
            case NOT_FOUND -> "resource not found, incorrect url, resource deleted or moved";
            case INTERNAL_SERVER_ERROR -> "server error, configuration issue, database error, application error";
            default -> "An unexpected error occurred. Please try again later";
        };
        if (status != null) {
            model.addAttribute("statusCode", httpStatus.value());
            model.addAttribute("reason", httpStatus.getReasonPhrase());
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("errorReason", errorReason);
        }
        return "error";
    }

    public String getErrorPath() {
        return "/error";
    }
}
