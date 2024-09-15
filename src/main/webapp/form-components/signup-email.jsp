<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="form-group">
    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30 form-control" type="email" name="email"
           placeholder="Email Address" id="emailInput" aria-describedby="emailHelp"
           value="${user.getAccount().getEmail()}">
    <small id="emailHelp" class="form-text text-muted" style="visibility: hidden;">Invalid email or email already
        exists.</small>
</div>

