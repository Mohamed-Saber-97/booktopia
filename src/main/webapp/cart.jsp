<%@include file="header.jsp" %>
<style>
    .how-itemcart1::after {
        pointer-events: none;
    }
</style>
<%@include file="notifications.jsp" %>
<form action="remove-cart-item" method="POST" id="removeFromCart">
    <input type="hidden" name="productId" id="productId">
</form>
<!-- Shoping Cart -->
<c:choose>
    <c:when test="${user.cartSize() > 0}">
        <form class="bg0 p-t-75 p-b-85" action="update-cart" method="post">
            <div class="container">
                <div class="row">
                    <div class="col-lg-10 col-xl-7 m-lr-auto m-b-50">
                        <div class="m-l-25 m-r--38 m-lr-0-xl">
                            <div class="wrap-table-shopping-cart">
                                <table class="table-shopping-cart">
                                    <tr class="table_head">
                                        <th class="column-1">Product</th>
                                        <th class="column-2"></th>
                                        <th class="column-3">Price</th>
                                        <th class="column-4">Quantity</th>
                                        <th class="column-5">Total</th>
                                    </tr>
                                    <c:forEach items="${cart}" var="item">
                                        <tr class="table_row">
                                            <td class="column-1">
                                                <div class="how-itemcart1">
                                                    <img src="/${item.productImagePath()}" alt="IMG"
                                                        class="remove-cart-item" data-product-id="${item.productId()}">
                                                </div>
                                            </td>
                                            <td class="column-2">${item.productName()}</td>
                                            <td class="column-3 price" data-product-id="${item.productId()}">
                                                ${item.productPrice()}</td>
                                            <td class="column-4">
                                                <div class="wrap-num-product flex-w m-l-auto m-r-0">
                                                    <div class="btn-num-product-down cart-decrement-operation cl8 hov-btn3 trans-04 flex-c-m"
                                                        data-product-id="${item.productId()}"
                                                        data-operation="decrement">
                                                        <i class="fs-16 zmdi zmdi-minus"></i>
                                                    </div>
                                                    <input class="mtext-104 cl3 txt-center num-product quantity"
                                                        type="number" data-product-id="${item.productId()}"
                                                        name="quantity[]" value="${item.quantity()}" min="1">

                                                    <div class="btn-num-product-up cart-increment-operation cl8 hov-btn3 trans-04 flex-c-m"
                                                        data-product-id="${item.productId()}"
                                                        data-operation="increment">
                                                        <i class="fs-16 zmdi zmdi-plus"></i>
                                                    </div>
                                                </div>
                                            </td>
                                            <td class="column-5 totals" data-product-id="${item.productId()}">$
                                                ${item.productPrice() * item.quantity()}</td>
                                            <input type="hidden" name="id[]" value="${item.productId()}">
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>

                            <div class="flex-w flex-sb-m bor15 p-t-18 p-b-15 p-lr-40 p-lr-15-sm">
                                <!-- <div
                                    class="flex-c-m stext-101 cl2 size-119 bg8 bor13 hov-btn3 p-lr-15 trans-04 pointer m-tb-10">
                                    Update Cart
                                </div> -->
                                <!-- <button id="updateCart"
                                    class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04">
                                    Update Cart
                                </button> -->
                            </div>
                        </div>
                    </div>

                    <div class="col-sm-10 col-lg-7 col-xl-5 m-lr-auto m-b-50">
                        <div class="bor10 p-lr-40 p-t-30 p-b-40 m-l-63 m-r-40 m-lr-0-xl p-lr-15-sm">
                            <div class="flex-w flex-t p-t-27 p-b-33">
                                <div class="size-208">
                                    <span class="mtext-101 cl2">
                                        Total:
                                    </span>
                                </div>

                                <div class="size-209 p-t-1">
                                    <span class="mtext-110 cl2 grand-total"></span>
                                </div>
                            </div>
                            <%@include file="/checkout.jsp" %>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </c:when>
    <c:when test="${user.cartSize() == 0}">
        <div class="container bg0 p-t-100 p-b-85">
            <div class="row">
                <div class="col-lg-10 col-xl-7 m-lr-auto m-b-50">
                    <div class="m-l-25 m-r--38 m-lr-0-xl">
                        <div class="">
                            <h1>Your cart is empty</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:when>
