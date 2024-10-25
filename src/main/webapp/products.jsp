<%@include file="header.jsp" %>

<style>
    /* Adjust product image container size */
    .block2 {
        width: 100%;
        max-width: 25rem; /* Adjust to control maximum width */
        margin: 0 auto; /* Center the product block */
    }

    /* Image container size */
    .block2-pic {
        height: 300px;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .block2-pic img {
        max-height: 100%;
        object-fit: contain;
    }

    /* Center the text and adjust padding */
    .block2-txt {
        text-align: center;
        padding: 10px 0;
    }

    /* Center the product name and price */
    .block2-txt-child1 {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
    }

    .block2-txt-child1 a {
        font-size: 1.1rem;
        color: #333;
        text-decoration: none;
    }

    .block2-txt-child1 span {
        font-size: 1.2rem;
        font-weight: bold;
        color: #555;
    }

    /* Adjust column size for products */
    .col-6, .col-md-4, .col-lg-3 {
        max-width: 280px; /* Adjust this value to control product container width */
        margin: auto;
    }
</style>


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
                                        <option value="${category.id()}">${category.name()}</option>
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
                    <div class="col-6 col-md-4 col-lg-3 p-b-35 isotope-item">
                        <div class="block2">
                            <div class="block2-pic hov-img0">
                                <img src="${product.imagePath()}" alt="IMG-PRODUCT">
                            </div>

                            <div class="block2-txt flex-w flex-t p-t-14">
                                <div class="block2-txt-child1 flex-col-l ">
                                    <a href="product?p=${product.id()}"
                                       class="stext-104 cl4 hov-cl1 trans-04 js-name-b2 p-b-6">
                                            ${product.name()}
                                    </a>

                                    <span class="stext-105 cl3">$
											${product.price()}
									</span>
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
            $('#loadMore').prop('disabled', true);

            let name = $('#name').val().trim();
            let minPrice = $('#minPrice').val().trim();
            let maxPrice = $('#maxPrice').val().trim();
            // let category = $('#categories').val().trim();
            let category = $('#categories').val() ? $('#categories').val().trim() : '';

            console.log('name: ' + name);
            console.log('minPrice: ' + minPrice);
            console.log('maxPrice: ' + maxPrice);
            console.log('category: ' + category);

            $.ajax({
                url: "/api/products/paging?name=" + name + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice + "&category=" + category,
                type: "GET",
                data: {
                    pageNumber: pageNumber++,
                },
                success: function (response) {
                    console.log(response);

                    let $newItems = $(response.map(function (product) {
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
                                      $ ` + product.price + `
                                    </span>
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