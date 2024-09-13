<%@include file="header.jsp" %>

<div class="alert alert-danger text-center m-t-20 mx-auto pos-fixed"
	style="width: fit-content; display: none; left: 50%; transform: translateX(-50%); z-index: 9999;">
</div>

<!-- Product Detail -->
<section class="sec-product-detail bg0 p-t-65 p-b-60">
	<div class="container">
		<div class="row">
			<div class="col-md-6 col-lg-7 p-b-30">
				<div class="p-l-25 p-r-30 p-lr-0-lg">
					<div class="wrap-slick3 flex-sb flex-w">
						<div class="wrap-slick3-dots"></div>
						<div class="wrap-slick3-arrows flex-sb-m flex-w"></div>

						<div class="slick3 gallery-lb">
							<div class="item-slick3" data-thumb="${product.getImagePath()}">
								<div class="wrap-pic-w pos-relative">
									<img src="${product.getImagePath()}" alt="IMG-PRODUCT">

									<a class="flex-c-m size-108 how-pos1 bor0 fs-16 cl10 bg0 hov-btn3 trans-04"
										href="${product.getImagePath()}">
										<i class="fa fa-expand"></i>
									</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="col-md-6 col-lg-5 p-b-30">
				<div class="p-r-50 p-t-5 p-lr-0-lg">
					<h4 class="mtext-105 cl2 js-name-detail p-b-14">
						${product.getName()}
					</h4>

					<span class="mtext-106 cl2">
						${product.getPrice()}
					</span>

					<p class="stext-102 cl3 p-t-23">
						${product.getDescription()}
					</p>

					<p class="stext-102 cl3 p-t-23">
						Autor: ${product.getAuthor()}
					</p>

					<p class="stext-102 cl3 p-t-23">
						Release date: ${product.getReleaseDate()}
					</p>

					<p class="stext-102 cl3 p-t-23">
						ISBN: ${product.getIsbn()}
					</p>

					<p class="stext-102 cl3 p-t-23">
						Stock: ${product.getQuantity()}
					</p>

					<div class="p-t-33">
						<div class="flex-w flex-r-m p-b-10">
							<c:if test="${sessionScope.buyer != null}">
							<div class="size-204 flex-w flex-m respon6-next">
								<div class="wrap-num-product flex-w m-r-20 m-tb-10">
									<div class="btn-num-product-down cl8 hov-btn3 trans-04 flex-c-m">
										<i class="fs-16 zmdi zmdi-minus"></i>
									</div>

									<input class="mtext-104 cl3 txt-center num-product" type="number" name="num-product"
										value="1">

									<div class="btn-num-product-up cl8 hov-btn3 trans-04 flex-c-m">
										<i class="fs-16 zmdi zmdi-plus"></i>
									</div>
								</div>
								<!-- js-addcart-detail -->
<%--								<c:if test="${sessionScope.buyer != null}">--%>
									<button id="addToCart" data-product-id="${product.getId()}"
										class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04">
										<c:if test="${!sessionScope.user.getCart().containsKey(product)}">
											Add to cart
										</c:if>
										<c:if test="${sessionScope.user.getCart().containsKey(product)}">
											Remove from cart
										</c:if>
									</button>
								</c:if>
								<c:if test="${sessionScope.buyer != null}">
									<button id="addToWishlist" data-product-id="${product.getId()}"
										class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04 m-t-10">
										<c:if test="${!sessionScope.user.getWishlist().contains(product)}">
											Add to wishlist
										</c:if>
										<c:if test="${sessionScope.user.getWishlist().contains(product)}">
											Remove from wishlist
										</c:if>
									</button>
								</c:if>
								<c:if test="${sessionScope.user == null}">
									<button form="loginForm"
										class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04">
										Login to add to cart
									</button>
								</c:if>
							</div>
						</div>
					</div>
					<form action="login" method="GET" id="loginForm"></form>
					<!-- <c:if test="${sessionScope.buyer != null}">
						<div class="flex-w flex-m p-l-100 p-t-40 respon7">
							<div class="flex-m p-r-10 m-r-11">
								<a href="#"
									class="fs-14 cl3 hov-cl1 trans-04 lh-10 p-lr-5 p-tb-2 js-addwish-detail tooltip100"
									data-tooltip="Add to Wishlist">
									<i class="zmdi zmdi-favorite"></i>
								</a>
							</div>
						</div>
					</c:if> -->
				</div>
			</div>
		</div>
	</div>
