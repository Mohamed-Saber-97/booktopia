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
                                    <th class="column-3">View</th>
                                    <th class="column-3">Delete</th>
                                </tr>
                                <c:forEach items="${products}" var="item">
                                    <tr class="table_row">
                                        <td class="column-1">
                                            <div class="how-itemcart1">
                                                <img src="${item.getImagePath()}" alt="IMG">
                                            </div>
                                        </td>
                                        <td class="column-2">${item.getName()}</td>
                                        <td class="column-2">${item.getAuthor()}</td>
                                        <td class="column-2"></td>
                                        <td class="column-2 price">
                                            ${item.getPrice()}</td>
                                        <td class="column-2">${item.getQuantity()}</td>
                                        <td class="column-3">
                                            <a href="edit-book?p=${item.getId()}">View</a>
                                        </td>
                                        <td class="column-3">
                                            <form action="delete-book" method="POST" style="display:inline;">
                                                <input type="hidden" name="id" value="${item.getId()}">
                                                <button type="submit" class="btn btn-link">Delete</button>
                                            </form>
                                        </td>
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
                            <h1>There is no available books</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:when>
</c:choose>

<!-- Load more -->
<c:if test="${not empty products}">
    <div class="flex-c-m flex-w w-full p-t-45 p-b-45">
        <button id="loadMore" class="flex-c-m stext-101 cl5 size-103 bg2 bor1 hov-btn1 p-lr-15 trans-04">
            Load More
        </button>
    </div>
</c:if>

<script>
    window.addEventListener('load', function () {
        let pageNumber = 1;
        let table = $('.table-shopping-cart');
        document.getElementById('loadMore').addEventListener('click', loadProducts);

        function loadProducts() {
            $('#loadMore').prop('disabled', true);

            $.ajax({
                url: "/next-products",
                type: "GET",
                data: {
                    page: pageNumber++,
                },
                success: function (response) {

                    let newItems = $(response.products.map(function (product) {
                        return `
                            <tr class="table_row">
                                <td class="column-1">
                                    <div class="how-itemcart1">
                                        <img src="` + product.imagePath + `" alt="IMG">
                                    </div>
                                </td>
                                <td class="column-2">` + product.name + `</td>
                                <td class="column-2">` + product.author + `</td>
                                <td class="column-2"></td>
                                <td class="column-2 price">
                                    ` + product.price + `</td>
                                <td class="column-2">` + product.quantity + `</td>
                                <td class="column-3">
                                    <a href="edit-book?p=` + product.id + `">View</a>
                                </td>
                                <td class="column-3">
                                    <form action="delete-book" method="POST" style="display:inline;">
                                        <input type="hidden" name="id" value="` + product.id + `">
                                        <button type="submit" class="btn btn-link">Delete</button>
                                    </form>
                                </td>
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