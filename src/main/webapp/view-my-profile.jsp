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
                <form action="update-profile" method="post" id="updateProfileForm">
                    <h4 class="mtext-105 cl2 txt-center p-b-30">
                        My profile
                    </h4>

                    <div class="bor8 m-b-20">
                        <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" name="name"
                            placeholder="Name" value="${user.getAccount().getName()}">
                    </div>
                    <!-- birthday -->
                    <div class="bor8 m-b-20">
                        <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" name="birthday"
                            onfocus="(this.type='date')" onblur="(this.type='text')" placeholder="Birthday"
                            value="${user.getAccount().getBirthday()}">
                    </div>
                    <!-- job -->
                    <div class="bor8 m-b-20">
                        <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" name="job"
                            placeholder="Job" value="${user.getAccount().getJob()}">
                    </div>
                    <div class="bor8 m-b-20 how-pos4-parent">
                        <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" name="email"
                            placeholder="Email Address" value="${user.getAccount().getEmail()}">
                    </div>
                    <div class="bor8 m-b-20">
                        <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="password" name="password"
                            placeholder="Password">
                    </div>
                    <!-- confirm password -->
                    <div class="bor8 m-b-20">
                        <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="password" name="confirmPassword"
                            placeholder="Confirm Password">
                    </div>
                </form>
            </div>

            <div class="size-210 bor10 flex-w flex-col-m p-lr-93 p-tb-30 p-lr-15-lg w-full-md">
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="number" min="0" name="creditLimit"
                        form="updateProfileForm" placeholder="Credit Limit" value="${user.getCreditLimit()}">
                </div>
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" name="phoneNumber"
                        form="updateProfileForm" placeholder="Phone Number"
                        value="${user.getAccount().getPhoneNumber()}">
                </div>
                <div class="bor8 m-b-20">
                    <select class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" id="countries" name="country"
                        form="updateProfileForm">
                        <c:forEach items="${countries}" var="country">
                            <option value="${country}" <c:if
                                test="${country.equals(user.getAccount().getAddress().getCountry())}">
                                selected
                                </c:if>>
                                ${country}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" name="city"
                        form="updateProfileForm" placeholder="City" value="${user.getAccount().getAddress().getCity()}">
                </div>
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" name="street"
                        form="updateProfileForm" placeholder="Street"
                        value="${user.getAccount().getAddress().getStreet()}">
                </div>
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" name="zipcode"
                        form="updateProfileForm" placeholder="Zipcode"
                        value="${user.getAccount().getAddress().getZipcode()}">
                </div>
                <button class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer"
                    form="updateProfileForm">
                    Update my profile
                </button>
            </div>
        </div>
    </div>
</section>
<!-- <script>
    window.onload = function () {
        fetch('https://restcountries.com/v3.1/all')
            .then(response => response.json())
            .then(data => {
                const countryNames = data.map(country => country.name.common);
                countryNames.sort();
                console.log(countryNames);
                const countriesSelect = document.getElementById('countries');
                countryNames.forEach(countryName => {
                    const option = document.createElement('option');
                    option.value = countryName;
                    option.text = countryName;
                    countriesSelect.appendChild(option);
                });
            })
            .catch(error => console.error('Error fetching country names:', error));
    }
</script> -->

<%@include file="footer.jsp"%>