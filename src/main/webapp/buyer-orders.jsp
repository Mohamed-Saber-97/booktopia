<%@include file="header.jsp" %>
<c:set var="i" value="1" />
<c:choose>
    <c:when test="${not empty orders}">
        <div class="container bg0 p-t-75 p-b-45">
            <div class="row">
                <div class="col-lg-10 col-xl-10 m-lr-auto m-b-50">
                    <div class="m-l-25 m-r--38 m-lr-0-xl">
                        <div class="wrap-table-shopping-cart">
                            <table class="table-shopping-cart">
                                <tr class="table_head">
                                    <th class="column-1">#</th>
                                    <th class="column-3">Status</th>
                                    <th class="column-3"></th>
                                    <th class="column-2">Number of products</th>
                                    <th class="column-3">View</th>
                                </tr>
                                <c:forEach items="${orders}" var="item">
                                    <tr class="table_row">
                                        <td class="column-1">${i}</td>
                                        <td class="column-3">${item.getStatus()}</td>
                                        <td class="column-3"></td>
                                        <td class="column-2">${item.getOrderProducts().size()}</td>
                                        <td class="column-3">
                                            <form action="buyer-order-products" method="get" style="display:inline;">
                                                <input type="hidden" name="p" value="${tempBuyer.getId()}">
                                                <input type="hidden" name="order" value="${item.getId()}">
                                                <button type="submit" class="btn btn-link">View</button>
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
    <c:when test="${empty orders}">
        <div class="container bg0 p-t-100 p-b-85">
            <div class="row">
                <div class="col-lg-10 col-xl-7 m-lr-auto m-b-50">
                    <div class="m-l-25 m-r--38 m-lr-0-xl">
                        <div class="">
                            <h1>This user hasn't orderd yet</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:when>
</c:choose>

<!-- Load more -->
<c:if test="${not empty orders}">
    <div class="flex-c-m flex-w w-full p-t-45 p-b-45">
        <button id="loadMore" class="flex-c-m stext-101 cl5 size-103 bg2 bor1 hov-btn1 p-lr-15 trans-04">
            Load More
        </button>
    </div>
</c:if>

<script>
    window.addEventListener('load', function () {
        let pageNumber = 1;
        let counter = ${i};
        let table = $('.table-shopping-cart');
        document.getElementById('loadMore').addEventListener('click', loadOrders);

        function loadOrders() {
            $('#loadMore').prop('disabled', true);

            $.ajax({
                url: "/next-orders",
                type: "GET",
                data: {
                    page: pageNumber++,
                },
                success: function (response) {
                    console.log(response);

                    let newItems = $(response.orders.map(function (order) {
                        return `
                            <tr class="table_row">
                                        <td class="column-1">`+ counter++ +`</td>
                                        <td class="column-2">` + order.name + `</td>
                                        <td class="column-3"></td>
                                        <td class="column-2">` + order.birthday + `</td>
                                        <td class="column-2">` + order.job + `</td>
                                        <td class="column-2">` + order.email + `</td>
                                        <td class="column-2">` + order.creditLimit + `</td>
                                        <td class="column-2">` + order.phoneNumber + `</td>
                                        <td class="column-3"><a href="buyer-profile?p=` + order.id + `">View</a></td>
                            </tr>
                        `;
                    }).join(''));


                    table.append(newItems);
                },
                error: function (xhr, status, error) {
                    console.error("An error occurred: " + error);
                },
                complete: function () {
                    $('#loadMore').prop('disabled', false);
                }
            });
        }
    });
</script>

<%@include file="footer.jsp" %>