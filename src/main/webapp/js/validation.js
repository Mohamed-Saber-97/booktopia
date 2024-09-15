$(document).ready(function () {
    // Initialize popovers for fields
    $('[data-bs-toggle="popover"]').popover({
        placement: 'right',
        trigger: 'manual'
    });

    // Name field validation
    function validateName() {
        let isValid = true;
        let nameField = $('#nameInput');
        let nameValue = nameField.val().trim();
        let nameHelp = $('#nameHelp');

        // Clear previous validation states
        nameField.removeClass('is-invalid is-valid');
        nameField.popover('hide');
        nameHelp.css('visibility', 'hidden');

        // Validate that the field is not empty and does not exceed 100 characters
        if (nameValue === '') {
            nameField.addClass('is-invalid');
            nameField.attr('data-bs-content', 'Name cannot be empty.');
            nameField.popover('show');
            nameHelp.text('Name cannot be empty.').css('visibility', 'visible');
            isValid = false;
        } else if (nameValue.length > 100) {
            nameField.addClass('is-invalid');
            nameField.attr('data-bs-content', 'Name cannot exceed 100 characters.');
            nameField.popover('show');
            nameHelp.text('Name should not exceed 100 characters.').css('visibility', 'visible');
            isValid = false;
        } else {
            // Input is valid
            nameField.addClass('is-valid');
            nameHelp.css('visibility', 'hidden');
        }

        return isValid;
    }

    // DOB field validation
    function validateDOB() {
        let isValid = true;
        let dobField = $('#dobInput');
        let dobValue = dobField.val();
        let dobHelp = $('#dobHelp');

        // Clear previous validation states
        dobField.removeClass('is-invalid is-valid');
        dobField.popover('hide');
        dobHelp.css('visibility', 'hidden');

        // Validate the date
        if (!dobValue) {
            dobField.addClass('is-invalid');
            dobField.attr('data-bs-content', 'Date of birth cannot be empty.');
            dobField.popover('show');
            dobHelp.text('Date of birth cannot be empty.').css('visibility', 'visible');
            isValid = false;
        } else {
            let dob = new Date(dobValue);
            let today = new Date();
            let age = today.getFullYear() - dob.getFullYear();
            let m = today.getMonth() - dob.getMonth();

            // Adjust age if the birthdate has not yet occurred this year
            if (m < 0 || (m === 0 && today.getDate() < dob.getDate())) {
                age--;
            }

            if (age < 18) {
                dobField.addClass('is-invalid');
                dobField.attr('data-bs-content', 'You must be at least 18 years old.');
                dobField.popover('show');
                dobHelp.text('You must be at least 18 years old.').css('visibility', 'visible');
                isValid = false;
            } else {
                // Input is valid
                dobField.addClass('is-valid');
                dobHelp.css('visibility', 'hidden');
            }
        }

        return isValid;
    }

    // Job title validation
    function validateJob() {
        let isValid = true;
        let jobField = $('#jobInput');
        let jobValue = jobField.val().trim();
        let jobHelp = $('#jobHelp');

        // Clear previous validation states
        jobField.removeClass('is-invalid is-valid');
        jobField.popover('hide');
        jobHelp.css('visibility', 'hidden');

        // Validate that the job title is not empty
        if (jobValue === '') {
            jobField.addClass('is-invalid');
            jobField.attr('data-bs-content', 'Job title cannot be empty.');
            jobField.popover('show');
            jobHelp.text('Job title cannot be empty.').css('visibility', 'visible');
            isValid = false;
        } else {
            jobField.addClass('is-valid');
            jobHelp.css('visibility', 'hidden');
        }

        return isValid;
    }

    // Email validation function
    function validateEmail() {
        var email = $("#emailInput").val();
        var emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
        var isValid = true;

        // Clear previous validation states
        $("#emailInput").removeClass("is-invalid is-valid");
        $("#emailHelp").css("visibility", "hidden");

        // Check email format
        if (!emailRegex.test(email)) {
            $("#emailInput").addClass("is-invalid");
            $("#emailHelp").text("Invalid email format").css("visibility", "visible");
            isValid = false;
        }

        return isValid;
    }

    // Password validation function
    function validatePassword() {
        var isValid = true;
        var passwordField = $('#passwordInput');
        var confirmPasswordField = $('#confirmPasswordInput');
        var passwordValue = passwordField.val();
        var confirmPasswordValue = confirmPasswordField.val();
        var passwordHelp = $('#passwordHelp');
        var confirmPasswordHelp = $('#confirmPasswordHelp');

        // Clear previous validation states
        passwordField.removeClass('is-invalid is-valid');
        confirmPasswordField.removeClass('is-invalid is-valid');
        passwordHelp.css('visibility', 'hidden');
        confirmPasswordHelp.css('visibility', 'hidden');

        // Validate password length
        if (passwordValue.length < 6) {
            passwordField.addClass('is-invalid');
            passwordHelp.text('Password must be at least 6 characters long.').css('visibility', 'visible');
            isValid = false;
        } else {
            passwordField.addClass('is-valid');
        }

        // Validate confirm password match
        if (passwordValue !== confirmPasswordValue) {
            confirmPasswordField.addClass('is-invalid');
            confirmPasswordHelp.text('Passwords must match.').css('visibility', 'visible');
            isValid = false;
        } else if (confirmPasswordValue.length >= 6) {
            confirmPasswordField.addClass('is-valid');
        }

        return isValid;
    }
    // Phone number validation function
    function validatePhoneNumber() {
        var phoneNumber = $("#phoneNumberInput").val();
        var phoneRegex = /^01[0-25]\d{8}$/; // Assuming this is an Egyptian phone number format
        var isValid = true;

        // Clear previous validation states
        $("#phoneNumberInput").removeClass("is-invalid is-valid");
        $("#phoneNumberHelp").css({ "visibility": "hidden", "color": "" });

        // Check phone number format
        if (!phoneRegex.test(phoneNumber)) {
            $("#phoneNumberInput").addClass("is-invalid");
            $("#phoneNumberHelp").text("Invalid phone number format").css({ "visibility": "visible", "color": "red" });
            isValid = false;
        }

        return isValid;
    }

    // Credit limit validation function
    function validateCreditLimit() {
        var isValid = true;
        var creditLimitField = $('#creditLimitInput');
        var creditLimitValue = creditLimitField.val();
        var creditLimitHelp = $('#creditLimitHelp');

        // Clear previous validation states
        creditLimitField.removeClass('is-invalid is-valid');
        creditLimitHelp.css('visibility', 'hidden');

        // Validate credit limit format and value
        var creditLimitRegex = /^\d+(\.\d+)?$/;
        if (!creditLimitRegex.test(creditLimitValue) || parseFloat(creditLimitValue) < 0) {
            creditLimitField.addClass('is-invalid');
            creditLimitHelp.text('Credit limit must be a non-negative number.').css('visibility', 'visible');
            isValid = false;
        } else {
            creditLimitField.addClass('is-valid');
        }

        return isValid;
    }

    // Country validation function
    function validateCountry() {
        var isValid = true;
        var countryField = $('#countryInput');
        var countryHelp = $('#countryHelp');

        // Clear previous validation states
        countryField.removeClass('is-invalid is-valid');  // Remove previous states
        countryField.popover('hide');
        countryHelp.css('visibility', 'hidden');

        // Check if a country is selected
        if (countryField.val() === '' || countryField.val() === null) {
            countryField.addClass('is-invalid');  // Add invalid state
            countryField.attr('data-bs-content', 'Please select a country.');
            countryField.popover('show');
            countryHelp.text('Please select a country.').css('visibility', 'visible');
            isValid = false;
        } else {
            // Country selection is valid
            countryField.addClass('is-valid');  // Add valid state (green border)
            countryHelp.css('visibility', 'hidden');
        }

        return isValid;
    }

    // City validation function
    function validateCity() {
        console.log('Validating city...');
        var isValid = true;
        var cityField = $('#cityInput');
        var cityValue = cityField.val().trim();
        var cityHelp = $('#cityHelp');

        // Clear previous validation states
        cityField.removeClass('is-invalid is-valid');
        cityField.popover('hide');
        cityHelp.css('visibility', 'hidden');

        // Validate that the field is not empty and does not exceed 100 characters
        if (cityValue === '') {
            console.log('City is empty');
            cityField.addClass('is-invalid');
            cityField.attr('data-bs-content', 'City cannot be empty.');
            cityField.popover('show');
            cityHelp.text('City cannot be empty.').css('visibility', 'visible');
            isValid = false;
        } else if (cityValue.length > 100) {
            console.log('City is too long');
            cityField.addClass('is-invalid');
            cityField.attr('data-bs-content', 'City should not exceed 100 characters.');
            cityField.popover('show');
            cityHelp.text('City should not exceed 100 characters.').css('visibility', 'visible');
            isValid = false;
        } else {
            cityField.addClass('is-valid');
            cityHelp.css('visibility', 'hidden');
        }

        return isValid;
    }

    // Street validation function
    function validateStreet() {
        var isValid = true;
        var streetField = $('#streetInput');
        var streetValue = streetField.val().trim();
        var streetHelp = $('#streetHelp');

        // Clear previous validation states
        streetField.removeClass('is-invalid is-valid');  // Remove previous states
        streetField.popover('hide');
        streetHelp.css('visibility', 'hidden');

        // Validate that the field is not empty and does not exceed 255 characters
        if (streetValue === '') {
            streetField.addClass('is-invalid');
            streetField.attr('data-bs-content', 'Street cannot be empty.');
            streetField.popover('show');
            streetHelp.text('Street cannot be empty.').css('visibility', 'visible');
            isValid = false;
        } else if (streetValue.length > 255) {
            streetField.addClass('is-invalid');
            streetField.attr('data-bs-content', 'Street should not exceed 255 characters.');
            streetField.popover('show');
            streetHelp.text('Street should not exceed 255 characters.').css('visibility', 'visible');
            isValid = false;
        } else {
            // Input is valid
            streetField.addClass('is-valid');  // Add valid state
            streetHelp.css('visibility', 'hidden');
        }

        return isValid;
    }

    // ZIP Code validation function
    function validateZipCode() {
        var isValid = true;
        var zipCodeField = $('#zipCodeInput');
        var zipCodeValue = zipCodeField.val().trim();
        var zipCodeHelp = $('#zipCodeHelp');

        // Clear previous validation states
        zipCodeField.removeClass('is-invalid is-valid');  // Remove previous states
        zipCodeField.popover('hide');
        zipCodeHelp.css('visibility', 'hidden');

        // Validate that the field is not empty and does not exceed 15 characters
        if (zipCodeValue === '') {
            zipCodeField.addClass('is-invalid');
            zipCodeField.attr('data-bs-content', 'ZIP Code cannot be empty.');
            zipCodeField.popover('show');
            zipCodeHelp.text('ZIP Code cannot be empty.').css('visibility', 'visible');
            isValid = false;
        } else if (zipCodeValue.length > 15) {
            zipCodeField.addClass('is-invalid');
            zipCodeField.attr('data-bs-content', 'ZIP Code should not exceed 15 characters.');
            zipCodeField.popover('show');
            zipCodeHelp.text('ZIP Code should not exceed 15 characters.').css('visibility', 'visible');
            isValid = false;
        } else {
            // Input is valid
            zipCodeField.addClass('is-valid');  // Add valid state
            zipCodeHelp.css('visibility', 'hidden');
        }

        return isValid;
    }
    function validateCategories() {
        var isValid = true;
        var categoriesField = $('.interest-bubble input[type="checkbox"]');
        var categoriesHelp = $('#categoriesHelp');

        // Clear previous validation states
        categoriesHelp.css('visibility', 'hidden');

        // Check if at least one category is selected
        if (!categoriesField.is(':checked')) {
            categoriesHelp.text('Please select at least one category.').css('visibility', 'visible');
            isValid = false;
        } else {
            // All fields are valid
            categoriesHelp.css('visibility', 'hidden');
        }

        return isValid;
    }

    // Array of validation functions
    const validations = [];
    validations.push(validateName);
    validations.push(validateDOB);
    validations.push(validateJob);
    validations.push(validateEmail);
    validations.push(validatePassword);
    validations.push(validatePhoneNumber);
    validations.push(validateCreditLimit);
    validations.push(validateCountry);
    validations.push(validateCity);
    validations.push(validateStreet);
    validations.push(validateZipCode);
    validations.push(validateCategories);



    // Validate each input field on change
    $('#nameInput').on('input', function () {
        validateName();
    });
    $('#dobInput').on('change', function () {
        validateDOB();
    });
    $('#jobInput').on('input', function () {
        validateJob();
    });
    $('#passwordInput, #confirmPasswordInput').on('input blur', function () {
        validatePassword();
    });

    $('#emailInput').on('blur', function () {
        validateEmail();

        // Proceed with AJAX if format is valid
        if (validateEmail()) {
            $.ajax({
                url: "/check-unique-email",
                method: "POST",
                data: { email: $("#emailInput").val() },
                success: function (response) {
                    if (response === "true") {
                        $("#emailInput").removeClass("is-invalid").addClass("is-valid");
                        $("#emailHelp").text("Email available").css({ "color": "green", "visibility": "visible" });
                    } else {
                        $("#emailInput").addClass("is-invalid").removeClass("is-valid");
                        $("#emailHelp").text("Email already exists").css({ "color": "red", "visibility": "visible" });
                    }
                },
                error: function (error) {
                    console.error("Error checking email:", error);
                    $("#emailInput").addClass("is-invalid").removeClass("is-valid");
                    $("#emailHelp").text("Error checking email. Please try again later.").css({ "color": "red", "visibility": "visible" });
                }
            });
        }
    });

    // Phone number input blur validation
    $("#phoneNumberInput").on("blur", function () {

        // Proceed with AJAX if format is valid
        if (validatePhoneNumber()) {
            // AJAX call to check unique phone number
            $.ajax({
                url: "/check-phone-number",
                method: "POST",
                data: { phoneNumber: $("#phoneNumberInput").val() },
                success: function (response) {
                    if (response === "true") {
                        // Phone number is available
                        $("#phoneNumberInput").removeClass("is-invalid").addClass("is-valid");
                        $("#phoneNumberHelp").text("Phone number available").css({ "visibility": "visible", "color": "green" });
                    } else if (response === "Phone number already exists") {
                        // Phone number already exists
                        $("#phoneNumberInput").addClass("is-invalid").removeClass("is-valid");
                        $("#phoneNumberHelp").text("Phone number already exists").css({ "visibility": "visible", "color": "red" });
                    } else {
                        // Handle any other error cases
                        $("#phoneNumberInput").addClass("is-invalid").removeClass("is-valid");
                        $("#phoneNumberHelp").text("Error checking phone number. Please try again later.").css({ "visibility": "visible", "color": "red" });
                    }
                },
                error: function (error) {
                    console.error("Error checking phone number:", error);
                    $("#phoneNumberInput").addClass("is-invalid").removeClass("is-valid");
                    $("#phoneNumberHelp").text("Error checking phone number. Please try again later.").css({ "visibility": "visible", "color": "red" });
                }
            });
        }
    });
    $('#creditLimitInput').on('input blur', function () {
        validateCreditLimit();
    });

    // Trigger country validation on change
    $('#countryInput').on('change', function () {
        validateCountry();
    });

    // Trigger city validation on input change
    $('#cityInput').on('input', function () {
        validateCity();
    });
    // Validate on street input change
    $('#streetInput').on('input', function () {
        validateStreet();
    });
    // Validate on input change
    $('#zipCodeInput').on('input', function () {
        validateZipCode();
    });
    // Validate on form submit
    $('#signupForm').on('submit', function (event) {
        let isValid = true;

        // Loop through all validations
        validations.forEach(function (validate) {
            if (!validate()) {
                isValid = false;
            }
        });

        // Prevent form submission if any validation fails
        if (!isValid) {
            event.preventDefault();
        }
    });
});
