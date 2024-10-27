<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%--
  Created by IntelliJ IDEA.
  User: Mohamed Saber
  Date: 9/11/2024
  Time: 7:26 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Check if the cart is empty --%>
<c:choose>
    <c:when test="${user.cartSize() == 0}">
        <!-- Element with dimmed style when the cart is empty -->
        <div style="
            opacity: 0.5;
            background-color: #f0f0f0;
            color: #999;
            padding: 10px;
            border-radius: 5px;
            text-align: center;
            font-weight: bold;
        ">
            Your cart is empty.
        </div>
    </c:when>
    <c:otherwise>
        <!-- Element with action button when there are items in the cart -->
        <div style="
            background-color: #ffffff;
            color: #000000;
            padding: 10px;
            border-radius: 5px;
            text-align: center;
            font-weight: bold;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        ">
            You have items in your cart.
            <a href="checkout.jsp" style="
                display: inline-block;
                margin-top: 10px;
                text-decoration: none;
            ">
                <button class="flex-c-m stext-101 cl0 size-116 bg3 bor14 hov-btn3 p-lr-15 trans-04 pointer">
                    Proceed to Checkout
                </button>
            </a>
        </div>
    </c:otherwise>
</c:choose>
