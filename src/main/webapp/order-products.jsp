<%@include file="header.jsp" %>

<c:choose>
    <c:when test="${not empty products}">
        <div class="container bg0 p-t-75 p-b-45">
            <div class="row">
                <div class="col-lg-10 col-xl-9 m-lr-auto m-b-50">
                    <div class="m-l-25 m-r--38 m-lr-0-xl">
                        <div class="wrap-table-shopping-cart">
                            <table class="table-shopping-cart">
                                <tr class="table_head">
                                    <th class="column-1">Book cover</th>
                                    <th class="column-2">Title</th>
                                    <th class="column-2">Author</th>
                                    <th class="column-2"></th>
                                    <th class="column-2">Price</th>
                                    <th class="column-2">Quantity</th>
                                    <th class="column-2">Total</th>
                                </tr>
                                <c:forEach items="${products}" var="item">
                                    <tr class="table_row">
                                        <td class="column-1">
                                            <div class="how-itemcart1">
                                                <img src="${item.getProduct().getImagePath()}" alt="IMG">
                                            </div>
                                        </td>
                                        <td class="column-2">${item.getProduct().getName()}</td>
                                        <td class="column-2">${item.getProduct().getAuthor()}</td>
                                        <td class="column-2"></td>
                                        <td class="column-2 price">${item.getPrice()}</td>
                                        <td class="column-2">${item.getQuantity()}</td>
                                        <td class="column-2">${item.getTotalPrice()}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:when>
    <c:when test="${empty products}">
        <div class="container bg0 p-t-100 p-b-85">
            <div class="row">
                <div class="col-lg-10 col-xl-7 m-lr-auto m-b-50">
                    <div class="m-l-25 m-r--38 m-lr-0-xl">
                        <div class="">
                            <h1>There is no available products in this order</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:when>
</c:choose>

<%@include file="footer.jsp" %>