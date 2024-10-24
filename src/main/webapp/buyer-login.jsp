<%@include file="/header.jsp" %>
<%@include file="/notifications.jsp" %>
<section class="bg0 p-t-104 p-b-116">
    <div class="container">
        <div class="flex-c-m flex-w">
            <div class="size-210 bor10 p-lr-70 p-t-55 p-b-70 p-lr-15-lg w-full-md">
                <form action="login" method="post" id="buyerLoginForm">
                    <h4 class="mtext-105 cl2 txt-center p-b-30">
                        Login
                    </h4>
                    <%@include file="/form-components/login-email.jsp" %>

                    <%@include file="/form-components/password.jsp" %>

                    <%@include file="/form-components/login-button.jsp" %>
                </form>
            </div>
        </div>
    </div>
    <script src="/js/login-validation.js"></script>
</section>

<%@include file="/footer.jsp" %>