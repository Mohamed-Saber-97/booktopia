<%@include file="/header.jsp" %>

<%@include file="/notifications.jsp" %>
<style>
    .interest-bubble {
        display: inline-block;
        margin: 5px;
        position: relative;
    }

    .interest-bubble input[type="checkbox"] {
        display: none;
    }

    .interest-bubble label {
        display: inline-flex;
        align-items: center;
        justify-content: center;
        padding: 10px 20px;
        background-color: #f8f9fa;
        border: 2px solid #dee2e6;
        border-radius: 25px;
        cursor: pointer;
        transition: all 0.3s ease;
    }

    .interest-bubble input[type="checkbox"]:checked + label {
        background-color: #28a745;
        border-color: #28a745;
        color: white;
    }
</style>

<section class="bg0 p-t-104 p-b-116">
    <div class="container">
        <div class="flex-w flex-tr">
            <div class="size-210 bor10 p-lr-70 p-t-55 p-b-70 p-lr-15-lg w-full-md">
                <h4 class="mtext-105 cl2 txt-center p-b-30">
                    Buyer's profile
                </h4>
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" readonly
                           value="${tempBuyer.name()}">
                </div>
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" readonly
                           value="${tempBuyer.dob()}">
                </div>
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" readonly
                           value="${tempBuyer.job()}">
                </div>
                <div class="bor8 m-b-20 how-pos4-parent">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" readonly
                           value="${tempBuyer.email()}">
                </div>
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="number" readonly
                           value="${tempBuyer.creditLimit()}">
                </div>
            </div>
            <div class="size-210 bor10 flex-w flex-col-m p-lr-93 p-tb-30 p-lr-15-lg w-full-md">
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" readonly
                           value="${tempBuyer.phoneNumber()}">
                </div>
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" readonly
                           value="${tempBuyer.country()}">
                </div>
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" readonly
                           value="${tempBuyer.city()}">
                </div>
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" readonly
                           value="${tempBuyer.street()}">
                </div>
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" readonly
                           value="${tempBuyer.zipCode()}">
                </div>
                <div class="form-group">
                    <label><b>Interests</b></label>
                    <div class="d-flex flex-wrap">
                        <c:forEach items="${categories}" var="category">
                            <div class="interest-bubble">
                                <input type="checkbox"
                                       id="category${category.id()}"
                                       name="categories"
                                       value="${category.id()}"
                                       <c:if test="${categoryIds.contains(category.id())}">checked</c:if>>
                                <label for="category${category.id()}">${category.name()}</label>
                            </div>
                        </c:forEach>
                    </div>
                    <small id="categoriesHelp" class="form-text text-muted" style="visibility: hidden;">
                        Please select at least one category.
                    </small>
                </div>
                <form action="buyer-orders" method="get">
                    <input type="hidden" name="p" value="${tempBuyer.id()}">
                    <button class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer">
                        View Orders
                    </button>
                </form>
            </div>
        </div>
    </div>
</section>

<%@include file="/footer.jsp" %>