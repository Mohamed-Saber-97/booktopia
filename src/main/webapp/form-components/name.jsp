<%--
  Created by IntelliJ IDEA.
  User: Mohamed Saber
  Date: 9/14/2024
  Time: 3:53 AM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<div class="form-group">
    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30 form-control" type="text" name="name" placeholder="Name" id="nameInput" data-bs-toggle="popover" data-bs-trigger="manual" value="${user.getAccount().getName()}">
    <small id="nameHelp" class="form-text text-muted" style="visibility: hidden;">Name should not exceed 100 characters.</small>
</div>