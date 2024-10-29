<%@include file="header.jsp" %>
<style>
    .how-itemcart1::after {
        pointer-events: none;
    }
</style>
<form action="remove-wishlist-item" method="POST" id="removeFromWishlist">
    <input type="hidden" name="productId" id="productId">
</form>
<%@include file="notifications.jsp" %>
<!-- Shoping Wishlist -->
<c:choose>
    <c:when test="${user.wishlistSize()> 0}">
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
                                    <c:forEach items="${wishlist}" var="item">
                                        <tr class="table_row">
                                            <td class="column-1">
                                                <div class="how-itemcart1">
                                                    <img src="/${item.productImagePath()}" alt="IMG"
                                                         class="remove-wishlist-item"
                                                         data-product-id="${item.productId()}">
                                                </div>
                                            </td>
                                            <td class="column-2">${item.productName()}</td>
                                            <td class="column-3">${item.productAuthor()}</td>
                                            <td class="column-5">$ ${item.productPrice()}</td>
                                            <input type="hidden" name="id[]" value="${item.productId()}">
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </c:when>
    <c:when test="${user.wishlistSize() == 0}">
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
                url: "/api/buyer-wishlist/remove/" + ${user.id()}+"/" + productId,
                type: 'POST',
                // data: {
                //     productId: productId,
                //     bucket: 'wishlist'
                // },
                success: function (response) {
                    console.log(response);
                    // response = response.trim();
                    if (response === 'Product removed from wishlist') {
                        $('.remove-wishlist-item[data-product-id=' + productId + ']').closest(
                            '.table_row')
                            .remove();
                        $('.wishlist').attr('data-notify', parseInt($('.wishlist').attr(
                                'data-notify')) -
                            1);
                    }
                }
            });
        }
    });
</script>

<%@include file="/footer.jsp" %>
