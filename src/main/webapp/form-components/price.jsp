<%--
  Created by IntelliJ IDEA.
  User: Mohamed Saber
  Date: 9/14/2024
  Time: 3:53 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="form-group">
    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30 form-control" type="number" step="0.01" name="price" placeholder="Price" id="priceInput" aria-describedby="priceHelp" value="${product.price}">
    <small id="priceHelp" class="form-text text-muted" style="visibility: hidden;">Price must be a non-negative number.</small>
</div>