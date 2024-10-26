<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="form-group">
    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30 form-control" type="tel" name="phoneNumber"
           placeholder="Phone Number" id="phoneNumberInput" aria-describedby="phoneNumberHelp" value="${user.phoneNumber()}">
    <small id="phoneNumberHelp" class="form-text text-muted" style="visibility: hidden;">Invalid phone number format or
        phone number already exists.</small>
</div>