<%--
  Created by IntelliJ IDEA.
  User: Mohamed Saber
  Date: 9/14/2024
  Time: 5:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<style>
    .interest-bubble {
        display: inline-block;
        margin: 5px;
        position: relative;
    }

    .interest-bubble input[type="checkbox"] {
        display: none;
    }

    .interest-bubble label {
        display: inline-flex;
        align-items: center;
        justify-content: center;
        padding: 10px 20px;
        background-color: #f8f9fa;
        border: 2px solid #dee2e6;
        border-radius: 25px;
        cursor: pointer;
        transition: all 0.3s ease;
    }

    .interest-bubble input[type="checkbox"]:checked + label {
        background-color: #28a745;
        border-color: #28a745;
        color: white;
    }
</style>
<c:if test="${sessionScope.admin == null}">
<div class="form-group">
    <label><b>Interests</b></label>
    <div class="d-flex flex-wrap">
        <c:forEach items="${categories}" var="category">
            <div class="interest-bubble">
                <input type="checkbox"
                       id="category${category.getId()}"
                       name="categories"
                       value="${category.getId()}"
                       <c:if test="${user.getInterests().contains(category)}">checked</c:if>>
                <label for="category${category.getId()}">${category.getName()}</label>
            </div>
        </c:forEach>
    </div>
    <small id="categoriesHelp" class="form-text text-muted" style="visibility: hidden;">
        Please select at least one category.
    </small>
</div>
</c:if>