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
    <textarea class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30 form-control" name="description"
              placeholder="Description" id="descriptionInput" data-bs-toggle="popover" data-bs-trigger="manual"
              rows="3"></textarea>
    <small id="descriptionHelp" class="form-text text-muted" style="visibility: hidden;">Description should not exceed
        500 characters.</small>
</div>