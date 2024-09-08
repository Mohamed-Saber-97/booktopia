<%@include file="header.jsp"%>

<c:if test="${not empty error}">
    <div class="alert alert-danger text-center" style="width: fit-content; margin: 0 auto;">
        ${error}
    </div>
</c:if>
<section class="bg0 p-t-104 p-b-116">
    <div class="container">
        <div class="flex-c-m flex-w">
            <div class="size-210 bor10 p-lr-70 p-t-55 p-b-70 p-lr-15-lg w-full-md">
                <form action="login" method="post" id="buyerLoginForm">
                    <h4 class="mtext-105 cl2 txt-center p-b-30">
                        Admin Login
                    </h4>

                    <div class="bor8 m-b-20">
                        <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="email" name="email"
                            placeholder="Email Address" required>
                    </div>
                    <div class="bor8 m-b-20">
                        <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="password" name="password"
                            placeholder="Password" required>
                    </div>

                    <button class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer"
                        form="buyerLoginForm">
                        Login
                    </button>
                </form>
            </div>
        </div>
    </div>
</section>

<%@include file="footer.jsp"%>