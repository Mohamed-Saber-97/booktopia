<%@include file="header.jsp" %>

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
							<div class="item-slick3" data-thumb="images/product-detail-01.jpg">
								<div class="wrap-pic-w pos-relative">
									<img src="images/product-detail-01.jpg" alt="IMG-PRODUCT">

									<a class="flex-c-m size-108 how-pos1 bor0 fs-16 cl10 bg0 hov-btn3 trans-04"
										href="images/product-detail-01.jpg">
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
								<button id="addToCart" data-product-id="${product.getId()}"
									class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04">
									<c:choose>
										<c:when test="${sessionScope.buyer != null}">
											<c:if test="${sessionScope.user.cart.contains(product)}">Remove from cart
											</c:if>
											<c:if test="${!sessionScope.user.cart.contains(product)}">Add to cart
											</c:if>
										</c:when>
										<c:otherwise>Login to add to cart
										</c:otherwise>
									</c:choose>

								</button>
							</div>
						</div>
					</div>

					<c:if test="${sessionScope.buyer != null}">
						<div class="flex-w flex-m p-l-100 p-t-40 respon7">
							<div class="flex-m p-r-10 m-r-11">
								<a href="#"
									class="fs-14 cl3 hov-cl1 trans-04 lh-10 p-lr-5 p-tb-2 js-addwish-detail tooltip100"
									data-tooltip="Add to Wishlist">
									<i class="zmdi zmdi-favorite"></i>
								</a>
							</div>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</section>

<script>
	window.addEventListener('load', function () {
		// document.getElementById('addToCart').addEventListener('click', function () {
		// 	const productId = this.getAttribute('data-product-id');
		// 	const numProduct = document.querySelector('.num-product').value;
		// 	fetch(`cart?productId=${productId}&numProduct=${numProduct}`, {
		// 		method: 'POST'
		// 	}).then(response => {
		// 		if (response.ok) {
		// 			alert('Product added to cart');
		// 		} else {
		// 			alert('Error adding product to cart');
		// 		}
		// 	});
		// });
		$("#addToCart").click(function (event) {
			event.preventDefault();
			const productId = $(this).attr('data-product-id');
			const numProduct = $('.num-product').val();
			$.post('addToCart', {
				productId: productId,
				quantity: numProduct
			}, function (data, status) {
				if (status === 'success') {
					if (data === 'added') {
						$('#cart').attr('data-notify', parseInt($('#cart').attr('data-notify')) +
							1);
						$('#addToCart').text('Remove from cart');
					} else {
						$('#cart').attr('data-notify', parseInt($('#cart').attr('data-notify')) -
							1);
						$('#addToCart').text('Add to cart');
					}
				} else {
					alert('Error adding product to cart');
				}
			});
		});
	});
</script>

<%@include file="footer.jsp" %>