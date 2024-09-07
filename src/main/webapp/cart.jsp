<%@include file="header.jsp" %>

<!-- Shoping Cart -->
<c:choose>
	<c:when test="${sessionScope.user.getCart().size() > 0}">
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
									<c:forEach items="${sessionScope.user.getCart()}" var="item">
										<tr class="table_row">
											<td class="column-1">
												<div class="how-itemcart1">
													<img src="${item.key.getImagePath()}" alt="IMG">
												</div>
											</td>
											<td class="column-2">${item.key.getName()}</td>
											<td class="column-3">${item.key.getPrice()}</td>
											<td class="column-4">
												<div class="wrap-num-product flex-w m-l-auto m-r-0">
													<div class="btn-num-product-down cl8 hov-btn3 trans-04 flex-c-m">
														<i class="fs-16 zmdi zmdi-minus"></i>
													</div>
													<input type="hidden" name="id" value="${item.key.getId()}">
													<input class="mtext-104 cl3 txt-center num-product" type="number"
														name="quantity" value="${item.value}">

													<div class="btn-num-product-up cl8 hov-btn3 trans-04 flex-c-m">
														<i class="fs-16 zmdi zmdi-plus"></i>
													</div>
												</div>
											</td>
											<td class="column-5 totals">$ ${item.key.getPrice() * item.value}</td>
										</tr>
									</c:forEach>
								</table>
							</div>

							<div class="flex-w flex-sb-m bor15 p-t-18 p-b-15 p-lr-40 p-lr-15-sm">
								<!-- <div
									class="flex-c-m stext-101 cl2 size-119 bg8 bor13 hov-btn3 p-lr-15 trans-04 pointer m-tb-10">
									Update Cart
								</div> -->
								<button id="updateCart"
									class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04">
									Update Cart
								</button>
							</div>
						</div>
					</div>

					<div class="col-sm-10 col-lg-7 col-xl-5 m-lr-auto m-b-50">
						<div class="bor10 p-lr-40 p-t-30 p-b-40 m-l-63 m-r-40 m-lr-0-xl p-lr-15-sm">
							<!-- <h4 class="mtext-109 cl2 p-b-30">
								Cart Totals
							</h4> -->

							<!-- <div class="flex-w flex-t bor12 p-b-13">
								<div class="size-208">
									<span class="stext-110 cl2">
										Subtotal:
									</span>
								</div>

								<div class="size-209">
									<span class="mtext-110 cl2">
										$79.65
									</span>
								</div>
							</div> -->
							<div class="flex-w flex-t p-t-27 p-b-33">
								<div class="size-208">
									<span class="mtext-101 cl2">
										Total:
									</span>
								</div>

								<div class="size-209 p-t-1">
									<span class="mtext-110 cl2 grand-total">
										$79.65
									</span>
								</div>
							</div>

							<button class="flex-c-m stext-101 cl0 size-116 bg3 bor14 hov-btn3 p-lr-15 trans-04 pointer">
								Proceed to Checkout
							</button>

						</div>
					</div>
				</div>
			</div>
		</form>
	</c:when>
	<c:when test="${sessionScope.user.getCart().size() == 0}">
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
		$('.grand-total').text('$' + grandTotal);
	});
</script>

<%@include file="footer.jsp" %>