</c:choose>

<script>
    window.addEventListener('load', function () {
        let grandTotal = 0;
        $('.totals').each(function () {
            grandTotal += parseFloat($(this).text().replace('$', ''));
        });
        $('.grand-total').text('$' + grandTotal.toFixed(2));

        $('.cart-increment-operation').click(function (event) {
            event.preventDefault();
            let productId = $(this).data('product-id');
            let operation = $(this).data('operation');
            let buyerId = ${user.id()};
            $.ajax({
                url: "/api/cart-items/increment/" + buyerId+"/"+productId,
                type: 'POST',
                // data: {
                //     id: productId,
                //     operation: operation
                // },
                success: function (response) {
                    console.log(response);
                    // response = response.trim();
                    $('.quantity[data-product-id=' + productId + ']').val(response);
                    let price = $('.price[data-product-id=' + productId + ']').text()
                        .replace(
                            '$', '').trim();
                    let quantity = $('.quantity[data-product-id=' + productId + ']').val()
                        .trim();
                    $(`.totals[data-product-id=` + productId + `]`).text(
                        '$' + (parseFloat(price) * parseInt(quantity)).toFixed(2)
                    );
                    grandTotal = 0;
                    $('.totals').each(function () {
                        grandTotal += parseFloat($(this).text().replace('$', ''));
                    });
                    $('.grand-total').text('$' + grandTotal.toFixed(2));
                }
            });
        });

        $('.cart-decrement-operation').click(function (event) {
            event.preventDefault();
            let productId = $(this).data('product-id');
            let operation = $(this).data('operation');
            let buyerId = ${user.id()};
            $.ajax({
                url: "/api/cart-items/decrement/" + buyerId+"/"+productId,
                type: 'POST',
                // data: {
                //     id: productId,
                //     operation: operation
                // },
                success: function (response) {
                    // response = response.trim();
                    $('.quantity[data-product-id=' + productId + ']').val(response);
                    let price = $('.price[data-product-id=' + productId + ']').text()
                        .replace(
                            '$', '').trim();
                    let quantity = $('.quantity[data-product-id=' + productId + ']').val()
                        .trim();
                    $(`.totals[data-product-id=` + productId + `]`).text(
                        '$' + (parseFloat(price) * parseInt(quantity)).toFixed(2)
                    );
                    grandTotal = 0;
                    $('.totals').each(function () {
                        grandTotal += parseFloat($(this).text().replace('$', ''));
                    });
                    $('.grand-total').text('$' + grandTotal.toFixed(2));
                }
            });
        });

        // iterate through .remove-cart-item elements and set up click event listener to grab the product id and submit the form
        $('.remove-cart-item').each(function () {
            $(this).click(function (event) {
                console.log('clicked');
                let productId = $(this).data('product-id');
                removeCartItem(productId);
                // $('#productId').val(productId);
                // $('#removeFromCart').submit();
            });
        });

        function removeCartItem(productId) {
            $.ajax({
                url: "/api/cart-items/remove/" + ${user.id()} + "/" + productId,
                type: 'DELETE',
                // data: {
                //     productId: productId,
                //     bucket: 'cart'
                // },
                success: function (response) {
                    // response = response.trim();
                    // if (response === 'success') {
                        $('.remove-cart-item[data-product-id=' + productId + ']').closest(
                                '.table_row')
                            .remove();
                        $('.cart').attr('data-notify', parseInt($('.cart').attr(
                                'data-notify')) -
                            1);
                        grandTotal = 0;
                        $('.totals').each(function () {
                            grandTotal += parseFloat($(this).text().replace('$', ''));
                        });
                        $('.grand-total').text('$' + grandTotal.toFixed(2));
                    // }
                }
            });
        }
    });
</script>

<%@include file="/footer.jsp" %>