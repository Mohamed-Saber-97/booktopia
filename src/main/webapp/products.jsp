<%@include file="header.jsp" %>


<!-- Product -->
<div class="bg0 m-t-23 p-b-140">
	<div class="container">
		<div class="flex-w flex-sb-m p-b-52">
			<div class="flex-w flex-c-m m-tb-10">
				<div class="flex-c-m stext-106 cl6 size-104 bor4 pointer hov-btn3 trans-04 m-r-8 m-tb-4 js-show-filter">
					<i class="icon-filter cl2 m-r-6 fs-15 trans-04 zmdi zmdi-filter-list"></i>
					<i class="icon-close-filter cl2 m-r-6 fs-15 trans-04 zmdi zmdi-close dis-none"></i>
					Filter
				</div>

				<div class="flex-c-m stext-106 cl6 size-105 bor4 pointer hov-btn3 trans-04 m-tb-4 js-show-search">
					<i class="icon-search cl2 m-r-6 fs-15 trans-04 zmdi zmdi-search"></i>
					<i class="icon-close-search cl2 m-r-6 fs-15 trans-04 zmdi zmdi-close dis-none"></i>
					Search
				</div>
			</div>


			<form action="products" method="get" id="search-form">
			</form>


			<!-- Search product -->
			<div class="dis-none panel-search w-full p-t-10 p-b-15">
				<div class="bor8 dis-flex p-l-15">
					<button class="size-113 flex-c-m fs-16 cl2 hov-cl1 trans-04" form="search-form">
						<i class="zmdi zmdi-search"></i>
					</button>
					<input class="mtext-107 cl2 size-114 plh2 p-r-15" form="search-form" type="text" name="name"
						value="${param.name}" id="name" placeholder="Search">
				</div>
			</div>

			<!-- Filter -->
			<div class="dis-none panel-filter w-full p-t-10">
				<div class="wrap-filter flex-w bg6 w-full p-lr-40 p-t-27 p-lr-15-sm">
					<!-- <div class="filter-col1 p-r-15 p-b-27">
						<div class="mtext-102 cl2 p-b-15">
							Sort By
						</div>

						<ul>
							<li class="p-b-6">
								<a href="#" class="filter-link stext-106 trans-04">
									Default
								</a>
							</li>

							<li class="p-b-6">
								<a href="#" class="filter-link stext-106 trans-04">
									Popularity
								</a>
							</li>

							<li class="p-b-6">
								<a href="#" class="filter-link stext-106 trans-04">
									Average rating
								</a>
							</li>

							<li class="p-b-6">
								<a href="#" class="filter-link stext-106 trans-04 filter-link-active">
									Newness
								</a>
							</li>

							<li class="p-b-6">
								<a href="#" class="filter-link stext-106 trans-04">
									Price: Low to High
								</a>
							</li>

							<li class="p-b-6">
								<a href="#" class="filter-link stext-106 trans-04">
									Price: High to Low
								</a>
							</li>
						</ul>
					</div> -->

					<div class="filter-col2 p-r-15 p-b-27">
						<div class="mtext-102 cl2 p-b-15">
							Price range
						</div>
						<div class="flex-w p-t-4 m-r--5">
							<div class="bor8 m-b-20">
								<input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" form="search-form"
									value="${param.minPrice}" id="minPrice" type="number" min="0" name="minPrice"
									placeholder="minimum price">
							</div>
							<div class="bor8 m-b-20">
								<input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" form="search-form"
									value="${param.maxPrice}" id="maxPrice" type="number" min="0" name="maxPrice"
									placeholder="maximum price">
							</div>


							<!-- <button class="size-113 flex-c-m fs-16 cl2 hov-cl1 trans-04">
								<i class="zmdi zmdi-search"></i>
							</button> -->
						</div>
						<c:if test="${not empty categories}">
							<div class="mtext-102 cl2 p-b-15">
								Categories
							</div>
							<div class="bor8 m-b-20">
								<select class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" form="search-form"
									id="categories" name="category">
									<c:if test="${empty param.category}">
										<option value="" disabled selected>Category</option>
									</c:if>
									<c:forEach items="${categories}" var="category">
										<option value="${category.getId()}">${category.getName()}</option>
									</c:forEach>
								</select>
							</div>
						</c:if>
						<!-- add margin buttom -->
						<button
							class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer m-b-10"
							id="resetForm" form="search-form">
							Reset
						</button>
						<button class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer"
							form="search-form">
							Search
						</button>
					</div>
				</div>
			</div>
		</div>

		<div class="row isotope-grid" id="productsDiv">
			<c:if test="${not empty products}">
				<c:forEach items="${products}" var="product">
					<div class="col-sm-6 col-md-4 col-lg-3 p-b-35 isotope-item">
						<div class="block2">
							<div class="block2-pic hov-img0">
								<img src="${product.getImagePath()}" alt="IMG-PRODUCT">
							</div>

							<div class="block2-txt flex-w flex-t p-t-14">
								<div class="block2-txt-child1 flex-col-l ">
									<a href="product?p=${product.getId()}"
										class="stext-104 cl4 hov-cl1 trans-04 js-name-b2 p-b-6">
										${product.getName()}
									</a>

									<span class="stext-105 cl3">
										${product.getPrice()}
									</span>
								</div>

								<div class="block2-txt-child2 flex-r p-t-3">
									<a href="add-to-wishlist"
										class="btn-addwish-b2 dis-block pos-relative js-addwish-b2">
										<img class="icon-heart1 dis-block trans-04" src="images/icons/icon-heart-01.png"
											alt="ICON">
										<img class="icon-heart2 dis-block trans-04 ab-t-l"
											src="images/icons/icon-heart-02.png" alt="ICON">
									</a>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</c:if>
		</div>

		<!-- Load more -->
		<c:if test="${not empty products}">
			<div class="flex-c-m flex-w w-full p-t-45">
				<button id="loadMore" class="flex-c-m stext-101 cl5 size-103 bg2 bor1 hov-btn1 p-lr-15 trans-04">
					Load More
				</button>
			</div>
		</c:if>
		<c:if test="${empty products}">
			<div class="flex-c-m flex-w w-full p-t-45">
				<h3 class="ltext-103 cl5">No books found</h3>
			</div>
		</c:if>
	</div>
