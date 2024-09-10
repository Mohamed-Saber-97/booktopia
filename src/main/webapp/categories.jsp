<%@include file="header.jsp" %>

<c:choose>
    <c:when test="${not empty categories}">
        <div class="container bg0 p-t-75 p-b-45">
            <div class="row">
                <div class="col-lg-10 col-xl-9 m-lr-auto m-b-50">
                    <div class="m-l-25 m-r--38 m-lr-0-xl">
                        <div class="wrap-table-shopping-cart">
                            <table class="table-shopping-cart">
                                <tr class="table_head">
                                    <th class="column-1">#</th>
                                    <th class="column-2">Category name</th>
                                </tr>
                                <c:forEach items="${categories}" var="item">
                                    <tr class="table_row">
                                        <td class="column-1">${i}</td>
                                        <td class="column-2">${item.getName()}</td>
                                        <td class="column-3">
                                            <a href="edit-category?p=${item.getId()}">View</a>
                                        </td>
                                        <td class="column-3">
                                            <form action="delete-category" method="POST" style="display:inline;">
                                                <input type="hidden" name="id" value="${item.getId()}">
                                                <button type="submit" class="btn btn-link">Delete</button>
                                            </form>
                                        </td>
                                    </tr>
                                    <c:set var="i" value="${i+1}" />
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:when>
    <c:when test="${empty categories}">
        <div class="container bg0 p-t-100 p-b-85">
            <div class="row">
                <div class="col-lg-10 col-xl-7 m-lr-auto m-b-50">
                    <div class="m-l-25 m-r--38 m-lr-0-xl">
                        <div class="">
                            <h1>There is no available categories</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:when>
</c:choose>

<%@include file="footer.jsp" %>