<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="form-group">
    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30 form-control" type="text" name="city"
           placeholder="City" id="cityInput" aria-describedby="cityHelp" data-bs-toggle="popover" data-bs-trigger="manual" value="${user.getAccount().getAddress().getCity()}">
    <small id="cityHelp" class="form-text text-muted" style="visibility: hidden;">City should not exceed 100 characters.</small>
</div>