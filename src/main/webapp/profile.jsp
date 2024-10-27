<%@taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/header.jsp" %>
<%@include file="/notifications.jsp" %>
<section class="bg0 p-t-104 p-b-116">
    <div class="container">
        <div class="row align-items-center mb-4">
            <div class="col-md-4">
                <!-- Placeholder for potential left-aligned content -->
            </div>
            <div class="col-md-4 text-center">
                <h4 class="mtext-105 cl2">
                    Update Profile
                </h4>
            </div>
            <div class="col-md-4">
                <!-- Placeholder for potential right-aligned content -->
            </div>
        </div>
        <div class="row">
            <!-- Form with all inputs -->
            <div class="col-md-12">
                <div class="p-lr-70 p-t-55 p-b-70 p-lr-15-lg">
                    <form action="update-profile" method="post" id="updateProfile">
                        <div class="row">
                            <!-- First part of the form (Left side) -->
                            <div class="col-md-6">
                                <div class="form-group">
                                    <%@include file="form-components/name.jsp" %>
                                    <%@include file="form-components/date-of-birth.jsp" %>
                                    <%@include file="form-components/job.jsp" %>
                                    <%@include file="form-components/signup-email.jsp" %>
                                    <%@include file="form-components/password.jsp" %>
                                    <%@include file="form-components/confirm-password.jsp" %>
                                    <%@include file="form-components/credit-limit.jsp" %>
                                    <%@include file="form-components/phone-number.jsp" %>
                                </div>
                            </div>

                            <!-- Second part of the form (Right side) -->
                            <div class="col-md-6">
                                <div class="form-group">
                                    <%@include file="form-components/country.jsp" %>
                                    <%@include file="form-components/city.jsp" %>
                                    <%@include file="form-components/street.jsp" %>
                                    <%@include file="form-components/zipcode.jsp" %>
                                    <%@include file="form-components/category.jsp" %>
                                    <%@include file="form-components/update-profile-button.jsp" %>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
<script type="text/javascript">
    var emailUrl = `<c:choose>
    <c:when test="${sessionScope.buyer != null}">
        /validator/buyer-update-email
    </c:when>
    <c:when test="${sessionScope.admin != null}">
        /validator/admin-update-email
    </c:when>
</c:choose>`.trim();
    var phoneUrl = `<c:choose>
    <c:when test="${sessionScope.buyer != null}">
        /validator/buyer-update-phone-number
    </c:when>
    <c:when test="${sessionScope.admin != null}">
        /validator/admin-update-phone-number
    </c:when>
</c:choose>`.trim();
</script>

<!-- Include your external JavaScript file -->
<script src="./js/update-profile-validation.js"></script>
<%@include file="/footer.jsp" %>