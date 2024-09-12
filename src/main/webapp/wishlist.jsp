<%@include file="header.jsp" %>
<style>
    .how-itemcart1::after {
        pointer-events: none;
    }
</style>
<form action="remove-wishlist-item" method="POST" id="removeFromWishlist">
    <input type="hidden" name="productId" id="productId">
</form>
<!-- Shoping Wishlist -->
<c:choose>
    <c:when test="${sessionScope.user.getWishlist().size() > 0}">
        <form class="bg0 p-t-75 p-b-85" action="update-wishlist" method="post">
            <div class="container">
                <div class="row">
                    <div class="col-lg-10 col-xl-7 m-lr-auto m-b-50">
                        <div class="m-l-25 m-r--38 m-lr-0-xl">
                            <div class="wrap-table-shopping-cart">
                                <table class="table-shopping-cart">
                                    <tr class="table_head">
                                        <th class="column-1">Product</th>
                                        <th class="column-2">Name</th>
                                        <th class="column-3">Author</th>
                                        <th class="column-5">Price</th>
                                    </tr>
                                    <c:forEach items="${sessionScope.user.getWishlist()}" var="item">
                                        <tr class="table_row">
                                            <td class="column-1">
                                                <div class="how-itemcart1">
                                                    <img src="${item.getImagePath()}" alt="IMG"
                                                         class="remove-wishlist-item" data-product-id="${item.getId()}">
                                                </div>
                                            </td>
                                            <td class="column-2">${item.getName()}</td>
                                            <td class="column-3">${item.getAuthor()}</td>
                                            <td class="column-5">$ ${item.getPrice()}</td>
                                            <input type="hidden" name="id[]" value="${item.getId()}">
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>

                            <div class="flex-w flex-sb-m bor15 p-t-18 p-b-15 p-lr-40 p-lr-15-sm">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </c:when>
    <c:when test="${sessionScope.user.getWishlist().size() == 0}">
        <div class="container bg0 p-t-100 p-b-85">
            <div class="row">
                <div class="col-lg-10 col-xl-7 m-lr-auto m-b-50">
                    <div class="m-l-25 m-r--38 m-lr-0-xl">
                        <div class="">
                            <h1>Your wishlist is empty</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:when>
</c:choose>

<script>
    window.addEventListener('load', function () {
        $('.remove-wishlist-item').each(function () {
            $(this).click(function (event) {
                console.log('clicked');
                let productId = $(this).data('product-id');
                removeWishlistItem(productId);
            });
        });

        function removeWishlistItem(productId) {
            $.ajax({
                url: 'remove-bucket-item',
                type: 'POST',
                data: {
                    productId: productId,
                    bucket: 'wishlist'
                },
                success: function (response) {
                    response = response.trim();
                    if (response === 'success') {
                        $('.remove-wishlist-item[data-product-id=' + productId + ']').closest(
                            '.table_row')
                            .remove();
                    }
                }
            });
        }
    });
</script>

<%@include file="footer.jsp" %>