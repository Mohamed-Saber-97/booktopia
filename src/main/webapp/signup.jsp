<%@include file="header.jsp" %>
    <c:if test="${not empty error}">
        <!-- <div class="alert alert-danger text-center" style="width: fit-content; margin: 0 auto;">
            ${error}
        </div> -->
    </c:if>
    <section class="bg0 p-t-104 p-b-116">
        <div class="container">
            <div class="flex-w flex-tr">


                <form action="signup" method="post" id="signupForm">
                    <h4 class="mtext-105 cl2 txt-center p-b-30" style="text-align: left;">
                        Sign up
                    </h4>
                    <div style="display: flex;flex-direction: row; align-items: left;">
                        <div class="size-210 bor10 p-lr-70 p-t-55 p-b-70 p-lr-15-lg w-full-md">


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
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="password"
                                    name="confirmPassword" placeholder="Confirm Password">
                            </div>
                        </div>

                        <div class="size-210 bor10 flex-w flex-col-m p-lr-93 p-tb-30 p-lr-15-lg w-full-md">
                            <!-- criditLimit -->
                            <div class="bor8 m-b-20">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="number" min="0"
                                    name="creditLimit" form="signupForm" placeholder="Credit Limit">
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
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" name="city"
                                    form="signupForm" placeholder="City">
                            </div>
                            <!-- street -->
                            <div class="bor8 m-b-20">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" name="street"
                                    form="signupForm" placeholder="Street">
                            </div>
                            <!-- zipcode -->
                            <div class="bor8 m-b-20">
                                <input class="stext-111 cl2 plh3 size-116 p-l-62 p-r-30" type="text" name="zipcode"
                                    form="signupForm" placeholder="Zipcode">
                            </div>


                        </div>
                    </div>
                </form>

                <button class="flex-c-m stext-101 cl0 size-121 bg3 bor1 hov-btn3 p-lr-15 trans-04 pointer"
                    form="signupForm">
                    Submit
                </button>



            </div>
        </div>
    </section>
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const form = document.querySelector('#signupForm');
            const fields = {
                email: form.querySelector('input[name="email"]'),
                name: form.querySelector('input[name="name"]'),
                birthday: form.querySelector('input[name="birthday"]'),
                password: form.querySelector('input[name="password"]'),
                confirmPassword: form.querySelector('input[name="confirmPassword"]'),
                job: form.querySelector('input[name="job"]'),
                phoneNumber: form.querySelector('input[name="phoneNumber"]'),
                credit: form.querySelector('input[name="creditLimit"]'),
                street: form.querySelector('input[name="street"]'),
                city: form.querySelector('input[name="city"]'),
                zipcode: form.querySelector('input[name="zipcode"]'),
            };

            // Attach blur event listeners for real-time validation
            for (const [key, input] of Object.entries(fields)) {
                input.addEventListener('blur', () => validateField(key));
            }

            form.addEventListener('submit', function (event) {
                let valid = true;

                // Clear previous error messages
                form.querySelectorAll('.error-message').forEach(el => el.remove());

                // Validate all fields on submit
                for (const key in fields) {
                    if (!validateField(key)) {
                        valid = false;
                    }
                }

                if (!valid) {
                    event.preventDefault();
                }
            });

            function validateField(fieldName) {
                const input = fields[fieldName];
                let valid = true;
                let message = '';

                switch (fieldName) {
                    case 'email':
                        if (!validateEmail(input.value)) {
                            message = 'Please enter a valid email address.';
                            valid = false;
                        }
                        break;
                    case 'name':
                        if (input.value.trim() === '') {
                            message = 'Full name is required.';
                            valid = false;
                        }
                        if (input.value.length > 100) {
                            message = 'Full name is too long.';
                            valid = false;
                        }
                        break;
                    case 'birthday':
                        if (input.value.trim() === '') {
                            message = 'Date of birth is required.';
                            valid = false;
                        }
                        if (validateAge(input.value) < 6574) {
                            message = 'User must be above 18.';
                            valid = false;
                        }
                        break;
                    case 'password':
                        if (input.value.length < 6) {
                            message = 'Password must be at least 6 characters long.';
                            valid = false;
                        }
                        if (input.value.length >= 6 && !validatePass(input.value)) {
                            message = 'Password must contain at least one digit.';
                            valid = false;
                        }
                        break;
                    case 'confirmPassword':
                        if (input.value !== fields.password.value) {
                            message = 'Passwords do not match.';
                            valid = false;
                        }
                        break;
                    case 'job':
                        if (input.value.trim() === '') {
                            message = 'Job is required.';
                            valid = false;
                        }
                        break;
                    case 'phoneNumber':
                        if (!validatePhone(input.value)) {
                            message = 'Please enter a valid phone number.';
                            valid = false;
                        }
                        break;
                    case 'street':
                        if (input.value.trim() === '') {
                            message = 'Street is required.';
                            valid = false;
                        }
                        break;
                    case 'city':
                        if (input.value.trim() === '') {
                            message = 'City is required.';
                            valid = false;
                        }
                        break;
                    case 'zipcode':
                        if (input.value.trim() === '' || !validateZip(input.value)) {
                            message = 'Valid zipcode is required.';
                            valid = false;
                        }
                        break;
                    case 'credit':
                        if (String.toString(input.value).trim() === '' ) {
                            message = 'Valid Credit Limit is required.';
                            valid = false;
                        }
                        break;
                }

                if (!valid) {
                    showError(input, message);
                } else {
                    clearError(input);
                }

                return valid;
            }

            function validateEmail(email) {
                const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                return emailPattern.test(email);
            }

            function validatePass(password) {
                const PassPattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;
                return PassPattern.test(password);
            }

            function validatePhone(phone) {
                const phonePattern = /^0?1[0125][0-9]{8}$/; 
                return phonePattern.test(phone);
            }
            function validateZip(zipCode) {
                const zipPattern = /^\d{5}(-\d{4})?$|^[A-Za-z]\d[A-Za-z] \d[A-Za-z]\d$/; 
                return zipPattern.test(zipCode);
            }


            
            function validateAge(age) {
                const date = new Date(age);
                const now = new Date();
                const diffTime = Math.abs(now - date);
                const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24));
                return diffDays;
            }
            
            //usless piece of function
            // function validateLimit(limit) {
            //     const creditPattern = /^\\d+(\\.\\d+)?$/;
            //     //return creditPattern.test(limit);
            //     return true;
            // }


            function showError(input, message) {
                // Ensure previous error message is removed
                clearError(input);

                const error = document.createElement('div');
                error.className = 'error-message';
                error.style.color = 'red';
                error.style.marginTop = '5px';
                error.textContent = message;
                input.parentElement.appendChild(error);
            }

            function clearError(input) {
                const existingError = input.parentElement.querySelector('.error-message');
                if (existingError) {
                    existingError.remove();
                }
            }
        });

        window.onload = function () {
            // Fetch and populate country dropdown
            fetch('https://restcountries.com/v3.1/all')
                .then(response => response.json())
                .then(data => {
                    const countryNames = data.map(country => country.name.common).sort();
                    const countriesSelect = document.getElementById('countries');
                    countryNames.forEach(countryName => {
                        const option = document.createElement('option');
                        option.value = countryName;
                        option.text = countryName;
                        countriesSelect.appendChild(option);
                    });
                })
                .catch(error => console.error('Error fetching country names:', error));
        };
    </script>

    <%@include file="footer.jsp" %>