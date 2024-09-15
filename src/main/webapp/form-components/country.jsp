<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<style>
    select.form-control:not([size]):not([multiple]) {
        height: calc(3.334rem);
    }
</style>

<div class="form-group">
    <select class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30 form-control" name="country" id="countryInput"
            data-bs-toggle="popover" data-bs-trigger="manual">
        <option value="" disabled selected>Country</option>
        <c:forEach items="${countries}" var="country">
            <option value="${country}"
                    <c:if
                            test="${country.equals(user.getAccount().getAddress().getCountry())}">
                        selected
                    </c:if>>
                    ${country}
            </option>
        </c:forEach>
    </select>
    <small id="countryHelp" class="form-text text-muted" style="visibility: hidden;">Please select a country.</small>
</div>