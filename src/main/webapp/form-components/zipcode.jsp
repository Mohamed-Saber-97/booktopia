<%--
  Created by IntelliJ IDEA.
  User: Mohamed Saber
  Date: 9/14/2024
  Time: 6:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="form-group">
    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30 form-control" type="text" name="zipCode"
           placeholder="ZIP Code" id="zipCodeInput" data-bs-toggle="popover" data-bs-trigger="manual" value="${user.getAccount().getAddress().getZipcode()}">
    <small id="zipCodeHelp" class="form-text text-muted" style="visibility: hidden;">ZIP Code should not exceed 15 characters.</small>
</div>