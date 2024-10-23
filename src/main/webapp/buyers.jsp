<%@include file="header.jsp" %>
<c:set var="i" value="1" />
<c:choose>
    <c:when test="${not empty buyers}">
        <div class="container bg0 p-t-75 p-b-45">
            <div class="row">
                <div class="col-lg-10 col-xl-10 m-lr-auto m-b-50">
                    <div class="m-l-25 m-r--38 m-lr-0-xl">
                        <div class="wrap-table-shopping-cart">
                            <table class="table-shopping-cart">
                                <tr class="table_head">
                                    <th class="column-1">#</th>
                                    <th class="column-3">Name</th>
                                    <th class="column-3"></th>
                                    <th class="column-2">Birthday</th>
                                    <th class="column-2">Job</th>
                                    <th class="column-2">Email</th>
                                    <th class="column-2">Credit limit</th>
                                    <th class="column-3">Phone number</th>
                                    <th class="column-3">View</th>
                                </tr>
                                <c:forEach items="${buyers}" var="item">
                                    <tr class="table_row">
                                        <td class="column-1">${i}</td>
                                        <td class="column-3">${item.getAccount().getName()}</td>
                                        <td class="column-3"></td>
                                        <td class="column-2">${item.getAccount().getBirthday()}</td>
                                        <td class="column-2">${item.getAccount().getJob()}</td>
                                        <td class="column-2 price">${item.getAccount().getEmail()}</td>
                                        <td class="column-2">${item.getCreditLimit()}</td>
                                        <td class="column-2">${item.getAccount().getPhoneNumber()}</td>
                                        <td class="column-3"><a href="buyer-profile?p=${item.getId()}">View</a></td>
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
    <c:when test="${empty buyers}">
        <div class="container bg0 p-t-100 p-b-85">
            <div class="row">
                <div class="col-lg-10 col-xl-7 m-lr-auto m-b-50">
                    <div class="m-l-25 m-r--38 m-lr-0-xl">
                        <div class="">
                            <h1>There is no buyers yet</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:when>
</c:choose>

<!-- Load more -->
<c:if test="${not empty buyers}">
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
        document.getElementById('loadMore').addEventListener('click', loadBuyers);

        function loadBuyers() {
            $('#loadMore').prop('disabled', true);

            $.ajax({
                url: "/next-buyers",
                type: "GET",
                data: {
                    page: pageNumber++,
                },
                success: function (response) {
                    console.log(response);

                    let newItems = $(response.buyers.map(function (buyer) {
                        return `
                            <tr class="table_row">
                                        <td class="column-1">`+ counter++ +`</td>
                                        <td class="column-2">` + buyer.name + `</td>
                                        <td class="column-3"></td>
                                        <td class="column-2">` + buyer.birthday + `</td>
                                        <td class="column-2">` + buyer.job + `</td>
                                        <td class="column-2">` + buyer.email + `</td>
                                        <td class="column-2">` + buyer.creditLimit + `</td>
                                        <td class="column-2">` + buyer.phoneNumber + `</td>
                                        <td class="column-3"><a href="buyer-profile?p=` + buyer.id + `">View</a></td>
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