</section>

<script>
	window.addEventListener('load', function () {
		$("#addToCart").click(function (event) {
			event.preventDefault();
			const productId = $(this).attr('data-product-id');
			const numProduct = $('.num-product').val();

			$.ajax({
				url: "/addToCart",
				type: "POST",
				data: {
					productId: productId,
					quantity: numProduct
				},
				success: function (response) {
					if (response.trim() == "Product added to cart") {
						$('.cart').attr('data-notify', parseInt($('.cart').attr(
								'data-notify')) +
							1);
						$('#addToCart').text('Remove from cart');
						$('.alert').text(response)
							.removeClass('alert-danger')
							.addClass('alert-info').show();

						setTimeout(function () {
							$('.alert').text('').removeClass('alert-info').addClass(
									'alert-danger')
								.hide();
						}, 7000);
					} else if (response.trim() === 'Product removed from cart') {
						$('.cart').attr('data-notify', parseInt($('.cart').attr(
								'data-notify')) -
							1);
						$('#addToCart').text('Add to cart');
						$('.alert').text(response).removeClass('alert-danger').addClass(
							'alert-info').show();
						setTimeout(function () {
							$('.alert').text('').removeClass('alert-info').addClass(
									'alert-danger')
								.hide();
						}, 7000);
					} else if (response.trim() === 'Invalid input') {
						$('.alert').text('Invalid input').show();
						setTimeout(function () {
							$('.alert').text('').hide();
						}, 7000);
					} else if (response.trim() === 'Product not found') {
						$('.alert').text('Product not found').show();
						setTimeout(function () {
							$('.alert').text('').hide();
						}, 7000);
					} else if (response.trim() === 'Invalid quantity') {
						$('.alert').text('Invalid quantity').show();
						setTimeout(function () {
							$('.alert').text('').hide();
						}, 7000);
					}
				},
				error: function (xhr, status, error) {
					console.error("Error fetching next products." + error);
				},
				complete: function () {
					console.log("Request completed.");
				}
			});
		});

		$("#addToWishlist").click(function (event) {
			event.preventDefault();
			const productId = $(this).attr('data-product-id');
			$.ajax({
				url: "/addToWishlist",
				type: "POST",
				data: {
					productId: productId
				},
				success: function (response) {
					if (response.trim() == "Product added to wishlist") {
						$('.wishlist').attr('data-notify', parseInt($('.wishlist').attr(
								'data-notify')) +
							1);
						$('#addToWishlist').text('Remove from wishlist');
						$('.alert').text(response)
							.removeClass('alert-danger')
							.addClass('alert-info').show();

						setTimeout(function () {
							$('.alert').text('').removeClass('alert-info').addClass(
									'alert-danger')
								.hide();
						}, 7000);
					} else if (response.trim() === 'Product removed from wishlist') {
						$('.wishlist').attr('data-notify', parseInt($('.wishlist').attr(
								'data-notify')) -
							1);
						$('#addToWishlist').text('Add to wishlist');
						$('.alert').text(response).removeClass('alert-danger').addClass(
							'alert-info').show();
						setTimeout(function () {
							$('.alert').text('').removeClass('alert-info').addClass(
									'alert-danger')
								.hide();
						}, 7000);
					} else if (response.trim() === 'Invalid input') {
						$('.alert').text('Invalid input').show();
						setTimeout(function () {
							$('.alert').text('').hide();
						}, 7000);
					} else if (response.trim() === 'Product not found') {
						$('.alert').text('Product not found').show();
						setTimeout(function () {
							$('.alert').text('').hide();
						}, 7000);
					}
				},
				error: function (xhr, status, error) {
					console.error("Error fetching next products." + error);
				},
				complete: function () {
					console.log("Request completed.");
				}
			});
		});
	});
</script>

<%@include file="footer.jsp" %>