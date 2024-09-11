<%@include file="header.jsp"%>

<c:if test="${not empty error}">
    <div class="alert alert-danger text-center" style="width: fit-content; margin: 0 auto;">
        ${error}
    </div>
</c:if>
<section class="bg0 p-t-104 p-b-116">
    <div class="container">
        <div class="flex-w flex-tr">
            <div class="size-210 bor10 p-lr-70 p-t-55 p-b-70 p-lr-15-lg w-full-md">
                <h4 class="mtext-105 cl2 txt-center p-b-30">
                    Buyer's profile
                </h4>
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" readonly
                        value="${tempBuyer.getAccount().getName()}">
                </div>
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" readonly
                        value="${tempBuyer.getAccount().getBirthday()}">
                </div>
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" readonly
                        value="${tempBuyer.getAccount().getJob()}">
                </div>
                <div class="bor8 m-b-20 how-pos4-parent">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" readonly
                        value="${tempBuyer.getAccount().getEmail()}">
                </div>
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="number" readonly
                        value="${tempBuyer.getCreditLimit()}">
                </div>
            </div>
            <div class="size-210 bor10 flex-w flex-col-m p-lr-93 p-tb-30 p-lr-15-lg w-full-md">
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" readonly
                        value="${tempBuyer.getAccount().getPhoneNumber()}">
                </div>
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" readonly
                        value="${tempBuyer.getAccount().getAddress().getCountry()}">
                </div>
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" readonly
                        value="${tempBuyer.getAccount().getAddress().getCity()}">
                </div>
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" readonly
                        value="${tempBuyer.getAccount().getAddress().getStreet()}">
                </div>
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" readonly
                        value="${tempBuyer.getAccount().getAddress().getZipcode()}">
                </div>
                <form action="buyer-orders" method="get">
                    <input type="hidden" name="p" value="${tempBuyer.getId()}">
                    <button class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer">
                        View Orders
                    </button>
                </form>
            </div>
        </div>
    </div>
</section>

<%@include file="footer.jsp"%>