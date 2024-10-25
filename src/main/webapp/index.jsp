<%@include file="/header.jsp" %>


<!-- Slider -->
<section class="section-slide">
    <div class="wrap-slick1">
        <div class="slick1">
            <div class="item-slick1" style="background-image: url(images/slide2.png);">
                <div class="container h-full">
                    <div class="flex-col-l-m h-full p-t-100 p-b-30 respon5">
                        <div class="layer-slick1 animated visible-false" data-appear="fadeInDown" data-delay="0">
							<span class="ltext-101 cl2 respon2">
								Unleash your mind's power
							</span>
                        </div>

                        <div class="layer-slick1 animated visible-false" data-appear="fadeInUp" data-delay="800">
                            <h2 class="ltext-201 cl2 p-t-19 p-b-43 respon1">
                                NEW EDITOR'S CHOICE
                            </h2>
                        </div>

                        <div class="layer-slick1 animated visible-false" data-appear="zoomIn" data-delay="1600">
                            <a href="products"
                               class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04">
                                Shop Now
                            </a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="item-slick1" style="background-image: url(images/slide1.png);">
                <div class="container h-full">
                    <div class="flex-col-l-m h-full p-t-100 p-b-30 respon5">
                        <div class="layer-slick1 animated visible-false" data-appear="rollIn" data-delay="0">
							<span class="ltext-101 cl2 respon2">
								Fulfill your horror needs
							</span>
                        </div>

                        <div class="layer-slick1 animated visible-false" data-appear="lightSpeedIn" data-delay="800">
                            <h2 class="ltext-201 cl2 p-t-19 p-b-43 respon1">
                                DEEP DARKNESS
                            </h2>
                        </div>

                        <div class="layer-slick1 animated visible-false" data-appear="slideInUp" data-delay="1600">
                            <a href="products"
                               class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04">
                                Shop Now
                            </a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="item-slick1" style="background-image: url(images/slide3.png);">
                <div class="container h-full">
                    <div class="flex-col-l-m h-full p-t-100 p-b-30 respon5">
                        <div class="layer-slick1 animated visible-false" data-appear="rotateInDownLeft" data-delay="0">
							<span class="ltext-101 cl2 respon2">
								Harry Potter
							</span>
                        </div>

                        <div class="layer-slick1 animated visible-false" data-appear="rotateInUpRight" data-delay="800">
                            <h2 class="ltext-201 cl2 p-t-19 p-b-43 respon1">
                                New series
                            </h2>
                        </div>

                        <div class="layer-slick1 animated visible-false" data-appear="rotateIn" data-delay="1600">
                            <a href="products"
                               class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04">
                                Shop Now
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>


<!-- Product -->
<section class="bg0 p-t-23 p-b-140" id="all-books">
    <div class="container">
        <div class="p-b-10 mb-3 mb-md-4 mb-lg-5">
            <h3 class="ltext-103 cl5">
                Discover awesome books
            </h3>
        </div>

        <div class="row isotope-grid">
            <c:if test="${not empty interests}">
                <c:forEach items="${interests}" var="interest">
                    <div class="col-sm-6 col-md-4 col-lg-3 p-b-35 isotope-item ${interest.categoryName()}">
                        <div class="block2">
                            <div class="block2-pic hov-img0">
                                <img src="${interest.imagePath()}" alt="IMG-PRODUCT">
                            </div>

                            <div class="block2-txt flex-w flex-t p-t-14">
                                <div class="block2-txt-child1 flex-col-l ">
                                    <a href="product?p=${interest.id()}"
                                       class="stext-104 cl4 hov-cl1 trans-04 js-name-b2 p-b-6">
                                            ${interest.name()}
                                    </a>

                                    <span class="stext-105 cl3">
                                            ${interest.price()}
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
        </div>

        <!-- Load more -->
        <div class="flex-c-m flex-w w-full p-t-45">
            <a href="products" class="flex-c-m stext-101 cl5 size-103 bg2 bor1 hov-btn1 p-lr-15 trans-04">
                Load More
            </a>
        </div>
    </div>
</section>

<%@include file="footer.jsp" %>