</div>

<script src="https://unpkg.com/imagesloaded@5/imagesloaded.pkgd.min.js"></script>
<script>
	window.addEventListener('load', function () {
		let $grid;
		let pageNumber = 1;

		$grid = $('.isotope-grid').isotope({
			itemSelector: '.isotope-item',
			layoutMode: 'fitRows',
			percentPosition: true,
			masonry: {
				columnWidth: '.isotope-item'
			}
			// fitRows: {
			// 	gutter: '.gutter-sizer'
			// }
		});

		$('#resetForm').click(function (event) {
			event.preventDefault();
			$('#name').val('');
			$('#minPrice').val('');
			$('#maxPrice').val('');
			$('#categories').val('');
		});

		document.getElementById('loadMore').addEventListener('click', loadProducts);

		function loadProducts() {
			console.log('Loading page ' + pageNumber);
			$('#loadMore').prop('disabled', true);

			let name = $('#name').val().trim();
			let minPrice = $('#minPrice').val().trim();
			let maxPrice = $('#maxPrice').val().trim();
			// let category = $('#categories').val().trim();
			let category = $('#categories').val() ? $('#categories').val().trim() : null;

			console.log('name: ' + name);
			console.log('minPrice: ' + minPrice);
			console.log('maxPrice: ' + maxPrice);
			console.log('category: ' + category);

			$.ajax({
				url: "/next-products",
				type: "GET",
				data: {
					page: pageNumber++,
					name: name,
					minPrice: minPrice,
					maxPrice: maxPrice,
					category: category,
				},
				success: function (response) {
					console.log(response.products);

					let $newItems = $(response.products.map(function (product) {
						return `
                    <div class="col-sm-6 col-md-4 col-lg-3 p-b-35 isotope-item">
                        <div class="block2">
                            <div class="block2-pic hov-img0">
                                <img src="` + product.imagePath + `" alt="IMG-PRODUCT">
                            </div>
                            <div class="block2-txt flex-w flex-t p-t-14">
                                <div class="block2-txt-child1 flex-col-l ">
                                    <a href="product?p=` + product.id + `" class="stext-104 cl4 hov-cl1 trans-04 js-name-b2 p-b-6">
                                       ` + product.name + `
                                    </a>
                                    <span class="stext-105 cl3">
                                      ` + product.price + `
                                    </span>
                                </div>
                                <div class="block2-txt-child2 flex-r p-t-3">
                                    <a href="add-to-wishlist" class="btn-addwish-b2 dis-block pos-relative js-addwish-b2">
                                        <img class="icon-heart1 dis-block trans-04" src="images/icons/icon-heart-01.png" alt="ICON">
                                        <img class="icon-heart2 dis-block trans-04 ab-t-l" src="images/icons/icon-heart-02.png" alt="ICON">
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                `;
					}).join(''));

					// Append new items to the grid
					$grid.append($newItems);

					// Use imagesLoaded on the entire grid
					imagesLoaded($grid, function () {
						// Update Isotope layout after images are loaded
						$grid.isotope('appended', $newItems);
						$grid.isotope('layout');
					});
				},
				error: function (xhr, status, error) {
					console.error("An error occurred: " + error);
				},
				complete: function () {
					$('#loadMore').prop('disabled', false);
				}
			});
		}

		// Initial layout
		imagesLoaded($grid, function () {
			$grid.isotope('layout');
		});
	});
</script>

<%@include file="footer.jsp" %>