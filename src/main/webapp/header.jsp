<!DOCTYPE html>
<html lang="en">
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@include file="/head.jsp" %>
<body class="animsition">
<!-- Header -->
<header class="${pageContext.request.requestURI eq '/products.jsp' ? 'header-v4' : '' }">
    <!-- Header desktop -->
    <div class="container-menu-desktop">
        <!-- Topbar -->
        <div class="top-bar">
            <div class="content-topbar flex-sb-m h-full container">
                <div class="left-top-bar">
                    Your only way to get enlightened
                </div>

                <div class="right-top-bar flex-w h-full">
                    <c:if test="${sessionScope.user != null}">
                        <a href="profile" class="flex-c-m trans-04 p-lr-25">
                            My Account
                        </a>
                    </c:if>
                    <c:if test="${sessionScope.buyer != null}">
                        <a href="orders" class="flex-c-m trans-04 p-lr-25">
                            Orders
                        </a>
                    </c:if>
                    <c:if test="${sessionScope.admin != null}">
                        <a href="books" class="flex-c-m trans-04 p-lr-25">
                            Manage books
                        </a>
                        <a href="categories" class="flex-c-m trans-04 p-lr-25">
                            Manage categories
                        </a>
                        <a href="buyers" class="flex-c-m trans-04 p-lr-25">
                            View buyers
                        </a>
                    </c:if>
                    <c:if test="${sessionScope.user == null}">
                        <a href="/buyers/signup" class="flex-c-m trans-04 p-lr-25">
                            Sign up
                        </a>
                        <a href="/buyers/login" class="flex-c-m trans-04 p-lr-25">
                            Login
                        </a>
                    </c:if>
                    <c:if test="${sessionScope.user != null}">
                        <a href="logout" class="flex-c-m trans-04 p-lr-25">
                            Logout
                        </a>
                    </c:if>
                </div>
            </div>
        </div>

        <div class="wrap-menu-desktop">
            <nav class="limiter-menu-desktop container">

                <!-- Logo desktop -->
                <a href="/" class="logo">
                    <img src="/images/icons/booktopia-logo.gif" alt="IMG-LOGO" style="max-height: 250%">
                </a>

                <!-- Menu desktop -->
                <div class="menu-desktop">
                    <ul class="main-menu">
                        <li>
                            <a href="products">All books</a>
                        </li>
                    </ul>
                </div>

                <!-- Icon header -->
                <div class="wrap-icon-header flex-w flex-r-m">
                    <c:if test="${sessionScope.buyer != null}">
                        <a href="cart"
                           class="dis-block icon-header-item cl2 hov-cl1 trans-04 p-l-22 p-r-11 icon-header-noti cart"
                           data-notify="${sessionScope.user.getCart().size()}">
                            <i class="zmdi zmdi-shopping-cart"></i>
                        </a>
                        <a href="wishlist"
                           class="dis-block icon-header-item cl2 hov-cl1 trans-04 p-l-22 p-r-11 icon-header-noti wishlist"
                           data-notify="${sessionScope.user.getWishlist().size()}">
                            <i class="zmdi zmdi-favorite-outline"></i>
                        </a>
                    </c:if>
                </div>
            </nav>
        </div>
    </div>

    <!-- Header Mobile -->
    <div class="wrap-header-mobile">
        <!-- Logo moblie -->
        <div class="logo-mobile">
            <a href="/"><img src="/images/icons/booktopia-logo.gif" alt="IMG-LOGO" style="max-height: 500%"></a>
        </div>

        <!-- Icon header -->
        <div class="wrap-icon-header flex-w flex-r-m m-r-15">
            <div class="icon-header-item cl2 hov-cl1 trans-04 p-r-11 js-show-modal-search">
                <i class="zmdi zmdi-search"></i>
            </div>

            <c:if test="${sessionScope.buyer != null}">
                <a href="cart"
                   class="dis-block icon-header-item cl2 hov-cl1 trans-04 p-r-11 p-l-10 icon-header-noti cart"
                   data-notify="${sessionScope.user.getCart().size()}">
                    <i class="zmdi zmdi-shopping-cart"></i>
                </a>

                <a href="wishlist"
                   class="dis-block icon-header-item cl2 hov-cl1 trans-04 p-r-11 p-l-10 icon-header-noti wishlist"
                   data-notify="${sessionScope.user.getWishlist().size()}">
                    <i class="zmdi zmdi-favorite-outline"></i>
                </a>
            </c:if>
        </div>

        <!-- Button show menu -->
        <div class="btn-show-menu-mobile hamburger hamburger--squeeze">
                <span class="hamburger-box">
                    <span class="hamburger-inner"></span>
                </span>
        </div>
    </div>


    <!-- Menu Mobile -->
    <div class="menu-mobile">
        <ul class="topbar-mobile">
            <li>
                <div class="left-top-bar">
                    Your only way to get enlightened
                </div>
            </li>

            <li>
                <div class="right-top-bar flex-w h-full">
                    <c:if test="${sessionScope.user != null}">
                        <a href="profile" class="flex-c-m p-lr-10 trans-04">
                            My Account
                        </a>
                    </c:if>
                    <c:if test="${sessionScope.buyer != null}">
                        <a href="orders" class="flex-c-m p-lr-10 trans-04">
                            Orders
                        </a>
                    </c:if>
                    <c:if test="${sessionScope.admin != null}">
                        <a href="books" class="flex-c-m p-lr-10 trans-04">
                            Manage books
                        </a>
                        <a href="categories" class="flex-c-m p-lr-10 trans-04">
                            Manage categories
                        </a>
                        <a href="buyers" class="flex-c-m p-lr-10 trans-04">
                            View buyers
                        </a>
                    </c:if>
                    <c:if test="${sessionScope.user == null}">
                        <a href="/buyers/signup" class="flex-c-m p-lr-10 trans-04">
                            Sign up
                        </a>
                        <a href="/buyers/login" class="flex-c-m p-lr-10 trans-04">
                            Login
                        </a>
                    </c:if>
                </div>
            </li>
        </ul>

        <ul class="main-menu-m">
            <li>
                <a href="products">All books</a>
            </li>

        </ul>
    </div>

    <!-- Modal Search -->
    <div class="modal-search-header flex-c-m trans-04 js-hide-modal-search">
        <div class="container-search-header">
            <button class="flex-c-m btn-hide-modal-search trans-04 js-hide-modal-search">
                <img src="/images/icons/icon-close2.png" alt="CLOSE">
            </button>

            <form class="wrap-search-header flex-w p-l-15">
                <button class="flex-c-m trans-04">
                    <i class="zmdi zmdi-search"></i>
                </button>
                <input class="plh3" type="text" name="search" placeholder="Search...">
            </form>
        </div>
    </div>
    <%@include file="/notifications.jsp" %>
</header>