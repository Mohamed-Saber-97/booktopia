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
                <form action="signup" method="post" id="signupForm">
                    <h4 class="mtext-105 cl2 txt-center p-b-30">
                        Sign up
                    </h4>

                    <div class="bor8 m-b-20">
                        <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" name="name"
                            placeholder="Name">
                    </div>
                    <!-- birthday -->
                    <div class="bor8 m-b-20">
                        <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" name="birthday"
                            onfocus="(this.type='date')" onblur="(this.type='text')" placeholder="Birthday">
                    </div>
                    <!-- job -->
                    <div class="bor8 m-b-20">
                        <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" name="job"
                            placeholder="Job">
                    </div>
                    <div class="bor8 m-b-20 how-pos4-parent">
                        <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" name="email"
                            placeholder="Email Address">
                    </div>
                    <!-- password -->
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
                <!-- criditLimit -->
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="number" min="0" name="creditLimit"
                        form="signupForm" placeholder="Credit Limit">
                </div>
                <!-- phoneNumber -->
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" name="phoneNumber"
                        form="signupForm" placeholder="Phone Number">
                </div>
                <!-- country (select tag and will be filled by js) -->
                <div class="bor8 m-b-20">
                    <select class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" id="countries" name="country"
                        form="signupForm">
                        <option value="" disabled selected>Country</option>
                    </select>
                </div>
                <!-- city -->
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" name="city" form="signupForm"
                        placeholder="City">
                </div>
                <!-- street -->
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" name="street" form="signupForm"
                        placeholder="Street">
                </div>
                <!-- zipcode -->
                <div class="bor8 m-b-20">
                    <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" name="zipcode"
                        form="signupForm" placeholder="Zipcode">
                </div>

                <button class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer"
                    form="signupForm">
                    Submit
                </button>
            </div>
        </div>
    </div>
</section>
<script>
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
</script>
<%@include file="footer.jsp"%>